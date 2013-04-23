import java.util.ArrayList;
import java.util.Random;

public class Environment
{
    private static Environment instance;
    
    private final Random RANDOM = new Random();
    
    public static Environment getInstance()
    {
        if (instance == null) instance = new Environment();
        return instance;
    }
    
    private double scalarMinValue = 0.0, scalarMaxValue = 1.0;
    private double gaussStdDev = 1.0f;
    private ArrayList<Double> varValues = new ArrayList<Double>();
    private ArrayList<String> varNames = new ArrayList<String>();

    public double getScalarMaxValue()
    {
        return scalarMaxValue;
    }

    public void setScalarMaxValue(double scalarMaxVal)
    {
        this.scalarMaxValue = scalarMaxVal;
    }

    public double getScalarMinValue()
    {
        return scalarMinValue;
    }
    
    public void setScalarMinValue(double scalarMinVal)
    {
        this.scalarMinValue = scalarMinVal;
    }

    public double getGaussStdDev()
    {
        return gaussStdDev;
    }

    public void setGaussStdDev(double gaussStdDev)
    {
        this.gaussStdDev = gaussStdDev;
    }
    
    public double getVariableValue(int i)
    {
        return varValues.get(i);
    }
    
    public void setVariableValue(int i, double value)
    {
        varValues.set(i, value);
    }
    
    public int addVariable(String name)
    {
        int i = varNames.size();
        if (varNames.contains(name)) return -1;
        varNames.add(name);
        varValues.add(0.0);
        return i;
    }
    
    public int findVariable(String name)
    {
        return varNames.indexOf(name);
    }
    
    public String getVariableName(int i)
    {
        return varNames.get(i);
    }
    
    public void setVariableName(int i, String name)
    {
        if (varNames.contains(name)) return;
        varNames.set(i, name);
    }
    
    public int getVariableCount()
    {
        return varValues.size();
    }

    public Random getRandom()
    {
        return RANDOM;
    }
    
    // TODO: possibly add "remove" operations for variables?
    
    private Environment()
    {}
}
