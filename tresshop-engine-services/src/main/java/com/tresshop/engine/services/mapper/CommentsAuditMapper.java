package com.tresshop.engine.services.mapper;

import com.tresshop.engine.client.likeandcomments.CommentsRequest;
import com.tresshop.engine.storage.entity.CommentsAuditEntity;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class CommentsAuditMapper {
    public CommentsAuditEntity populate(CommentsRequest commentsRequest){
        CommentsAuditEntity commentsAuditEntity = new CommentsAuditEntity();
        commentsAuditEntity.setItemCommentsOnId(commentsRequest.getItemCommentsOnId());
        commentsAuditEntity.setItemType(commentsRequest.getItemType());
        commentsAuditEntity.setCustomerId(commentsRequest.getCustomerId());
        commentsAuditEntity.setComments(commentsRequest.getComments());
        commentsAuditEntity.setUpdationDate(Timestamp.valueOf(LocalDateTime.now()));
        return commentsAuditEntity;
    }
}
