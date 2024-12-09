import static java.lang.System.out;
import java.io.IOException;


public class Main
{
    public static void main ( String [ ] args ) throws IOException
    {
        History history = new HistoryExcel ( );
        Object [ ] obj = { "abc", "def" };
        history.writeHistoryToFile ( obj );
    }
}
