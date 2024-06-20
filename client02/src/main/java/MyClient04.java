import com.alibaba.fastjson2.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 请求客户端
 */
public class MyClient04 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 2019);

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        String msg = "food";
        outputStream.write(msg.getBytes(Charset.forName("UTF-8")));

        byte[] bytes = new byte[4096];
        int read = inputStream.read(bytes);
        if (read <= 0) {
            System.out.println("not received msg from server");
            return;
        }

        String inputMsg = new String(bytes, 0, read, Charset.forName("UTF-8"));
        JSONArray array = JSONArray.parse(inputMsg);
        System.out.println(array);
    }
}

