import java.util.Scanner;

public class InputStdin implements Input
{
    private static InputStdin ourInstance = new InputStdin();

    public static InputStdin getInstance()
    {
        return ourInstance;
    }

    @Override
    public String readName()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private InputStdin()
    {
    }
}