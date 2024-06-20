package console;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.Objects;

/**
 * 协议： 使用JSON方式发送字符串数据、解析返回的字符串数据，JSON中包含cmd指令、服务端接收JSON请求数据解析获取cmd指令处理，返回JSON数据、 JSON数据包含status，reason，data三个字段
 */
public class ClientTest {
    public static void main(String[] args) {
        SocketConnection connection = new SocketConnection("127.0.0.1", 2019);

        JSONObject jreq = new JSONObject();
        jreq.put("cmd", "food");
        connection.send(jreq.toString());

        String received = connection.received();

        System.out.println("接收 ：" + received);

        handleMsg(received);
        connection.close();
    }

    private static void handleMsg(String received) {
        JSONObject resp = JSONObject.parseObject(received);

        Object status = resp.get("status");
        if (Objects.nonNull(status) && !status.equals(200)) {
            System.out.println(resp.get("reason"));
            return;
        }

        Object data = resp.get("data");
        JSONArray jsonArray = JSONArray.of(data);
        System.out.println();
        System.out.println("data: " + jsonArray);
    }
}
