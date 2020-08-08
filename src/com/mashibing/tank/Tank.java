package com.mashibing.tank;

import com.mashibing.tank.FrameWork.TankFrame;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.ResourceManager.PropertyMgr;
import com.mashibing.tank.ResourceManager.ResourceMgr;
import com.mashibing.tank.Strategies.FireStrategy;
import com.mashibing.tank.net.Message.ObjectType;
import org.junit.jupiter.params.provider.EnumSource;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;


public class Tank extends GameObject {
    public static UUID uuid = UUID.randomUUID();
    private Dir dir = Dir.DOWN;
    private Group group;
    private static final int SPEED = 5;
    public static int WIDTH;
    public static int HEIGHT;
    private boolean living = true;
    private boolean moving = false;            //刚开始moving设置为false

    FireStrategy fireStrategy;                 //FireStrategy是函数式接口，可以用Lambda表达式给fireStrategy赋值。
    public Model gm;
    int prevX, prevY;

    public Rectangle rect = new Rectangle();

    public Tank(int x, int y, Dir dir, Group group, Model gm) {
        this.x = x;
        this.y = y;
        this.type = ObjectType.TANK;

        prevX = x;
        prevY = y;
        this.dir = dir;
        this.group=  group;


        this.WIDTH = group == Group.GOOD? ResourceMgr.tankDG.getWidth():ResourceMgr.tankDB.getWidth();
        this.HEIGHT = group == Group.GOOD? ResourceMgr.tankDG.getHeight():ResourceMgr.tankDB.getHeight();

        rect.x = this.x;
        rect.y = this.y;

        String gfs = (String) PropertyMgr.getInstance().get("goodFS");
        String bfs = (String) PropertyMgr.getInstance().get("badFS");

        try {
            fireStrategy = group == Group.GOOD ? (FireStrategy) Class.forName(gfs).newInstance(): (FireStrategy) Class.forName(bfs).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        joinModel(gm);

    }

    @Override
    public Integer IntHashCode(){
        System.out.println(Integer.valueOf(Objects.hash(uuid,type)));
        return Integer.valueOf(Objects.hash(uuid,type)); }

    public void setFireStrategy(FireStrategy fireStrategy) {
        this.fireStrategy = fireStrategy;
    }

    public FireStrategy getFireStrategy() {
        return fireStrategy;
    }

    public Model getGm() {
        return gm;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public  int getHEIGHT() {
        return HEIGHT;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    @Override
    public void setModel(Model model) {
        this.gm = model;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public void joinModel(Model model) {
        if ( model == null ) return;
        gm = model;
        synchronized (gm.getTanks()) {
            if (!(gm.getTanks().get(IntHashCode()) != null)) {
                gm.getTanks().put(IntHashCode(), this);
            }
        }
    }

    public Rectangle getRect() {
        return rect;
    }


    @Override
    public void paint(Graphics g) {
        if(!living) return;

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.tankLG: ResourceMgr.tankLB, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.tankRG: ResourceMgr.tankRB, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.tankUG: ResourceMgr.tankUB, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.tankDG: ResourceMgr.tankDB, x, y, null);
                break;
        }
        move();
    }

    /**
     * TODO: move()方法中需发送消息
     */
    @Override
    public void move(){
        if(!moving) return;
        prevX = x; prevY = y;

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

        boundsCheck();
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }
    //边界检测
    private void boundsCheck(){
        if(this.x < 2) x = 2;
        if(this.y < 28) y = 28;
        if(this.x > TankFrame.GAME_WIDTH- Tank.WIDTH-2) x = TankFrame.GAME_WIDTH- Tank.WIDTH-2;
        if(this.y > TankFrame.GAME_HEIGHT- Tank.HEIGHT-2) y = TankFrame.GAME_HEIGHT- Tank.HEIGHT-2;
    }

    public void Retreat(){
        this.x = prevX;
        this.y = prevY;
    }



    public void fire(){
        fireStrategy.fire(this);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public void die(){
        synchronized (gm.getTanks()) {
            this.living = false;
            this.gm.getTanks().remove(IntHashCode());
        }
    }



}
