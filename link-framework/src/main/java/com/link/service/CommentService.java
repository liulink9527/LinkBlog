package com.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.link.domain.Result;
import com.link.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-01-16 19:43:58
 */
public interface CommentService extends IService<Comment> {

    Result commentList(String s, Long articleId, Integer pageNum, Integer pageSize);

    Result addComment(Comment comment);
}
