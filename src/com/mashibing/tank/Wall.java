package com.mashibing.tank;

import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.net.Message.ObjectType;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;


/**
 * Wall对象是由Server内部初始化分发给各个Client
 */

public class Wall extends GameObject {

    public static UUID uuid = UUID.randomUUID();
    private final int w = 20;
    private final int h = 10;
    public Rectangle rect;
    private boolean living = true;
    public Model gm;


    public Wall(int x, int y, Model gm){
        this.x = x;
        this.y = y;
        this.type = ObjectType.WALL;
        this.rect = new Rectangle(x, y, w, h);

        joinModel(gm);
    }

    @Override
    public Integer IntHashCode() {
        return Integer.valueOf(Objects.hash(uuid, type, x, y));
    }

    @Override
    public void paint(Graphics g) {
        if( !living ) { return; }
        Color color = g.getColor();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(color);

    }

    @Override
    public void joinModel(Model model) {
        if(model == null) return;
        gm = model;
        synchronized (gm.getWalls()) {
            if ( !( gm.getWalls().get(IntHashCode()) != null ) ) {
                gm.getWalls().put(IntHashCode(), this);
            }
        }
    }

    @Override
    public void die() {
        synchronized (gm.getWalls()) {
            this.living = false;
            gm.getWalls().remove(IntHashCode());
        }
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

    //接到新增物体需要setId
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
        return w;
    }

    @Override
    public int getHEIGHT() {
        return h;
    }

    @Override
    public Dir getDir() {
        return null;
    }

    @Override
    public void setModel(Model model) {

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
    public void move() {

    }


}
