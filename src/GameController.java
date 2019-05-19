import java.util.ArrayList;
import java.util.List;

public class GameController
{
    private Board board;
    private List<Player> players;
    private List<Spectator> spectators;
    private int turnNumber;
    private Output output;
    private Input input;

    public void start()
    {
        restartTurnNumber();
        restartPlayerPieces();
        playersSetUpPieces();
        printStartingBoard();
        playTurn();
    }

    private void playTurn()
    {
        for (Player player : players)
        {
            player.nextMove();
        }
    }

    private void printStartingBoard()
    {
        output.printStartingMessage();
        board.printBoard();
        output.printPlayerNames(players);
        output.printSpectatorNames(spectators);
    }

    private void restartPlayerPieces()
    {
        for (Player player : players)
        {
            giveStartingPieces(player);
        }
    }

    private void playersSetUpPieces()
    {
        for (Player player : players)
        {
            player.PutPiecesOnStartingPositions(board);
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
        players = new ArrayList<>(PlayerColor.howManyColors());
        // If we have less players than colors, then amount of spectators defaults to 0, se we don`t get negative size
        spectators = new ArrayList<>(howManyPlayers - PlayerColor.howManyColors() >= 0 ?
                howManyPlayers - PlayerColor.howManyColors() : 0);

        for (int i = 0; i < howManyPlayers; i++)
        {
            if (i == 0)
            {
                output.printPlayerNamePrompt(PlayerColor.WHITE);
                players.add(i, new RandomPlayer(PlayerColor.WHITE, input.readName()));
            }
            else if (i == 1)
            {
                output.printPlayerNamePrompt(PlayerColor.BLACK);
                players.add(i, new RandomPlayer(PlayerColor.BLACK, input.readName()));
            }
            else
            {
                // Excess players will be spectators
                output.printSpectatorNamePrompt();
                spectators.add(new Spectator(input.readName()));
            }
        }
    }

    private void giveStartingPieces(Player player)
    {
        player.restartPieces();

        for (PieceType pieceType : PieceType.values())
        {
            for (int i = 0; i < PieceType.howManyStartingPiecesOfType(pieceType); i++)
            {
                player.givePiece(new Piece(pieceType, player.getColor()));
            }
        }
    }

    public GameController(int boardXSize, int boardYSize, int howManyPlayers)
    {
        this.input = InputStdin.getInstance();
        this.output = OutputStdout.getInstance();
        prepareBoard(boardXSize, boardYSize);
        preparePlayers(howManyPlayers);
    }
}

