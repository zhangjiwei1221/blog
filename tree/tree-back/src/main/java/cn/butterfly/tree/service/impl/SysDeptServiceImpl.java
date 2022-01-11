package cn.butterfly.tree.service.impl;

import cn.butterfly.tree.constant.ErrorMsgConstants;
import cn.butterfly.tree.entity.SysDept;
import cn.butterfly.tree.exception.ApiException;
import cn.butterfly.tree.mapper.SysDeptMapper;
import cn.butterfly.tree.service.ISysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * 部门服务实现类
 *
 * @author zjw
 * @date 2021-12-11
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public List<SysDept> tree() {
        return baseMapper.tree();
    }

    @Override
    public List<SysDept> deptWithChildren(Long id) {
        SysDept sysDept = baseMapper.selectById(id);
        return baseMapper.selectList(new LambdaQueryWrapper<SysDept>()
                .ge(SysDept::getLft, sysDept.getLft())
                .le(SysDept::getRgt, sysDept.getRgt())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysDept sysDept) {
        LambdaQueryWrapper<SysDept> condition = new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getId, sysDept.getParentId());
        SysDept parentDept = baseMapper.selectOne(condition);
        Long rgt = parentDept.getRgt();
        updateNodeByRgt(rgt);
        sysDept.setLft(rgt);
        sysDept.setRgt(rgt + 1);
        save(sysDept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDept sysDept) {
        Long id = sysDept.getId();
        SysDept originDept = baseMapper.selectById(id);
        if (Objects.equals(originDept.getParentId(), sysDept.getParentId())) {
            updateById(sysDept);
            return;
        }
        delete(id);
        sysDept.setId(null);
        add(sysDept);
    }

    /**
     * 新增节点时根据右节点更新其它节点的左右节点值
     *
	 * @param rgt 右节点值
     */
    private void updateNodeByRgt(Long rgt) {
        lambdaUpdate()
                .setSql("lft = lft + 2")
                .ge(SysDept::getLft, rgt)
                .update();
        lambdaUpdate()
                .setSql("rgt = rgt + 2")
                .ge(SysDept::getRgt, rgt)
                .update();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        validateExistChild(id);
        updateChildNode(id);
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysDept> getByParentId(Long parentId) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getParentId, parentId)
        );
    }

    @Override
    public Boolean isLeaf(SysDept sysDept) {
        return sysDept.getRgt() - sysDept.getLft() == 1L;
    }

    /**
     * 校验指定节点是否存在子节点, 若存在则报错提醒
     *
	 * @param id id
     */
    private void validateExistChild(Long id) {
        LambdaQueryWrapper<SysDept> condition = new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getParentId, id);
        Long count = baseMapper.selectCount(condition);
        if (count > 0) {
            throw new ApiException(ErrorMsgConstants.EXIST_CHILDREN_DEPT_ERROR);
        }
    }

    /**
     * 根据父节点 id 更新子节点的左右节点值
     *
	 * @param parentId 父节点 id
     */
    private void updateChildNode(Long parentId) {
        SysDept sysDept = baseMapper.selectById(parentId);
        Long rgt = sysDept.getRgt();
        long width = rgt - sysDept.getLft() + 1;
        lambdaUpdate()
                .setSql("lft = lft - " + width)
                .gt(SysDept::getLft, rgt)
                .update();
        lambdaUpdate()
                .setSql("rgt = rgt - " + width)
                .gt(SysDept::getRgt, rgt)
                .update();
    }

}
