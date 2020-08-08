package com.mashibing.tank.net.Client;

import com.mashibing.tank.FrameWork.TankFrame;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Group;
import com.mashibing.tank.net.Message.Msg;
import com.mashibing.tank.net.Message.ObjectJoinMsg;
import com.mashibing.tank.net.Message.ObjectType;
import com.mashibing.tank.net.MsgDecoder;
import com.mashibing.tank.net.MsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class Client {

	private Channel channel = null;
	public static final Client INSTANCE = new Client();
	public static final GameModel ClientModel = TankFrame.INSTANCE.GM;


	public void connect() {

		EventLoopGroup group = new NioEventLoopGroup(1);

		Bootstrap b = new Bootstrap();

		try {
			ChannelFuture f = b.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ClientChannelInitializer())
					.connect("localhost", 8888);

			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						System.out.println("not connected!");
					} else {
						System.out.println("connected!");
						// initialize the channel
						channel = future.channel();
					}
				}
			});

			f.sync();
			// wait until close
			f.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public void send(Msg msg) {
		channel.writeAndFlush(msg);
	}

	/*public static void main(String[] args) throws Exception {
		Client c = new Client();
		c.connect();
	}*/

	/*public void closeConnect() {
		this.send("_bye_");
		//channel.close();
	}*/

}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
			.addLast(new MsgEncoder())
			.addLast(new MsgDecoder())
			.addLast(new ClientHandler());
	}

}


class ClientHandler extends SimpleChannelInboundHandler<Msg> {

	/**
	 * 客户端: channelActive后不做任何处理, 先接收Server端的消息初始化游戏物体
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//ctx.writeAndFlush(new ObjectJoinMsg(ObjectType.TANK, TankFrame.INSTANCE.GM.getMyTank()) );
		//ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.myTank));
	}


	/**
	 * 收到服务器端消息, 新物体消息和动作消息分开处理
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx,  Msg msg) throws Exception {
		if(msg instanceof ObjectJoinMsg) {
			handleMsg(msg);
		}

		msg.handle();
	}

	private void handleMsg(Msg msg){
		msg.setModel(Client.INSTANCE.ClientModel);
		//((ObjectJoinMsg)msg).setGroup(Group.BAD);
		//msg.getObject().setModel(Client.INSTANCE.ClientModel);
	}


}
