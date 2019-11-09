package pers.chxuan.jrpc.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import pers.chxuan.jrpc.codec.NetworkMessageDecoder;
import pers.chxuan.jrpc.codec.NetworkMessageEncoder;

public class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new NetworkMessageEncoder());
        socketChannel.pipeline().addLast(new NetworkMessageDecoder());
        socketChannel.pipeline().addLast(new TcpConnection());
    }
}
