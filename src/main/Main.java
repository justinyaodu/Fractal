package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import datatype.ComplexNumber;
import fractal.Mandelbrot;
import graphics.ImageFrame;
import input.MouseClickInput;

public class Main
{
	static boolean running = true;
	static boolean previewComplete = false;
	static boolean renderComplete = false;

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
		initJFrame();

		while (running)
		{
			update();
		}
	}

	static void initJFrame()
	{
		mainWindow = new ImageFrame("Fractal", null);

		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainWindow.setLocationRelativeTo(null);

		//mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//mainWindow.setResizable(false);
		//mainWindow.setUndecorated(true);

		mainWindow.addMouseListener(new MouseClickInput());

		mainWindow.setVisible(true);

		renderImageToFrame();
	}

	static void update()
	{
		long endTime = System.currentTimeMillis() + millisRenderTime;

		if (!previewComplete)
		{
			renderPreview();
			previewComplete = true;
			renderImageToFrame();
			System.out.println("Preview rendered");
		}

		else if (!renderComplete && renderContinue(endTime))
		{			
			renderImageToFrame();
			renderComplete = true;
			System.out.println("Image rendered");
		}

		// System.out.println("Update");
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

	public static void onMouseClick(MouseEvent mouseEvent)
	{
		double x = ((double) mouseEvent.getX() / (length - 1) * 2 - 1);
		x *= (double) length / height * halfSize;
		double y = ((double) mouseEvent.getY() / (height - 1) * 2 - 1);
		y *= halfSize;

		ComplexNumber offset = new ComplexNumber(x, y);
		centre = ComplexNumber.add(centre, offset);
		resetRender();

		System.out.println(centre.toString());
	}

	static boolean renderContinue(long endTime)
	{
		bufferedImage = Mandelbrot.getAreaTimed(centre, halfSize, length, height, iterations, endTime);
		return bufferedImage != null;
	}

	static void renderImageToFrame()
	{
		mainWindow.updateImage(bufferedImage);
		mainWindow.pack();
	}

	static void renderPreview()
	{
		bufferedImage = Mandelbrot.getAreaTimed(centre, halfSize, length / previewQuality, height / previewQuality,
				iterations, Long.MAX_VALUE);
		Mandelbrot.resetRender();
	}

	static void resetRender()
	{
		Mandelbrot.resetRender();
		previewComplete = false;
		renderComplete = false;
		bufferedImage = null;
		System.out.println("Render restarted");
		update();
	}
}
