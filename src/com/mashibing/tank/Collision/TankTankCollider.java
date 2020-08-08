package com.mashibing.tank.Collision;


import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Tank;

public class TankTankCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {

        if(o1 instanceof Tank && o2 instanceof Tank) {
            if (((Tank)o1).rect.intersects(((Tank) o2).rect)) {
                ((Tank) o1).Retreat();
                ((Tank) o2).Retreat();
                return false;
            }
            return false;
        }
        return true;

    }
}
