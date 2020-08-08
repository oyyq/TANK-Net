package com.mashibing.tank.Strategies;

import com.mashibing.tank.*;
import com.mashibing.tank.GameObject.GameObject;

public class FourDirectionFire implements FireStrategy {

    @Override
    public void fire(GameObject t) {                    // t --> Tank
        int bX = (int) (t.getX()+ 0.5*t.getWIDTH()- Bullet.WIDTH*0.5);
        int bY = (int) (t.getY()+ 0.5*t.getHEIGHT()- Bullet.HEIGHT*0.5);
        Dir[] dirs = Dir.values();
        for (Dir dir: dirs){
            DefaultFactory.getInstance().createBullet(t.getId(), bX, bY, dir, t.getGroup(), ((Tank)t).gm);
        }

        if(t.getGroup() == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();

    }
    //将构造方法设置成private将无法通过Class.forName.newInstance()来构造一个对象
    public FourDirectionFire(){}
    private static class StrategyHolder{
        private final static FourDirectionFire INSTANCE = new FourDirectionFire();
    }
    public static FourDirectionFire getInstance(){
        return StrategyHolder.INSTANCE;
    }

}
