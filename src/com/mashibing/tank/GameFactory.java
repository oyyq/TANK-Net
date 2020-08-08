package com.mashibing.tank;

import com.mashibing.tank.GameObject.GameObject;
import java.io.Serializable;
import java.util.UUID;

public abstract class GameFactory implements Serializable {
    //x, y, dir, group...都是参数而不是GameFactory自己的属性
    public abstract GameObject createTank(int x, int y, Dir dir, Group group, Model gm );
    public abstract GameObject createExplode(int x, int y, Model gm);
    public abstract GameObject createBullet(UUID source,  int x, int y, Dir dir, Group group,Model gm);

}
