package com.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.link.constants.RedisConstants;
import com.link.constants.SystemConstants;
import com.link.domain.Result;
import com.link.domain.entity.Article;
import com.link.domain.entity.Category;
import com.link.domain.vo.ArticleDetailVo;
import com.link.domain.vo.ArticleListVo;
import com.link.domain.vo.HotArticleVo;
import com.link.domain.vo.PageVo;
import com.link.mapper.ArticleMapper;
import com.link.service.ArticleService;
import com.link.service.CategoryService;
import com.link.utils.BeanCopyUtils;
import com.link.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Link
 * @Description
 * @date 2022-12-25 15:22
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result hotArticleList() {
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper();
        qw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        qw.orderByDesc(Article::getViewCount);
        Page<Article> p = new Page(1, 10);
        page(p, qw);

        List<Article> articles = p.getRecords();
        articles = articles.stream()
                .map(article -> {
                    //获取redis浏览量
                    Integer viewCount = redisCache.getCacheMapValue(RedisConstants.VIEW_COUNT, article.getId().toString());
                    article.setViewCount(Long.valueOf(viewCount));
                    return article;
                }).collect(Collectors.toList());
        List<HotArticleVo> list = BeanCopyUtils.copyListBean(articles, HotArticleVo.class);
//        List<HotArticleVo> list = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            list.add(vo);
//        }
        return Result.okResult(list);

    }

    @Override
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper();
        //是否是分类查询列表
        qw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);

        //分页查询
        //状态是正常发布的
        qw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //是否置顶
        qw.orderByDesc(Article::getIsTop);
        Page<Article> p = new Page<>(pageNum, pageSize);
        page(p, qw);

        //查询CategoryName
        List<Article> articles = p.getRecords();
        articles = articles.stream().map(article -> {
            //获取分类id
            Category category = categoryService.getById(article.getCategoryId());
            //设置分类名称
            article.setCategoryName(category.getName());

            //获取redis浏览量
            Integer viewCount = redisCache.getCacheMapValue(RedisConstants.VIEW_COUNT, article.getId().toString());
            article.setViewCount(Long.valueOf(viewCount));
            return article;
        }).collect(Collectors.toList());

        //封装Vo
        List<ArticleListVo> articleVos = BeanCopyUtils.copyListBean(articles, ArticleListVo.class);


        PageVo pageVo = new PageVo(articleVos, p.getTotal());

        return Result.okResult(pageVo);
    }

    @Override
    public Result getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //获取redis浏览量
        Integer viewCount = redisCache.getCacheMapValue(RedisConstants.VIEW_COUNT, id.toString());
        article.setViewCount(Long.valueOf(viewCount));
        //根据分类名查询categoryId
        Category category = categoryService.getById(article.getCategoryId());
        if (category != null) {
            //设置分类名称
            article.setCategoryName(category.getName());
        }


        //封装Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return Result.okResult(articleDetailVo);
    }

    @Override
    public Result updateViewCount(Long id) {
        redisCache.increBy(RedisConstants.VIEW_COUNT, id.toString(), 1);
        return Result.okResult();
    }


}
