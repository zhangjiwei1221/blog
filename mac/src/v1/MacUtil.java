package v1;

import java.net.NetworkInterface;
import java.util.*;

/**
 * 获取 mac 地址工具类 v1 版
 *
 * @date 2021/5/13
 * @author zjw
 */
public class MacUtil {

    public static void main(String[] args) {
        getMac().forEach(System.out::println);
    }

    /**
     * 获取本机 mac 地址集合
     *
     * @return mac 地址集合
     */
    public static List<String> getMac() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Optional.ofNullable(networkInterface.getHardwareAddress())
                        .ifPresent(mac -> list.add(format(mac)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将 mac 字节数组格式化为全大写并且使用 - 作为分隔符的字符串
     *
	 * @param mac 获取到的 mac 字节数组
     *
     * @return 格式化后的 mac 地址
     */
    private static String format(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (byte b : mac) {
            sb.append(String.format("%02X", b)).append("-");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
