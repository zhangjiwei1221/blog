package cn.butterfly.flinkcdc.util;

import cn.butterfly.flinkcdc.enums.CheckPointStorageType;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 检查点工具类
 *
 * @author zjw
 * @date 2023-03-14
 */
public final class SavepointUtils {

    /**
     * 根据检查点目录查询具体检查点
     *
	 * @param checkpointDir 检查点目录
     * @return 检查点
     */
    public static String getSavepointRestore(String checkpointDir) {
        if (checkpointDir.startsWith(CheckPointStorageType.HDFS.getPrefix())) {
            return null;
        }
        var file = new File(checkpointDir);
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        var files = file.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        file = Arrays.stream(files)
                .filter(File::isDirectory)
                .max(Comparator.comparing(File::lastModified))
                .orElse(null);
        if (file == null) {
            return null;
        }
        files = file.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        file = Arrays.stream(files)
                .filter(File::isDirectory)
                .filter(f -> f.getName().startsWith("chk-"))
                .max(Comparator.comparing(File::lastModified))
                .orElse(null);
        if (file == null) {
            return null;
        }
        return file.getPath();
    }

    private SavepointUtils() {}

}
