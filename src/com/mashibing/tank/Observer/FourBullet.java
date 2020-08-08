package com.mashibing.tank.Observer;



import com.mashibing.tank.Strategies.FireStrategy;
import com.mashibing.tank.Strategies.FourDirectionFire;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_CONTROL;


public class FourBullet implements TankFireObserver {
        FireStrategy fireStrategy = new FourDirectionFire();
        @Override
        public boolean actionOnFire(fireEvent e) {
            KeyEvent event = e.getEvent();
            int key = event.getKeyCode();
            if(key == VK_CONTROL){
                fireStrategy.fire(e.getTank());
                return false;
            }
            return true;
        }

}
