import java.util.ArrayList;
import java.util.List;

public enum PieceType
{
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;

    public static int howManyPieceTypes()
    {
        return 6;
    }

    public static int howManyStartingPiecesOfType(PieceType pieceType)
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

    public static char findLookFor(PieceType pieceType, PlayerPiecesColor playerPiecesColor)
    {
        switch (playerPiecesColor)
        {
            case COLORLESS:
                return '?';
            case black:
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
            case white:
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

    public static List<Coordinate> getStartingCoordinatesFor(PieceType pieceType, PlayerPiecesColor color)
    {
        List<Coordinate> returnedList = new ArrayList<>(1);
        int colorValue = color == PlayerPiecesColor.white ? 0 : 7;

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
                colorValue = color == PlayerPiecesColor.white ? 1 : 6;
                for (int i = 0; i < howManyStartingPiecesOfType(PieceType.PAWN); i++)
                {
                    returnedList.add(new Coordinate(i, colorValue));
                }
                break;
        }

        return returnedList;
    }
}
