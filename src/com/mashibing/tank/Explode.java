package com.mashibing.tank;


import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.ResourceManager.ResourceMgr;
import com.mashibing.tank.net.Message.ObjectType;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

/**
 * 音乐移到GameModel中处理
 */
public class Explode extends GameObject {

    public UUID uuid = UUID.randomUUID();
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private boolean living = true;
    private int step = 0;
    Model gm;

    public Explode(int x, int y, int step,  Model gm) {
        this.x = x;
        this.y = y;
        this.type = ObjectType.EXPLODE;
        this.step = step;

        //new Thread(()->new Audio("audio/explode.wav").play()).start();
        joinModel(gm);

    }

    public void paint(Graphics g) {
        if( !living ) { return; }
        g.drawImage(ResourceMgr.explodes[step], x,y, null);
        move();
        if(step >=  ResourceMgr.explodes.length ) {
            die();
        }
    }


    @Override
    public void die() {
        synchronized (gm.getExplodes()) {
            living = false;
            this.gm.getExplodes().remove(IntHashCode());
        }
    }

    @Override
    public void joinModel(Model model) {
        if(model == null) return;
        gm = model;
        synchronized (gm.getExplodes()) {
            if (!(gm.getExplodes().get(IntHashCode()) != null)) {
                gm.getExplodes().put(IntHashCode(), this);
            }
        }
    }

    public int getStep() {
        return step;
    }

    @Override
    public Integer IntHashCode() {
        return Integer.valueOf(Objects.hash(uuid, type,  x, y));
    }

    @Override
    public Group getGroup() {
        return null;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public int getWIDTH() {
        return 0;
    }

    @Override
    public int getHEIGHT() {
        return 0;
    }

    @Override
    public Dir getDir() {
        return null;
    }

    @Override
    public void setModel(Model model) {
        gm = model;
    }

    @Override
    public void setMoving(boolean moving) {
    }

    @Override
    public void fire() {
    }

    @Override
    public void setDir(Dir dir) {
    }

    @Override
    public void setGroup(Group group) {
    }


    @Override
    public synchronized void move() {
       step++;
    }


}
