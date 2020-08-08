package com.mashibing.tank.Observer;


import com.mashibing.tank.Strategies.DefaultFireStrategy;
import com.mashibing.tank.Strategies.FireStrategy;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_Z;

public class SingleBullet implements TankFireObserver {
    FireStrategy fireStrategy = new DefaultFireStrategy();
    @Override
    public boolean actionOnFire(fireEvent e) {
        KeyEvent event = e.getEvent();
        int key = event.getKeyCode();
        if(key == VK_Z){
            fireStrategy.fire(e.getTank());
            return false;
        }

        return true;
    }
}
