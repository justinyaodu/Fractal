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
import datatype.FractalConfiguration;
import fractal.Mandelbrot;
import graphics.ImageFrame;

public class Main
{
	static boolean running = true;

	static int previewQuality = 16;

	static GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int length = graphicsDevice.getDisplayMode().getWidth();
	public static int height = graphicsDevice.getDisplayMode().getHeight();

	static FractalConfiguration configuration = new FractalConfiguration(new ComplexNumber(0, 0), 2, 64);

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
				configuration.setCentre(new ComplexNumber(configuration.centre.real,
						configuration.centre.imaginary - (configuration.halfSize / 2)));
				update();
				break;
			case KeyEvent.VK_A:
				configuration.setCentre(new ComplexNumber(configuration.centre.real - (configuration.halfSize / 2),
						configuration.centre.imaginary));
				update();
				break;
			case KeyEvent.VK_S:
				configuration.setCentre(new ComplexNumber(configuration.centre.real,
						configuration.centre.imaginary + (configuration.halfSize / 2)));
				update();
				break;
			case KeyEvent.VK_D:
				configuration.setCentre(new ComplexNumber(configuration.centre.real + (configuration.halfSize / 2),
						configuration.centre.imaginary));
				update();
				break;
			case KeyEvent.VK_UP:
				configuration.multiplyHalfSize(0.5);
				update();
				break;
			case KeyEvent.VK_DOWN:
				configuration.multiplyHalfSize(2);
				update();
				break;
			case KeyEvent.VK_RIGHT:
				configuration.multiplyIterations(2);
				update();
				break;
			case KeyEvent.VK_LEFT:
				configuration.multiplyIterations(0.5);
				update();
				break;
		}
	}

	public static void onMouseClick(MouseEvent mouseEvent)
	{
		double x = ((double) mouseEvent.getX() / (length - 1) * 2 - 1);
		x *= (double) length / height * configuration.halfSize;
		double y = ((double) mouseEvent.getY() / (height - 1) * 2 - 1);
		y *= configuration.halfSize;

		ComplexNumber offset = new ComplexNumber(x, y);
		configuration.setCentre(ComplexNumber.add(configuration.centre, offset));

		update();
	}

	static void update()
	{
		Mandelbrot.terminateTrigger = true;

		renderImage();

		renderImageToFrame();
	}

	static void renderImage()
	{
		bufferedImage = null;

		while (bufferedImage == null)
		{
			bufferedImage = Mandelbrot.getArea(configuration, length, height);
		}
	}

	static void renderImageToFrame()
	{
		mainWindow.updateImage(bufferedImage);
	}
}
