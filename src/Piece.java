public class Piece
{
    private PieceType pieceType;
    private Player owner;
    private PlayerPiecesColor color;
    private char look;
    private Coordinate currentPosition;

    public void setCurrentPosition(Coordinate currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public char getLook()
    {
        return look;
    }

    public Coordinate getCurrentPosition()
    {
        return currentPosition;
    }

    public Player getOwner()
    {
        return owner;
    }

    public PlayerPiecesColor getColor()
    {
        return color;
    }

    public PieceType getPieceType()
    {
        return pieceType;
    }

    public Piece(PieceType pieceType, PlayerPiecesColor color, Player owner)
    {
        this.owner = owner;
        this.pieceType = pieceType;
        this.color = color;
        this.look = PieceType.findLookFor(pieceType, color);
        this.currentPosition = null;
    }
}
