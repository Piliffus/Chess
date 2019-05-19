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
    private boolean gameOver;

    public void start()
    {
        restartGameOver();
        restartTurnNumber();
        restartPlayerPieces();
        playersSetUpPieces();
        printStartingBoard();
        playGame();
    }

    private void restartGameOver()
    {
        gameOver = false;
    }

    // TODO: komunikaty o ruchach, zbiciu, końcu gry - excep, timeout, krolzbity,
    private void playGame()
    {
        for (; turnNumber <= 50 && !gameOver; turnNumber++)
        {
            output.printTurnNumber(turnNumber);
            playTurn();
        }
    }

    private void playTurn()
    {
        for (Player player : players)
        {
            if (player.kingLost()) gameOver = true;

            if (!gameOver)
            {
                try
                {
                    player.nextMove();
                }
                catch (NoPossibleMovesException e)
                {
                    gameOver = true;
                }
                finally
                {
                    output.printBoard(board);
                }
            }
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
            player.PutPiecesOnStartingPositions();
        }
    }

    private void restartTurnNumber()
    {
        this.turnNumber = 1;
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
                players.add(i, new RandomPlayer(PlayerColor.WHITE, input.readName(), board));
            }
            else if (i == 1)
            {
                output.printPlayerNamePrompt(PlayerColor.BLACK);
                players.add(i, new RandomPlayer(PlayerColor.BLACK, input.readName(), board));
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
                player.givePiece(new Piece(pieceType, player.getColor(), player));
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

