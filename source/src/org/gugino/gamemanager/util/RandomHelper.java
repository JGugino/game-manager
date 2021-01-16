package org.gugino.gamemanager.util;

import java.util.Random;

public class RandomHelper {

	public static int randomMinMax(int _min, int _max) {
		Random _rand = new Random();
		return _rand.nextInt(_max + 1 - _min) + _min;
	}
	
}