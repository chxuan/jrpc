package pers.chxuan.jrpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pers.chxuan.jrpc.entity.NetworkMessage;

public class NetworkMessageEncoder extends MessageToByteEncoder<NetworkMessage> {

//    private static final int FIXED_LENGHT = 4 + 4 + 4 + 4;

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          NetworkMessage in,
                          ByteBuf out) throws Exception {

        out.writeInt(in.getTotalLength());
        out.writeInt(in.getSerial());

        out.writeInt(in.getMessageClassNameLength());
        if (in.getMessageClassNameLength() > 0) {
            out.writeBytes(in.getMessageClassName().getBytes());
        }

        out.writeInt(in.getContentLength());
        out.writeBytes(in.getContent());
    }
}
