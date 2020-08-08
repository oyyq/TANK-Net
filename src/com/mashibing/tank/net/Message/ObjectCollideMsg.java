package com.mashibing.tank.net.Message;

import com.mashibing.tank.*;
import com.mashibing.tank.GameObject.GameObject;

import java.io.*;

/**
 * 不需要
 */

public class ObjectCollideMsg implements Msg {
    public Model model = null;             //Server端的Servermodel或者Client端的Gamemodel
    public ObjectType type;
    public Integer hashCode;
    public MsgType msgType = MsgType.ObjectDie;

    public ObjectCollideMsg() {}

    public ObjectCollideMsg(ObjectType type, Integer hashCode){
        this.type = type;
        this.hashCode = hashCode;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    //依据Object的类型找出存储队列, 检查待删除物体是否存在, 并调用GameObject.die()令其自行处理die逻辑
    //TODO 应对各队列加锁, 但是Die消息不发送, 因此不解决了
    @Override
    public void handle() {

    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            //TODO 碰撞消息




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

            type = ObjectType.values()[dis.readInt()];
            hashCode = Integer.valueOf(dis.readInt());
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
