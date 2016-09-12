package fractal;

import java.awt.Color;
import java.awt.image.BufferedImage;

import datatype.ComplexNumber;
import datatype.FractalConfiguration;

public class Mandelbrot
{
	// TODO accelerate image rendering using previously rendered image
	public enum Direction
	{
		Up, Down, Left, Right
	};

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

	public static BufferedImage getArea(FractalConfiguration configuration, int length, int height)
	{
		ComplexNumber min = new ComplexNumber(configuration.centre.real - configuration.halfSize * length / height,
				configuration.centre.imaginary - configuration.halfSize);
		ComplexNumber max = new ComplexNumber(configuration.centre.real + configuration.halfSize * length / height,
				configuration.centre.imaginary + configuration.halfSize);

		BufferedImage bufferedImage = new BufferedImage(length, height, BufferedImage.TYPE_3BYTE_BGR);

		int[] colours = new int[configuration.iterations + 2];

		for (int i = 0; i < configuration.iterations + 2; i++)
		{
			float value = (float) i / (configuration.iterations + 1);
			colours[i] = new Color(Math.max((2 * value - 1), 0), Math.min((2 * value), 1), Math.max((2 * value - 1), 0))
					.getRGB();
		}

		for (int x = 0; x < length; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (terminateTrigger)
				{
					terminateTrigger = false;
					return null;
				}

				if (bufferedImage.getRGB(x, y) != -16777216)
					continue;

				ComplexNumber point = new ComplexNumber(min.real + (max.real - min.real) * x / (length - 1),
						min.imaginary + (max.imaginary - min.imaginary) * y / (height - 1));
				int value = getPoint(point, configuration.iterations);
				bufferedImage.setRGB(x, y, colours[value]);
			}
		}

		return bufferedImage;
	}
}
