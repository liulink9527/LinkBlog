package com.link.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.link.domain.entity.Link;
import org.springframework.stereotype.Repository;


/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-12-29 16:35:08
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

}

