package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mashibing.tank.*;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.net.Message.*;

import com.mashibing.tank.net.MsgDecoder;
import com.mashibing.tank.net.MsgEncoder;
import com.mashibing.tank.net.Server.ServerModel;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.InternetProtocolFamily;
import org.junit.jupiter.api.Test;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;


class TankJoinMsgCodecTest {

	@Test
	void testEncoder() {
		//测试ObjectJoinMsg的codec
/*
		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgEncoder());

		Model gameModel = GameModel.getINSTANCE();

		Tank tank1 = new Tank(5,10, Dir.DOWN, Group.GOOD, gameModel);
		Tank tank2 = new Tank(2,9,Dir.RIGHT, Group.BAD, gameModel);
		Bullet bullet = new Bullet(tank1.getId(), 4,6, Dir.UP, Group.GOOD, gameModel);
		Wall wall = new Wall(12,14, gameModel);
		Explode explode = new Explode(36,54, 0, gameModel);

		ObjectJoinMsg msg = new ObjectJoinMsg("Server");
		msg.setModel(gameModel);

		ch.writeOutbound(msg);
		ByteBuf buf = (ByteBuf)ch.readOutbound();


		int typeOrdinal = buf.readInt();
		MsgType type = MsgType.values()[typeOrdinal];
		assertEquals(MsgType.ObjectJoin,  type);
		int len = buf.readInt();

		*/

/*
		ObjectType type1 = ObjectType.values()[buf.readInt()];
		UUID uuid = new UUID(buf.readLong(), buf.readLong());
		int x = buf.readInt();
		int y = buf.readInt();

		Group group = Group.values()[buf.readInt()];
		Dir dir = Dir.values()[buf.readInt()];
		int remained = buf.readableBytes();
		byte[] StrategyBytes = new byte[remained];
		buf.getBytes(buf.readerIndex(), StrategyBytes);

		FireStrategy fireStrategy = (FireStrategy) SerializeUtil.unserialize(StrategyBytes);
		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.DOWN, dir);
		assertEquals(Group.GOOD,group);
		assertEquals(id,uuid);
		assert fireStrategy instanceof FourDirectionFire;*/



		//ObjectDieCodec
		/*EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
				.addLast(new MsgEncoder());
		Tank tank = new Tank(5,10, Dir.DOWN, Group.GOOD, null);

		ObjectDieMsg msg = new ObjectDieMsg(ObjectType.TANK, tank.IntHashCode() );

		ch.writeOutbound(msg);
		ByteBuf buf = (ByteBuf)ch.readOutbound();

		int typeOrdinal = buf.readInt();
		MsgType type = MsgType.values()[typeOrdinal];
		assertEquals(MsgType.ObjectDie,  type);
		int len = buf.readInt();

		ObjectType type1 = ObjectType.values()[buf.readInt()];
		int hash = buf.readInt();
		assertEquals(type1, ObjectType.TANK);
		assertEquals(hash, tank.IntHashCode().hashCode());*/


		/*EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
				.addLast(new MsgEncoder());
		Tank tank = new Tank(5,10, Dir.DOWN, Group.GOOD, null);

		TankMoveMsg msg = new TankMoveMsg(tank.IntHashCode(), true);

		ch.writeOutbound(msg);
		ByteBuf buf = (ByteBuf)ch.readOutbound();

		int typeOrdinal = buf.readInt();
		MsgType type = MsgType.values()[typeOrdinal];
		assertEquals(MsgType.TankMove,  type);
		int len = buf.readInt();

		int hash = buf.readInt();
		boolean moving = buf.readBoolean();
		assertEquals(hash, tank.IntHashCode().intValue());
		assertEquals(moving, true);*/


	}


	@Test
	void testDecoder() {

		EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
			.addLast(new MsgDecoder());

		Model serverModel = ServerModel.getINSTANCE();

		assertEquals(serverModel.getTanks().size(), 0);
		assertEquals(serverModel.getBullets().size(), 0);
		assertEquals(serverModel.getExplodes().size(), 0);
		assertEquals(serverModel.getWalls().size(), 0);

		Tank tank1 = new Tank(5,10, Dir.DOWN, Group.GOOD, serverModel);
		Tank tank2 = new Tank(2,9, Dir.RIGHT, Group.BAD, serverModel);

//		Bullet bullet = new Bullet(tank1.getId(), 4,6, Dir.UP, Group.GOOD, serverModel);
//		Explode explode = new Explode(36,54, 0, serverModel);
//		Wall wall = new Wall(12,14, serverModel);

//		assertEquals(serverModel.getTanks().size(), 2);
//		assertEquals(serverModel.getBullets().size(), 1);
//		assertEquals(serverModel.getExplodes().size(), 1);
//		assertEquals(serverModel.getWalls().size(), 1);
//
//		Model gameModel = GameModel.getINSTANCE();
//		assertEquals(gameModel.getTanks().size(), 0);
//		assertEquals(gameModel.getBullets().size(), 0);
//		assertEquals(gameModel.getExplodes().size(), 0);
//		assertEquals(gameModel.getWalls().size(), 0);
//
//		ObjectJoinMsg msg;
//		for(Map.Entry<Integer, GameObject> entry: serverModel.getTanks().entrySet()) {
//			Integer key = entry.getKey();
//			GameObject value = entry.getValue();
//			msg = new ObjectJoinMsg( value.type, key );
//			msg.setModel(serverModel);
//
//			ByteBuf buf = Unpooled.buffer();
//			buf.writeInt(msg.getMsgType().ordinal());
//			byte[] bytes = msg.toBytes();
//			int len = bytes.length;
//			//System.out.println("Send Bytes " + len);
//			buf.writeInt(len);
//			buf.writeBytes(bytes);
//
//			ch.writeInbound(buf.duplicate());
//
//			ObjectJoinMsg msgR = ch.readInbound();
//			msgR.setModel(gameModel);
//			msgR.handle();
//
//		}






		//ObjectDieCodec
		/*EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
				.addLast(new MsgDecoder());

		Tank tank = new Tank(5, 10, Dir.DOWN, Group.GOOD, null);
		ObjectDieMsg msg = new ObjectDieMsg(ObjectType.TANK, tank.IntHashCode());

		ByteBuf buf = Unpooled.buffer();

		buf.writeInt(msg.getMsgType().ordinal());
		byte[] bytes = msg.toBytes();
		int len = bytes.length;
		buf.writeInt(len);
		buf.writeBytes(bytes);
		ch.writeInbound(buf.duplicate());

		ObjectDieMsg msgR = ch.readInbound();
		assertEquals(msgR.type, ObjectType.TANK);
		assertEquals(msgR.hashCode.intValue(), tank.IntHashCode().intValue() );
		*/

		/*EmbeddedChannel ch = new EmbeddedChannel();
		ch.pipeline()
				.addLast(new MsgDecoder());

		Tank tank = new Tank(5, 10, Dir.DOWN, Group.GOOD, null);
		TankMoveMsg msg = new TankMoveMsg(tank.IntHashCode(), true);

		ByteBuf buf = Unpooled.buffer();

		buf.writeInt(msg.getMsgType().ordinal());
		byte[] bytes = msg.toBytes();
		int len = bytes.length;
		buf.writeInt(len);
		buf.writeBytes(bytes);
		ch.writeInbound(buf.duplicate());

		TankMoveMsg msgR = ch.readInbound();
		assertEquals(msgR.type, ObjectType.TANK);
		assertEquals(msgR.hashCode.intValue(), tank.IntHashCode().intValue() );
		assertEquals(msgR.moving, true);*/




	}




}
