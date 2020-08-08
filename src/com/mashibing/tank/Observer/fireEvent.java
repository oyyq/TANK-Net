package com.mashibing.tank.Observer;


import com.mashibing.tank.GameObject.GameObject;

import java.awt.event.KeyEvent;


/**
 * 坦克触发打出炮弹策略
 * 事件 / 坦克
 */

public class fireEvent{

    private GameObject tank;
    public boolean fire = true;
    private KeyEvent event;
    private fireEvent(){}
    private static final fireEvent INSTANCE = new fireEvent();

    public static fireEvent getINSTANCE(KeyEvent event, GameObject tank) {
        INSTANCE.event = event;
        INSTANCE.tank = tank;
        return INSTANCE;
    }

    public GameObject getTank() {
        return tank;
    }
    public KeyEvent getEvent() {
        return event;
    }

    public void launch(){           //应该采用监听者责任链模式, 事件耦合监听者进行相应
        ObserverChain.getObserverChain().actionOnFire(INSTANCE);
    }

}


