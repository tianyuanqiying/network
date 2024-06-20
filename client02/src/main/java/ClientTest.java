public class ClientTest {
    public static void main(String[] args) {
        SocketConnection connection = new SocketConnection("127.0.0.1", 2019);

        connection.send("food");

        String received = connection.received();
        System.out.println("接收 ：" + received);

        connection.close();
    }
}
