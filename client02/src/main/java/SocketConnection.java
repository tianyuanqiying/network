import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 */
public class SocketConnection {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Charset charset = Charset.forName("UTF-8");

    /**
     * 创建socket链接
     * @param ip 服务IP
     * @param port 服务端口号
     */
    public SocketConnection(String ip, Integer port) {
        try {
            this.socket = new Socket(ip, port);

            this.inputStream = socket.getInputStream();

            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param msg 消息内容
     */
    public void send(String msg) {
        try {
            System.out.println("发送 ：" + msg);
            this.outputStream.write(msg.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收数据
     * @return 消息字符串
     */
    public String received() {
        try {
            byte[] buffer = new byte[4096];
            int read = this.inputStream.read(buffer);
            if (read < 0) {
                return "";
            }
            return new String(buffer, 0, read, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭socket链接
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
