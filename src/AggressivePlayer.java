import java.util.List;
import java.util.Random;

public class AggressivePlayer extends Player
{
    private Random random;

    @Override
    public void nextMove() throws NoPossibleMovesException
    {
        List<PossibleMove> possibleMoves = getAllPossibleMoves();

        if (!possibleMoves.isEmpty())
        {
            // Aggressive player prefers beating moves
            PossibleMove moveToMake = bestBeatingMove(possibleMoves);
            // If we cannot beat any piece, then just do random move
            if (moveToMake == null)
            {
                moveToMake = possibleMoves.get(random.nextInt(possibleMoves.size()));
            }
            output.prepareTurnEndMessage(moveToMake, board);
            putPieceOnPosition(moveToMake);
        }
        else
        {
            throw new NoPossibleMovesException(this);
        }
    }

    private PossibleMove bestBeatingMove(List<PossibleMove> possibleMoves)
    {
        // Possible moves are moves that either go to empty space, or enemy. Former are discarded, and later will be
        // evaluated: K > Q > R > Kn > B > P. Finding K will terminate searching, as we always want to beat the king.

        PossibleMove bestMove = null;
        int bestScore = 0;

        for (PossibleMove possibleMove : possibleMoves)
        {
            Piece checkedPiece = board.getFields()
                    [possibleMove.getNewCoordinate().getX()][possibleMove.getNewCoordinate().getY()].getPiece();

            // It has to be enemy piece, friendly were discarded
            if (checkedPiece != null)
            {
                // King always takes precedence, as beating him will win us the game
                if (checkedPiece.getPieceType() == PieceType.KING) return possibleMove;

                int scoreForThisMove = getScoreFor(checkedPiece.getPieceType());

                if (scoreForThisMove > bestScore)
                {
                    bestMove = possibleMove;
                    bestScore = scoreForThisMove;
                }
                // draws are decided by chance
                else if (scoreForThisMove == bestScore)
                {
                    boolean keepCurrentMove = random.nextBoolean();
                    if (!keepCurrentMove)
                    {
                        bestMove = possibleMove;
                    }
                }
            }
        }

        return bestMove;
    }

    private int getScoreFor(PieceType pieceType)
    {
        switch (pieceType)
        {
            case KING:
                return 6;
            case QUEEN:
                return 5;
            case ROOK:
                return 4;
            case KNIGHT:
                return 3;
            case BISHOP:
                return 2;
            case PAWN:
                return 1;
            default:
                return 0;
        }
    }

    public AggressivePlayer(PlayerPiecesColor color, String name, Board board, Output output)
    {
        super(color, name, board, output);
        this.random = new Random();
    }
}
