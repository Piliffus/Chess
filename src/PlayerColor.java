public enum PlayerColor
{
    WHITE, BLACK, COLORLESS;

    public static int howManyColors()
    {
        // Excludes colorless
        return 2;
    }
}
