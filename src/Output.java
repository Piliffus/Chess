import java.util.List;

public interface Output
{
    void printPieces(List<List<Piece>> pieces);

    void printBoard(Board board, int turnNumber);

    void printStartingMessage(List<Player> players, List<Spectator> spectators, Board board);

    void printPlayerNames(List<Player> players);

    void printSpectatorNames(List<Spectator> spectators);

    void printSpectatorNamePrompt();

    void printPlayerNamePrompt(PlayerPiecesColor color);

    void printTurnNumber(int turnNumber);

    void prepareTurnEndMessage(Player.PossibleMove moveToMake, Board board);

    void printPreparedTurnEndMessage();

    void printPreparedGameEndMessage();

    void prepareGameEndMessage(List<Player> players, Player player, GameController.GameEndConditions gameEndCondition);
}
