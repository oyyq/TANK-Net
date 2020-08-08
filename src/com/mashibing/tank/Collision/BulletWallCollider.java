package com.mashibing.tank.Collision;


import com.mashibing.tank.Bullet;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Wall;

public class BulletWallCollider  implements Collider{
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if( o1 instanceof Bullet && o2 instanceof Wall ) {
            Bullet bullet = (Bullet) o1;
            Wall wall = (Wall) o2;
            if(bullet.rect.intersects(wall.rect)) {
                bullet.die();  wall.die();
                return false;
            }
        }else if(o2 instanceof Bullet && o1 instanceof Wall)
            return collide(o2, o1);

        return true;
    }
}
