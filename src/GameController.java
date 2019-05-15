import java.util.ArrayList;
import java.util.List;

public class GameController
{
    private Board board;
    private List<Player> players;
    private int turnNumber;

    public void start()
    {
        restartTurnNumber();
        restartPlayerPieces();
        playersSetUpPieces();
        board.printBoard();
    }

    private void restartPlayerPieces()
    {
        for (Player player : players)
        {
            if (player.getClass() != SpectatorPlayer.class)
            {
                giveStartingPieces(player);
            }
        }
    }

    private void playersSetUpPieces()
    {
        for (Player player : players)
        {
            if (player.getClass() != SpectatorPlayer.class)
            {
                player.PutPiecesOnStartingPositions(board);
            }
        }
    }

    private void restartTurnNumber()
    {
        this.turnNumber = 0;
    }

    private void prepareBoard(int x, int y)
    {
        this.board = new Board(x, y);
    }

    private void preparePlayers(int howManyPlayers)
    {
        //TODO: different types of players
        players = new ArrayList<>(howManyPlayers);

        for (int i = 0; i < howManyPlayers; i++)
        {
            if (i == 0)
            {
                players.add(i, new RandomPlayer(PlayerColor.WHITE));
            }
            else if (i == 1)
            {
                players.add(i, new RandomPlayer(PlayerColor.BLACK));
            }
            else
            {
                players.add(i, new SpectatorPlayer(PlayerColor.COLORLESS));
            }
        }
    }

    private void giveStartingPieces(Player player)
    {
        player.restartPieces();

        for (PieceType pieceType : PieceType.values())
        {
            for (int i = 0; i < RuleBook.getInstance().howManyStartingPiecesOfType(pieceType); i++)
            {
                player.givePiece(new Piece(pieceType, player.getColor()));
            }
        }
    }

    public GameController(int boardXSize, int boardYSize, int howManyPlayers)
    {
        prepareBoard(boardXSize, boardYSize);
        preparePlayers(howManyPlayers);
    }
}

