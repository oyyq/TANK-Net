package com.mashibing.tank.Collision;


import com.mashibing.tank.Bullet;
import com.mashibing.tank.Explode;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Tank;

/**
 * TODO 修改是: Bullet不与和自己有相同id的Tank碰撞
 */
public class BulletTankCollider implements Collider{

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if ((o1 instanceof Bullet && o2 instanceof Tank) ){

            if(o1.getGroup() == o2.getGroup()) return false;
            if( ( ( (Bullet) o1).rect ).intersects( ( (Tank) o2).rect ) )
            {
                o1.die();
                o2.die();
                int ex = o1.getX();
                ex+= Tank.WIDTH/2- Explode.WIDTH/2;
                int ey = o2.getY();
                ey+= Tank.HEIGHT/2- Explode.HEIGHT/2;
                ((Bullet)o1).gm.getFactory().createExplode(ex, ey,  ( (Bullet) o1).gm  );

            }

            //Bullet和Tank没碰撞上, false --> 碰撞链继续处理, 其实后面的handler也没进行处理
            return false;

        }else if ( o1 instanceof Tank && o2 instanceof Bullet ){
            return collide(o2, o1);
        }
        return true;
    }

}
