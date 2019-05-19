import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Player
{
    private PlayerColor color;
    private List<List<Piece>> pieces;
    private String name;
    private Output output;
    private Board board;

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

    protected final List<PossibleMove> calculatePossibleMoves(Piece piece)
    {
        List<PossibleMove> returnedPossibleMoves = new ArrayList<>(0);
        List<Coordinate> newPossibleCoordinates = getPossibleMoves(piece);

        for (Coordinate newPossibleCoordinate : newPossibleCoordinates)
        {
            returnedPossibleMoves.add(new PossibleMove(piece, newPossibleCoordinate));
        }

        return returnedPossibleMoves;
    }

    protected class PossibleMove
    {
        private Piece piece;
        private Coordinate newCoordinate;

        public PossibleMove(Piece piece, Coordinate newCoordinate)
        {
            this.piece = piece;
            this.newCoordinate = newCoordinate;
        }
    }

    public final void restartPieces()
    {
        for (List<Piece> pieceType : pieces)
        {
            pieceType.clear();
        }
    }

    protected final List<Piece> findListWithType(PieceType pieceType)
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
                return new ArrayList<>(0);
        }
    }

    public final void PutPiecesOnStartingPositions()
    {
        List<Coordinate> coordinates;

        for (PieceType pieceType : PieceType.values())
        {
            coordinates = PieceType.getStartingCoordinatesFor(pieceType, this.color);

            for (int i = 0, coordinatesSize = coordinates.size(); i < coordinatesSize; i++)
            {
                Coordinate coordinate = coordinates.get(i);
                putPieceOnPosition(getPiece(pieceType, i), coordinate);
            }
        }
    }

    private void putPieceOnPosition(Piece piece, Coordinate coordinate)
    {
        board.putPiece(piece, coordinate);
    }

    protected void putPieceOnPosition(PossibleMove madeMove)
    {
        board.putPiece(madeMove.piece, madeMove.newCoordinate);
    }

    private Piece getPiece(PieceType pieceType, int whichPiece)
    {
        return Objects.requireNonNull(findListWithType(pieceType)).get(whichPiece);
    }

    private List<Coordinate> getPossibleMoves(Piece piece)
    {
        switch (piece.getPieceType())
        {
            case KING:
                return getPossibleMovesKing(piece);
//            case QUEEN:
//                return getPossibleMovesQueen(piece);
//            case ROOK:
//                return getPossibleMovesRook(piece);
//            case BISHOP:
//                return getPossibleMovesBishop(piece);
//            case KNIGHT:
//                return getPossibleMovesKnight(piece);
//            case PAWN:
//                return getPossibleMovesPawn(piece);
            default:
                return new ArrayList<>(0);
        }
    }

    private List<Coordinate> getPossibleMovesKing(Piece piece)
    {
        ArrayList<Coordinate> returnedPossibleNewCoordinates = new ArrayList<>(0);

        // three rows - above, below and current
        for (int y = -1; y < 2; y++)
        {
            for (int x = -1; x < 2; x++)
            {
                int newX = piece.getCurrentPosition().getX() + x;
                int newY = piece.getCurrentPosition().getY() + y;
                if (xOk(newX) && yOk(newY))
                {
                    if (!fieldOccupiedByFriendly(newX, newY))
                    {
                        returnedPossibleNewCoordinates.add(new Coordinate(newX, newY));
                    }
                }
            }
        }

        return returnedPossibleNewCoordinates;
    }

    private boolean fieldOccupiedByFriendly(int newX, int newY)
    {
        Piece pieceOnCheckedField = board.getFields()[newX][newY].getPiece();
        // Checked field is empty
        if (pieceOnCheckedField == null)
        {
            return false;
        }
        // If piece on checked field has same color as piece we are about to move, then it is friendly so return true
        else return pieceOnCheckedField.getColor() == this.color;
    }

    private boolean yOk(int newY)
    {
        return newY < board.getSizeY() && newY >= 0;
    }

    private boolean xOk(int newX)
    {
        return newX < board.getSizeX() && newX >= 0;
    }

    public Player(PlayerColor color, String name, Board board)
    {
        this.board = board;
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

    public abstract void nextMove();
}
