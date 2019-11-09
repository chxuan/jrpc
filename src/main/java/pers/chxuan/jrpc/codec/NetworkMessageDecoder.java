package pers.chxuan.jrpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import pers.chxuan.jrpc.entity.NetworkMessage;

public class NetworkMessageDecoder extends LengthFieldBasedFrameDecoder {

    public NetworkMessageDecoder() {
        super(50 * 1024 * 1024,
                0,
                4,
                -4,
                0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
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

        return message;
    }
}
