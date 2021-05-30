package v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 获取 mac 地址工具类 v2 版
 *
 * @date 2021/5/13
 * @author zjw
 */
public class MacUtil {

    private static final String WIN_PREFIX = "win";
    private static final String OS_NAME_PROPERTY = "os.name";
    private static final String WIN_COMMAND = "ipconfig /all";
    private static final String UNIX_COMMAND = "/sbin/ifconfig -a";
    private static final String MAC_REGEX = "(([a-f0-9]{2}-){5}|([a-f0-9]{2}:){5})[a-f0-9]{2}";
    private static final Pattern pattern = Pattern.compile(MAC_REGEX, Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        getMac().forEach(System.out::println);
    }

    /**
     * 根据不同操作系统执行不同命令
     * 获取本机 mac 地址集合
     *
     * @return mac 地址集合
     */
    private static List<String> getMac() {
        try {
            String osName = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
            if (osName.startsWith(WIN_PREFIX)) {
                return getMacByCommand(WIN_COMMAND);
            }
            return getMacByCommand(UNIX_COMMAND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 通过正则表达式提取执行命令得到的结果集中的 mac 地址
     * 并调整得到的 mac 地址的格式
     *
	 * @param command 查看网络信息的命令
     *
     * @return mac 地址集合
     */
    private static List<String> getMacByCommand(String command) throws IOException {
        List<String> macList = new ArrayList<>();
        List<String> strList = execCommand(command);
        for (String str : strList) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find() && matcher.end() == str.length()) {
                macList.add(matcher.group().replace(":", "-").toUpperCase());
            }
        }
        return macList;
    }

    /**
     * 执行命令并得到结果的每一行组成的字符串数组
     *
	 * @param command 查看网络信息的命令
     *
     * @return 执行命令返回的所有数据行
     */
    private static List<String> execCommand(String command) throws IOException {
        List<String> strList = new ArrayList<>();
        Process process = Runtime.getRuntime().exec(command);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return br.lines().collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        process.destroy();
        return strList;
    }

}
