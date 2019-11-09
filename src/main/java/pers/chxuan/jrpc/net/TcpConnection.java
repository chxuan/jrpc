package pers.chxuan.jrpc.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class TcpConnection extends ChannelInboundHandlerAdapter {

    protected ChannelHandlerContext ctx;

    private String socketKey = "";

    protected String getSocketKey() {
        if (socketKey.isEmpty()) {
            if (ctx != null) {
                socketKey = getAddress(ctx.channel().localAddress()) + "_" + getAddress(ctx.channel().remoteAddress());
            }
        }

        return socketKey;
    }

    protected String getAddress(SocketAddress address) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
        return inetSocketAddress.getHostString() + ":" + inetSocketAddress.getPort();
    }
}
