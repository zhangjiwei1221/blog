package cn.butterfly.tree.controller;

import cn.butterfly.tree.base.BaseResult;
import cn.butterfly.tree.entity.SysDept;
import cn.butterfly.tree.node.TreeMerger;
import cn.butterfly.tree.node.TreeSelectNode;
import cn.butterfly.tree.service.ISysDeptService;
import cn.butterfly.tree.util.CopyBeanUtils;
import cn.butterfly.tree.vo.SysDeptInfoVO;
import cn.butterfly.tree.vo.SysDeptTreeVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 部门控制器
 *
 * @author zjw
 * @date 2021-12-11
 */
@RestController
@RequestMapping("/api/system/dept")
public class SysDeptController {

    @Resource
    private ISysDeptService sysDeptService;

    /**
     * 获取部门树结构
     *
     * @return 部门树结构
     */
    @GetMapping("/tree")
    public BaseResult<List<SysDeptTreeVO>> tree() {
        List<SysDept> sysDeptList = sysDeptService.tree();
        List<SysDeptTreeVO> sysDeptTreeVoList = CopyBeanUtils.copyList(sysDeptList, SysDeptTreeVO::new);
        return BaseResult.success(TreeMerger.mergeTreeList(sysDeptTreeVoList));
    }

    /**
     * 获取下拉部门树结构列表
     *
     * @return 下拉部门树结构列表
     */
    @GetMapping("/treeSelect")
    public BaseResult<List<TreeSelectNode>> treeSelect() {
        List<SysDept> sysDeptList = sysDeptService.tree();
        return BaseResult.success(TreeMerger.mergeTreeSelectList(sysDeptList, SysDept::getName));
    }

    /**
     * 根据 id 获取部门信息
     *
     * @return 部门信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SysDeptInfoVO> info(@PathVariable("id") Long id) {
        SysDept sysDept = sysDeptService.getById(id);
        SysDeptInfoVO sysDeptInfoVO = CopyBeanUtils.copy(sysDept, SysDeptInfoVO::new);
        Optional.ofNullable(sysDeptService.getById(sysDept.getParentId()))
                .ifPresent(parentDept -> sysDeptInfoVO.setParentDeptName(parentDept.getName()));
        return BaseResult.success(sysDeptInfoVO);
    }

    /**
     * 根据部门 id 查询指定部门及以下部门树结构列表
     *
     * @param id 部门 id
     * @return 下拉部门树结构列表
     */
    @GetMapping("/childTree/{id}")
    public BaseResult<List<TreeSelectNode>> childTree(@PathVariable("id") Long id) {
        List<SysDept> sysDeptList = sysDeptService.deptWithChildren(id);
        return BaseResult.success(TreeMerger.mergeTreeSelectList(sysDeptList, SysDept::getName));
    }

    /**
     * 新增部门
     *
     * @param sysDept 部门
     * @return 结果
     */
    @PostMapping("/add")
    public BaseResult<Object> add(@RequestBody SysDept sysDept) {
        sysDeptService.add(sysDept);
        return BaseResult.success();
    }

    /**
     * 修改部门
     *
     * @param sysDept 部门
     * @return 结果
     */
    @PutMapping("/update")
    public BaseResult<Object> update(@RequestBody SysDept sysDept) {
        sysDeptService.update(sysDept);
        return BaseResult.success();
    }

    /**
     * 根据 id 删除部门
     *
     * @param id 部门 id
     * @return 结果
     */
    @DeleteMapping("/delete/{id}")
    public BaseResult<Object> delete(@PathVariable("id") Long id) {
        sysDeptService.delete(id);
        return BaseResult.success();
    }

}
