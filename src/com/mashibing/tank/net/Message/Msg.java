package com.mashibing.tank.net.Message;

import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Model;

/**
 * 各种事件发生时的消息接口
 */

public interface Msg {
    void setModel(Model model);
    void handle();
    byte[] toBytes();
    void parse(byte[] bytes);
    MsgType getMsgType();

}
