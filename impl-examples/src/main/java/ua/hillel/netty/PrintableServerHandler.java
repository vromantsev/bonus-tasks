package ua.hillel.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PrintableServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            var sb = new StringBuilder();
            while (in.isReadable()) {
                sb.append((char) in.readByte());
                System.out.flush();
            }
            System.out.println("Received message: " + sb);
        } finally {
            in.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // exception handling
        ctx.close();
    }
}
