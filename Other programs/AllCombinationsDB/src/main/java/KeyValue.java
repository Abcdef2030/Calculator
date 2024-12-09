import static java.lang.System.out;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;


public abstract class KeyValue implements Comparable<KeyValue>
{
    private String value = null;
    Logger logger = Logger.getAnonymousLogger ( );
    private static Set<String> set = new HashSet ( );
    KeyValue ( String value )
    {
        this.value = value;
        if ( ! set.add ( this.value ) )  logger.warning ( "The object with value like '" + value + "' is already created" );
    }
    String getValue ( )  { return this.value; }
    @Override
    public String toString ( )  { return this.value; }
    @Override
    public boolean equals ( Object obj )
    {
        if ( obj == null )  return false;
        if ( ! this.getClass ( ).getName ( ).equals ( obj.getClass ( ).getName ( ) ) )  return false;
        KeyValue object = (KeyValue)obj;
        return this.value.equals ( object.getValue ( ) );
    }
    @Override
    public int hashCode ( )  { return Objects.hash ( this.value ); }
    @Override
    public int compareTo ( KeyValue obj )  { return this.value.compareTo ( obj.getValue ( ) ); }
    static Set<String> getSet ( )  { return set; }
}


class A extends KeyValue
{
    A ( String value )  { super ( value ); }
}

class B extends KeyValue
{
    B ( String value )  { super ( value ); }
}

class C extends KeyValue
{
    C ( String value )  { super ( value ); }
}