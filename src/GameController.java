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
    // If waitForUser is true game will wait for prompt from user before each turn
    private boolean waitForUser;
    // If randomNames is true game won`t ask player for custom player names
    private boolean randomNames;

    // TODO: fauxAnimatedMode

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

    private void playGame()
    {
        if (waitForUser)
        {
            input.userConfirmation();
        }

        for (; turnNumber <= 50 && !gameOver; turnNumber++)
        {
            output.printTurnNumber(turnNumber);
            playTurn();
            if (waitForUser)
            {
                input.userConfirmation();
            }
        }

        if (!gameOver)
        {
            output.prepareGameEndMessage(players, null, GameEndConditions.TIMEOUT);
            gameOver = true;
        }

        output.printPreparedGameEndMessage();
    }

    private void playTurn()
    {
        for (Player player : players)
        {
            checkIfAnyKingDefeated();

            if (!gameOver)
            {
                try
                {
                    player.nextMove();
                }
                catch (NoPossibleMovesException e)
                {
                    gameOver = true;
                    output.prepareGameEndMessage(players, e.getLoser(), GameEndConditions.OUT_OF_MOVES);
                }
                finally
                {
                    output.printBoard(board);
                    output.printPreparedTurnEndMessage();
                }
            }
        }
    }

    private void checkIfAnyKingDefeated()
    {
        for (Player player : players)
        {
            if (player.kingLost())
            {
                gameOver = true;
                output.prepareGameEndMessage(players, player, GameEndConditions.KING_DEFEATED);
            }
        }
    }

    protected enum GameEndConditions
    {
        KING_DEFEATED, TIMEOUT, OUT_OF_MOVES
    }

    private void printStartingBoard()
    {
        output.printStartingMessage();
        board.printBoard();
        output.printPlayerNames(players);
        if (!spectators.isEmpty())
        {
            output.printSpectatorNames(spectators);
        }
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
        players = new ArrayList<>(PlayerPiecesColor.howManyColors());
        // If we have less players than colors, then amount of spectators defaults to 0, se we don`t get negative size
        spectators = new ArrayList<>(howManyPlayers - PlayerPiecesColor.howManyColors() >= 0 ?
                howManyPlayers - PlayerPiecesColor.howManyColors() : 0);

        for (int i = 0; i < howManyPlayers; i++)
        {
            if (i == 0)
            {
                if (!randomNames) output.printPlayerNamePrompt(PlayerPiecesColor.white);
                players.add(i, new RandomPlayer(PlayerPiecesColor.white, randomNames ? input.randomName() : input.readName(), board));
            }
            else if (i == 1)
            {
                if (!randomNames) output.printPlayerNamePrompt(PlayerPiecesColor.black);
                players.add(i, new RandomPlayer(PlayerPiecesColor.black, randomNames ? input.randomName() : input.readName(), board));
            }
            else
            {
                // Excess players will be spectators
                if (!randomNames) output.printSpectatorNamePrompt();
                spectators.add(new Spectator(randomNames ? input.randomName() : input.readName()));
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

    public GameController(int boardXSize, int boardYSize, int howManyPlayers, boolean waitForUser, boolean randomNames)
    {
        this.waitForUser = waitForUser;
        this.randomNames = randomNames;
        this.input = InputStdin.getInstance();
        this.output = OutputStdout.getInstance();
        prepareBoard(boardXSize, boardYSize);
        preparePlayers(howManyPlayers);
    }
}

