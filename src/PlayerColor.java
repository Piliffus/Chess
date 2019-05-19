public enum PlayerColor
{
    white, black, COLORLESS;

    public static int howManyColors()
    {
        // Excludes colorless
        return 2;
    }
}
