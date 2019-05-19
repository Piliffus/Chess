import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player
{
    private Random random;

    @Override
    public void nextMove()
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

        //TODO: end game if no moves possible
        if (!possibleMoves.isEmpty())
        {
            PossibleMove moveToMake = possibleMoves.get(random.nextInt(possibleMoves.size()));
            putPieceOnPosition(moveToMake);
        }
    }

    public RandomPlayer(PlayerColor color, String name, Board board)
    {
        super(color, name, board);
        this.random = new Random();
    }
}
