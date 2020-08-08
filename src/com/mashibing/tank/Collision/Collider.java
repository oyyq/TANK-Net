package com.mashibing.tank.Collision;

import com.mashibing.tank.GameObject.GameObject;

import java.io.Serializable;

public interface Collider extends Serializable {


    boolean collide(GameObject o1, GameObject o2);

}
