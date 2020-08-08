package com.mashibing.tank;

import com.mashibing.tank.Collision.ColliderChain;
import com.mashibing.tank.FrameWork.TankFrame;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.ResourceManager.PropertyMgr;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameModel implements Serializable, Model {
    //单例
    public static GameModel getINSTANCE() {
        return INSTANCE;
    }
    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    private Map<Integer, GameObject> tanks = new HashMap<>();
    private Map<Integer, GameObject> bullets = new HashMap<>();
    private Map<Integer, GameObject> walls = new HashMap<>();
    private Map<Integer, GameObject> explodes = new HashMap<>();

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


    //游戏物体碰撞时, 将所有游戏物体装入objects处理碰撞逻辑
    private List<GameObject> objects = new ArrayList<>();

    public GameFactory factory  = DefaultFactory.getInstance();

    @Override
    public GameFactory getFactory() {
        return factory;
    }
    int initTankCount;
    GameObject myTank;
    ColliderChain chain = new ColliderChain();

    private GameModel(){ }

    private void init(){
        tanks.clear();
        bullets.clear();
        explodes.clear();
        walls.clear();

        //myTank = factory.createTank(0, 0, Dir.DOWN, Group.GOOD,  this);
    }

    public GameObject getMyTank() {
        return myTank;
    }

    public void paint(Graphics g) {

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


        for (int i = 0; i < objects.size(); i++)
            objects.get(i).paint(g);

        for (int i = 0; i < objects.size(); i++) {
            for (int j = i+1; j < objects.size(); j++)
                chain.collide(objects.get(i), objects.get(j));
        }

    }

    public void save() {
        File file = new File("/Users/ouyangyunqing/IdeaProjects/TANK-设计模式/SAVE/tank.data");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(myTank);
            oos.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void load() {
        File file = new File("/Users/ouyangyunqing/IdeaProjects/TANK-设计模式/SAVE/tank.data");

        if(file.exists()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(file));
                myTank = (Tank) ois.readObject();
                objects = (List) ois.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        TankFrame.INSTANCE.myTank = this.myTank;

    }
}
