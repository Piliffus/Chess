public class SpectatorPlayer extends Player
{
    @Override
    public void givePiece(Piece piece)
    {
        System.out.println("Spectator does not have any pieces");
    }

    @Override
    public void printPieces()
    {
        System.out.println("Spectator does not have any pieces");
    }

    @Override
    public void PutPiecesOnStartingPositions(Board board)
    {
        System.out.println("Spectator does not have any pieces");
    }

    public SpectatorPlayer(PlayerColor color)
    {
        // Spectator overrides assigned color to be colorless
        super(color);
        this.color = PlayerColor.COLORLESS;
    }
}
