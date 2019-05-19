import java.util.ArrayList;
import java.util.List;

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
        findListWithType(piece.getPieceType()).add(piece);
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
        return findListWithType(pieceType).get(whichPiece);
    }

    private List<Coordinate> getPossibleMoves(Piece piece)
    {
        // Queen, rook and bishop use fieldSuitability without bound checking
        switch (piece.getPieceType())
        {
            case KING:
                return getPossibleMovesKing(piece);
            case QUEEN:
                return getPossibleMovesQueen(piece);
            case ROOK:
                return getPossibleMovesRook(piece);
            case BISHOP:
                return getPossibleMovesBishop(piece);
            case KNIGHT:
                return getPossibleMovesKnight(piece);
            case PAWN:
                return getPossibleMovesPawn(piece);
            default:
                return new ArrayList<>(0);
        }
    }

    private List<Coordinate> getPossibleMovesPawn(Piece piece)
    {
        ArrayList<Coordinate> returnedPossibleNewCoordinates = new ArrayList<>(0);

        fieldSuitabilityCheckWithBoundCheck(piece.getCurrentPosition().getX(), piece.getCurrentPosition().getY() + 1,
                returnedPossibleNewCoordinates);

        fieldSuitabilityCheckPawnSpecialCase(piece.getCurrentPosition().getX() + 1, piece.getCurrentPosition().getY()+1,
                returnedPossibleNewCoordinates);

        fieldSuitabilityCheckPawnSpecialCase(piece.getCurrentPosition().getX() - 1, piece.getCurrentPosition().getY()+1,
                returnedPossibleNewCoordinates);

        return returnedPossibleNewCoordinates;
    }

    private boolean fieldSuitabilityCheckPawnSpecialCase(int x, int y, ArrayList<Coordinate> returnedPossibleNewCoordinates)
    {
        // Check for bounds
        if (xOk(x) && yOk(y))
        {
            if (!fieldOccupiedByFriendly(x, y))
            {
                if (fieldOccupiedByEnemy(x, y))
                {
                    returnedPossibleNewCoordinates.add(new Coordinate(x, y));
                    return true;
                }
                else // Pawn can only move there if it has an enemy there
                {
                    return false;
                }
            }
            else // field occupied by friendly, so we return false
            {
                return false;
            }
        }
        // Out of bounds
        else return false;
    }

    private List<Coordinate> getPossibleMovesKnight(Piece piece)
    {
        ArrayList<Coordinate> returnedPossibleNewCoordinates = new ArrayList<>(0);

        // up
        for (int addedToY = 1, removedAndAddedToX = 2; addedToY <= 2; addedToY++, removedAndAddedToX--)
        {
            fieldSuitabilityCheckWithBoundCheck(piece.getCurrentPosition().getX() + removedAndAddedToX,
                    piece.getCurrentPosition().getY() + addedToY, returnedPossibleNewCoordinates);
            fieldSuitabilityCheckWithBoundCheck(piece.getCurrentPosition().getX() - removedAndAddedToX,
                    piece.getCurrentPosition().getY() + addedToY, returnedPossibleNewCoordinates);
        }

        // down
        for (int removedFromY = 1, removedAndAddedToX = 2; removedFromY <= 2; removedFromY++, removedAndAddedToX--)
        {
            fieldSuitabilityCheckWithBoundCheck(piece.getCurrentPosition().getX() + removedAndAddedToX,
                    piece.getCurrentPosition().getY() - removedFromY, returnedPossibleNewCoordinates);
            fieldSuitabilityCheckWithBoundCheck(piece.getCurrentPosition().getX() - removedAndAddedToX,
                    piece.getCurrentPosition().getY() - removedFromY, returnedPossibleNewCoordinates);
        }

        return returnedPossibleNewCoordinates;
    }

    private List<Coordinate> getPossibleMovesQueen(Piece piece)
    {
        List<Coordinate> returnedPossibleNewCoordinates = getPossibleMovesRook(piece);
        returnedPossibleNewCoordinates.addAll(getPossibleMovesBishop(piece));

        return returnedPossibleNewCoordinates;
    }

    private List<Coordinate> getPossibleMovesBishop(Piece piece)
    {
        ArrayList<Coordinate> returnedPossibleNewCoordinates = new ArrayList<>(0);

        // up - right

        for (int newX = piece.getCurrentPosition().getX() + 1, newY = piece.getCurrentPosition().getY() + 1;
             newX < board.getSizeX() && newY < board.getSizeY(); newX++, newY++)
        {
            if (!fieldSuitabilityCheck(newX, newY, returnedPossibleNewCoordinates)) break;
        }

        // up - left

        for (int newX = piece.getCurrentPosition().getX() - 1, newY = piece.getCurrentPosition().getY() + 1;
             newX >= 0 && newY < board.getSizeY(); newX--, newY++)
        {
            if (!fieldSuitabilityCheck(newX, newY, returnedPossibleNewCoordinates)) break;
        }

        // down - right

        for (int newX = piece.getCurrentPosition().getX() + 1, newY = piece.getCurrentPosition().getY() - 1;
             newX < board.getSizeX() && newY >= 0; newX++, newY--)
        {
            if (!fieldSuitabilityCheck(newX, newY, returnedPossibleNewCoordinates)) break;
        }

        // down - left

        for (int newX = piece.getCurrentPosition().getX() - 1, newY = piece.getCurrentPosition().getY() - 1;
             newX >= 0 && newY >= 0; newX--, newY--)
        {
            if (!fieldSuitabilityCheck(newX, newY, returnedPossibleNewCoordinates)) break;
        }

        return returnedPossibleNewCoordinates;
    }

    private List<Coordinate> getPossibleMovesRook(Piece piece)
    {
        ArrayList<Coordinate> returnedPossibleNewCoordinates = new ArrayList<>(0);

        // left

        for (int newX = piece.getCurrentPosition().getX() - 1; newX >= 0; newX--)
        {
            if (!fieldSuitabilityCheck(newX, piece.getCurrentPosition().getY(), returnedPossibleNewCoordinates)) break;
        }

        // down

        for (int newY = piece.getCurrentPosition().getY() - 1; newY >= 0; newY--)
        {
            if (!fieldSuitabilityCheck(piece.getCurrentPosition().getX(), newY, returnedPossibleNewCoordinates)) break;
        }

        // right

        for (int newX = piece.getCurrentPosition().getX() + 1; newX < board.getSizeX(); newX++)
        {
            if (!fieldSuitabilityCheck(newX, piece.getCurrentPosition().getY(), returnedPossibleNewCoordinates)) break;
        }

        // up

        for (int newY = piece.getCurrentPosition().getY() + 1; newY < board.getSizeY(); newY++)
        {
            if (!fieldSuitabilityCheck(piece.getCurrentPosition().getX(), newY, returnedPossibleNewCoordinates)) break;
        }

        return returnedPossibleNewCoordinates;
    }

    private boolean fieldSuitabilityCheck(int x, int y, ArrayList<Coordinate> returnedPossibleNewCoordinates)
    {
        if (!fieldOccupiedByFriendly(x, y))
        {
            returnedPossibleNewCoordinates.add(new Coordinate(x, y));
            return true;
        }
        else // field occupied by friendly, so we return false: signal to finish searching for possible new coordinates.
        {
            return false;
        }
    }

    private boolean fieldSuitabilityCheckWithBoundCheck(int x, int y, ArrayList<Coordinate> returnedPossibleNewCoordinates)
    {
        // Additional check for bounds
        if (xOk(x) && yOk(y))
        {
            if (!fieldOccupiedByFriendly(x, y))
            {
                returnedPossibleNewCoordinates.add(new Coordinate(x, y));
                return true;
            }
            else // field occupied by friendly, so we return false: signal to finish searching for possible new coordinates.
            {
                return false;
            }
        }
        // Out of bounds
        else return false;
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
                fieldSuitabilityCheckWithBoundCheck(newX, newY, returnedPossibleNewCoordinates);
            }
        }

        return returnedPossibleNewCoordinates;
    }

    private boolean fieldOccupiedByEnemy(int newX, int newY)
    {
        Piece pieceOnCheckedField = board.getFields()[newX][newY].getPiece();
        // Checked field is empty
        if (pieceOnCheckedField == null)
        {
            return false;
        }
        // If piece on checked field has different color from piece we are about to move, then it is enemy so return true
        else return pieceOnCheckedField.getColor() != this.color;
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
