package com.link.controller;

import com.link.domain.Result;
import com.link.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Link
 * @Description
 * @date 2022-12-29 16:32
 */
@RestController
@Api
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @ApiOperation("查询友链列表")
    @GetMapping("/getAllLink")
    public Result getAllLink() {
        Result r = linkService.getAllLink();
        return r;
    }

}
