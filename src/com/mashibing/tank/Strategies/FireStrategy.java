package com.mashibing.tank.Strategies;


import com.mashibing.tank.GameObject.GameObject;
import java.io.Serializable;

//开火策略
public interface FireStrategy extends Serializable {
    void fire(GameObject t);
}
