package com.link.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.link.domain.entity.Category;
import org.springframework.stereotype.Repository;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-29 10:48:04
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}

