package cn.butterfly.tree.service.impl;

import cn.butterfly.tree.dto.SysDeptVO;
import cn.butterfly.tree.entity.SysDept;
import cn.butterfly.tree.mapper.SysDeptMapper;
import cn.butterfly.tree.node.TreeMerger;
import cn.butterfly.tree.node.TreeSelectNode;
import cn.butterfly.tree.service.ISysDeptService;
import cn.butterfly.tree.util.CopyBeanUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 部门服务实现类
 *
 * @author zjw
 * @date 2021-12-11
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public List<SysDeptVO> tree() {
        List<SysDept> sysDeptList = baseMapper.tree();
        List<SysDeptVO> sysDeptVoList = CopyBeanUtils.copyList(sysDeptList, SysDeptVO::new);
        return TreeMerger.mergeTreeList(sysDeptVoList);
    }

    @Override
    public List<TreeSelectNode> treeSelect() {
        List<SysDept> sysDeptList = baseMapper.tree();
        List<TreeSelectNode> treeSelectNodeList = CopyBeanUtils.copyList(
                sysDeptList,
                TreeSelectNode::new,
                TreeSelectNode::setValue,
                SysDept::getName
        );
        return TreeMerger.mergeTreeList(treeSelectNodeList);
    }

}
