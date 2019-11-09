package pers.chxuan.jrpc.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpClient.class);

    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Bootstrap bootstrap;

    private String ip;

    private int port;

    public boolean connect(String ip, int port) {
        this.ip = ip;
        this.port = port;

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new CustomChannelInitializer());

        try {
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            if (channelFuture.isSuccess()) {
                LOGGER.info("连接服务成功,ip:{},port:{}", ip, port);
                return true;
            }
        } catch (Exception e) {
            LOGGER.info("连接服务失败,ip:{},port:{},exception:{}", ip, port, e.getMessage());
        }

        return false;
    }

    public void close() {
        try {
            workerGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
