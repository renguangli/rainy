package com.rainy.controller;

import com.rainy.common.Result;
import com.rainy.common.dto.IdNameDto;
import com.rainy.sys.entity.Resource;
import com.rainy.sys.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/resource")
    public Result save(@RequestBody Resource resource){
        return Result.ok(resourceService.save(resource));
    }

    @PutMapping("/resource")
    public Result update(@RequestBody Resource resource){
        return Result.ok(resourceService.updateById(resource));
    }

    @DeleteMapping("/resource")
    public Result remove(@RequestBody IdNameDto dto){
        return Result.ok(resourceService.removeById(dto.getId()));
    }

}
