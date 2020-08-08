package com.mashibing.tank.ResourceManager;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {
    /**
     * 资源及载器
     */
    public static BufferedImage tankLG, tankUG, tankRG, tankDG;
    public static BufferedImage tankLB, tankUB, tankRB, tankDB;
    public static BufferedImage bulletL, bulletU, bulletR, bulletD;
    public static BufferedImage[] explodes = new BufferedImage[16];
    /**
     * 统一加载硬盘上所有图片到内存
     * static语句块在ResourceMgr.class被load到内存时自动执行
     */
    static {

        try {
            //classLoader的getResourceAsStream方法
            tankUG = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            tankLG = ImageUtil.rotateImage(tankUG, -90);
            tankRG = ImageUtil.rotateImage(tankUG, 90);
            tankDG = ImageUtil.rotateImage(tankUG, 180);

            tankUB = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            tankLB = ImageUtil.rotateImage(tankUB, -90);
            tankRB = ImageUtil.rotateImage(tankUB, 90);
            tankDB = ImageUtil.rotateImage(tankUB, 180);

            bulletL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
            bulletD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));

            for(int i = 0; i<16; i++)
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" +(i+1)+".gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
