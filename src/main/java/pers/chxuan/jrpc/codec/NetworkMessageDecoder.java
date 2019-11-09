package pers.chxuan.jrpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import pers.chxuan.jrpc.entity.NetworkMessage;

import java.util.List;

public class NetworkMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext context,
                          ByteBuf in,
                          List<Object> out) throws Exception {

        NetworkMessage message = new NetworkMessage();

        message.setTotalLength(in.readInt());
        message.setSerial(in.readInt());

        message.setMessageClassNameLength(in.readInt());
        if (message.getMessageClassNameLength() > 0) {
            ByteBuf byteBuf = in.readBytes(message.getMessageClassNameLength());
            message.setMessageClassName(byteBuf.toString(CharsetUtil.UTF_8));
            ReferenceCountUtil.release(byteBuf);
        }

        message.setContentLength(in.readInt());
        if (message.getContentLength() > 0) {
            byte[] content = new byte[message.getContentLength()];
            in.readBytes(content);
            message.setContent(content);
        }

        out.add(message);
    }
}
