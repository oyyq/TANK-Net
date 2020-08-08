package com.mashibing.tank;

import com.mashibing.tank.FrameWork.TankFrame;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.ResourceManager.ResourceMgr;
import com.mashibing.tank.net.Message.ObjectType;
import java.awt.*;
import java.util.Objects;
import java.util.UUID;


public class Bullet extends GameObject {

    public static UUID uuid = UUID.randomUUID();
    public final UUID source;                             //发射源的UUID, 不可变
    private static final int SPEED = 6;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x, y;
    public Model gm;
    private Dir dir;
    private boolean living = true;
    private Group group = Group.BAD;
    public Rectangle rect = new Rectangle();

    @Override
    public Integer IntHashCode() {
        return Integer.valueOf(Objects.hash(uuid, source, type, dir));
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public Bullet(UUID source, int x, int y, Dir dir, Group group, Model gm){
        this.source = source;
        this.x = x;
        this.y = y;
        this.type = ObjectType.BULLET;

        this.dir = dir;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        joinModel(gm);
    }


    public void paint(Graphics g){
        if(!living){ return;}

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }

        move();
    }

    @Override
    public void move(){
        //子弹不设置停止状态
        switch(dir){
            case LEFT:
                x -= SPEED; break;
            case UP:
                y -= SPEED; break;
            case RIGHT:
                x += SPEED; break;
            case DOWN:
                y += SPEED; break;
        }

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            living = false;
        }
        //update rect更新矩形
        rect.x = this.x;
        rect.y = this.y;

    }


    @Override
    public void joinModel(Model model) {
        if(model == null) return;
        gm = model;
        synchronized (gm.getBullets()) {
            if (!(gm.getBullets().get(IntHashCode()) != null)) {
                gm.getBullets().put(IntHashCode(), this);
            }
        }
    }

    @Override
    public int getWIDTH() {
        return WIDTH;
    }

    @Override
    public int getHEIGHT() {
        return HEIGHT;
    }


    @Override
    public Dir getDir() {
        return dir;
    }

    @Override
    public void setModel(Model model) {
        this.gm = model;
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

    public void die(){
        synchronized (gm.getBullets()) {
            this.living = false;
            gm.getBullets().remove(IntHashCode());
        }
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

}
