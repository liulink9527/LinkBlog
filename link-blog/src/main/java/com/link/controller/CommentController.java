package com.link.controller;

import com.link.constants.SystemConstants;
import com.link.domain.Result;
import com.link.domain.entity.Comment;
import com.link.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Link
 * @Description
 * @date 2023-01-16 19:44
 */
@Api
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public Result commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.COMMENT_ARTICLE, articleId, pageNum, pageSize);
    }

    @PostMapping
    public Result addComment(@RequestBody Comment comment) {
        Result r = commentService.addComment(comment);
        return r;
    }

    @GetMapping("/linkCommentList")
    public Result linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.COMMENT_LINK, null, pageNum, pageSize);
    }


}
