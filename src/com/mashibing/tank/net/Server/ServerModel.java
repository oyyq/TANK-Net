package com.mashibing.tank.net.Server;

import com.mashibing.tank.Collision.ColliderChain;
import com.mashibing.tank.GameFactory;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.Model;
import com.mashibing.tank.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServerModel implements Model {

    private static final ServerModel INSTANCE = new ServerModel();
    public static ServerModel getINSTANCE() {
        return INSTANCE;
    }
    ColliderChain chain = new ColliderChain();

    //存放所有游戏物体
    private Map<Integer, GameObject> explodes = new HashMap<>();
    private Map<Integer, GameObject> tanks = new HashMap<>();
    private Map<Integer, GameObject> bullets = new HashMap<>();
    private Map<Integer, GameObject> walls = new HashMap<>();

    static {
        INSTANCE.init();
    }

    private List<GameObject> objects = new ArrayList<>();

    @Override
    public Map<Integer, GameObject> getTanks() {
        return tanks;
    }

    @Override
    public Map<Integer, GameObject> getBullets() {
        return bullets;
    }

    @Override
    public Map<Integer, GameObject> getWalls() {
        return walls;
    }

    @Override
    public Map<Integer, GameObject> getExplodes() {
        return explodes;
    }


    @Override
    public GameFactory getFactory() {
        return null;
    }

    void init(){

        tanks.clear();
        bullets.clear();
        walls.clear();
        explodes.clear();

//        new Wall(150,150, this);
//        new Wall(550,150,this);
//        new Wall(300,300, this);
//        new Wall(550,300,this);

    }

    /**
     * 从ServerFrame调用Server.start()开始,
     * 抛出一个thread, 间隔25ms进行GameObject的updaet
     */
    public void update(){
        objects.clear();
        synchronized (tanks){
            objects.addAll(tanks.values());
        }
        synchronized (bullets){
            objects.addAll(bullets.values());
        }
        synchronized (walls){
            objects.addAll(walls.values());
        }

        //碰撞成功会调用GameObject.die()方法 --> 从Map中移除
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i+1; j < objects.size(); j++)
                chain.collide(objects.get(i), objects.get(j));
        }

    }



}
