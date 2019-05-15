import java.util.ArrayList;
import java.util.List;

public class RuleBook
{
    private static RuleBook ourInstance = new RuleBook();

    public static RuleBook getInstance()
    {
        return ourInstance;
    }

    public int howManyStartingPiecesOfType(PieceType pieceType)
    {
        switch (pieceType)
        {
            case PAWN: return 8;
            case BISHOP:
            case KNIGHT:
            case ROOK:
                return 2;
            case QUEEN:
            case KING:
                return 1;
            default:
                return 0;
        }
    }

    public char findLookFor(PieceType pieceType, PlayerColor playerColor)
    {
        switch (playerColor)
        {
            case COLORLESS:
                return '?';
            case BLACK:
                switch (pieceType)
                {
                    case KING:
                        return '♚';
                    case QUEEN:
                        return '♛';
                    case ROOK:
                        return '♜';
                    case BISHOP:
                        return '♝';
                    case KNIGHT:
                        return '♞';
                    case PAWN:
                        return '♟';
                }
            case WHITE:
                switch (pieceType)
                {
                    case KING:
                        return '♔';
                    case QUEEN:
                        return '♕';
                    case ROOK:
                        return '♖';
                    case BISHOP:
                        return '♗';
                    case KNIGHT:
                        return '♘';
                    case PAWN:
                        return '♙';
                }
        }

        return '?';
    }

    public List<Coordinate> getStartingCoordinatesFor(PieceType pieceType, PlayerColor color)
    {
        List<Coordinate> returnedList = new ArrayList<>(1);
        int colorValue = color == PlayerColor.WHITE ? 0 : 7;

        switch (pieceType)
        {
            case ROOK:
                returnedList.add(new Coordinate(0, colorValue));
                returnedList.add(new Coordinate(7, colorValue));
                break;
            case KNIGHT:
                returnedList.add(new Coordinate(1, colorValue));
                returnedList.add(new Coordinate(6, colorValue));
                break;
            case BISHOP:
                returnedList.add(new Coordinate(2, colorValue));
                returnedList.add(new Coordinate(5, colorValue));
                break;
            case QUEEN:
                returnedList.add(new Coordinate(3, colorValue));
                break;
            case KING:
                returnedList.add(new Coordinate(4, colorValue));
                break;
            case PAWN:
                colorValue = color == PlayerColor.WHITE ? 1 : 6;
                for (int i = 0; i < howManyStartingPiecesOfType(PieceType.PAWN); i++)
                {
                    returnedList.add(new Coordinate(i, colorValue));
                }
                break;
        }

        return returnedList;
    }

    private RuleBook()
    {
    }
}
