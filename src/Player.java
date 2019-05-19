import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Player
{
    private PlayerColor color;
    private List<List<Piece>> pieces;
    private String name;
    private Output output;

    public final PlayerColor getColor()
    {
        return color;
    }

    public final String getName()
    {
        return name;
    }

    public final void printPieces()
    {
        output.printPieces(pieces);
    }

    public final void givePiece(Piece piece)
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

    public final void restartPieces()
    {
        for (List<Piece> pieceType : pieces)
        {
            pieceType.clear();
        }
    }

    public final void PutPiecesOnStartingPositions(Board board)
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

    public Player(PlayerColor color, String name)
    {
        this.output = OutputStdout.getInstance();
        this.color = color;
        // Make main list with types
        this.pieces = new ArrayList<>(PieceType.howManyPieceTypes());

        // Make specific lists with types
        for (int i = 0; i < PieceType.howManyPieceTypes(); i++)
        {
            this.pieces.add(i, new ArrayList<>(1));
        }

        this.name = name;

    }
}
