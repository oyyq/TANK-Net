package com.mashibing.tank.GameObject;


import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import com.mashibing.tank.Model;
import com.mashibing.tank.net.Message.ObjectType;
import java.awt.*;
import java.util.UUID;


public abstract class GameObject {

    public ObjectType type;
    protected int x,y;
    public abstract void paint(Graphics g);
    public abstract Group getGroup();

    public abstract void die();
    public abstract int getX();
    public abstract int getY();
    public abstract void setId(UUID uuid);
    public abstract UUID getId();

    //public abstract void collideWith(GameObject tank);
    public abstract int getWIDTH();
    public abstract int getHEIGHT();
    public abstract Dir getDir();
    //设定GameObject所在的Model
    public abstract void setModel(Model model);
    public abstract void setMoving(boolean moving);
    public abstract void fire();
    public abstract void setDir(Dir dir);

    public abstract void setGroup(Group group);
    public abstract void move();
    public abstract void joinModel(Model model);
    public abstract Integer IntHashCode();

}
