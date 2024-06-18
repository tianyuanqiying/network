import com.alibaba.fastjson2.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 服务器应答
 */
public class MyServer03 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2019);

        while (true) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int read = inputStream.read(buffer);
            if (read <= 0) {
                continue;
            }

            String inputMsg = new String(buffer, 0, read, "UTF-8");
            System.out.println("received : " + inputMsg);

            JSONArray array = new JSONArray();
            if (inputMsg.equals("food")) {
                array.add("rice");
                array.add("mantou");
            }else if (inputMsg.equals("drink")) {
                array.add("water");
                array.add("cafe");
            }

            System.out.println("send : " + array.toString());
            byte[] outputMsg = array.toString().getBytes(Charset.forName("UTF-8"));
            outputStream.write(outputMsg);
        }
    }
}
