import java.util.List;

public class OutputStdout implements Output
{
    private static OutputStdout ourInstance = new OutputStdout();

    public static OutputStdout getInstance()
    {
        return ourInstance;
    }

    @Override
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

    @Override
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

    @Override
    public void printStartingMessage()
    {
        System.out.println("Chessboard:");
    }

    @Override
    public void printPlayerNames(List<Player> players)
    {
        for (Player player : players)
        {
            System.out.println(player.getColor().name() + ": " + player.getName());
        }
    }

    @Override
    public void printSpectatorNamePrompt()
    {
        System.out.println("Assign name to a spectator:");
    }

    @Override
    public void printPlayerNamePrompt(PlayerColor color)
    {
        System.out.println("Assign name to player playing " + color.name() + " pieces:");
    }

    @Override
    public void printSpectatorNames(List<Spectator> spectators)
    {
        boolean first = true;
        boolean justOneSpectator = true;
        for (Spectator spectator : spectators)
        {
            if (!first)
            {
                if (justOneSpectator)
                {
                    justOneSpectator = false;
                }

                System.out.print(", " + spectator.getName());
            }
            else
            {
                System.out.print(spectator.getName());
                first = false;
            }
        }

        if (justOneSpectator)
        {
            System.out.println(" is watching the game");
        }
        else
        {
            System.out.println(" are watching the game");
        }
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
