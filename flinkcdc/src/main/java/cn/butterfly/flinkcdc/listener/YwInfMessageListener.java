package cn.butterfly.flinkcdc.listener;

import cn.butterfly.flinkcdc.annotation.FlickCdcMessageListener;
import cn.butterfly.flinkcdc.pojo.SxglDeptYwInf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户表消息监听器
 *
 * @author zjw
 * @date 2023-03-14
 */
@Slf4j
@Component
@FlickCdcMessageListener("sxgl_dept_yw_inf")
public class YwInfMessageListener extends AbstractMessageListener<SxglDeptYwInf> {

    @Override
    public void create(SxglDeptYwInf sxglDeptYwInf) {
        log.info("新增事项: {}", sxglDeptYwInf);
    }

}
