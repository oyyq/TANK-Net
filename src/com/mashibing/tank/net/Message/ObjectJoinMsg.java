package com.mashibing.tank.net.Message;


import com.mashibing.tank.*;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Serialize.SerializeUtil;
import com.mashibing.tank.Strategies.FireStrategy;

import java.io.*;
import java.util.UUID;


/**
 * Server端增加维护GameObject, 不采用序列化的方式 --> 由于对象持有GameModel引用, 太大
 * 为保证新加入的Client是低延迟的, 我们要将队列中的GameObject装在一条ObjectJoinMsg中发送出去 --> TODO 一次性发送队列失败了..
 */

public class ObjectJoinMsg implements Msg {

    private Model model = null;
    public final MsgType msgType = MsgType.ObjectJoin;
    private ObjectType type;
    private Integer hashCode;
    private GameObject object;

    public ObjectJoinMsg(){
    }

    public ObjectJoinMsg(ObjectType type,  Integer hashCode){
        this.type = type;
        this.hashCode = hashCode;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void handle() {
        this.hashCode = object.IntHashCode();
        //将GameObject放入Model
        object.joinModel(model);
    }

    @Override
    public byte[] toBytes() {

            ByteArrayOutputStream baos = null;
            DataOutputStream dos = null;
            byte[] bytes = null;
            try {
                baos = new ByteArrayOutputStream();
                dos = new DataOutputStream(baos);
                GameObject value;

                switch (type) {
                    case TANK:
                        {
                            value = model.getTanks().get(hashCode);
                            //GameObject的type
                            dos.writeInt(type.ordinal());
                            //uuid, 16bytes, 高8bytes, 低8bytes
                            dos.writeLong(value.getId().getMostSignificantBits());
                            dos.writeLong(value.getId().getLeastSignificantBits());
                            //x, y
                            dos.writeInt(value.getX());
                            dos.writeInt(value.getY());

                            dos.writeInt(value.getDir().ordinal());
                            dos.write(SerializeUtil.serialize(((Tank) value).getFireStrategy()));
                        }
                        break;
                    case BULLET:
                        {
                            value = model.getBullets().get(hashCode);
                            //GameObject的type
                            dos.writeInt(type.ordinal());
                            //uuid, 16bytes, 高8bytes, 低8bytes
                            dos.writeLong(value.getId().getMostSignificantBits());
                            dos.writeLong(value.getId().getLeastSignificantBits());
                            //x, y
                            dos.writeInt(value.getX());
                            dos.writeInt(value.getY());

                            dos.writeLong(((Bullet) value).source.getMostSignificantBits());
                            dos.writeLong(((Bullet) value).source.getLeastSignificantBits());
                            dos.writeInt(value.getDir().ordinal());
                        }
                        break;
                    case EXPLODE:
                        {
                            value = model.getExplodes().get(hashCode);
                            //GameObject的type
                            dos.writeInt(type.ordinal());
                            //uuid, 16bytes, 高8bytes, 低8bytes
                            dos.writeLong(value.getId().getMostSignificantBits());
                            dos.writeLong(value.getId().getLeastSignificantBits());
                            //x, y
                            dos.writeInt(value.getX());
                            dos.writeInt(value.getY());

                            dos.writeInt(((Explode)value).getStep() );
                        }
                        break;
                    case WALL:
                        {
                            value = model.getWalls().get(hashCode);
                            //GameObject的type
                            dos.writeInt(type.ordinal());
                            //uuid, 16bytes, 高8bytes, 低8bytes
                            dos.writeLong(value.getId().getMostSignificantBits());
                            dos.writeLong(value.getId().getLeastSignificantBits());
                            //x, y
                            dos.writeInt(value.getX());
                            dos.writeInt(value.getY());
                        }
                        break;
                    default:
                        throw new IllegalStateException("不合法物体类型 !");
                }

                dos.flush();
                bytes = baos.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    baos.close();
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return bytes;

    }

    /**
     * 传递进来的bytes是完整消息
     * 赋给info, 在handle做处理
     * @param bytes
     */
    @Override
    public void parse(byte[] bytes) {
        //GameObject object = null;
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            ObjectType type = ObjectType.values()[dis.readInt()];
            this.type = type;                   //接收Object类型
            UUID uuid = new UUID(dis.readLong(), dis.readLong());
            int x = dis.readInt();
            int y = dis.readInt();

            Dir dir;

            switch (type){
                case TANK:
                    {
                        dir = Dir.values()[dis.readInt()];
                        FireStrategy fireStrategy = (FireStrategy) SerializeUtil.unserialize(dis.readAllBytes());
                        object = new Tank(x, y, dir, Group.BAD, model);
                        ((Tank) object).setFireStrategy(fireStrategy);
                    }
                    break;
                case BULLET:
                    {
                        UUID source = new UUID(dis.readLong(), dis.readLong());
                        dir = Dir.values()[dis.readInt()];
                        object = new Bullet(source, x, y, dir, Group.BAD, model);

                    }
                    break;
                case EXPLODE:
                    {
                        int step = dis.readInt();
                        object = new Explode(x, y, step, model);
                    }
                    break;
                case WALL:
                    object = new Wall(x,y, model);
                    break;
                default:
                    throw new IllegalStateException("不合法物体类型 !");
            }

            //标记id
            object.setId(uuid);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public MsgType getMsgType() {
        return msgType;
    }

}
