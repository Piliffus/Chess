public class Board
{
    private int sizeX;
    private int sizeY;
    private Field[][] fields;
    private Output output;

    public void putPiece(Piece piece, Coordinate coordinate)
    {
        // We must remove piece from it`s previous position, but only if it`s on the board
        if (piece.getCurrentPosition() != null)
        {
            fields[piece.getCurrentPosition().getX()][piece.getCurrentPosition().getY()].setPiece(null);
        }
        // If our destination is occupied by another piece, we remove it from the game. Calculation of possible moves
        // ensures we won`t remove friendly piece
        if (fields[coordinate.getX()][coordinate.getY()].getPiece() != null)
        {
            fields[coordinate.getX()][coordinate.getY()].getPiece().getOwner()
                    .removePiece(fields[coordinate.getX()][coordinate.getY()].getPiece());
        }
        // We then put piece on it`s new position
        fields[coordinate.getX()][coordinate.getY()].setPiece(piece);
        // And update it`s currentPosition info used for calculating moves
        piece.setCurrentPosition(coordinate);
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
        this.output = OutputStdout.getInstance();
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
