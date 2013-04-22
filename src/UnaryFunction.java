public class UnaryFunction extends Node
{
    public static final int NEGATE = 0, SQUARE_ROOT = 1, ABSOLUTE = 2,
            LOG = 3, SIN = 4, COS = 5, TAN = 6, ASIN = 7,
            ACOS = 8, ATAN = 9;
    public static final int NUM_TYPES = 10;
    
    private Node child;
    
    private int type;

    public UnaryFunction(Node child, int type)
    {
        this.child = child;
        this.type = type;
    }

    @Override
    public double evaluate()
    {
        return function(child.evaluate());
    }
    
    public double function(double a)
    {
        switch (type)
        {
            case NEGATE:
                return -a;
            case SQUARE_ROOT:
                return Math.sqrt(a);
            case ABSOLUTE:
                return Math.abs(a);
            case LOG:
                return Math.log(a);
            case SIN:
                return Math.sin(a);
            case COS:
                return Math.cos(a);
            case TAN:
                return Math.tan(a);
            case ASIN:
                return Math.asin(a);
            case ACOS:
                return Math.acos(a);
            case ATAN:
                return Math.atan(a);
            default:
                return Double.NaN;
        }
    }
}
