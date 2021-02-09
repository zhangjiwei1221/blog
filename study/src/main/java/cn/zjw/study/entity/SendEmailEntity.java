package cn.zjw.study.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * SendEmailEntity
 *
 * @author zjw
 * @createTime 2021/2/7 18:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailEntity {

    private String date;
    private String email;

}
