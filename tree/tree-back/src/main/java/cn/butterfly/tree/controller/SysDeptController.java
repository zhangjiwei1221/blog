package cn.butterfly.tree.controller;

import cn.butterfly.tree.base.BaseResult;
import cn.butterfly.tree.dto.SysDeptVO;
import cn.butterfly.tree.node.TreeSelectNode;
import cn.butterfly.tree.service.ISysDeptService;
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
    public BaseResult<List<SysDeptVO>> tree() {
        List<SysDeptVO> sysDeptTree = sysDeptService.tree();
        return BaseResult.success(sysDeptTree);
    }

    /**
     * 获取下拉部门树结构列表
     *
     * @return 下拉部门树结构列表
     */
    @GetMapping("/treeSelect")
    public BaseResult<List<TreeSelectNode>> treeSelect() {
        List<TreeSelectNode> sysDeptTreeSelect = sysDeptService.treeSelect();
        return BaseResult.success(sysDeptTreeSelect);
    }

}
