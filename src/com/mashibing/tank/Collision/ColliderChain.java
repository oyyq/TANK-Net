package com.mashibing.tank.Collision;


import com.mashibing.tank.GameObject.GameObject;
import java.util.LinkedList;
import java.util.List;

/**
 * ColliderChain实现了Collider接口
 * 于是ColliderChain可以利用add方法和ColliderChain组合在一起
 * chain1.add(chain2)
 */
public class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>() ;
    public void add(Collider c){
        colliders.add(c);
    }

    /**
     * 碰撞链上的所有节点
     */
    public ColliderChain(){
        add(new BulletTankCollider());
        add(new TankTankCollider());
        add(new BulletWallCollider());
        add(new TankWallCollider());
    }

    //递归逻辑
    public boolean collide(GameObject o1, GameObject o2){
        for(int i = 0; i<colliders.size(); i++){
            if( !colliders.get(i).collide(o1, o2) ) return false;
        }
        return true;
    }

}
