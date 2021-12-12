package cn.butterfly.tree.mapper;

import cn.butterfly.tree.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 部门 mapper 类
 *
 * @author zjw
 * @date 201-12-11
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 获取部门树结构列表
     *
     * @return 部门树结构列表
     */
    List<SysDept> tree();

}
