package example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
//		ByteBuf in = (ByteBuf)msg;
//		try {
//			while(in.isReadable()) {
//				System.out.print((char) in.readByte());
//				System.out.flush();
//			}
//		} finally  {
//			ReferenceCountUtil.release(msg);
//		}
		
		//echo server
/*		StringBuilder sBuilder = new StringBuilder();
		ByteBuf in = (ByteBuf)msg;
		try {
			while(in.isReadable()) {
				char cur = (char)in.readByte();
				if (cur != ' ') {
					sBuilder.append(cur);
				} else {
					System.out.print(sBuilder.toString());
					sBuilder = new StringBuilder();
					System.out.flush();
				}
			}
		} finally  {
			ReferenceCountUtil.release(msg);
		}*/
//		ctx.write(msg);
//		ctx.flush();
		ctx.writeAndFlush(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		//Depends on how you want to deal with errors
		cause.printStackTrace();
		ctx.close();
	}
}
