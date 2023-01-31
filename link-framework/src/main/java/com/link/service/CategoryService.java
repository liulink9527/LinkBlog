package com.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.link.domain.Result;
import com.link.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-12-29 10:49:21
 */
public interface CategoryService extends IService<Category> {
    Result getCategoryList();
}
