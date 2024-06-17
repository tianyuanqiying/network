# 服务器模型
- 主机 Host , 即一台电脑，特指连接网络的电脑
- 服务器 Server , 提供服务的主机或程序
- 客户端 Client ,  请求服务的主机或程序
- 服务 Service , 例如, 客户端请求下载一个文件，服务器提供返回这个文件的数据，则称为“文件下载服务”

特点：
- 一对多，地位不对等
- 客户端主动，服务器被动
- 服务器是常开的
- 客户端之间没有交互


# IP地址与端口

- IP地址：服务器的主机地址 x.x.x.x 格式
- 端口：    服务号，界于 1 ~ 65535 之间

简单地说，IP地址用于标识一台主机，而端口用于标识该主机上的一个服务。( 类比：公司电话 + 分机号 )
## IP地址
IP地址的格式 : x.x.x.x   , 由4个整数组成

每个x的范围在 0~255 之间

比如，192.168.0.100，101.200.181.153、
  
如果超出255，就是一个错误的IP，如 300.1.256.0

## 端口
端口 Port ，代表一个服务号码

此号码介于 1 ~ 65535 之间，但是 1 ~ 1024 之内的服务基本已被系统服务占用。

在自己新开服务时，可以在 2000-10000 之间找一个端口
## 端口占用
检查端口占用： netstat -ano | find "2019"  

其中，这里是检查 2019 端口有没有被占用


关闭我们的Server，用netstat命令检查端口占用

开启我们的Server，再用netstat检查一下 …

连续运行2次Server程序，则第2次运行会报错：

端口占用错误： Address already in use: JVM_Bind
## Server 与 Client

对于服务器来说，开启服务时要申请端口号

ServerSocket serverSock = new ServerSocket(2019);

对于客户端来说，连接服务时要指定目标地址

sock.connect( new InetSocketAddress("127.0.0.1",2019));


# 代码框架
## 服务端框架代码
1 申请一个服务端口 

    serverSock = new ServerSocket(2019)

2 accept 等待连接

    Socket conn = serverSock.accept();
  当有人连接至服务器时，accept()返回并得到一路连接

其中，ServerSocket代表一个服务，Socket代表一路连接

3 读写 Socket 连接

    inputStream = conn.getInputStream()
    outputStream = conn.getOutputStream()

4 关闭连接

    conn.close()

5 等待下一个客户端的连接 accept()

## 客户端代码框架
客户端要简单一些，当需要服务时，发起连接即可

1 连接至服务器

     sock.connect() 

2 发达请求、接收应答

     inputStream = conn.getInputStream()
     outputStream = conn.getOutputStream()

3 关闭连接 sock.close() 
