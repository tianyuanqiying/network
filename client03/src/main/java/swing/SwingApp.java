package swing;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class SwingApp
{
	private static void createGUI()
	{
		// JFrame指一个窗口，构造方法的参数为窗口标题
		// 语法：因为MyFrame是JFrame的子类，所以可以这么写
		JFrame frame = new MainFrame("Swing Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// 设置窗口的其他参数，如窗口大小
		frame.setSize(500, 350);
		
		// 显示窗口
		frame.setVisible(true);		
	}
	
	public static void main(String[] args)
	{
		// 设置界面样式 Look And Feel
//		try {
//			UIManager.setLookAndFeel(
//					UIManager.getSystemLookAndFeelClassName());
//	    } catch(Exception ex)
//		{
//	    	ex.printStackTrace();
//		}
		
		// 此段代码间接地调用了 createGUI()，具体原理在 Swing高级篇 里讲解
		// 初学者先照抄此代码框架即可
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				createGUI();
			}
		});

	}
}
