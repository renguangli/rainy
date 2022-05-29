package com.rainy.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.common.util.ValidateUtils;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.common.dto.IdNameDto;
import com.rainy.common.dto.IdNamesDto;
import com.rainy.common.enums.OperationType;
import com.rainy.core.entity.Org;
import com.rainy.core.service.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * rainy
 *
 * @author renguangli
 * @date 2022/3/16 19:49
 */
@Api(tags = "组织管理")
@ApiSupport(author = "renguangli@bonc.com.cn", order = 3)
@RestController
@RequiredArgsConstructor
public class OrgController {

    private final OrgService orgService;

    @ApiOperation("组织结构树")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.QUERY, detail = "'查询了组织结构树'")
    @GetMapping("/orgs/tree")
    public Result getOrgTree() {
        return Result.ok(orgService.getOrgTree());
    }

    @ApiOperation("组织列表")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "组织管理", operationTypeCode = OperationType.QUERY, detail = "'查询了组织列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组织结构名称")
    })
    @GetMapping("/orgs")
    public Result list(PageInfo<Org> page, String name, Integer orgId) {
        if (orgId != null) {
            List<Org> orgList = orgService.listOrgsById(orgId);
            page.setRecords(orgList);
            return Result.ok(page);
        }
        QueryWrapper<Org> qw = new QueryWrapper<>();
        qw.likeRight(StrUtil.isNotBlank(name), "name", name);
        if (page.isPaged()) {
            return Result.ok(orgService.page(page, qw));
        }
        page.setRecords(orgService.list(qw));
        return Result.ok(page);
    }

    @ApiOperation("新增组织")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.ADD, detail = "'新增了组织[' + #org.name + '].'")
    @ApiOperationSupport(ignoreParameters = {"org.id", "org.children"})
    @PostMapping("/org")
    public Result save(@RequestBody @Valid Org org) {
        boolean exists = orgService.exists("name", org.getName());
        ValidateUtils.isTrue(exists, "岗位[" + org.getName() + "]已存在！");
        return Result.ok(orgService.save(org));
    }

    @ApiOperation("删除组织")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.DELETE, detail = "'删除了组织[' + #dto.name + '].'")
    @DeleteMapping("/org")
    public Result remove(@RequestBody @Valid IdNameDto dto) {
        QueryWrapper<Org> qw = new QueryWrapper<>();
        qw.eq("parent_id", dto.getId());
        long count = orgService.count(qw);
        ValidateUtils.isGtZero(count, "该组织下有子组织，请先删除子组织!");
        return Result.ok(orgService.removeById(dto.getId()));
    }

    @ApiOperation("批量删除组织")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了组织[' + #dto.names + '].'")
    @DeleteMapping("/orgs")
    public Result batchRemove(@RequestBody IdNamesDto dto) {
        return Result.ok(orgService.removeBatchByIds(dto.getIds()));
    }

    @ApiOperation("更新组织")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了组织[' + #org.name + '].'")
    @PutMapping("/org")
    public Result update(@RequestBody @Valid Org org) {
        boolean exists = orgService.exists(org.getId(), "name", org.getName());
        ValidateUtils.isTrue(exists, "岗位[" + org.getName() + "]已存在！");
        return Result.ok(orgService.updateById(org));
    }

}
