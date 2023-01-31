package com.link.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.link.domain.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-16 19:44:22
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> selectAllByArticleIdAndRootId(@Param("articleId") Long articleId);
}

