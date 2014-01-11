package com.aussipvp.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.aussipvp.entity.mob.BasicMob;
import com.aussipvp.entity.mob.Chaser;
import com.aussipvp.entity.mob.Star;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	public static int h, w;

	Random random = new Random();
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = setWidth(image.getWidth());
			int h = setHeight(image.getHeight());
			this.h = h;
			this.w = w;
			setTiles(new int[w * h]);
			image.getRGB(0, 0, w, h, getTiles(), 0, w);
		} catch (IOException e) {
			System.out.println("Could not load level file!");
			e.printStackTrace();
		}
		/*for (int i = 0; i < 10; i++) {
			add(new BasicMob(random.nextInt(20) + 1, random.nextInt(20) + 1));
		}*/
		//add(new Chaser(10, 10));
		//add(new Star(25, 25));
	}

	protected void generateLevel() {

	}

}
