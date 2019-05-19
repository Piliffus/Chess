import java.util.List;

public class Output
{
    public static void printPieces(List<List<Piece>> pieces)
    {
        for (List<Piece> pieceType: pieces)
        {
            for (Piece piece : pieceType)
            {
                System.out.print(piece.getLook());
                System.out.print(' ');
            }
        }
        System.out.print('\n');
    }

    public static void printBoard(Board board)
    {
        for (int x = 0; x < board.getSizeX(); x++)
        {
            for (int y = 0; y < board.getSizeY(); y++)
            {
                if (board.getFields()[y][x].getPiece() == null)
                {
                    System.out.print(".");
                }
                else
                {
                    System.out.print(board.getFields()[y][x].getPiece().getLook());
                }
            }
            System.out.print('\n');
        }
    }
}
