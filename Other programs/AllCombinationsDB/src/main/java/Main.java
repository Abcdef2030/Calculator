import static java.lang.System.out;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;


public class Main
{
    static Set<KeyValue> fillSet ( )
    {
        Set<KeyValue> set = new TreeSet ( );
        set.add ( new A ( "0" ) );
        set.add ( new A ( "1" ) );
        set.add ( new B ( "2" ) );
        set.add ( new C ( "3" ) );
        set.add ( new C ( "4" ) );

        set.add ( new A ( "0" ) );

        return set;
    }
    public static void main ( String [ ] args )
    {
        fillSet ( );
        try
        {
            AllCombinations obj = new AllCombinations ( 3, "key_value" );
            obj.createCombinationsTable ( KeyValue.getSet ( ) );
        }
        catch ( IllegalArgumentException ex )  { out.println ( ex.getMessage ( ) ); }
    }
}
