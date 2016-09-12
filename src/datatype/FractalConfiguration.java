package datatype;

public class FractalConfiguration
{
	public ComplexNumber centre;
	public double halfSize;
	public int iterations;

	public FractalConfiguration(ComplexNumber centre, double halfSize, int iterations)
	{
		this.centre = centre;
		this.halfSize = halfSize;
		this.iterations = iterations;
	}

	public void setCentre(ComplexNumber centre)
	{
		this.centre = ComplexNumber.clamp(centre, new ComplexNumber(-2, -2), new ComplexNumber(2, 2));
	}

	public void multiplyHalfSize(double multiplier)
	{
		halfSize = Math.min(2, halfSize * multiplier);
	}

	public void multiplyIterations(double multiplier)
	{
		iterations = Math.max(1, (int) (iterations * multiplier));
	}
}
