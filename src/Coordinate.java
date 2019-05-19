public class Coordinate
{
    private int x;
    private int y;

    @Override
    public String toString()
    {
        char xRowName = 'A';
        xRowName += x;

        return "" + xRowName + (y+1);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
