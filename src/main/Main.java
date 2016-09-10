package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import datatype.ComplexNumber;
import fractal.Mandelbrot;
import graphics.ImageFrame;

public class Main
{
	static boolean running = true;

	static int previewQuality = 16;

	static GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int length = graphicsDevice.getDisplayMode().getWidth();
	public static int height = graphicsDevice.getDisplayMode().getHeight();

	static ComplexNumber centre = new ComplexNumber(0, 0);
	static int iterations = 64;
	static double halfSize = 2;

	static int millisRenderTime = 50;
	static long startTime = System.currentTimeMillis();

	static ImageFrame mainWindow;

	static BufferedImage bufferedImage = new BufferedImage(length, height, BufferedImage.TYPE_4BYTE_ABGR);

	public static void main(String[] args)
	{
		init();
	}

	static void init()
	{
		renderImage();

		mainWindow = new ImageFrame("Fractal", bufferedImage);
	}

	static void renderImageToFile(String name)
	{
		File output = new File(name + System.currentTimeMillis() + ".png");
		try
		{
			ImageIO.write(bufferedImage, "png", output);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("File rendered");
	}

	public static void onKeyDown(KeyEvent keyEvent)
	{
		switch (keyEvent.getKeyCode())
		{
			case KeyEvent.VK_W:
				centre.imaginary -= halfSize / 2;
				update();
				break;
			case KeyEvent.VK_A:
				centre.real -= halfSize / 2;
				update();
				break;
			case KeyEvent.VK_S:
				centre.imaginary += halfSize / 2;
				update();
				break;
			case KeyEvent.VK_D:
				centre.real += halfSize / 2;
				update();
				break;
			case KeyEvent.VK_UP:
				halfSize /= 2;
				update();
				break;
			case KeyEvent.VK_DOWN:
				halfSize *= 2;
				update();
				break;
			case KeyEvent.VK_RIGHT:
				iterations *= 2;
				update();
				break;
			case KeyEvent.VK_LEFT:
				iterations /= 2;
				update();
				break;
		}
	}

	public static void onMouseClick(MouseEvent mouseEvent)
	{
		Mandelbrot.terminateTrigger = true;

		double x = ((double) mouseEvent.getX() / (length - 1) * 2 - 1);
		x *= (double) length / height * halfSize;
		double y = ((double) mouseEvent.getY() / (height - 1) * 2 - 1);
		y *= halfSize;

		ComplexNumber offset = new ComplexNumber(x, y);
		centre = ComplexNumber.add(centre, offset);

		update();
	}

	static void update()
	{
		renderImage();

		renderImageToFrame();
	}

	static void renderImage()
	{
		bufferedImage = null;

		while (bufferedImage == null)
		{
			bufferedImage = Mandelbrot.getArea(centre, halfSize, length, height, iterations);
		}
	}

	static void renderImageToFrame()
	{
		mainWindow.updateImage(bufferedImage);
	}
}
