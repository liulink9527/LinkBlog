package com.link.controller;

import com.link.domain.Result;
import com.link.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Link
 * @Description
 * @date 2022-12-25 15:25
 */
@RestController
@Api(value = "文章接口")
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("查询热门文章列表")
    @GetMapping("/hotArticleList")
    public Result hotArticleList() {
        //查询热门文章封装成Result对象
        Result r = articleService.hotArticleList();
        return r;
    }

    @ApiOperation("分页查询文章列表")
    @GetMapping("/articleList")
    public Result articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        Result r = articleService.articleList(pageNum, pageSize, categoryId);
        return r;
    }

    @ApiOperation("文章详情")
    @GetMapping("/{id}")
    public Result getArticleDetail(@PathVariable("id") Long id) {
        Result r = articleService.getArticleDetail(id);
        return r;
    }

    @PutMapping("/updateViewCount/{id}")
    public Result updateViewCount(@PathVariable Long id) {
        return articleService.updateViewCount(id);
    }

}
