package com.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.link.constants.SystemConstants;
import com.link.domain.Result;
import com.link.domain.entity.Article;
import com.link.domain.entity.Category;
import com.link.domain.vo.CategoryVo;
import com.link.mapper.ArticleMapper;
import com.link.mapper.CategoryMapper;
import com.link.service.ArticleService;
import com.link.service.CategoryService;
import com.link.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-12-29 10:50:20
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public Result getCategoryList() {
        //查询文章表，查到正式发布的文章
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper();
        qw.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(qw);
        //去重，获得category_id
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categoryList = listByIds(categoryIds);

        //判断状态
        categoryList = categoryList.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //封装VO
        List<CategoryVo> categoryVos = BeanCopyUtils.copyListBean(categoryList, CategoryVo.class);
        return Result.okResult(categoryVos);

    }
}
