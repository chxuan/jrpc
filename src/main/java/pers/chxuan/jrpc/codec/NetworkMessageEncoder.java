package pers.chxuan.jrpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pers.chxuan.jrpc.entity.NetworkMessage;

public class NetworkMessageEncoder extends MessageToByteEncoder<NetworkMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          NetworkMessage in,
                          ByteBuf out) throws Exception {

        out.writeInt(in.getTotalLength());
        out.writeInt(in.getSerial());

        out.writeInt(in.getNameLength());
        if (in.getNameLength() > 0) {
            out.writeBytes(in.getName().getBytes());
        }

        out.writeInt(in.getContentLength());
        out.writeBytes(in.getContent());
    }
}
