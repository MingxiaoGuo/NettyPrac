package example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{
	// will be invoked when a connection is established 
	// and ready to generate traffic
	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		// To send a new msg, need to allocate new buffer
		final ByteBuf time = ctx.alloc().buffer(4);
		time.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));
		// A ChannelFuture represents an I/O operation which has not yet occurred
		// All operations are asynchronous in Netty
		// The connection might close before a message is sent
		final ChannelFuture future = ctx.writeAndFlush(time);
		// The reason we need add a listener to channelfuture
		// is we have to make sure close() happens after operation is done
		//future.addListener(ChannelFutureListener.CLOSE);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture f) throws Exception {
				assert future == f;
				ctx.close();	
			}
		});
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
