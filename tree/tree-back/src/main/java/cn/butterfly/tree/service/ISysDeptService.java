package cn.butterfly.tree.service;

import cn.butterfly.tree.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 部门服务类
 *
 * @author zjw
 * @date 2021-12-11
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 获取部门树结构列表
     *
     * @return 部门树结构列表
     */
    List<SysDept> tree();

}
