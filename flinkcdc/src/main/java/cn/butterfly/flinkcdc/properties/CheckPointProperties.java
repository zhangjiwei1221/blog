package cn.butterfly.flinkcdc.properties;

import cn.butterfly.flinkcdc.enums.CheckPointStorageType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * 检查点配置
 *
 * @author zjw
 * @date 2023-03-14
 */
@Getter
@Setter
@ConfigurationProperties(CheckPointProperties.PREFIX)
public class CheckPointProperties {

    /**
     * Prefix of {@link CheckPointProperties}.
     */
    public static final String PREFIX = "flinkcdc.checkpoint";

    /**
     * 存储方式
     */
    private CheckPointStorageType storageType = CheckPointStorageType.FILE;

    /**
     * 检查点间隔, 默认 60s.
     */
    private Long interval = 60_000L;

    /**
     * 检查点保存目录, 默认为: 项目目录/checkpoint.
     */
    private String saveDir = System.getProperty("user.dir") + File.separator + "checkpoint";

}
