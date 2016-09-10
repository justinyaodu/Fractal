package fractal;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import datatype.ComplexNumber;

public class Mandelbrot
{
	public static boolean terminateTrigger = false;

	// **Julia*//
	public static int getPoint(ComplexNumber z, ComplexNumber c, int iterations)
	{
		for (int passes = 0; passes < iterations; passes++)
		{
			z.square();
			z = ComplexNumber.add(z, c);
			if (z.squaredAbsoluteValue() > 4)
				return passes;
		}
		return iterations;
	}

	// **Mandelbrot*//
	public static int getPoint(ComplexNumber c, int iterations)
	{
		ComplexNumber z = ComplexNumber.zero;
		// ComplexNumber z = c;

		for (int passes = 0; passes <= iterations; passes++)
		{
			z.square();
			z = ComplexNumber.add(z, c);
			if (z.squaredAbsoluteValue() > 4)
				return passes;
		}
		return iterations + 1;
	}

	public static BufferedImage getArea(ComplexNumber centre, double halfSize, int length, int height, int iterations)
	{
		ComplexNumber min = new ComplexNumber(centre.real - halfSize * length / height, centre.imaginary - halfSize);
		ComplexNumber max = new ComplexNumber(centre.real + halfSize * length / height, centre.imaginary + halfSize);

		int[][] points = new int[length][height];
		for (int i = 0; i < length; i++)
		{
			Arrays.fill(points[i], -1);
		}

		BufferedImage bufferedImage = new BufferedImage(length, height, BufferedImage.TYPE_3BYTE_BGR);

		int[] colours = new int[iterations + 2];

		for (int i = 0; i < iterations + 2; i++)
		{
			float value = (float) i / (iterations + 1);
			colours[i] = new Color(Math.max((2 * value - 1), 0), Math.min((2 * value), 1), Math.max((2 * value - 1), 0))
					.getRGB();
		}

		for (int x = 0; x < length; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (terminateTrigger)
				{
					System.out.println("Render aborted");
					terminateTrigger = false;
					return null;
				}

				if (points[x][y] >= 0)
					continue;

				ComplexNumber point = new ComplexNumber(min.real + (max.real - min.real) * x / (length - 1),
						min.imaginary + (max.imaginary - min.imaginary) * y / (height - 1));
				int value = getPoint(point, iterations);
				points[x][y] = value;
				bufferedImage.setRGB(x, y, colours[value]);
			}
		}

		return bufferedImage;
	}

	@Deprecated
	public static BufferedImage arrayToImage(int[][] points, int iterations)
	{
		int length = points.length;
		int height = points[0].length;

		int[] colours = new int[iterations + 2];

		for (int i = 0; i < iterations + 2; i++)
		{
			float value = (float) i / (iterations + 1);
			colours[i] = new Color(Math.max((2 * value - 1), 0), Math.min((2 * value), 1), Math.max((2 * value - 1), 0))
					.getRGB();
		}

		BufferedImage bufferedImage = new BufferedImage(length, height, BufferedImage.TYPE_4BYTE_ABGR);

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < length; x++)
			{
				bufferedImage.setRGB(x, y, colours[points[x][y]]);
			}
		}

		return bufferedImage;
	}
}
