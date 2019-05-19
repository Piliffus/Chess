public class NoPossibleMovesException extends Exception
{
    private Player loser;

    public Player getLoser()
    {
        return loser;
    }

    public NoPossibleMovesException(Player player)
    {
        super();
        this.loser = player;
    }
}
