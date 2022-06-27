package com.rainy.controller;

import com.rainy.common.Result;
import com.rainy.sys.entity.Resource;
import com.rainy.sys.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/6/27 15:31
 */
@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("/resources/tree")
    public Result listTree(){
        List<Resource> list = resourceService.listTree();
        return Result.ok(list);
    }

}
