package com.test.proxy9.tank;

import java.util.Random;

public class Tank implements Moveable {
	@Override
	public void move() {
		try {
			System.out.println("Tank moving...");
			Thread.sleep(new Random().nextInt(10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
