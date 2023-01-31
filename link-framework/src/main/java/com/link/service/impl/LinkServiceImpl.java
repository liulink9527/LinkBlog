package com.link.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.link.constants.SystemConstants;
import com.link.domain.Result;
import com.link.domain.entity.Link;
import com.link.domain.vo.LinkVo;
import com.link.mapper.LinkMapper;
import com.link.service.LinkService;
import com.link.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-12-29 16:35:08
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public Result getAllLink() {
        LambdaQueryWrapper<Link> qw = new LambdaQueryWrapper();
        qw.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(qw);

        //封装Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyListBean(links, LinkVo.class);
        return Result.okResult(linkVos);
    }
}
