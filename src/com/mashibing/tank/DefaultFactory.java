package com.mashibing.tank;

import com.mashibing.tank.GameObject.GameObject;
import java.util.UUID;

public class DefaultFactory extends GameFactory {

    @Override
    public GameObject createTank(int x, int y, Dir dir, Group group, Model gm) {
        return new Tank(x, y, dir, group, gm);
    }

    @Override
    public GameObject createExplode(int x, int y, Model gm) {
        return new Explode(x, y,0, gm);
    }


    @Override
    public GameObject createBullet(UUID source, int x, int y, Dir dir, Group group, Model gm) {
        return new Bullet(source, x, y, dir, group,  gm) ;
    }

    public static final class factoryHolder{
        private static final DefaultFactory INSTANCE  = new DefaultFactory();
    }

    public static DefaultFactory getInstance(){
        return factoryHolder.INSTANCE;
    }

}
