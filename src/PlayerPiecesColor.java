public enum PlayerPiecesColor
{
    white, black, COLORLESS;

    public static int howManyColors()
    {
        // Excludes colorless
        return 2;
    }
}
