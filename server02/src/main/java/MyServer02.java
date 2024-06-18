import com.alibaba.fastjson2.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传输对象，利用JSON序列化与反序列化
 */
public class MyServer02
{

	public static void main(String[] args) throws Exception
	{
		// 建立服务器, 服务于 2019MyServer01 端口
        ServerSocket serverSock = new ServerSocket(2019);
        System.out.println("服务器启动,等待连接...");
        while(true)
        {
        	// 监听请求,阻塞等待,直接有客户端发起连接 ...
            Socket conn = serverSock.accept();
            
            // 从连接里得到输入输出流
            // InputStream: 可以读取客户端发来的数据
            // OutputStream: 可以发送数据给客户端
            InputStream inputStream = conn.getInputStream();
            OutputStream outputStream = conn.getOutputStream();
            
            // 接收数据
            byte[] inputData = new byte[4000];
            int n = inputStream.read(inputData);
            if( n<=0)
            {
            	conn.close();
            	continue;
            }
            
            // 发送数据
            String inputMsg = new String(inputData, 0, n, "UTF-8");
            Student student = JSONObject.parseObject(inputMsg, Student.class);
            System.out.println("收到数据：" + student.toString());

            byte[] outputData = inputMsg.getBytes("UTF-8");
            outputStream.write(outputData);
            System.out.println("ECHO: " + inputMsg + "\n");
            
            // 关闭连接
            conn.close();            
        }
      
	}

}
