package swing;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/* 客户端的连接
 * 
 */
public class ClientConnection
{
	public String charset = "UTF-8";  // 字符集: UTF-8 或 GBK
	
	private Socket sock ;	
    private InputStream inputStream ;
    private OutputStream outputStream ; 
	private byte[] inputBuffer = new byte[4000]; // 用于接收的缓冲区
	
	// 连接至服务器
	public void connect(String ip, int port) throws Exception
	{
		sock = new Socket();
		sock.connect( new InetSocketAddress(ip,port));
        inputStream= sock.getInputStream();
        outputStream = sock.getOutputStream(); 
	}
	
	// 关闭连接
	public void close()
	{
		try {
			sock.close();
			sock = null;
		}catch(Exception e)
		{			
		}
	}
	
	// 发送字符串
	public void sendString(String msg) throws Exception
	{
		byte[] data = msg.getBytes( this.charset );
		outputStream.write(data);
	}
	
	// 接收字符串
	public String recvString() throws Exception
	{
		int n = inputStream.read(inputBuffer);
		String msg = new String(inputBuffer, 0, n, "UTF-8");
		return msg;
	}
}
