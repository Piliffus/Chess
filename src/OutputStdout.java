import java.util.List;

public class OutputStdout implements Output
{
    private static OutputStdout ourInstance = new OutputStdout();

    public static OutputStdout getInstance()
    {
        return ourInstance;
    }

    public void printPieces(List<List<Piece>> pieces)
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

    public void printBoard(Board board)
    {
        printBoardFrame(boardSides.TOP, board.getSizeX());
        for (int x = 0; x < board.getSizeX(); x++)
        {
            printBoardFrame(boardSides.LEFT);
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
            printBoardFrame(boardSides.RIGHT);
            System.out.print('\n');
        }
        printBoardFrame(boardSides.BOTTOM, board.getSizeX());
    }

    private void printBoardFrame(boardSides side)
    {
        switch (side)
        {
            case LEFT:
            case RIGHT:
                System.out.print('║');
                break;
        }
    }

    private void printBoardFrame(boardSides side, int boardWidth)
    {
        char left = '?';
        char right = '?';

        switch (side)
        {
            case TOP:
                left = '╔';
                right = '╗';
                break;
            case BOTTOM:
                left = '╚';
                right = '╝';
                break;
        }

        System.out.print(left);
        for (int i = 0; i < boardWidth; i++)
        {
            System.out.print('═');
        }
        System.out.print(right);
        System.out.print('\n');
    }

    private enum boardSides
    {
        LEFT, RIGHT, TOP, BOTTOM
    }

    private OutputStdout()
    {
    }
}
