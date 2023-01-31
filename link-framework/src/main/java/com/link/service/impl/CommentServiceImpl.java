package com.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.link.constants.SystemConstants;
import com.link.domain.Result;
import com.link.domain.entity.Comment;
import com.link.domain.entity.User;
import com.link.domain.vo.CommentVo;
import com.link.domain.vo.PageVo;
import com.link.mapper.CommentMapper;
import com.link.service.CommentService;
import com.link.service.UserService;
import com.link.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-01-16 19:43:58
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Override
    public Result commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        //查找根评论
        LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper();

        qw.eq(SystemConstants.COMMENT_ARTICLE.equals(commentType), Comment::getArticleId, articleId);
        qw.eq(Comment::getRootId, -1);

        qw.eq(Comment::getType, commentType);
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, qw);
        List<Comment> records = page.getRecords();
        long total = page.getTotal();
        List<CommentVo> commentVos = toCommentVoList(records);

        //查询所有根评论的子评论集合
        commentVos.stream()
                .forEach(commentVo -> {
                    Long id = commentVo.getId();
                    List<CommentVo> children = getChildren(id);
                    commentVo.setChildren(children);
                });
        return Result.okResult(new PageVo(commentVos, total));
    }

    @Override
    public Result addComment(Comment comment) {
        save(comment);
        return Result.okResult();
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyListBean(list, CommentVo.class);

        return commentVos.stream()
                .map(commentVo -> {
                    Long toCommentUserId = commentVo.getToCommentUserId();
                    if (toCommentUserId != -1) {
                        User user = userService.getById(toCommentUserId);
                        commentVo.setToCommentUserName(user.getNickName());
                    }

                    User user1 = userService.getById(commentVo.getCreateBy());
                    commentVo.setUsername(user1.getNickName());
                    return commentVo;
                })
                .collect(Collectors.toList());
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> qw = new LambdaQueryWrapper();
        qw.eq(Comment::getRootId, id);
        qw.orderByAsc(Comment::getCreateTime);
        List<Comment> children = commentMapper.selectList(qw);
        List<CommentVo> commentVos = toCommentVoList(children);
        return commentVos;
    }

}
