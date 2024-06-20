package console;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
/**
 * 协议： 使用JSON方式发送字符串数据、解析返回的字符串数据，JSON中包含cmd指令、服务端接收JSON请求数据解析获取cmd指令处理，返回JSON数据、 JSON数据包含status，reason，data三个字段
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
        JSONObject jreq = JSONObject.parseObject(inputMsg);
        Object cmd = jreq.get("cmd");
        if (Objects.isNull(cmd)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("reason", "cmd is null");
            jsonObject.put("status", 500);
            return jsonObject.toString();
        }

        JSONObject jresp = new JSONObject();
        JSONArray array = new JSONArray();
        if (cmd.equals("food")) {
            array.add("rice");
            array.add("mantou");
            jresp.put("data", array);
            jresp.put("status", 200);
        }else if (cmd.equals("drink")) {
            array.add("water");
            array.add("cafe");
            jresp.put("data", array);
            jresp.put("status", 200);
        }else {
            jresp.put("reason", "cmd error");
            jresp.put("status", 500);
        }

        return jresp.toString();
    }
}
