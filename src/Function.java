public abstract class Function extends Node
{
    protected int type;

    protected Function(Node[] children, int type)
    {
        super(children);
        this.type = type;
    }

    public abstract int getNumTypes();
    
    @Override
    public void mutate()
    {
        int rand = (int)(Math.random()*(getNumTypes()-1));
        if (rand >= this.type) rand++;
        type = rand;
    }
}
