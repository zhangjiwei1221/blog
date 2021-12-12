package cn.butterfly.tree.controller;

import cn.butterfly.tree.base.BaseResult;
import cn.butterfly.tree.entity.SysDept;
import cn.butterfly.tree.node.TreeMerger;
import cn.butterfly.tree.node.TreeSelectNode;
import cn.butterfly.tree.service.ISysDeptService;
import cn.butterfly.tree.util.CopyBeanUtils;
import cn.butterfly.tree.vo.SysDeptTreeVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

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

}
