
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 传输字符串
 */
public class MyClient01
{

	public static void main(String[] args) throws Exception
	{
		// 一个Socket代表一路连接
        Socket sock = new Socket();
        
        // 连接至服务器
        sock.connect( new InetSocketAddress("127.0.0.1",2019));
        
        // InputStream用于接收数据, OutputStream用于发送数据
        InputStream inputStream= sock.getInputStream();
        OutputStream outputStream = sock.getOutputStream();
        
        // 发送数据
        String outputMsg = "阿发你好";
        byte[] outputData = outputMsg.getBytes("UTF-8");
        outputStream.write(outputData);
        System.out.println(">>: " + outputMsg);
        
        // 接收数据
        byte[] inputData = new byte[4000];
        int n = inputStream.read(inputData);
        String inputMsg = new String(inputData, 0,n,"UTF-8");
        System.out.println("<<: " + inputMsg);
        
        // 关闭连接
        sock.close();
	}

}
