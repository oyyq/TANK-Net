package com.mashibing.tank.net.Message;

import com.mashibing.tank.Dir;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Model;
import java.io.*;
import java.util.Map;


public class TankDirChangeMsg implements Msg{

    public final MsgType msgType = MsgType.DirChange;
    private ObjectType type = ObjectType.TANK;

    private Model model;
    private Integer hashCode;
    private Dir dir;


    public TankDirChangeMsg(Integer hashCode, Dir dir){
        this.hashCode = hashCode;
        this.dir = dir;
    }

    public TankDirChangeMsg() {
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * 消息处理逻辑
     * Server端和Client端都需要handle()
     */
    @Override
    public void handle() {
        Map<Integer, GameObject> tanks = this.model.getTanks();

        final GameObject tank;
        synchronized (tanks){
            tank = tanks.get(hashCode);
        }

        if(tank == null) {
            throw new IllegalStateException("坦克不存在 ??");
        }else {
            tank.setDir(dir);
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
            dos.writeInt(dir.ordinal());

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
            this.dir = Dir.values()[dis.readInt()];
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
