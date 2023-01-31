package com.link.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.link.domain.Result;
import com.link.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-12-29 16:35:08
 */
public interface LinkService extends IService<Link> {

    Result getAllLink();
}
