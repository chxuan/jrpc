package pers.chxuan.jrpc.tcp;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;

public class TcpServerConnection extends TcpConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpServerConnection.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("接收到客户端新连接,ip:{}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端下线,ip:{}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NetworkMessage message = (NetworkMessage) msg;
        LOGGER.info("接收到客户端消息,name:{}", message.getName());

        ctx.writeAndFlush(message);
    }
}
