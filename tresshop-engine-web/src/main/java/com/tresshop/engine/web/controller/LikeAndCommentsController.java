package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.likeandcomments.*;
import com.tresshop.engine.services.likeandcomments.LikeAndCommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/likeAndComments")
public class LikeAndCommentsController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LikeAndCommentsService likeAndCommentsService;

    @PostMapping(
            value = "/like",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<GenericResponse> like(
            @RequestBody LikesRequest likesRequest){
        return new ResponseEntity<>(likeAndCommentsService.like(likesRequest), HttpStatus.OK);
    }

    @PostMapping(
            value = "/likeCount",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<LikesCountResponse> getTotalLikes(
            @RequestBody LikeCountRequest likeCountRequest){
        return new ResponseEntity<>(likeAndCommentsService.totalLikeCount(likeCountRequest), HttpStatus.OK);
    }

    @PostMapping(
            value = "/comment",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<CommentsResponse> comment(
            @RequestBody CommentsRequest commentsRequest){
        return new ResponseEntity<>(likeAndCommentsService.comment(commentsRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/rate",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<GenericResponse> rate(
            @RequestBody RatingsRequest ratingsRequest){
        return new ResponseEntity<>(likeAndCommentsService.rate(ratingsRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/watchLater",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<GenericResponse> watchLater(
            @RequestBody WatchLaterRequest watchLaterRequest){
        return new ResponseEntity<>(likeAndCommentsService.watchLater(watchLaterRequest), HttpStatus.OK);
    }
    @PostMapping(
            value = "/audit",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<GenericResponse> audit(
            @RequestBody CommentsAuditRequest commentsAuditRequest){
        return new ResponseEntity<>(likeAndCommentsService.audit(commentsAuditRequest),HttpStatus.OK);
    }

    @PostMapping(
            value = "/editComment",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<CommentsResponse> editComment(
            @RequestBody CommentsEditRequest commentsEditrequest){
        return new ResponseEntity<>(likeAndCommentsService.editComment(commentsEditrequest),HttpStatus.OK);
    }
    @GetMapping(value = "/getAverageRating/{itemType}/{itemId}")
    public ResponseEntity<RatingsResponse> getAverageRatings(@PathVariable String itemType
            , @PathVariable String itemId) {
        return new ResponseEntity<>(likeAndCommentsService.getAvarageRating(itemId, itemType),HttpStatus.OK);
    }
    @GetMapping(value = "/getAllWatchLater/{customerId}")
    public ResponseEntity<WatchLaterResponse> getAllWatchLater(@PathVariable String customerId){
        return new ResponseEntity<>(likeAndCommentsService.getAllWatchLaterProducts(customerId), HttpStatus.OK);
    }
    @GetMapping(value = "/getAllComments/{itemType}/{itemId}")
    public ResponseEntity<AllCommentsResponse> getAllComments(@PathVariable String itemType, @PathVariable String itemId){
        return new ResponseEntity<>(likeAndCommentsService.getAllCommentsByItem(itemType, itemId), HttpStatus.OK);
    }

}
