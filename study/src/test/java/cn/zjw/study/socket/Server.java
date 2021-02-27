package cn.zjw.study.socket;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;

/**
 * Server
 *
 * @author zjw
 * @createTime 2021/2/25 13:03
 */
public class Server {

    public static void post(String path, String data) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(data);
            out.flush();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            Socket socket = server.accept();
            byte[] bytes = new byte[1024];
            InputStream stream = socket.getInputStream();
            if (stream.read(bytes) != -1) {
                post("http://localhost/socket", "");
                System.out.println(new String(bytes));
            }

            stream.close();
            socket.close();
        }
    }

}
