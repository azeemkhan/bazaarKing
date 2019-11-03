package com.tresshop.engine.services.likeandcomments;

import com.tresshop.engine.client.likeandcomments.*;

public interface LikeAndCommentsService {
    GenericResponse like(LikesRequest likesRequest);
    LikesResponse getLikedUserIds(LikeCountRequest likeCountRequest);
    LikesCountResponse totalLikeCount(LikeCountRequest likeCountRequest);
    CommentsResponse comment(CommentsRequest commentsRequest);
    GenericResponse rate(RatingsRequest ratingsRequest);
    GenericResponse watchLater(WatchLaterRequest watchLaterRequest);
    GenericResponse audit(CommentsAuditRequest commentsAuditRequest);
    CommentsResponse editComment(CommentsEditRequest commentsEditrequest);
    WatchLaterResponse getAllWatchLaterProducts(String customerId);
    RatingsResponse getAvarageRating(String itemId, String itemType);
    AllCommentsResponse getAllCommentsByItem(String itemType, String itemId);
}
