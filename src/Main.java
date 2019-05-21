public class Main
{
    //TODO: animation speed setting?
    public static void main(String[] args)
    {
        GameController gameController = new GameController(8, 8, 3, 50,
                false, true, true);
        gameController.start();
    }
}