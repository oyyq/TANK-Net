package com.mashibing.tank.net.Message;

import com.mashibing.tank.GameFactory;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Model;
import java.io.*;
import java.util.Map;


/**
 * 在初始化Tank实例时我们就初始化不同
 * Client端Tank的fireStrategy
 * TankFireMsg只需处理fire动作 --> fire()
 */
public class TankFireMsg implements Msg{

    public final MsgType msgType = MsgType.TankFire;
    private ObjectType type = ObjectType.TANK;

    private Model model;
    private Integer hashCode;

    public TankFireMsg() {}

    public TankFireMsg(Integer hashCode){
        this.hashCode = hashCode;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Server端和Client端都需要handle()
     * 处理坦克fire的动作逻辑, 但是bullet的新增是在
     * ServerModel & GameModel分别处理, 不发送或处理
     * BulleJoinMsg
     */
    @Override
    public void handle() {
        Map<Integer, GameObject> tanks = this.model.getTanks();
        final GameObject tank;

        //锁的粒度应该尽量小
        synchronized (tanks){
            tank = tanks.get(hashCode);
        }

        if (tank == null){
            throw new IllegalStateException("坦克不存在 ??"); }
        else {
            tank.fire();
        }

    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeInt(hashCode.intValue());

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

    @Override
    public void parse(byte[] bytes) {

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.hashCode = Integer.valueOf(dis.readInt());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
