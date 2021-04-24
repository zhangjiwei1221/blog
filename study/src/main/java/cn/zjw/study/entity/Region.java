package cn.zjw.study.entity;

import lombok.Data;

/**
 * Region
 *
 * @author zjw
 * @date 2021/4/9
 */
@Data
public class Region {

    private Long id;

    private Long parentId;

    private String name;

    private Integer levelType;

}
