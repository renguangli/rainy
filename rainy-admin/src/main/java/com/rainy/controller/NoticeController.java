package com.rainy.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.dto.PageInfo;
import com.rainy.util.WebUtils;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.sys.entity.Notice;
import com.rainy.sys.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * rainy-console
 *
 * @author renguangli
 * @date 2022/4/28 18:07
 */
@Api(tags = NoticeController.MODULE_NAME)
@ApiSupport(author = "renguangli@bonc.com.cn", order = 3)
@RestController
@RequiredArgsConstructor
public class NoticeController {

    static final String MODULE_NAME = "通知公告";

    private final NoticeService noticeService;

    @ApiOperation("通知列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = MODULE_NAME, operationTypeCode = OperationType.QUERY, detail = "'查询了公告列表第' + #page.current + '页,每页' + #page.size + '条数据'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "公告标题")
    })
    @GetMapping("/notices")
    public Result list(PageInfo<Notice> page, String title){
        QueryWrapper<Notice> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(title), "title", title);
        if (page.isPaged()) {
            return Result.ok(noticeService.page(page, qw));
        }
        page.setRecords(noticeService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("新增公告")
    @SysLog(module = MODULE_NAME, operationTypeCode = OperationType.ADD, detail = "'新增了公告[' + #notice.title + '].'")
    @PostMapping("/notice")
    public Result save(@RequestBody @Valid Notice notice) {
        Integer userId = WebUtils.getLoginIdAsInt();
        notice.setUserId(userId);
        return Result.ok(noticeService.save(notice));
    }

    @ApiOperation("删除公告")
    @SysLog(module = MODULE_NAME, operationTypeCode = OperationType.DELETE, detail = "'删除了公告[' + #dto.name + '].'")
    @DeleteMapping("/notice")
    public Result remove(@RequestBody @Valid IdNameDto dto) {
        return Result.ok(noticeService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除公告")
    @SysLog(module = MODULE_NAME, operationTypeCode = OperationType.DELETE, detail = "'批量删除了公告[' + #dto.names + '].'")
    @DeleteMapping("/notices")
    public Result batchDel(@RequestBody @Valid IdNamesDto dto) {
        return Result.ok(noticeService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新公告")
    @SysLog(module = MODULE_NAME, operationTypeCode = OperationType.UPDATE, detail = "'更新了公告[' + #notice.title + '].'")
    @PutMapping("/notice")
    public Result update(@RequestBody @Valid Notice notice) {
        return Result.ok(noticeService.updateById(notice));
    }

}
