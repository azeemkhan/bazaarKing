package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.likeandcomments.CommentsAuditRequest;
import com.tresshop.engine.storage.entity.CommentsAuditEntity;
import com.tresshop.engine.storage.entity.CommentsEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentsMapper {
    public CommentsEntity populate(CommentsAuditEntity commentsAuditEntity
            , CommentsAuditRequest commentsAuditRequest){
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setCrId(commentsAuditEntity.getCrId());
        commentsEntity.setCommentQuality(commentsAuditRequest.getCommentQuality());
        commentsEntity.setComments(commentsAuditEntity.getComments());
        commentsEntity.setCreationDate(commentsAuditEntity.getCreationDate());
        commentsEntity.setCustomerId(commentsAuditEntity.getCustomerId());
        commentsEntity.setUpdationDate(commentsAuditEntity.getUpdationDate());
        commentsEntity.setItemCommentsOnId(commentsAuditEntity.getItemCommentsOnId());
        commentsEntity.setItemType(commentsAuditEntity.getItemType());
        return commentsEntity;
    }
}
