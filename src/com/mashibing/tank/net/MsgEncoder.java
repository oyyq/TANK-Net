package com.mashibing.tank.net;

import com.mashibing.tank.net.Message.Msg;
import com.mashibing.tank.net.Message.ObjectJoinMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 为什么需要编解码器?
 * 解决TCP协议的分包, 粘包问题
 */

public class MsgEncoder extends MessageToByteEncoder<Msg>{
	/**
	 * MsgEncoder以及MsgDecoder定义协议
	 * 由ChannelHandlerContext
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
		//消息类型, 消息有效长度
		buf.writeInt(msg.getMsgType().ordinal());
		byte[] bytes = msg.toBytes();
		int len = bytes.length;
		buf.writeInt(len);
		buf.writeBytes(msg.toBytes());
	}
	

}
