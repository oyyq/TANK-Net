package com.mashibing.tank.net.Server;


import com.mashibing.tank.net.Message.Msg;
import com.mashibing.tank.net.Message.ObjectJoinMsg;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Server端要保存游戏物体,
 * TODO Server端转发GameObject die的消息--> 消息处理将GameObject living状态设置为false
 */

public class Server {

	public static final Server INSTANCE = new Server();
	public static final ServerModel model = ServerModel.getINSTANCE();


	public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	public void serverStart() {

		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			ChannelFuture f = b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pl = ch.pipeline();
						pl.addLast(new ServerChildHandler());
					}
				})
				.bind(8888)
				.sync();


			//抛出thread每隔25ms 更新ServerModel
			new Thread(()-> {
				while(true) {
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					model.update();
				}
			}).start();

			ServerFrame.INSTANCE.updateServerMsg("server started!");		//这里最好用个CountDownLatch

			f.channel().closeFuture().sync(); 					//return ChannelFuture TODO 阻塞

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}


/**
 * TODO 完善收发消息, 消息处理逻辑
 */
class ServerChildHandler extends ChannelInboundHandlerAdapter { //SimpleChannleInboundHandler Codec

	/**
	 * 有新Client连入, 直接发送Server保存副本
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		Server.clients.add(channel);
		//channel.writeAndFlush(new ObjectJoinMsg(hashCode));
	}


	//同一时间收到多条 --> 加锁
	@Override
	public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		handleMsg((Msg) msg);
		((Msg) msg).handle();

		//服务器转发消息
		Server.clients.writeAndFlush(msg);
	}


	private void handleMsg(Msg msg){
		msg.setModel(Server.INSTANCE.model);

		//msg.getObject().setModel(Server.INSTANCE.model);
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		Server.clients.remove(ctx.channel());
		ctx.close();
	}
	
	
}





