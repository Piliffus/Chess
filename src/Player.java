import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Player
{
    protected PlayerColor color;
    private List<List<Piece>> pieces;

    public final PlayerColor getColor()
    {
        return color;
    }

    public void printPieces()
    {
        Output.printPieces(pieces);
    }

    public void givePiece(Piece piece)
    {
        Objects.requireNonNull(findListWithType(piece.getPieceType())).add(piece);
    }

    private List<Piece> findListWithType(PieceType pieceType)
    {
        switch (pieceType)
        {
            case KING:
                return pieces.get(0);
            case QUEEN:
                return pieces.get(1);
            case ROOK:
                return pieces.get(2);
            case BISHOP:
                return pieces.get(3);
            case KNIGHT:
                return pieces.get(4);
            case PAWN:
                return pieces.get(5);
            default:
                return null;
        }
    }

    public Player(PlayerColor color)
    {
        this.color = color;
        pieces = new ArrayList<>(PieceType.howManyPieceTypes());

        for (int i = 0; i < PieceType.howManyPieceTypes(); i++)
        {
            pieces.add(i, new ArrayList<>(1));
        }
    }

    public void restartPieces()
    {
        for (List<Piece> pieceType : pieces)
        {
            pieceType.clear();
        }
    }

    public void PutPiecesOnStartingPositions(Board board)
    {
        List<Coordinate> coordinates;

        for (PieceType pieceType : PieceType.values())
        {
            coordinates = RuleBook.getInstance().getStartingCoordinatesFor(pieceType, this.color);

            for (int i = 0, coordinatesSize = coordinates.size(); i < coordinatesSize; i++)
            {
                Coordinate coordinate = coordinates.get(i);
                putPieceOnPosition(board, getPiece(pieceType, i), coordinate);
            }
        }
    }

    private void putPieceOnPosition(Board board, Piece piece, Coordinate coordinate)
    {
        board.putPiece(piece, coordinate);
    }

    private Piece getPiece(PieceType pieceType, int whichPiece)
    {
        return Objects.requireNonNull(findListWithType(pieceType)).get(whichPiece);
    }
}
