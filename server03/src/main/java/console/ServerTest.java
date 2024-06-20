package console;

public class ServerTest {
    public static void main(String[] args) {
        WebServer webServer = new WebServer(2019);
        webServer.startService();
    }
}
