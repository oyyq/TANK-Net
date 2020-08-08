package com.mashibing.tank.net;

import java.util.List;

import com.mashibing.tank.net.Message.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 解码器, 解码成具体的Msg子类
 */
public class MsgDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		//读出MsgType以及Msg长度 --> 8字节
		if (in.readableBytes() < 8) return;
		//根据注释, 标记当前的readIndex, 在resetReaderIndex时就重置回mark的位置
		in.markReaderIndex();

		MsgType type = MsgType.values()[in.readInt()];
		int Msglen = in.readInt();

		if (in.readableBytes() < Msglen) {
			in.resetReaderIndex();
			return;
		}

		byte[] bytes = new byte[Msglen];
		//将ByteBuf的数据读入bytes中
		in.readBytes(bytes);

		Msg msg = MsgFactory.createMsg(type, bytes);

		out.add(msg);

	}

}
