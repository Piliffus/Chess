import java.util.*;

public class InputStdin implements Input
{
    public static InputStdin getInstance()
    {
        return ourInstance;
    }
    private static InputStdin ourInstance = new InputStdin();
    private Random random = new Random();
    private List<String> namesList = prepareRandomNames();

    private ArrayList<String> prepareRandomNames()
    {
        // "The Hitchhiker's Guide to the Galaxy", "The Astronauts", "War Games", "I, robot", "Terminator",
        // "Terminator" again, "2001: A space odyssey", "The Nine Billion Names of God", "Mona Lisa Overdrive"

        String[] namesArray = {"Deep Thought", "MARAX", "WOPR", "VIKI", "Skynet", "T-1000", "HAL 9000", "Mark V", "Continuity"};
        ArrayList<String> returnedNamesList = new ArrayList<>(namesArray.length);

        returnedNamesList.addAll(Arrays.asList(namesArray));

        return returnedNamesList;
    }

    @Override
    public String readName()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void userConfirmation()
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    @Override
    public String randomName()
    {
        if (namesList.isEmpty())
        {
            namesList = prepareRandomNames();
        }

        String string = namesList.get(random.nextInt(namesList.size()));
        namesList.remove(string);

        return string;
    }

    private InputStdin()
    {
    }
}
