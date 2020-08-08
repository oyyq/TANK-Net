package com.mashibing.tank.Strategies;

import com.mashibing.tank.*;
import com.mashibing.tank.GameObject.GameObject;

/**
 * 默认开火策略，将其做成单例，这样传递给Tank.fire()方法时，不会每次new出一个新对象
 */
public class DefaultFireStrategy  implements FireStrategy {
    @Override
    public void fire(GameObject t) {

        int bX = (int) (t.getX()+ 0.5*t.getWIDTH()- Bullet.WIDTH*0.5);
        int bY = (int) (t.getY()+ 0.5*t.getHEIGHT()- Bullet.HEIGHT*0.5);
        //new GameModel.Bullet(bX, bY, t.getDir(), t.getGroup(),  t.getTf());          //子弹打出方向与当前坦克方向相同, 目前子弹只是从tank图片的边角打出
        DefaultFactory.getInstance().createBullet(t.getId(), bX, bY, t.getDir(), t.getGroup(), ((Tank)t).gm);
        if(t.getGroup() == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();

    }
    public DefaultFireStrategy(){}
    private static class StrategyHolder{
        private final static DefaultFireStrategy INSTANCE = new DefaultFireStrategy();
    }
    public static DefaultFireStrategy getInstance(){
        return StrategyHolder.INSTANCE;
    }

}
