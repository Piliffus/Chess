import java.util.List;

public class OutputStdout implements Output
{
    private String preparedEndTurnMessage;
    private String preparedGameEndMessage;

    private static OutputStdout ourInstance = new OutputStdout();

    public static OutputStdout getInstance()
    {
        return ourInstance;
    }

    @Override
    public void prepareTurnEndMessage(Player.PossibleMove moveToMake, Board board)
    {
        Piece otherPiece = board.getFields()[moveToMake.getNewCoordinate().getX()][moveToMake.getNewCoordinate().getY()].getPiece();
        Piece movedPiece = moveToMake.getPiece();

        preparedEndTurnMessage = "" + movedPiece.getLook();
        preparedEndTurnMessage += " moves from ";
        preparedEndTurnMessage += movedPiece.getCurrentPosition().toString();
        preparedEndTurnMessage += " to ";
        preparedEndTurnMessage += moveToMake.getNewCoordinate().toString();
        if (otherPiece != null)
        {
            preparedEndTurnMessage += " beating ";
            preparedEndTurnMessage += otherPiece.getLook();
        }
        preparedEndTurnMessage += "\n\n"; // Looks clearer with additional empty line
    }

    @Override
    public void prepareGameEndMessage(List<Player> players, Player loser, GameController.GameEndConditions gameEndCondition)
    {
        String winnerName = "";
        if (loser != null)
        {
            for (Player player : players)
            {
                if (player != loser)
                {
                    winnerName = player.getName();
                    winnerName += " (";
                    winnerName += player.getColor().name();
                    winnerName += " pieces).";
                    break;
                }
            }
        }

        preparedGameEndMessage = "";
        switch (gameEndCondition)
        {
            case KING_DEFEATED:
                preparedGameEndMessage += "The King has been defeated, game is over!\n";
                preparedGameEndMessage += "And the winner is:\n";
                preparedGameEndMessage += winnerName + '\n';
                break;
            case TIMEOUT:
                preparedGameEndMessage += "Time is up!\n";
                preparedGameEndMessage += "The game ends with a draw\n";
                break;
            case OUT_OF_MOVES:
                preparedGameEndMessage += (loser == null ? "Somebody" : "Player " + loser.getName()) + " is out of moves!";
                preparedGameEndMessage += "And the winner is:\n";
                preparedGameEndMessage += winnerName + '\n';
                break;
        }
    }

    @Override
    public void printPreparedTurnEndMessage()
    {
        System.out.print(preparedEndTurnMessage);
        preparedEndTurnMessage = "";
    }

    @Override
    public void printPreparedGameEndMessage()
    {
        System.out.print(preparedGameEndMessage);
        preparedGameEndMessage = "";
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
        printXRowsNames(board.getSizeX());
        printBoardFrame(boardSides.TOP, board.getSizeX());
        for (int y = board.getSizeY() - 1; y >= 0; y--)
        {
            printYRowName(y+1);
            printBoardFrame(boardSides.LEFT);
            for (int x = 0; x < board.getSizeX(); x++)
            {
                if (board.getFields()[x][y].getPiece() == null)
                {
                    System.out.print(".");
                }
                else
                {
                    System.out.print(board.getFields()[x][y].getPiece().getLook());
                }
            }
            printBoardFrame(boardSides.RIGHT);
            System.out.print('\n');
        }
        printBoardFrame(boardSides.BOTTOM, board.getSizeX());
    }

    private void printYRowName(int rowNumber)
    {
        System.out.print(rowNumber);
    }

    private void printXRowsNames(int boardWidth)
    {
        System.out.print("  ");
        char initialCharacter = 'A';
        for (int i = 0; i < boardWidth; i++)
        {
            System.out.print(initialCharacter);
            initialCharacter++;
        }
        System.out.print('\n');
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
            System.out.println(player.getColor().name() + " pieces: " + player.getName());
        }
    }

    @Override
    public void printSpectatorNamePrompt()
    {
        System.out.println("Assign name to a spectator:");
    }

    @Override
    public void printPlayerNamePrompt(PlayerPiecesColor color)
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

    @Override
    public void printTurnNumber(int turnNumber)
    {
        System.out.println("Turn " + turnNumber + ":");
        System.out.print('\n');
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

        System.out.print(" ");
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
