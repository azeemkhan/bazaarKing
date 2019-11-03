package com.tresshop.engine.services.likeandcomments.impl;

import com.tresshop.engine.base.exception.ExceptionResponse;
import com.tresshop.engine.client.likeandcomments.*;
import com.tresshop.engine.services.likeandcomments.LikeAndCommentsService;
import com.tresshop.engine.services.mapper.*;
import com.tresshop.engine.storage.entity.*;
import com.tresshop.engine.storage.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LikeAndCommentsServiceImpl implements LikeAndCommentsService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    CommentsRepository commentsRepository;
    @Autowired
    CommentsAuditRepository commentsAuditRepository;
    @Autowired
    RatingsRepository ratingsRepository;
    @Autowired
    WatchLaterRepository watchLaterRepository;
    @Autowired
    LikesMapper likesMapper;
    @Autowired
    CommentsAuditMapper commentsAuditMapper;
    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    RatingsMapper ratingsMapper;
    @Autowired
    WatchLaterMapper watchLaterMapper;

    @Override
    public GenericResponse like(LikesRequest likesRequest) {

        LikesEntity likesEntity = likesMapper.populate(likesRequest);

        try{
            if (likesRequest.isLiked()){
                likesRepository.save(likesEntity);
                log.info("Liked by : {}", likesEntity.getCustomerId());
            } else{
                likesRepository.delete(likesEntity);
                log.info("Undo Like by : {}", likesEntity.getCustomerId());
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
        return replyGeneric();
    }

    @Override
    public LikesCountResponse totalLikeCount(LikeCountRequest likeCountRequest) {
        boolean isLiked = false;
        try {
            Optional<LikesEntity> likesEntity = likesRepository
                    .findUserLiked(likeCountRequest.getItemLikedId()
                            ,likeCountRequest.getItemType()
                            ,likeCountRequest.getCustomerId());
            if (likesEntity.isPresent()) isLiked = true;
            Optional<Integer> totalLikes = likesRepository
                    .totalLikeCount(likeCountRequest.getItemLikedId()
                            , likeCountRequest.getItemType());
            LikesCountResponse likesCountResponse = new LikesCountResponse();
            likesCountResponse.setLiked(isLiked);
            likesCountResponse.setTotalCount(totalLikes.get().intValue());
            return likesCountResponse;
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @Override
    public LikesResponse getLikedUserIds(LikeCountRequest likeCountRequest) {
        try {
            Optional<List<String>> userIds = likesRepository
                    .findAllUsersLiked(likeCountRequest.getItemLikedId()
                    ,likeCountRequest.getItemType());
            LikesResponse likesResponse = new LikesResponse();
            likesResponse.setCustomers(userIds.get());
            return likesResponse;
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @Override
    public CommentsResponse comment(CommentsRequest commentsRequest) {

        CommentsAuditEntity commentsAuditEntity = commentsAuditMapper.populate(commentsRequest);
        if (commentsRequest.getComments() != null) {
            try {
                commentsAuditRepository.save(commentsAuditEntity);
                log.info("Commented by user :{}", commentsRequest.getCustomerId());
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
            }
        }
        return sendCommentsResponse(commentsAuditEntity);
    }

    @Override
    public GenericResponse audit(CommentsAuditRequest commentsAuditRequest){

        CommentsAuditEntity commentsAuditEntity = null;
        try {
            Optional<CommentsAuditEntity> commentsAuditEntityFetched = commentsAuditRepository
                    .findByCrId(commentsAuditRequest.getCrId());
            if (commentsAuditEntityFetched.isPresent()) {
                commentsAuditEntity = commentsAuditEntityFetched.get();
                if (commentsAuditRequest.isApproved()) {
                    CommentsEntity commentsEntity = commentsMapper.populate(commentsAuditEntity, commentsAuditRequest);
                    commentsRepository.save(commentsEntity);
                    commentsAuditRepository.delete(commentsAuditEntity);
                    log.info("Comments approved and save to comments table Id: {}", commentsAuditRequest.getCrId());
                }
                else {
                    log.info("Comment is not approved Id:{}", commentsAuditRequest.getCrId());
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
        }

        return replyGeneric();
    }

    @Override
    public CommentsResponse editComment(CommentsEditRequest commentsEditrequest) {
        CommentsAuditEntity commentsAuditEntity = null;
        try {
            Optional<CommentsEntity> commentsEntityFetched = commentsRepository
                    .findByCrId(commentsEditrequest.getCrId());
            if(commentsEntityFetched.isPresent()) {
                CommentsEntity commentsEntity = commentsEntityFetched.get();
                commentsAuditEntity = populateCommentsAuditEntity(commentsEntity, commentsEditrequest);
                commentsAuditRepository.save(commentsAuditEntity);
                commentsRepository.delete(commentsEntity);
            } else {
                Optional<CommentsAuditEntity> commentsAuditEntityFetched = commentsAuditRepository
                        .findByCrId(commentsEditrequest.getCrId());
                commentsAuditEntity = commentsAuditEntityFetched.get();
                commentsAuditEntity.setComments(commentsEditrequest.getComment());
                commentsAuditEntity.setUpdationDate(Timestamp.valueOf(LocalDateTime.now()));
                commentsAuditRepository.save(commentsAuditEntity);
            }

            log.info("Moved comments from comments table to comments audit table for review. Id: {}"
                    , commentsEditrequest.getCrId());
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new ExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Try later!");
        }
        return sendCommentsResponse(commentsAuditEntity);
    }

    @Override
    public GenericResponse rate(RatingsRequest ratingsRequest) {
        RatingsEntity ratingsEntity = ratingsMapper.populate(ratingsRequest);
        try {
            ratingsRepository.save(ratingsEntity);
            log.info("Rated by user :{}", ratingsEntity.getCustomerId());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
        }

        return replyGeneric();
    }

    @Override
    public GenericResponse watchLater(WatchLaterRequest watchLaterRequest) {
        WatchLaterEntity watchLaterEntity = watchLaterMapper.populate(watchLaterRequest);
        try{
            if (watchLaterRequest.isWatchLater()){
                watchLaterRepository.save(watchLaterEntity);
                log.info("Added to watch later by : {}", watchLaterEntity.getCustId());
            } else{
                watchLaterRepository.delete(watchLaterEntity);
                log.info("Remove from watch later list by : {}", watchLaterEntity.getCustId());
            }
        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
        return replyGeneric();
    }

    public GenericResponse replyGeneric(){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus(HttpStatus.OK.value());
        genericResponse.setMessage("Done");
        return genericResponse;
    }

    public CommentsResponse sendCommentsResponse(CommentsAuditEntity commentsAuditEntity){
        CommentsResponse commentsResponse = new CommentsResponse();
        commentsResponse.setCustomerId(commentsAuditEntity.getCustomerId());
        commentsResponse.setComment(commentsAuditEntity.getComments());
        return commentsResponse;
    }


    public CommentsAuditEntity populateCommentsAuditEntity(CommentsEntity commentsEntity
            , CommentsEditRequest commentsEditRequest){
        CommentsAuditEntity commentsAuditEntity = new CommentsAuditEntity();
        commentsAuditEntity.setComments(commentsEditRequest.getComment());
        commentsAuditEntity.setCustomerId(commentsEntity.getCustomerId());
        commentsAuditEntity.setItemCommentsOnId(commentsEntity.getItemCommentsOnId());
        commentsAuditEntity.setItemType(commentsEntity.getItemType());
        commentsAuditEntity.setUpdationDate(Timestamp.valueOf(LocalDateTime.now()));
        commentsAuditEntity.setCreationDate(commentsEntity.getCreationDate());
        return commentsAuditEntity;
    }
    public WatchLaterResponse getAllWatchLaterProducts(String customerId){
        try {
            Optional<List<String>> productsList = watchLaterRepository.findAllProductsWatchLaterByUser(customerId);

            List<String> products = new ArrayList<>();

            if (productsList.isPresent()){
                products = productsList.get();
            }
            WatchLaterResponse watchLaterResponse = new WatchLaterResponse();
            watchLaterResponse.setCustomerId(customerId);
            watchLaterResponse.setProducts(products);
            return watchLaterResponse;

        } catch (Exception ex){
            log.error(ex.getMessage());
            throw new ExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }
    @Override
    public RatingsResponse getAvarageRating(String itemId, String itemType){
        long totalNumberOfRate = 1;
        double sumOfRatings = 0;
        double average = 0;
        try {
            Optional<List<RatingsEntity>> sumRatings = ratingsRepository.sumOfRatingsOfItem(itemId, itemType);
            Optional<Long> countRatings = ratingsRepository.countOfRatingsOfItem(itemId, itemType);
            if (sumRatings.isPresent() && countRatings.isPresent()){
                sumOfRatings = sumRatings.get().stream().mapToDouble(RatingsEntity::getRatings).sum();
                totalNumberOfRate = countRatings.get().longValue();
                BigDecimal avarageRatings = new BigDecimal(sumOfRatings/totalNumberOfRate);
                 average = avarageRatings.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
            }
            RatingsResponse ratingsResponse = new RatingsResponse();
            ratingsResponse.setItemRatingOnId(itemId);
            ratingsResponse.setItemType(itemType);
            ratingsResponse.setAverageRating(average);
            return ratingsResponse;
        } catch (Exception ex){
            log.error("Exception while getting ratings for itemid:{}"
                    , itemId
                    , ex.getCause());
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR
                    ,"Something went wrong when fetching all ratings.");
        }
    }
    @Override
    public AllCommentsResponse getAllCommentsByItem(String itemType, String itemId){
        List<Comment> comments = new ArrayList<>();
        try {
            Optional<List<CommentsEntity>> commentsEntities = commentsRepository
                    .getAllComments(itemId, itemType);
            if (commentsEntities.isPresent()){
                comments = commentsEntities
                        .get()
                        .stream()
                        .map(entity -> {
                            Comment comment = new Comment();
                            comment.setCustomerId(entity.getCustomerId());
                            comment.setComment(entity.getComments());
                            return comment;
                        }).collect(Collectors.toList());
            }

        } catch (Exception ex) {
            log.error("Exception while getting all comments for customer:{}"
                    , itemId
                    , ex.getCause());
            throw new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR
                    , "Something went wrong when fetching all comments.");
        }
        AllCommentsResponse allCommentsResponse = new AllCommentsResponse();
        allCommentsResponse.setCustomerId(itemId);
        allCommentsResponse.setCommentList(comments);
        return allCommentsResponse;
    }

}
