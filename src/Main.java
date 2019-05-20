public class Main
{
    public static void main(String[] args)
    {
        GameController gameController = new GameController(8, 8, 2, 50, false, true, true);
        gameController.start();
    }
}
