package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.net.ConnectException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import af.swing.AfPanel;
import af.swing.border.AfBorder;
import af.swing.layout.AfRowLayout;
import af.swing.thread.AfShortTask;


public class MainFrame extends JFrame
{		
	JTextField cmdField = new JTextField();
	JTextArea  logField = new JTextArea();
	
	public MainFrame(String title)
	{
		super(title);

		AfPanel root = new AfPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
		root.padding(4);
		
		// 上面：命令输入区
		AfPanel top = new AfPanel();
		top.padding(4,0,4,0); // 设置填充 
		top.setPreferredSize(new Dimension(0,36)); // 设置高度
		root.add(top, BorderLayout.PAGE_START);
		
		top.setLayout(new AfRowLayout(10));
		top.add(cmdField, "1w");
		
		JButton sendButton = new JButton("发送");
		top.add(sendButton, "auto");
			
		// 中间：日志显示区
		JScrollPane scroll = new JScrollPane(logField);
		root.add(scroll, BorderLayout.CENTER);
		logField.setEditable(false);
		logField.setFont(logField.getFont().deriveFont(14.0f)); // 字体大小
		AfBorder.addPadding(logField, 8); // 设置填充
		
		
		// 按钮事件处理
		sendButton.addActionListener( (e)->{
			// 取得用户输入的命令
			String cmd = cmdField.getText().trim();
			if(cmd.length() > 0)
			{
				// 创建工作线程
				new SendCmdTask(cmd).start();
			}			
		});		
	}
	
	private class SendCmdTask extends Thread
	{
		String cmd ;
		
		public SendCmdTask(String cmd)
		{
			this.cmd = cmd;
		}
		
		@Override
		public void run()
		{
			ClientConnection conn = new ClientConnection();
			try {
				doSend(conn);
			}
			catch(ConnectException e)
			{
				showMessage("无法连接服务器!");
			}
			catch(Exception e)
			{
				showMessage("出错: " + e.getMessage());
			}
			
	        // 关闭连接
	        conn.close();
	        showMessage("\n");
		}
		
		private void doSend(ClientConnection conn) throws Exception
		{
			// 连接服务器
			conn.connect( "127.0.0.1", 2019);
            
	        // 发送请求
			JSONObject jreq = new JSONObject();
			jreq.put("cmd", cmd);
	        showMessage(">> " + jreq.toString());
			conn.sendString(jreq.toString());
			
			// 接收应答
			String response = conn.recvString();
			JSONObject jresp = JSONObject.parseObject(response);
			int status = jresp.getIntValue("status");
			if(status != 0)
			{
				String reason = jresp.getString("reason");
				throw new Exception(reason);
			}
			else
			{
				showMessage("<< ");
				JSONArray jdata = jresp.getJSONArray("data");
				for(int i=0; i< jdata.size(); i++)
		        {
		        	String item = jdata.getString(i);
		        	showMessage("* " + item);
		        }
			}	        	     
		}
		
		// 显示消息到界面
		public void showMessage(String msg)
		{
			SwingUtilities.invokeLater( ()->{
				logField.append(msg + "\n");
			});
		}
	}
}
