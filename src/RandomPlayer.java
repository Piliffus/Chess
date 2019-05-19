import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player
{
    private Random random;

    @Override
    public void nextMove() throws NoPossibleMovesException
    {
        List<PossibleMove> possibleMoves = new ArrayList<>(0);
        for (PieceType pieceType : PieceType.values())
        {
            List<Piece> pieces = findListWithType(pieceType);
            for (Piece piece : pieces)
            {
                possibleMoves.addAll(calculatePossibleMoves(piece));
            }
        }

        if (!possibleMoves.isEmpty())
        {
            PossibleMove moveToMake = possibleMoves.get(random.nextInt(possibleMoves.size()));
            output.prepareTurnEndMessage(moveToMake, board);
            putPieceOnPosition(moveToMake);
        }
        else throw new NoPossibleMovesException(this);
    }

    public RandomPlayer(PlayerColor color, String name, Board board)
    {
        super(color, name, board);
        this.random = new Random();
    }
}
