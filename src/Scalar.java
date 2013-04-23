public class Scalar extends Node
{
    private double value;

    public Scalar(double value)
    {
        super(null);
        this.value = value;
    }
    
    @Override
    public double evaluate()
    {
        return value;
    }

    @Override
    public void mutate()
    {
        value += Tree.RANDOM.nextGaussian()*Environment.getInstance().getGaussStdDev();
    }

    @Override
    public Node sheep()
    {
        return new Scalar(this.value);
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return String.format("%.6G", value);
    }
    
}
