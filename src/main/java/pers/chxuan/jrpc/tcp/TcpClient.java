package pers.chxuan.jrpc.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.codec.NetworkMessageDecoder;
import pers.chxuan.jrpc.codec.NetworkMessageEncoder;
import pers.chxuan.jrpc.entity.NetworkMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class TcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpClient.class);

    private Bootstrap bootstrap;

    private CountDownLatch countDownLatch;

    private TcpConnection connection;

    private String ip;

    private int port;

    protected AtomicInteger connectStatus = new AtomicInteger(TcpConnectStatus.NOT_CONNECT);

    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    protected boolean connect() {
        initEnv();
        return doConnect();
    }

    protected boolean send(NetworkMessage message) {
        if (connectStatus.get() == TcpConnectStatus.CONNECTED) {
            return connection.send(message);
        }

        return false;
    }

    protected void connectSuccessCallback() {
        countDownLatch.countDown();
    }

    protected void disconnectCallback() {
        doConnect();
    }

    protected abstract void receivedNetworkMessageCallBack(NetworkMessage message);

    private void initEnv() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new NetworkMessageEncoder());
                socketChannel.pipeline().addLast(new NetworkMessageDecoder());
                socketChannel.pipeline().addLast(createTcpClientConnection());
            }
        });
    }

    private boolean doConnect() {
        connectStatus.set(TcpConnectStatus.CONNECTING);

        try {
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            if (channelFuture.isSuccess()) {
                countDownLatch.await();
                connectStatus.set(TcpConnectStatus.CONNECTED);
                LOGGER.info("连接服务成功,socketKey:{}", connection.getSocketKey());

                return true;
            }
        } catch (Exception e) {
            LOGGER.info("连接服务失败,ip:{},port:{},exception:{}", ip, port, e.getMessage());
        }

        return false;
    }

    private TcpConnection createTcpClientConnection() {
        countDownLatch = new CountDownLatch(1);
        connection = new TcpClientConnection(this);
        return connection;
    }
}
