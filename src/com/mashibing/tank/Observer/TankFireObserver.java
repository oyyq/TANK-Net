package com.mashibing.tank.Observer;

import java.io.Serializable;

public interface TankFireObserver extends Serializable {
    boolean actionOnFire(fireEvent e);
}


//需要定义各种具体的TankFireHandler