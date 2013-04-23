public class UnaryFunction extends Function
{
    public static final int NEGATE = 0, SQUARE_ROOT = 1, ABSOLUTE = 2,
            LOG = 3, SIN = 4, COS = 5, TAN = 6, ASIN = 7,
            ACOS = 8, ATAN = 9;
    public static final int NUM_TYPES = 10;
    
    public UnaryFunction(Node child, int type)
    {
        super(new Node[]{child}, type);
    }

    @Override
    public int getNumTypes()
    {
        return NUM_TYPES;
    }

    @Override
    public double evaluate()
    {
        return function(children[0].evaluate());
    }

    @Override
    public Node sheep()
    {
        return new UnaryFunction(children[0].sheep(),this.type);
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

    @Override
    public String toString()
    {
        String strf;
        switch (type)
        {
            case NEGATE:
                strf = "-(%s)"; break;
            case SQUARE_ROOT:
                strf = "sqrt(%s)"; break;
            case ABSOLUTE:
                strf = "abs(%s)"; break;
            case LOG:
                strf = "log(%s)"; break;
            case SIN:
                strf = "sin(%s)"; break;
            case COS:
                strf = "cos(%s)"; break;
            case TAN:
                strf = "tan(%s)"; break;
            case ASIN:
                strf = "asin(%s)"; break;
            case ACOS:
                strf = "acos(%s)"; break;
            case ATAN:
                strf = "atan(%s)"; break;
            default:
                strf = "???(%s)"; break;
        }
        return String.format(strf, children[0].toString());
    }

}
