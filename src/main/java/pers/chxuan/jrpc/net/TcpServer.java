package pers.chxuan.jrpc.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpServer.class);

    private ServerBootstrap bootstrap = new ServerBootstrap();

    private EventLoopGroup bossGroup = new NioEventLoopGroup();

    private EventLoopGroup workerGroup;

    public TcpServer(int workerThreads) {
        workerGroup = new NioEventLoopGroup(workerThreads);
    }

    public boolean run(String ip, int port) {
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.localAddress(ip, port);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new CustomChannelInitializer());

        try {
            bootstrap.bind().sync();
            LOGGER.info("绑定端口成功,ip:{},port:{}", ip, port);
            return true;
        } catch (Exception e) {
            releaseResource();
            LOGGER.error("绑定本地服务端口失败,ip:{},port:{},exception:{}", ip, port, e.getMessage());
        }

        return false;
    }

    private void releaseResource() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
