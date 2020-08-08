package com.mashibing.tank.net.Message;

import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Model;

import java.io.*;
import java.util.Map;

/**
 * 只有Tank需要手动控制Move
 */

public class TankMoveMsg implements Msg {

    private Model model;
    public ObjectType type = ObjectType.TANK;
    private final MsgType msgType = MsgType.TankMove;

    private Integer hashCode;
    private boolean moving;

    public TankMoveMsg() {}
    public TankMoveMsg(Integer hashCode, boolean moving) {
        this.hashCode= hashCode;
        this.moving = moving;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * ServerModel & Gamemodel需要handle() --> TankMove
     */
    @Override
    public void handle() {
        Map<Integer, GameObject> tanks = this.model.getTanks();

        //加锁处理
        final GameObject tank;
        synchronized (tanks) {
            tank = tanks.get(hashCode);
        }

        if (tank == null) {
            throw new IllegalStateException("坦克不存在 ??");
        } else {
            tank.setMoving(moving);
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
            dos.writeBoolean(moving);

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
            this.moving = dis.readBoolean();
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
