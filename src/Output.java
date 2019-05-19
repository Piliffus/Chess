import java.util.List;

public interface Output
{
    void printPieces(List<List<Piece>> pieces);

    void printBoard(Board board);

    void printStartingMessage();

    void printPlayerNames(List<Player> players);

    void printSpectatorNames(List<Spectator> spectators);

    void printSpectatorNamePrompt();

    void printPlayerNamePrompt(PlayerColor color);
}
