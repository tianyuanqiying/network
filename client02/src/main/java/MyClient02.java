
import com.alibaba.fastjson2.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 传输字符串
 */
public class MyClient02 {

    public static void main(String[] args) throws Exception {
        Socket sock = new Socket();

        sock.connect(new InetSocketAddress("127.0.0.1", 2019));
        InputStream inputStream = sock.getInputStream();
        OutputStream outputStream = sock.getOutputStream();
        Student student = new Student();
        student.setName("nihao");
        student.setAge(11);
        JSONObject from = JSONObject.from(student);


        byte[] outputData = from.toString().getBytes("UTF-8");
        outputStream.write(outputData);
        System.out.println(">>: " + from);

        // 接收数据
        byte[] inputData = new byte[4000];
        int n = inputStream.read(inputData);
        String inputMsg = new String(inputData, 0, n, "UTF-8");
        System.out.println("<<: " + inputMsg);

        // 关闭连接
        sock.close();
    }

}
