public class BinaryFunction extends Node
{
    public static final int ADD = 0, SUBTRACT = 1, MULTIPLY = 2, DIVIDE = 3,
            POWER = 4;
    public static final int NUM_TYPES = 6;
    
    private int type;

    public BinaryFunction(Node left, Node right, int type)
    {
        super(new Node[]{left, right});
        this.type = type;
    }
    
    @Override
    public double evaluate()
    {
        return function(children[0].evaluate(), children[1].evaluate());
    }
    
    public double function(double a, double b)
    {
        switch (type)
        {
            case ADD:
                return a + b;
            case SUBTRACT:
                return a - b;
            case MULTIPLY:
                return a * b;
            case DIVIDE:
                return a / b;
            case POWER:
                return Math.pow(a, b);
            default:
                return Double.NaN;
        }
    }
}
