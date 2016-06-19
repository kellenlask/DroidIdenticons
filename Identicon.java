package com.lask.scientifichabits.Utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * A class to create horizontally symmetrical Identicons from object hashes.
 * Inspiration: https://github.com/davidhampgonsalves/Contact-Identicons
 *
 * The Approach:
 * 1. Create the left half of the com.lask.scientifichabits.Utils.Identicon grid as a bitmap with 1:1 grid cell : pixel ratio
 *		a. Create foreground color
 *		b. For each cell, determine whether or not it should be foreground/background
 *		c. Apply established color to pixel in bitmap
 *
 * 2. Mirror the left half onto the right half
 * 3. Expand the generated com.lask.scientifichabits.Utils.Identicon pattern onto a bitmap of the desired size *
 *
 * @author Kellen
 */
public class Identicon {

	/**
	 * Accepts an object, runs the hashCode() method on that object, and using
	 * the generated hash code, creates an identicon. Uses default values of
	 * 5 x 5, & white background.
	 * @param obj - The object to generate an identicon from
	 * @return - the bitmap identicon
	 */
	public static Bitmap generate(Object obj) {
		return generate(obj, 6, 32, Color.parseColor("#FFFFFF"));
	}

	public static Bitmap generate(Object obj, int pixSize) {
		return generate(obj, 6, pixSize, Color.parseColor("#FFFFFF"));
	}

	public static Bitmap generate(Object obj, int gridSize, int pixSize) {
		return generate(obj, gridSize, pixSize, Color.parseColor("#FFFFFF"));
	}

	/**
	 * Accepts an object, runs the hashCode() method on that object, and using
	 * the generated hash code, creates an identicon.
	 * @param obj The object whose hash will be used to create the com.lask.scientifichabits.Utils.Identicon
	 * @param gridSize The side length in squares of the identicon
	 * @param pixSize The side length in pixels of the identicon
	 * @param background The background color of the image
	 * @return The generated Bitmap
	 */
	public static Bitmap generate(Object obj, int gridSize, int pixSize, int background) {
		// Prepare the com.lask.scientifichabits.Utils.Identicon
		Bitmap identicon = Bitmap.createBitmap(gridSize, gridSize, Config.ARGB_8888);
		int hash = obj.hashCode();

		// Generate a color for foreground (255 = 1 byte = 8 bits)
		int foreground = generateForegroundColor(hash, background);

		// Create pattern
		boolean usedForeground = false;
		int rotationCount = 0;
		while(!usedForeground && rotationCount < 32) {	// Java ints are 32 bits: 32 rotations = 0 rotations.
			hash = Integer.rotateLeft(hash, rotationCount);
			rotationCount++;

			for (int x = 0; x < (gridSize + 1) / 2; x++) {
				// Fill in the squares in the Y dimension (With mirroring)
				for (int y = 0; y < gridSize; y++) {
					int pixelColor = getPixelColor(hash, x, y, foreground, background);

					if(pixelColor == foreground) {
						usedForeground = true;
					}

					identicon.setPixel(x, y, pixelColor);
					identicon.setPixel(gridSize - (x + 1), y, pixelColor);
				}
			}
		}

		// Map the Identicon pattern onto a full-sized bitmap
		Bitmap bmpWithBorder = Bitmap.createBitmap(pixSize, pixSize, identicon.getConfig());
		Canvas canvas = new Canvas(bmpWithBorder);
		canvas.drawColor(background);
		identicon = Bitmap.createScaledBitmap(identicon, pixSize - 2, pixSize - 2, false);
		canvas.drawBitmap(identicon, 1, 1, null);

		return identicon;
	}

	/**
	 * Determines whether or not to use foreground or background when filling in
	 * a cell in the com.lask.scientifichabits.Utils.Identicon.
	 * @param hash - The hash code of the object to represent
	 * @param x - The X coordinate in the generated Identicon pattern grid
	 * @param y - The Y coordinate in the generated Identicon pattern grid
	 * @param foreground - The foreground color as an int
	 * @param background - The background color as an int
	 * @return - the color to use to fill the grid cell (background/foreground)
	 */
	private static int getPixelColor(int hash, int x, int y, int foreground, int background) {
		// If the hash as binary rotated by a semi-arbitrary amount ends in 1
		int rotated = Integer.rotateLeft(hash, (int) (2.718281 * x + y));	// Rotate x bytes and y bits along

		if((rotated & hash & 2) == 2) {
			return foreground;
		}

		return background;
	}


	/**
	 * Method to generate a foreground color that will stick out well enough on the background color.
	 * @param hash - The hash of the object from which the identicon is being created
	 * @param background - the background color of the identicon
	 * @return - the integer representation of the foreground color to use.
	 */
	private static int generateForegroundColor(int hash, int background) {
		int foreground = background;

		double fIndex = calcBrightnessIndex(foreground);
		double bIndex = calcBrightnessIndex(background);

		int rotation = 0;
		while (Math.abs(fIndex - bIndex) < 30 && rotation < 32) {	// Java int is 32 bits
			int r = Integer.rotateLeft(hash, rotation) & 255;
			int g = Integer.rotateLeft(hash, 8 + rotation) & 255;
			int b = Integer.rotateLeft(hash, 16 + rotation) & 255;
			foreground = Color.argb(255, r, g, b);

			rotation++;
			fIndex = calcBrightnessIndex(foreground);
			bIndex = calcBrightnessIndex(background);
		}

		return foreground;
	}


	/**
	 * Calculates an index representing the brightness of the given color
	 * @param color - color to calculate brightness for
	 * @return - the brightness index
	 */
	public static double calcBrightnessIndex(int color) {
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);

		return ( 299 * r + 587 * g + 114 * b ) / 1000;
	}
}
