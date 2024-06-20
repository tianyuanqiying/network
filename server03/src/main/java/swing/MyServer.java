package swing;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.net.ServerSocket;
import java.net.Socket;


public class MyServer
{
	ServerSocket serverSock;
	
	public MyServer()
	{		
	}
	
	public void startService() throws Exception
	{
		// 建立服务器, 服务于 2019 端口
		serverSock = new ServerSocket(2019);
        System.out.println("服务器启动,等待连接...");
        
        while(true)
        {
        	// 监听请求,阻塞等待,直接有客户端发起连接 ...
            Socket sock = serverSock.accept();
            
            ServerConnection conn = new ServerConnection(sock);                      
            String request = conn.recvString();
            System.out.println("GOT: " + request);
            
            /* 根据不同的的请求，作出相应的应答 */            
            String response = handleRequest(request);                       
            conn.sendString(response);
            
            // 关闭连接
            conn.close();            
        }
	}
	
	private String handleRequest(String request)
	{
		JSONObject jreq = JSONObject.parseObject(request);
		String cmd = jreq.getString("cmd").trim();
		
		// jresp: 应答
		JSONObject jresp = new JSONObject();
        if(cmd.equals("food"))
        {
        	JSONArray array = new JSONArray();
        	array.add("薯条");
        	array.add("鸡块");
        	array.add("小龙虾");
        	
        	jresp.put("status", 0);
        	jresp.put("data", array);
        }        
        else if(cmd.equals("drink"))
        {
        	JSONArray array = new JSONArray();
        	array.add("奶茶");
        	array.add("橙汁");
        	array.add("白可水");
        	
        	jresp.put("status", 0);
        	jresp.put("data", array);
        }
        else 
        {
        	jresp.put("status", -1);
        	jresp.put("reason", "不支持的命令类型: " + cmd);
        }
        
        return jresp.toString();
	}
	
	public static void main(String[] args) throws Exception
	{
		MyServer server = new MyServer();
		server.startService();     
	}

}
