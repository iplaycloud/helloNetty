package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * Sharable表示此对象在channel间共享
 * handler类是我们的具体业务类
 * */
@ChannelHandler.Sharable//注解@Sharable可以让它在channels间共享
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /*
    * 接收到数据
    * */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {

        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        String body = new String(req, "UTF-8");

        System.out.println("The time server receive order:" + body);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        cause.printStackTrace();//捕捉异常信息
        ctx.close();//出现异常时关闭channel
    }
}
