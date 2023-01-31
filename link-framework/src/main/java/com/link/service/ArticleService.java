package com.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.link.domain.Result;
import com.link.domain.entity.Article;

/**
 * @author Link
 * @Description
 * @date 2022-12-25 15:21
 */
public interface ArticleService extends IService<Article> {

    Result hotArticleList();

    Result articleList(Integer pageNum, Integer pageSize, Long categoryId);

    Result getArticleDetail(Long id);

    Result updateViewCount(Long id);
}
