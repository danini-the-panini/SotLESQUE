public class BinaryFunction extends Function
{
    public static final int ADD = 0, SUBTRACT = 1, MULTIPLY = 2, DIVIDE = 3,
            POWER = 4;
    public static final int NUM_TYPES = 6;
    
    public BinaryFunction(Node left, Node right, int type)
    {
        super(new Node[]{left, right}, type);
    }

    @Override
    public int getNumTypes()
    {
        return NUM_TYPES;
    }
    
    @Override
    public double evaluate()
    {
        return function(children[0].evaluate(), children[1].evaluate());
    }

    @Override
    public Node sheep()
    {
        return new BinaryFunction(children[0].sheep(),children[1].sheep(), this.type);
    }

    private double function(double a, double b)
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

    @Override
    public String toString()
    {
        String strf;
        switch (type)
        {
            case ADD:
                strf = "(%s) + (%s)"; break;
            case SUBTRACT:
                strf = "(%s) - (%s)"; break;
            case MULTIPLY:
                strf = "(%s) * (%s)"; break;
            case DIVIDE:
                strf = "(%s) / (%s)"; break;
            case POWER:
                strf = "(%s) ^ (%s)"; break;
            default:
                strf = "(%s) ??? (%s)"; break;
        }

        return String.format(strf, children[0].toString(), children[1].toString());
    }

}
