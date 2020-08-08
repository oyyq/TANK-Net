package com.mashibing.tank.Collision;


import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Tank;
import com.mashibing.tank.Wall;

public class TankWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if( o1 instanceof Tank && o2 instanceof Wall){
            Tank tank =(Tank) o1;
            Wall wall = (Wall) o2;
            if(tank.getRect().intersects(wall.rect)){
                tank.Retreat();
                return false;
            }

        }else if (o2 instanceof Tank && o1 instanceof Wall ){
            return collide(o2, o1);
        }

        return true;
    }
}
