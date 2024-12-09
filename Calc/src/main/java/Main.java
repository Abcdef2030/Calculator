import static java.lang.System.*;


public class Main
{
    static ArithmeticTable system = new ArithmeticTable ( );
    public static void main ( String [ ] args ) throws CloneNotSupportedException
    {
//        try { Device.getDevice ( system ).runCalculator ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "\nE X C E P T I O N -- When try to call the constructor " + Colors.WHITE + "'Device ( ... )'" + Colors.RED + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
//        try { FileDevice.getFileDevice ( system ).runCalculator ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "\nE X C E P T I O N -- When try to call the constructor " + Colors.WHITE + "'Device ( ... )'" + Colors.RED + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
        try
        {
            Device device = Device.getDevice ( );
            out.println ( device.runCalculator ( "1/11" ) );
        }
        catch ( NullPointerException ex )  { out.println ( ex.getMessage ( ) ); }
        catch ( IllegalArgumentException exc )  { out.println ( exc.getMessage ( ) ); }
     }
}


// °  ¯
//  ( ( 1 + 2№5 ) ^ 5 - ( 1 - 2№5 ) ^ 5 ) / ( 2^5 * 2№5 )
//  ( ( 1 + 2№5 ) ^ 9 - ( 1 - 2№5 ) ^ 9 ) / ( 2^9 * 2№5 )
