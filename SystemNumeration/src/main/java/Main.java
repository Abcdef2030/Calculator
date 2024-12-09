import static java.lang.System.out;
import static java.lang.System.setOut;


public class Main
{
    public static void main ( String [ ] args )
    {
        SystemNumeration system = new SystemNumeration ( 12, 12 );

        out.println ( system );

        String expression = system.checkExpression ( "12A56 ; 85" );
        out.println ( expression );

        out.println ( system.checkNumberFirstZeros ( "0000123" ) );
    }
}
