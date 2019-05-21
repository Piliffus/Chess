import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player
{
    private Random random;

    @Override
    public void nextMove() throws NoPossibleMovesException
    {
        List<PossibleMove> possibleMoves = getAllPossibleMoves();

        if (!possibleMoves.isEmpty())
        {
            PossibleMove moveToMake = possibleMoves.get(random.nextInt(possibleMoves.size()));
            output.prepareTurnEndMessage(moveToMake, board);
            putPieceOnPosition(moveToMake);
        }
        else
        {
            throw new NoPossibleMovesException(this);
        }
    }

    public RandomPlayer(PlayerPiecesColor color, String name, Board board, Output output)
    {
        super(color, name, board, output);
        this.random = new Random();
    }
}
