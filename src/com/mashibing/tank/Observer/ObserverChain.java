package com.mashibing.tank.Observer;


import java.util.ArrayList;
import java.util.List;

public class ObserverChain implements TankFireObserver {
    public static List<TankFireObserver> observers = new ArrayList<>();
    //单例模式, 安全发布, 访问OBSERVER_CHAIN时已经初始化完毕
    private static final ObserverChain OBSERVER_CHAIN = new ObserverChain();

    public static ObserverChain getObserverChain() {
        return OBSERVER_CHAIN;
    }
    static {
        add(new SingleBullet());
        add(new FourBullet());
    }
    @Override
    public boolean actionOnFire(fireEvent e) {
        for(int i = 0; i< observers.size(); i++)
            if(!observers.get(i).actionOnFire(e))
                return false;

        return true;

    }
    public static void add(TankFireObserver tankFireObserver){
        observers.add(tankFireObserver);
    }

}
