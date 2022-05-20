package com.rainy.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rainy.admin.dto.PageInfo;
import com.rainy.common.OperationType;
import com.rainy.common.Result;
import com.rainy.common.annotation.SysLog;
import com.rainy.core.entity.Org;
import com.rainy.core.service.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Validated
@RestController
public class OrgController {

    @Resource
    private OrgService orgService;

    @ApiOperation("组织结构树")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.QUERY, detail = "'查询了组织结构树'")
    @GetMapping("/orgs/tree")
    public Result getOrgTree() {
        return Result.ok(orgService.getOrgTree());
    }

    @ApiOperation("组织列表(分页）")
    @ApiOperationSupport(ignoreParameters = {"records", "orders", "total", "pages"})
    @SysLog(module = "组织管理", operationTypeCode = OperationType.QUERY, detail = "'查询了组织列表第' + #page.current + '页.每页' + #page.size + '条数据'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组织结构名称")
    })
    @GetMapping("/orgs")
    public Result listOrgs(PageInfo<Org> page, String name, Integer orgId) {
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
    public Result addOrg(@RequestBody @Valid Org org) {
        return Result.ok(orgService.save(org));
    }

    @ApiOperation("删除组织")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.DELETE, detail = "'删除了组织[' + #id + '].'")
    @DeleteMapping("/org/{id:[0-9]+}")
    public Result removeOrg(@PathVariable Integer id) {
        return Result.ok(orgService.removeById(id));
    }

    @ApiOperation("批量删除组织")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.DELETE, detail = "'批量删除了组织[' + #ids + '].'")
    @DeleteMapping("/orgs")
    public Result batchRemoveOrg(@RequestBody List<Integer> ids) {
        return Result.ok(orgService.removeBatchByIds(ids));
    }

    @ApiOperation("更新组织")
    @SysLog(module = "组织管理", operationTypeCode = OperationType.UPDATE, detail = "'更新了组织[' + #org.name + '].'")
    @PutMapping("/org")
    public Result updateOrg(@RequestBody @Valid Org org) {
        return Result.ok(orgService.updateById(org));
    }

}
