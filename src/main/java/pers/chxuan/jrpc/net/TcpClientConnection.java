package pers.chxuan.jrpc.net;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;

import java.util.concurrent.TimeUnit;

public class TcpClientConnection extends TcpConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpClientConnection.class);

    private static final int RECONNECT_INTERVAL = 3;

    private TcpClient tcpClient;

    public TcpClientConnection(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public boolean send(NetworkMessage message) {
        if (super.ctx.channel().isActive()) {
            ChannelFuture future = super.ctx.writeAndFlush(message);
            future.addListener((ChannelFuture channelFuture) -> {
                if (channelFuture.isSuccess()) {
                    LOGGER.info("发送消息成功,socketKey:{},name:{}", super.getSocketKey(), message.getName());
                } else {
                    LOGGER.info("发送消息失败,socketKey:{},name:{}", super.getSocketKey(), message.getName());
                    channelFuture.channel().close();
                }
            });

            return true;
        }

        return false;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.ctx = ctx;
        tcpClient.connectSuccessCallback();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("与服务器断开连接,socketKey:{}", super.getSocketKey());
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("与服务器断开连接,正尝试重连...");

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                tcpClient.disconnectCallback();
            }
        }, RECONNECT_INTERVAL, TimeUnit.SECONDS);

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("连接发生异常,exception:{}", cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NetworkMessage message = (NetworkMessage) msg;
        tcpClient.receivedNetworkMessageCallBack(message);
    }
}
