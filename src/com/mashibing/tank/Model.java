package com.mashibing.tank;
import com.mashibing.tank.GameObject.GameObject;

import java.util.Map;

public interface Model {
    //注意, 若定义成static方法 --> 必须写方法体

    Map<Integer, GameObject> getTanks();
    Map<Integer, GameObject> getBullets();
    Map<Integer, GameObject> getWalls();
    Map<Integer, GameObject> getExplodes();

    GameFactory getFactory();

}
