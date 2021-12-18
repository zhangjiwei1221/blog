package cn.butterfly.tree.service.impl;

import cn.butterfly.tree.constant.ErrorMsgConstants;
import cn.butterfly.tree.entity.SysDept;
import cn.butterfly.tree.exception.ApiException;
import cn.butterfly.tree.mapper.SysDeptMapper;
import cn.butterfly.tree.service.ISysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public List<SysDept> tree() {
        return baseMapper.tree();
    }

    @Override
    public void delete(Long id) {
        LambdaQueryWrapper<SysDept> condition = new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getParentId, id);
        Long count = baseMapper.selectCount(condition);
        if (count > 0) {
            throw new ApiException(ErrorMsgConstants.EXIST_CHILDREN_DEPT_ERROR);
        }
        baseMapper.deleteById(id);
    }

}
