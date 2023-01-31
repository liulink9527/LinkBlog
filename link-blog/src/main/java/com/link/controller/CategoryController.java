package com.link.controller;

import com.link.domain.Result;
import com.link.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Link
 * @Description
 * @date 2022-12-29 10:40
 */
@RestController
@Api(value = "文章分类接口")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询文章分类列表")
    @GetMapping("/getCategoryList")
    public Result getCategoryList() {
        Result r = categoryService.getCategoryList();
        return r;
    }
}
