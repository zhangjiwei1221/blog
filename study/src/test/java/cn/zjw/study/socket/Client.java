package cn.zjw.study.socket;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Client
 *
 * @author zjw
 * @createTime 2021/2/25 13:03
 */
public class Client {

    public static void main(String[] args) throws IOException {
//        Socket socket = new Socket("localhost", 8888);
//        Scanner sc = new Scanner(System.in);
//        String str = sc.next();
//        socket.getOutputStream().write(str.getBytes(StandardCharsets.UTF_8));
//        socket.getOutputStream().flush();
        post("http://localhost/socket/123");
    }

    public static void post(String path) {
        try {
            URL u = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
