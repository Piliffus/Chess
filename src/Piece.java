import java.util.ArrayList;
import java.util.List;

public class Piece
{
    private PieceType pieceType;
    private PlayerColor color;
    private char look;
    private Coordinate currentPosition;

    public char getLook()
    {
        return look;
    }

    public Coordinate getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(Coordinate currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public PlayerColor getColor()
    {
        return color;
    }

    public PieceType getPieceType()
    {
        return pieceType;
    }

    public Piece(PieceType pieceType, PlayerColor color)
    {
        this.pieceType = pieceType;
        this.color = color;
        this.look = PieceType.findLookFor(pieceType, color);
        this.currentPosition = null;
    }
}
