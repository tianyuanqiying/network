import com.alibaba.fastjson2.JSONArray;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器服务
 */
public class WebServer {
    private ServerSocket serverSocket;
    private Integer port;
    public WebServer(Integer port) {
        this.port = port;
    }

    public void startService() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();

                ClientConnection connection = new ClientConnection(clientSocket);

                String msg = connection.received();

                String outputMsg = handle(msg);

                connection.send(outputMsg);

                connection.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String handle(String inputMsg) {
        JSONArray array = new JSONArray();
        if (inputMsg.equals("food")) {
            array.add("rice");
            array.add("mantou");
        }else if (inputMsg.equals("drink")) {
            array.add("water");
            array.add("cafe");
        }
        return array.toString();
    }
}
