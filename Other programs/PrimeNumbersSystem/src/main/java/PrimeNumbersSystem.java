import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.in;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.Collectors;


class PrimeNumbersSystem extends SystemNumeration  // Идея об этом классе возникает при необходимости получения некого массива простых множителей данного НЕ простого числа. Тогда становится необходимо составить все НЕ простые числа путем выполнения операции произведения разных простых чисел в разных количествах. Однако эти «разных» простых чисел могут составить определенную систему простых чисел, и по этой системе можно выполнить множество иных операций ( по мимо вышеупомянутого произведения ). Вообще любая система составляется на некой основе, в свою очередь имеющее некое начало. Тут при построении системы простых чисел основой являются сами простые число, а то «некое начало» есть число ‘два’.  Система строится следующим образом: задается количество простых чисел, а так же задается система счисления, по которой вычисляются простые числа в заданном количестве.
{
    private ArithmeticTable system_numeration = null;
    private int index = 0;  // Это индексное значение относится к очередному добавляемому простого числа в систему простых чисел

    PrimeNumbersSystem ( Integer prime_numeration, Integer numeration ) throws IllegalArgumentException, NullPointerException  { this ( prime_numeration, new ArithmeticTable ( numeration, 0 ) ); }

    PrimeNumbersSystem ( Integer prime_numeration ) throws IllegalArgumentException, NullPointerException  { this ( prime_numeration, 10 ); }

    PrimeNumbersSystem ( Integer prime_numeration, ArithmeticTable system_numeration ) throws IllegalArgumentException, IllegalStateException, NullPointerException
    {
        try { if ( prime_numeration < 2 )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'PrimeNumbersSystem ( " + Colors.YELLOW + prime_numeration + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is I L L E G A L - It must be more then " + Colors.BLUE + "'" + 0 + "' " + Colors.NOCOLOR ); }  catch ( NullPointerException ex )   { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'PrimeNumbersSystem ( " + Colors.YELLOW + "'Integer', " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        this.system_numeration = system_numeration;
        this.index = 1;
        this.numeration_array = new String [ prime_numeration ];
        try { this.numeration_array [ 0 ] = this.system_numeration.TWO; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'PrimeNumbersSystem ( " + Colors.YELLOW + prime_numeration + ", " + "'ArithmeticTable'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        try { this.makePrimeStream ( ).forEach ( value -> this.numeration_array [ -- this.index ] = value ); }  catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the constructor " + Colors.WHITE + "'PrimeNumbersSystem ( " + Colors.YELLOW + prime_numeration + ", " + system_numeration + Colors.WHITE + " )'  " + Colors.NOCOLOR ); }
    }

    private Stream<String> makePrimeStream ( )  // Выводится бесконечный поток простых чисел, начинающийся с числа ‘два’
    {
        Operator.endlessStream ( this.system_numeration.THREE, this.system_numeration.ONE, this.system_numeration ).takeWhile ( value -> { if ( this.isPrime ( value ) )  { this.numeration_array [ this.index ] = value;  ++ this.index; }  return this.index < this.numeration_array.length; } ).forEach ( value -> { } );
        Stream.Builder<String> built = Stream.builder ( );
        int index = 0;
        try { while ( true )  built.add ( this.numeration_array [ index ++ ] ); }  catch ( ArrayIndexOutOfBoundsException ex ) { }
        return built.build ( );
    }

    private boolean isPrime ( String number )
    {
        String half = Operation.divide.arithmeticOperation ( number, this.system_numeration.TWO, this.system_numeration, false ).split ( "\\W" ) [ 0 ];
        int index = 0;
        while ( this.compareNumbers ( this.numeration_array [ index ], half ) <= 0 )  if ( this.isZero ( Operation.rest.arithmeticOperation ( number, this.numeration_array [ index ++ ], this.system_numeration, false ) ) )  return false;
        return true;
    }

    private boolean isPrimeTheory ( String number )  // По действию этой функции вычисляется – является ли данное число простым по имеющийся теории
    {
//		if ( system_numeration.compareNumbers ( number, system_numeration.TWO ) == 0 )  return true;  // Это выражение исполняется отдельно, по скольку алгоритм по данной теории ‘двойку’ не считает простым числом
        String pow_result = Operation.pow.arithmeticOperation ( system_numeration.TWO, Operation.subtract.arithmeticOperation ( number, this.system_numeration.ONE, this.system_numeration, false ), this.system_numeration, false );
        String rest_result = Operation.rest.arithmeticOperation ( pow_result, number, this.system_numeration, false );
        return system_numeration.compareNumbers ( rest_result, system_numeration.ONE ) == 0;
    }

    ArithmeticTable getSystemNumeration ( )  { return this.system_numeration; }

    PrimeNumbersSystem abbreviateSystem ( int count, boolean left_rigth )  // Действием этой функции количество простых чисел, с которых состоит данная система простых чисел, сокращается на указанное первым параметром количестве. Сокращение выполняется с лево или с право, определяется вторым параметром
    {
        if ( this.numeration_array.length - count < 1 )  return null;
        String [ ] abbredied_array = new String [ this.numeration_array.length - count ];
        if ( left_rigth )  for ( int index = 0;  index < this.numeration_array.length - count;  ++ index )  abbredied_array [ index ] = this.numeration_array [ index ];
        else  for ( int index = this.numeration_array.length - 1;  index >= count;  -- index )  abbredied_array [ index - count ] = this.numeration_array [ index ];
        this.numeration_array = abbredied_array;
        return this;
    }

    @Override
    public String toString ( )
    {
        StringBuilder built = new StringBuilder ( Colors.AZURE + "Here the prime system numeration from count of " + Colors.BLUE + "'" + this.numeration_array.length + "' " + Colors.AZURE + "is ___" );
        for ( int index = 0;  index < this.numeration_array.length;  ++ index )
        {
            built.append ( Colors.colors_array [ ( index % Colors.colors_array.length ) ] );
            built.append ( this.numeration_array [ index ] + " " );
        }
        built.append ( Colors.NOCOLOR );

        return built.toString ( );
    }
}
