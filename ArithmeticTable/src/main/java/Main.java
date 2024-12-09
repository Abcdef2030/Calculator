import static java.lang.System.out;


public class Main
{
    public static void main ( String [ ] args )
    {
        ArithmeticTable table = new ArithmeticTable ( );

        out.println ( table );
        out.println ( table.getAdditionalTable ( ) );
        out.println ( table.getSubtractiveTable ( ) );
        out.println ( table.getMultiplicationTable ( ) );
    }
}
