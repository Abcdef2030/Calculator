import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.in;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


class MultiplyTable  // Идея об этом классе возникает при необходимости получения некого массива простых множителей данного НЕ простого числа. Тогда возникает обобщенная идея – составить таблицу простых множителей множество НЕ простых чисел путем выполнения множество операций произведения множество простых чисел. По этому классу, для составлении таблицы простых множителей множество чисел, задается система простых чисел, а так же задается значение, относящийся к количеству простых чисел, участвующие в одной операции произведения простых чисел, с целью получения одного НЕ простого числа.
{
    private PrimeNumbersSystem system = null;
    private HashMap<String, Object [ ]> table = new HashMap ( );  // Тут ключевое значение относится к НЕ простому числу, а обычное значение есть массивное - массив простых множителей, и задается по классу 'Object', по скольку функция 'toArray ( )' описания класса 'Stream' возвращает значение по классу же 'Object'
    private WheelInterface counter = null;  // Этот счетчик создается по системе простых чисел, то есть показания его колес являются простыми числами по данной системе простых чисел, с елью с помощью этого счетчика вычислить все возможные комбинации простых чисел в данной системе счисления простых чисел, в указанном количестве, для составления выражения их произведения, в свою очередь – с целью вычисления НЕ простого числа, простыми множителями которой будут являться показания колес данного счетчика. Таким образом -  вычисляются комбинации простых чисел, составляется выражение их произведения, результат произведения в месте с этими «показаниями» колес счетчика вводятся в карту ‘table’.
    private Integer wheel_count = 0;
    private String result = null;

    MultiplyTable ( Integer prime_numeration, Integer wheel_count ) throws IllegalArgumentException, NullPointerException  { this ( new PrimeNumbersSystem ( prime_numeration, new ArithmeticTable ( 10, 0 ) ), wheel_count ); }

    MultiplyTable ( Integer prime_numeration, Integer numeration, Integer wheel_count ) throws IllegalArgumentException, NullPointerException  { this ( new PrimeNumbersSystem ( prime_numeration, new ArithmeticTable ( numeration, 0 ) ), wheel_count ); }

    MultiplyTable ( Integer prime_numeration, ArithmeticTable system_numeration, Integer wheel_count )  { this ( new PrimeNumbersSystem ( prime_numeration, system_numeration ), wheel_count ); }

    MultiplyTable ( )  { this ( 10, 10 ); }

    MultiplyTable ( PrimeNumbersSystem system, Integer wheel_count ) throws IllegalArgumentException, NullPointerException
    {
        if ( system == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'MultiplyTable ( " + Colors.YELLOW + "'PrimeNumbersSystem', " + wheel_count + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
        this.system = system;
        try { if ( wheel_count < 1 )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the constructor " + Colors.WHITE + "'MultiplyTable ( " + Colors.YELLOW + "'PrimeNumbersSystem', " + wheel_count + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L.  It must be not less then " + Colors.BLUE + "'1'" + Colors.NOCOLOR ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'MultiplyTable ( " + Colors.YELLOW + "'PrimeNumbersSystem', " + "'Integer'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        this.wheel_count = wheel_count;
        this.counter = null;
        this.setTable ( );
    }

    private void setTable ( ) throws NullPointerException, IllegalStateException
    {
        CounterSingleWheel counter = new CounterSingleWheel ( this.system );
        while ( this.wheel_count > 0 )
        {
            try
            {
                counter.getStreamUniqueGroups ( this.wheel_count ).forEach ( value ->
                {
                    Object [ ] array = value.toArray ( );
                    String result = this.computeMultiply ( array );
                    this.table.put ( result, array );
                } );
                this.wheel_count --;
            }
            catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'MultiplyTable::setTable ( )'  " + Colors.NOCOLOR ); }
            catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'MultiplyTable :: setTable ( )'  " + Colors.NOCOLOR ); }
        }
    }

    private String computeMultiply ( Object [ ] array ) throws NullPointerException  // Действием этой функции выполняется вычисление результата произведений множителей, имеющийся в составе массивного элемента, определяющийся по параметру.
    {
        String result = this.system.getSystemNumeration ( ).ONE;  // Это есть начальный множитель
        try { for ( Object number : array )  result = Operation.multiply.arithmeticOperation ( result, (String)number, this.system.getSystemNumeration ( ), false ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'MultiplyTable :: computeMultiply ( " + Colors.YELLOW + "'Object [ ]'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        return result;
    }

    @Override
    public String toString ( ) throws IllegalStateException
    {
        StringBuilder built = new StringBuilder ( Colors.AZURE + "Here is the multipliers table for the count of " + Colors.YELLOW + "'" + this.table.size ( ) + "'" + Colors.AZURE + " numbers __ \n" + Colors.NOCOLOR );
        try
        {
            this.table.keySet ( ).stream ( ).sorted ( ( value_1, value_2 ) -> this.system.getSystemNumeration ( ).compareNumbers ( value_1, value_2 ) ).forEach ( value ->
                    {
                        built.append ( Colors.GREEN );
                        built.append ( value );
                        built.append ( Colors.YELLOW );
                        built.append ( " = " );
                        Object [ ] multiplayers_array = this.table.get ( value );
                        for ( Object str : multiplayers_array )
                        {
                            built.append ( Colors.BLUE );
                            built.append ( str );
                            built.append ( Colors.YELLOW );
                            built.append ( " * " );
                        }
                        built.append ( Colors.BLUE );
                        built.append ( this.system.getSystemNumeration ( ).ONE );
                        built.append ( "\n" );
                    }
            );
        }
        catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'MultiplyTable :: toString ( )'  " + Colors.NOCOLOR ); }

        built.append ( "\n".concat ( Colors.AZURE.concat ( "The end ! ____________________" + Colors.NOCOLOR ) ) );
        out.println ( this.system.toString ( ) );

        return built.toString ( );
    }
}
