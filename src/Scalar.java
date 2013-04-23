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

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }
    
}
