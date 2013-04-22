public class Scalar extends Node
{
    private double value;

    public Scalar(double value)
    {
        this.value = value;
    }
    
    @Override
    public double evaluate()
    {
        return value;
    }
    
}
