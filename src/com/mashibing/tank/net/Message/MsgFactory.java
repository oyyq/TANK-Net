package com.mashibing.tank.net.Message;


/**
 * 生产具体的Msg子类
 */

public class MsgFactory {

    public static Msg createMsg(MsgType type, byte[] bytes) {
        Msg msg ;

        switch (type){
            case ObjectJoin:
                msg = new ObjectJoinMsg();
                break;
            case TankMove:
                msg = new TankMoveMsg();
                break;
            case TankFire:
                msg = new TankFireMsg();
                break;
            case DirChange:
                msg = new TankDirChangeMsg();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        msg.parse(bytes);
        return msg;
    }

}
