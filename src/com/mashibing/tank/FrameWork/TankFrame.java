package com.mashibing.tank.FrameWork;


import com.mashibing.tank.Audio;
import com.mashibing.tank.Dir;
import com.mashibing.tank.GameObject.GameObject;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Observer.fireEvent;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.event.KeyEvent.*;


public class TankFrame extends Frame {

    //单例
    public final static TankFrame INSTANCE = new TankFrame();
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
    public GameModel GM = GameModel.getINSTANCE();
    public GameObject myTank = GM.getMyTank();

    public TankFrame() {
        setVisible(true);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setTitle("tank war");
        setResizable(false);

        addWindowListener(new WindowAdapter() {           //窗口监听器
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //增加键盘监听处理类
        this.addKeyListener(new MyKeyListener());
        myTank.setMoving(false);

    }

    Image offScreenImage = null;                                            //定义一张图片在内存中

    @Override
    public void update(Graphics g){                                          //屏幕上的画笔
        if(offScreenImage == null)
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);         //在内存中设置好图片的大小

        Graphics gOffScreen = offScreenImage.getGraphics();                //getGraphics拿到内存画笔
        Color c = gOffScreen.getColor();

        gOffScreen.setColor(Color.BLACK);                                   //将背景重新画一遍，设置为黑色
        gOffScreen.fillRect(0,0,GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);

        paint(gOffScreen);
        g.drawImage(offScreenImage, 0,0, null);                //将整体图片画出
    }

    @Override
    public void paint(Graphics g) {
        GM.paint(g);                                //只将画笔传递给Gamemodel, 让Gamemodel把自己画出来
    }

    /**
     * 如何让该黑方块动起来？-->让黑方块的坐标设置为变量
     * 不断重新调用paint方法来重新绘制，这是自动刷新的方法
     * 添加键盘监听处理类处理键盘活动，人为控制小方块移动
     * 键盘监听：在Frame内部监听，也就是小窗口内部监听键盘活动
     */
    class MyKeyListener extends KeyAdapter {
        public MyKeyListener() {
            super();
        }
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key) {
                case VK_LEFT:
                    bL = true;
                    break;
                case VK_UP:
                    bU = true;
                    break;
                case VK_RIGHT:
                    bR = true;
                    break;
                case VK_DOWN:
                    bD = true;
                    break;
                case VK_S:
                    GM.save();
                    break;
                case VK_L:
                    GM.load();
                    break;

                default:
                    break;
            }

            setMainTankDir();
            new Thread(()->new Audio("audio/tank_move.wav").play()).start();
        }


        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case VK_LEFT:
                    bL = false;
                    break;
                case VK_UP:
                    bU = false;
                    break;
                case VK_RIGHT:
                    bR = false;
                    break;
                case VK_DOWN:
                    bD = false;
                    break;

                case VK_CONTROL:
                    fireEvent.getINSTANCE(e, myTank).launch();
                    break;
            }

            setMainTankDir();
        }
        private void setMainTankDir(){
            if(!bL && !bU && !bR && !bD) myTank.setMoving(false);
            else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bU) myTank.setDir(Dir.UP);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bD) myTank.setDir(Dir.DOWN);
                //如果四个方向键都没按下，那么坦克应该停下
            }
        }

    }
}



