package datatype;

public class ComplexNumber
{
	public double real;
	public double imaginary;

	public static ComplexNumber zero = new ComplexNumber(0, 0);

	public ComplexNumber(double real, double imaginary)
	{
		this.real = real;
		this.imaginary = imaginary;
	}

	public static ComplexNumber add(ComplexNumber a, ComplexNumber b)
	{
		return new ComplexNumber(a.real + b.real, a.imaginary + b.imaginary);
	}

	public void square()
	{
		double r = real * real - imaginary * imaginary;
		double i = 2 * imaginary * real;
		real = r;
		imaginary = i;
	}

	public double squaredAbsoluteValue()
	{
		return real * real + imaginary * imaginary;
	}

	@Override
	public String toString()
	{
		return (real + " + " + imaginary + "i");
	}
}