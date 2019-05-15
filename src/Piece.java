public class Piece
{
    private PieceType pieceType;
    private PlayerColor color;
    private char look;

    public char getLook()
    {
        return look;
    }

    public PieceType getPieceType()
    {
        return pieceType;
    }

    public Piece(PieceType pieceType, PlayerColor color)
    {
        this.pieceType = pieceType;
        this.color = color;
        this.look = RuleBook.getInstance().findLookFor(pieceType, color);
    }
}
