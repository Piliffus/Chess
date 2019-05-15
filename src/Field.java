public class Field
{
    private Piece piece;

    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    public Field()
    {
        this.piece = null;
    }
}
