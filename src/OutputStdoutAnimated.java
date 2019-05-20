import java.util.List;
import java.util.concurrent.TimeUnit;

public class OutputStdoutAnimated extends OutputStdout
{
    private static OutputStdoutAnimated ourInstance = new OutputStdoutAnimated();

    public static OutputStdoutAnimated getInstance()
    {
        return ourInstance;
    }

    @Override
    public void printBoard(Board board, int turnNumber)
    {
        waitFrame();
        clearScreen();
        super.printBoard(board, turnNumber);
        if (!printTurnNumber) printTurnNumber = true;
    }
/*
    todo diferent OS recognition
    todo king defeated bug - wrong loser
*/
private void waitFrame()
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(1500);
        }
        catch (java.lang.InterruptedException e)
        {
            System.exit(-1);
        }
    }

    private void waitLongFrame()
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(3000);
        }
        catch (java.lang.InterruptedException e)
        {
            System.exit(-1);
        }
    }

    @Override
    public void printStartingMessage(List<Player> players, List<Spectator> spectators, Board board)
    {
        super.printStartingMessage(players, spectators, board);
        waitLongFrame();
    }

    @Override
    protected void printStaringBoard(Board board)
    {
        int turn = 0;
        super.printBoard(board, turn);
    }

    private OutputStdoutAnimated()
    {
        super();
    }
}
