package com.mashibing.tank;

import com.mashibing.tank.FrameWork.TankFrame;
import com.mashibing.tank.net.Client.Client;

public class Main {

	public static void main(String[] args){
		TankFrame tf = TankFrame.INSTANCE;
		tf.setVisible(true);

		//new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		new Thread(()-> {
			while(true) {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tf.repaint();
			}
		}).start();
		
		//连接到服务器, or you can use a thread to run this, TODO: 用客户端的单例
		Client c = Client.INSTANCE;
		c.connect();
		
	}

}
