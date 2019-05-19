public class Board
{
    private int sizeX;
    private int sizeY;
    private Field[][] fields;
    Output output = OutputStdout.getInstance();

    public void putPiece(Piece piece, Coordinate coordinate)
    {
        fields[coordinate.getX()][coordinate.getY()].setPiece(piece);
    }

    public void printBoard()
    {
        output.printBoard(this);
    }

    public int getSizeX()
    {
        return sizeX;
    }

    public int getSizeY()
    {
        return sizeY;
    }

    public Field[][] getFields()
    {
        return fields;
    }

    public Board(int sizeX, int sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.fields = new Field[sizeX][sizeY];

        for (int x = 0; x < this.sizeX; x++)
        {
            for (int y = 0; y < this.sizeY; y++)
            {
                this.fields[x][y] = new Field();
            }
        }

    }
}
