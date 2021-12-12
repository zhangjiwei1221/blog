package cn.butterfly.tree.service.impl;

import cn.butterfly.tree.entity.SysDept;
import cn.butterfly.tree.mapper.SysDeptMapper;
import cn.butterfly.tree.service.ISysDeptService;
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

}
