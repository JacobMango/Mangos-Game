package com.aussipvp.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aussipvp.entity.mob.Wanderer;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = setWidth(image.getWidth());
			int h = setHeight(image.getHeight());
			setTiles(new int[w * h]);
			image.getRGB(0, 0, w, h, getTiles(), 0, w);
			add(new Wanderer(10, 12));
		} catch (IOException e) {
			System.out.println("Could not load level file!");
			e.printStackTrace();
		}
	}

	protected void generateLevel() {

	}

}
