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


public class SystemNumeration implements Comparable<SystemNumeration>, Cloneable  // После создания системы счисления изменения в ней внести будет НЕ возможно
{
    static Scanner input = new Scanner ( in );
    public static String LEFT_BRACKET = "├";
    public static String RIGHT_BRACKET = "┤";
    public static String POINT = ",";
    public static String DEGREE = "°";  // Этот знак обозначает умножение на десять в степени, описывающийся числом, следующий за этим знаком. В дальнейшем для возможности опознания подобных арифметических операций, к подобным подвыражением после того «следующего» числа будет добавлен еще один такой же знак
    public static String DEGREE_NEGATIVE = "¯";  // Этот знак обозначает умножение на десять в отрицательной  степени, описывающийся числом, следующий за этим знаком. В дальнейшем для возможности опознания подобных арифметических операций, к подобным подвыражением после того «следующего» числа будет добавлен еще один такой же знак
    public static String FACTORIAL = "!";
    public static String ROOT = "√";
    public static String REST = "◊";
    public static String PERSENT = "%";
    public static String POWER = "º";
    public static String MULTIPLY = "×";
    public static String DIVIDE = "÷";
    public static String MINUS = "□";
    public static String PLUS = "ⱷ";
    public static String LESS = "<";
    public static String LESSOREVEN = "<=";
    public static String MORE = ">";
    public static String MOREOREVEN = ">=";
    public static String EVEN = "=";
    public static String NOTEVEN = "<>";
    private Matcher matcher = null;
    protected String NUMBER;  // Этот элемент из себя представляет регулярное выражение, соответствующее цифровым символам данного системе счислению в произвольном количестве
    protected String ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN;
    protected String regex_for_adding_zero = null, regex_for_adding_degree = null, regex_for_adding_degree_negative = null, regex_for_checking_expression = null, regex_degree_zero = null, add_degree = null, add_degree_negative = null;

    protected static final String standart_numeration = "0123456789ABCDEFGHIJKLMNOPQRSTUVWYZ";  // Это стандартные символы систем счисления, в текстовом представлении
    protected String [ ] numeration_array = null;  // Это массив цифровых символов данной системы счисления
    private static List<String> arithmetic_operators = null;  // Стандартные знаки арифметических операторов. Тут знак умножения обозначен как ' ×', то есть знак ‘звездочка’ исключен.  Этот элемент в определении этого класса в месте с элементом 'more_for_check' понадобится для выполнения предварительной проверки введенного арифметического выражения на предмет отсутствия лишних символов. А в определении класса 'ArithmeticBoard' без элемента 'more_for_check' понадобится для составления регулярных выражений для нахождения под-выражений арифметических операций в составе выражения имеющегося арифметического выражения ( это при определения конструктора класса ‘ArithmeticBoard’  ) – по скольку среди имеющихся знаков арифметических операций встречаются двух-символьные, необходимо что бы в текстовом представлении эти арифметические знаки содержались как отдельные элементы одной коллекции, и это коллекция есть ‘arithmetic_operators’
    public static final String more_for_check = SystemNumeration.LEFT_BRACKET.concat ( SystemNumeration.RIGHT_BRACKET ).concat ( SystemNumeration.DEGREE ).concat ( SystemNumeration.DEGREE_NEGATIVE );
    protected Integer ciphers_count = 10;  // Этот элемент носит количество цифр после запятой в составе дробных чисел
    Map<String, Integer> symbol_map = new HashMap ( );  // Это карта символов и их индексов необходим для нахождения соответствующего индекса без заведения цикла над массивным элементом 'numeration_array'
    Map<Integer, String> map_symbol = new HashMap ( );  // Это карта индексов и их символов необходим для внесения указания колеса по индексу без вызова функции вывода массивного элемента данной системы счисления
    public static SystemNumeration standart_system = new SystemNumeration ( 10, 5 );

    //--- C O N S T R U C T O R S ---
    SystemNumeration ( )  { this ( 10, 5 ); }

    SystemNumeration ( Integer numeration ) throws IllegalArgumentException, NullPointerException  { this ( numeration, 5 ); }

    SystemNumeration ( Integer numeration, Integer ciphers_count ) throws IllegalArgumentException, NullPointerException  // Будет выполнятся попытка строения системы счисления в блоке ‘try’, и в случае неудачи этот блок будет обойден – то есть при отсутствии системы счисления ни по какому ни одно действие не будет выполнено
    {
        try { if ( numeration < 2  ||  numeration > standart_numeration.length ( ) )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the constructor " + Colors.WHITE + "'SystemNumeration ( " + Colors.YELLOW + numeration + Colors.WHITE + " )'" + Colors.RED + " is I L L E G A L - It must be " + ( numeration < 2 ? "more then " + Colors.BLUE + "'" + 1 + "' " + Colors.RED : "no more then " + Colors.BLUE + "'" + standart_numeration.length ( ) + "'  " + Colors.RED ) + Colors.NOCOLOR ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'SystemNumeration ( " + Colors.YELLOW + "'Integer', " + ciphers_count + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        this.numeration_array = new String [ numeration ];
        int index = 0;
        for ( ;  index < numeration;  ++ index )
        {
            this.numeration_array [ index ] = String.valueOf ( standart_numeration.charAt ( index ) );
            this.symbol_map.put ( numeration_array [ index ], index );
            this.map_symbol.put ( index, numeration_array [ index ] );
        }

        SystemNumeration.arithmetic_operators = new ArrayList ( );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.POINT );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.LESS );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.LESSOREVEN );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.MORE );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.MOREOREVEN );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.EVEN );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.NOTEVEN );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.FACTORIAL );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.ROOT );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.REST );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.PERSENT );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.POWER );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.MULTIPLY );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.DIVIDE );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.MINUS );  SystemNumeration.arithmetic_operators.add ( SystemNumeration.PLUS );

        this.setNUMBER ( );
        this.setAllNUMBERS ( );
        this.setRegexes ( );
        try { if ( ciphers_count < 0 )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'SystemNumeration ( " + Colors.YELLOW + numeration + ", " + ciphers_count + Colors.WHITE + " )'" + Colors.RED + " is  N E G A T I V E  " + Colors.NOCOLOR ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The second argument of the Constructor " + Colors.WHITE + "'SystemNumeration ( " + Colors.YELLOW + numeration + ", " + "'Integer'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        this.ciphers_count = ciphers_count;
    }

    //--- S E T T E R S ---
    void setCiphersCount ( Integer ciphers_count )  { this.ciphers_count = ciphers_count; }

    private void setNUMBER ( )
    {
        StringBuilder built = new StringBuilder ( SystemNumeration.MINUS + "?[" );
        for ( String str : this.numeration_array )  built.append ( str );
        built.append ( "]+" );
        this.NUMBER = built.toString ( );
    }

    private void setAllNUMBERS ( )
    {
        this.ZERO = this.numeration_array [ 0 ];  this.ONE = this.numeration_array [ 1 ];  this.TEN = this.ONE.concat ( this.ZERO );
        try { this.TWO = this.numeration_array [ 2 ];  this.THREE = this.numeration_array [ 3 ];  this.FOUR = this.numeration_array [ 4 ];  this.FIVE = this.numeration_array [ 5 ];  this.SIX = this.numeration_array [ 6 ];  this.SEVEN = this.numeration_array [ 7 ];  this.EIGHT = this.numeration_array [ 8 ];  this.NINE = this.numeration_array [ 9 ]; }  catch ( ArrayIndexOutOfBoundsException ex )
        {
            int length = this.numeration_array.length;
            switch ( length )
            {
                case 9 : { NINE = TEN;  break; }
                case 8 : { EIGHT = TEN;  NINE = ONE.concat ( ONE );  break; }
                case 7 : { SEVEN = TEN;  EIGHT = ONE.concat ( ONE );  NINE = ONE.concat ( TWO );  break; }
                case 6 : { SIX = TEN;  SEVEN = ONE.concat ( ONE );  EIGHT = ONE.concat ( TWO );  NINE = ONE.concat ( THREE );  break; }
                case 5 : { FIVE = TEN;  SIX = ONE.concat ( ONE );  SEVEN = ONE.concat ( TWO );  EIGHT = ONE.concat ( THREE );  NINE = ONE.concat ( FOUR );  break; }
                case 4 : { FOUR = TEN;  FIVE = ONE.concat ( ONE );  SIX = ONE.concat ( TWO );  SEVEN = ONE.concat ( THREE );  EIGHT = TWO.concat ( ZERO );  NINE = TWO.concat ( ONE );  break; }
                case 3 : { THREE = TEN;  FOUR = ONE.concat ( ONE );  FIVE = ONE.concat ( TWO );  SIX = TWO.concat ( ZERO );  SEVEN = TWO.concat ( ONE );  EIGHT = TWO.concat ( TWO );  NINE = ONE.concat ( ZERO.concat ( ZERO ) );  break; }
                case 2 : { TWO = TEN;  THREE = ONE.concat ( ONE );  FOUR = ONE.concat ( ZERO.concat ( ZERO ) );  FIVE = ONE.concat ( ZERO.concat ( ONE ) );  SIX = ONE.concat ( ONE.concat ( ZERO ) );  SEVEN = ONE.concat ( ONE.concat ( ONE ) );  EIGHT = ONE.concat ( ZERO.concat ( ZERO.concat ( ZERO ) ) );  NINE = ONE.concat ( ZERO.concat ( ZERO.concat ( ONE ) ) );  break; }
            }
        }
    }

    private void setRegexes ( )
    {
        this.regex_for_adding_zero = "\\W".concat ( SystemNumeration.POINT.concat ( this.NUMBER ) );  // Этот регулярное выражение Не подходит для случае, когда данное арифметическое выражение начинается или заканчивается дробным символом, и по этому после выполнения поиска чисел, заканчивающийся дробным знаком составе данного арифметического выражения, так же будет проверятся на тот же предмет конечное составное число этого арифметического выражения

        StringBuilder built = new StringBuilder ( "[^" );
        for ( String str : this.numeration_array )  built.append ( str );
        built.append ( this.arithmetic_operators.toString ( ).replaceAll ( "[\\[\\] ]", "" ) );
        built.append ( this.more_for_check );
        built.append ( "]" );
        this.regex_for_checking_expression = built.toString ( );

        this.regex_for_adding_degree = NUMBER.concat ( DEGREE.concat ( NUMBER ) );  this.regex_for_adding_degree_negative = NUMBER.concat ( DEGREE_NEGATIVE.concat ( NUMBER ) );

        this.regex_degree_zero = this.NUMBER.concat ( "[" + this.ZERO + "]+" ).concat ( "(" + DEGREE + "|" + DEGREE_NEGATIVE + ")".concat ( this.NUMBER ) );  // Этот элемент носит регулярное выражение для нахождения в внесенной арифметической выражении числа, в составе которых имеются конечные нулевые символы, а так же эти числа имеют степень десяточной части. Этот элемент понадобится для предварительного обнаружения подобных чисел с целью их опртимизации

        String before_after_degree = "[" + RIGHT_BRACKET.concat ( FACTORIAL.concat ( ROOT.concat ( REST.concat ( PERSENT.concat ( POWER.concat ( MULTIPLY.concat ( DIVIDE.concat ( MINUS.concat ( PLUS.concat ( LESS.concat ( MORE.concat ( EVEN.concat ( NOTEVEN ) ) ) ) ) ) ) ) ) ) ) ) ) + "]";
        this.add_degree = before_after_degree.concat ( this.NUMBER.concat ( DEGREE.concat ( this.NUMBER.concat ( before_after_degree ) ) ) );
        this.add_degree_negative = before_after_degree.concat ( this.NUMBER.concat ( DEGREE_NEGATIVE.concat ( this.NUMBER.concat ( before_after_degree ) ) ) );
    }

    //--- G E T T E R S ---
    String [ ] getNumerationArray ( ) { return this.numeration_array; }

    Integer getNumeration ( ) { return this.numeration_array.length; }

    String getSymbol ( int index ) throws ArrayIndexOutOfBoundsException   { try { return this.numeration_array [ index ]; }  catch ( ArrayIndexOutOfBoundsException ex )  { throw new ArrayIndexOutOfBoundsException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: getSymbol ( " + Colors.YELLOW + index + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  It must be " + ( ( index < 2 ? "more then one" : "less the " + this.getNumeration ( ) ) ) + Colors.NOCOLOR ); } }

    static int getStandartIndex ( char symbol ) throws IllegalArgumentException
    {
        for ( int index = 0;  index < standart_numeration.length ( );  ++ index )  if ( standart_numeration.charAt ( index ) == symbol )  return index;
        throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: getStandartIndex ( " + Colors.YELLOW + symbol + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );
    }

    static String getNumberText ( String number, boolean upper_case ) throws NumberFormatException, IllegalArgumentException  // Тут булево параметр относится к регистру выведенного символьного значения
    {
        Integer int_number = null;
        try { int_number = Integer.parseInt ( number ); }  catch ( NumberFormatException ex )  { throw new NumberFormatException ( Colors.RED + "E X C E P T I O N -- The function " + Colors.WHITE + " 'SystemNumeration :: getNumberText ( String " + Colors.YELLOW + number + ", " + upper_case + Colors.WHITE + " )' " + Colors.RED + " may be called only for numbers with DESIMAL symbols  " + Colors.NOCOLOR ); }

        return SystemNumeration.getNumberText ( int_number, upper_case );
    }

    static String getNumberText ( int number, boolean upper_case ) throws IllegalArgumentException  // Тут булево параметр относится к регистру выведенного символьного значения
    {
        String text = ( number < 0 ? ( ! upper_case ? "minus " : "MINUS "  ) : "" );
        switch ( Math.abs ( number ) )
        {
            case 0 : { text = text.concat ( ( ! upper_case ? "zero" : "ZERO" ) );  break; }
            case 1 : { text = text.concat ( ( ! upper_case ? "one" : "ONE" ) );  break; }
            case 2 : { text = text.concat ( ( ! upper_case ? "two" : "TWO" ) );  break; }
            case 3 : { text = text.concat ( ( ! upper_case ? "three" : "THREE" ) );  break; }
            case 4 : { text = text.concat ( ( ! upper_case ? "four" : "FOUR" ) );  break; }
            case 5 : { text = text.concat ( ( ! upper_case ? "five" : "FIVE" ) );  break; }
            case 6 : { text = text.concat ( ( ! upper_case ? "six" : "SIX" ) );  break; }
            case 7 : { text = text.concat ( ( ! upper_case ? "seven" : "SEVEN" ) );  break; }
            case 8 : { text = text.concat ( ( ! upper_case ? "eight" : "EIGHT" ) );  break; }
            case 9 : { text = text.concat ( ( ! upper_case ? "nine" : "NINE" ) );  break; }
            case 10 : { text = text.concat ( ( ! upper_case ? "ten" : "TEN" ) );  break; }
            case 11 : { text = text.concat ( ( ! upper_case ? "eleven" : "ELEVEN" ) );  break; }
            case 12 : { text = text.concat ( ( ! upper_case ? "twelve" : "TWELVE" ) );  break; }
            case 13 : { text = text.concat ( ( ! upper_case ? "thirteen" : "THIRTEEN" ) );  break; }
            case 14 : { text = text.concat ( ( ! upper_case ? "fourteen" : "FOURTEEN" ) );  break; }
            case 15 : { text = text.concat ( ( ! upper_case ? "fiveteen" : "FIVETEEN" ) );  break; }
            case 16 : { text = text.concat ( ( ! upper_case ? "sixteen" : "SIXTEEN" ) );  break; }
            case 17 : { text = text.concat ( ( ! upper_case ? "seventeen" : "SEVENTEEN" ) );  break; }
            case 18 : { text = text.concat ( ( ! upper_case ? "eighteen" : "EIGHTTEEN" ) );  break; }
            case 19 : { text = text.concat ( ( ! upper_case ? "nineteen" : "NINETEEN" ) );  break; }
            case 20 : { text = text.concat ( ( ! upper_case ? "twenty" : "TWENTY" ) );  break; }
            default : throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: getNumberText ( " + Colors.YELLOW + number + ", " + upper_case + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );
        }
        return text;
    }

    String getMaxMin ( String number_1, String number_2, boolean maxmin  ) throws NullPointerException  // Возвращает наибольший или наименьший аргумент из первых двух, зависимо от третьего
    {
        Integer compared = null;
        try
        {
            compared = this.compareNumbers ( number_1, number_2 );
            if ( compared >= 0 )  return ( maxmin ? number_1 : number_2 );
            else if ( compared < 0 )  return ( maxmin ? number_2 : number_1 );
        }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: getMaxMin ( " + Colors.YELLOW + number_1 + ", " + number_2 + ", " + maxmin + Colors.WHITE + " )'" + Colors.RED + " has an  N U L L  symbol  " + Colors.NOCOLOR ); }
        return number_1;
    }

    Integer getCiphersCount ( )  { return this.ciphers_count; }

    static String getArithmeticOperationsString ( )  // Это функция выводит все знаки всех арифметических операций, содержащийся в коллекции 'arithmetic_operators' и в текстовом элементе 'more_for_check' как одно текстовое выражение для выполнения проверки имеющийся арифметического выражения, то есть при исполнении функции 'checkExpression ( ... )' описания этого класса
    {
        StringBuilder built = new StringBuilder ( );
        Iterator<String> iter = SystemNumeration.arithmetic_operators.iterator ( );
        iter.forEachRemaining ( ( value ) -> { built.append ( value ); } );
        return built.toString ( ).concat ( more_for_check );
    }

    static List<String> getArithmeticOperations ( )  { return SystemNumeration.arithmetic_operators; }

    //--- B O O L E A N S   A N D   C O M P A R E ---
    static boolean isNegative ( String number ) throws NullPointerException  { try { return number.charAt ( 0 ) == MINUS.charAt ( 0 ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: isNegative ( " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); } }  // Возвращаемое значение относится к тому, что данное текстовое значение из себя представляет отрицательное число

    boolean isZero ( String number ) throws NullPointerException  // Рассматриваются три варианта: данное число есть целое, данное число имеет десяточный степень, данное число дробное
    {
        try
        {
            number = number.replaceFirst ( SystemNumeration.MINUS, "" );  // Сперва данное число освобождается о знака минуса, в случае он имеется
            number = number.replaceFirst ( SystemNumeration.POINT, "" );  // За тем оно освобождается с дробного знака, если он имеется
            number = number.split ( "\\W" ) [ 0 ];  // За тем данное число подменивается со своей целой частью
            int index = number.length ( );
            while ( ( index -- ) > 0 )  if ( number.charAt ( index ) != this.ZERO.charAt ( 0 ) )  return false;  return true;
        }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: isZero ( " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    static boolean resultSignIsNegative ( String number_1, String number_2 ) throws NullPointerException  { try { return ( isNegative ( number_1 )  ^  isNegative ( number_2 ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: resultSignIsNegative ( " + Colors.YELLOW + number_1 + ", " + number_2 + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); } }  // Возвращаемое значение относится к отрицательному знаку результата операции умножения или деления с участием двух чисел, определяющийся первым и вторым параметрами

    Integer compareNumbers ( String number_1, String number_2 ) throws NullPointerException  // По действию этой функции вычисляется результат сравнения обычных чисел ( без дробных составных символов и без десяточных частей со степенями )
    {
        try
        {
            // С начало необходимо выравнивать количество составных цифр обеих чисел путем добавления начальных символов с нулевыми обозначениями
            while ( number_1.length ( ) < number_2.length ( ) )  number_1 = this.ZERO.concat ( number_1 );  while ( number_2.length ( ) < number_1.length ( ) )  number_2 = this.ZERO.concat ( number_2 );
            int index = 0;
            while ( true )
            {
                Integer index_1 = this.symbol_map.get ( number_1.substring ( index, index + 1 ) ),  index_2 = this.symbol_map.get ( number_2.substring ( index, ++ index ) );
                if ( ! index_1.equals ( index_2 )  &&  ! index_2.equals ( index_1 ) )  return ( index_1 - index_2 );
            }
        }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: compareNumbers ( " + Colors.YELLOW + number_1 + ", " + number_2 + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        catch ( StringIndexOutOfBoundsException ex )  { return 0; }
    }

    //--- C H E C K E R S ---
    boolean checkSymbol ( char simbol )
    {
        for ( String ch : this.numeration_array )  if ( ch.charAt ( 0 ) == simbol )  return true;
        return false;
    }

    boolean checkSymbol ( String simbol )
    {
        for ( String ch : this.numeration_array )  if ( ch.equals ( simbol ) )  return true;
        return false;
    }

    boolean checkNumber ( String number ) throws NullPointerException { try { return ( number.replaceAll ( "[^-" + this.NUMBER + "]", "" ).equals ( number ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: checkNumber ( " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); } }  // Составляется новое текстовое значение, представляющее из себя некое число, путем попытки удаления с данного текстового значения все те символы, которые не имеют отношение к цифрам данной системы счисления, и если в результате эти два текстовые значения не эквивалентны, это означает что в составе данного текстового значения все таки имеются ошибочные символы

    String checkNumberFirstZeros ( String number ) throws NullPointerException  // Выполняется проверка над данным числом на предмет начальных излишних нулевых цифр с целью их удаления
    {
        try
        {
            StringBuilder built = new StringBuilder ( number );
            int start_index = 0;
            if ( number.charAt ( start_index ) == SystemNumeration.MINUS.charAt ( 0 ) )  start_index ++;
            if ( number.indexOf ( this.POINT ) != -1 )
            {
                while ( built.charAt ( start_index ) == this.ZERO.charAt ( 0 )  &&   built.charAt ( start_index + 1 ) != this.POINT.charAt ( 0 ) )  built.delete ( start_index, start_index + 1 );
                while ( built.charAt ( built.length ( ) - 1 ) == this.ZERO.charAt ( 0 ) )  built.delete ( built.length ( ) - 1, built.length ( ) );
                if ( built.charAt ( built.length ( ) - 1 ) == SystemNumeration.POINT.charAt ( 0 ) )  built.delete ( built.length ( ) - 1, built.length ( ) );
            }
            else
            {
                while ( built.length ( ) > ( 1 + start_index / 1 )  &&  built.charAt ( start_index ) == this.ZERO.charAt ( 0 ) )  built.delete ( start_index, start_index + 1 );
                if ( SystemNumeration.isNegative ( number )  &&  this.isZero ( built.toString ( ) ) )  built.delete ( 0, 1 );
            }

            return built.toString ( );
        }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: checkNumberFirstZeros ( String " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    StringBuilder checkNumberFirstZeros ( StringBuilder number )
    {
        try
        {
            int start_index = 0;
            if ( number.charAt ( start_index ) == SystemNumeration.MINUS.charAt ( 0 ) )  start_index ++;
            if ( number.indexOf ( this.POINT ) != -1 )
            {
                while ( number.charAt ( start_index ) == this.ZERO.charAt ( 0 )  &&   number.charAt ( start_index + 1 ) != this.POINT.charAt ( 0 ) )  number.delete ( start_index, start_index + 1 );
                while ( number.charAt ( number.length ( ) - 1 ) == this.ZERO.charAt ( 0 ) )  number.delete ( number.length ( ) - 1, number.length ( ) );
                if ( number.charAt ( number.length ( ) - 1 ) == SystemNumeration.POINT.charAt ( 0 ) )  number.delete ( number.length ( ) - 1, number.length ( ) );
            }
            else
            {
                while ( number.length ( ) > ( 1 + start_index / 1 )  &&  number.charAt ( start_index ) == this.ZERO.charAt ( 0 ) )  number.delete ( start_index, start_index + 1 );
                if ( SystemNumeration.isNegative ( number.toString ( ) )  &&  this.isZero ( number.toString ( ) ) )  number.delete ( 0, 1 );
            }

            return number;
        }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: checkNumberFirstZeros ( StringBuilder " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    String checkExpression ( String expression ) throws NullPointerException  // В случае наличии ошибок может быть возвращено пустое текстовое значение, а может при значении 'true' второго параметра предлагаться исправленное арифметическое выражение
    {
        try
        {
            expression = this.replaceWrongSymbol ( expression );

            // Переработка под-выражения, являющийся факториалом чисел. Это процедура в себе вносит следующее – после знака факториала добавляется нулевой цифровой символ, что бы цело под-выражение состояло с двух числовых значений и со знака факториала между ними, по сколку по потоковой «трубе» будет передано три текстовых значений как правило
            expression = expression.replaceAll ( "!", "!0" );
            expression = this.addZeroAfterPoint ( expression );
            expression = this.addFirstLastZero ( expression );
            expression = this.addDegree ( expression );
            expression = this.checkBracketsCount ( expression );

            Pattern pattern = Pattern.compile ( this.regex_for_checking_expression );
            this.matcher = pattern.matcher ( expression );
            while ( matcher.find ( ) )
            {
                String correct_expr = expression.replaceFirst ( this.regex_for_checking_expression, "" );
                int index_for_wrong = matcher.start ( );
                out.println ( Colors.PURPURE + " A T E N T I O N _ There is an incorrect simbol of " + Colors.YELLOW + "'" + expression.charAt ( index_for_wrong ) + "' " + Colors.PURPURE + " in this expression " + Colors.WHITE + expression + Colors.PURPURE + ". \n Do you want do delete it " + Colors.AZURE + "( D / d ) " + Colors.PURPURE + "and input this expression __" + Colors.WHITE + " '" + correct_expr + "' " + Colors.PURPURE + "\n  or do you want to replace it " + Colors.AZURE + "( R / r ) " + Colors.PURPURE );
                String answer = input.nextLine ( );
                switch ( answer.charAt ( 0 ) )
                {
                    case 'D' : case 'd' : { expression = expression.replaceFirst ( this.regex_for_checking_expression, "" );  matcher = pattern.matcher ( expression );  break; }
                    case 'R' : case 'r' :
                {
                    out.print ( "Then input the write symbol __ " );
                    answer = input.nextLine ( );
                    expression = expression.replaceFirst ( this.regex_for_checking_expression, answer );
                    expression = this.replaceWrongSymbol ( expression );
                    matcher = pattern.matcher ( expression );
                    break;
                }
                    default : out.println ( Colors.PURPURE + "	Then it returns 'null' " + Colors.NOCOLOR );   return null;
                }
            }
            out.println ( "\u001B[45m The checked arithmetic expression is __ " + expression + "\u001B[0m" );
            return expression;
        }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: checkExpression ( " + Colors.YELLOW + expression + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
    }

    private String replaceWrongSymbol ( String expression ) throws NullPointerException
    {
        expression = expression.replaceAll ( " ", "" );
        expression = expression.replaceAll ( "\\(", LEFT_BRACKET );
        expression = expression.replaceAll ( "\\)", RIGHT_BRACKET );
        expression = expression.replaceAll ( "\\.", POINT );
        expression = expression.replaceAll ( ",", POINT );
        expression = expression.replaceAll ( "\\@", DEGREE );
        expression = expression.replaceAll ( "\\#", DEGREE_NEGATIVE );
        expression = expression.replaceAll ( DEGREE_NEGATIVE.concat ( MINUS ), DEGREE );
        expression = expression.replaceAll ( DEGREE_NEGATIVE.concat ( "-" ), DEGREE );
        expression = expression.replaceAll ( DEGREE.concat ( MINUS ), DEGREE_NEGATIVE );
        expression = expression.replaceAll ( DEGREE.concat ( "-" ), DEGREE_NEGATIVE );
        expression = expression.replaceAll ( "\\№", ROOT );
        expression = expression.replaceAll ( "\\$", REST );
        expression = expression.replaceAll ( "\\%", PERSENT );
        expression = expression.replaceAll ( "\\^", POWER );
        expression = expression.replaceAll ( "\\*", MULTIPLY );
        expression = expression.replaceAll ( "\\:", DIVIDE );
        expression = expression.replaceAll ( "/", DIVIDE );
        expression = expression.replaceAll ( "\\-", MINUS );
        expression = expression.replaceAll ( "\\+", PLUS );
        expression = expression.replaceAll ( "!=", NOTEVEN );
        expression = expression.replaceAll ( "\\W".concat ( LEFT_BRACKET ).concat ( RIGHT_BRACKET ), "" );
        // Это процедура относится к случае, когда в составе данного арифметического выражения имеется нулевой степень десяточной части, исполнение этой процедуры состоит в удалении всех подобных нулевых степеней с данного состава
        Pattern pattern = Pattern.compile ( "(" + DEGREE +  "|" + DEGREE_NEGATIVE + ")" + "[" + this.ZERO + "]+" + "(" + DEGREE +  "|" + DEGREE_NEGATIVE + ")?" );
        Matcher matcher = pattern.matcher ( expression );
        while ( matcher.find ( ) )
        {
            int index_start = matcher.start ( );  int index_end = matcher.end ( );
            String zero_degree = expression.substring ( index_start, index_end );
            expression = expression.replaceAll ( zero_degree, "" );
            matcher = pattern.matcher ( expression );
        }
        return expression;
    }

    private String addZeroAfterPoint ( String expression ) throws NullPointerException  // Это функция имеется для того, что бы подправить внесенные пользователем арифметические выражение, в смысле - с конца чисел, конечный символ которых есть дробный или с начало чисел, начинающийся с дробного символа,  добавить нулевую цифру
    {
        StringBuilder built = this.addZero ( new StringBuilder ( expression ), this.regex_for_adding_zero, true );
        this.regex_for_adding_zero = this.NUMBER.concat ( SystemNumeration.POINT.concat ( "\\W" ) );  // Это регулярное выражение есть обратное предыдущему, то есть относится к добавлению нулевых цифр к началу чисел, начинающийся с дробного символа
        built = this.addZero ( built, this.regex_for_adding_zero, false );
        // Это есть процедура добавления нулевых цифр с самого начало и с самого конца ( при необходимости ) данного арифметического выражения
        if ( built.charAt ( built.length ( ) - 1 ) == SystemNumeration.POINT.charAt ( 0 ) )  built.append ( this.ZERO );
        if ( built.charAt (0 ) == SystemNumeration.POINT.charAt ( 0 ) )  built.insert ( 0, this.ZERO );
        return built.toString ( );
    }

    private String addFirstLastZero ( String expression ) throws NullPointerException  // По действию этой функции в случае данное арифметическое выражение начинается / заканчивается со знака / со знаком плюса или минуса, или же начинается / заканчивается с открывающей скобки / с закрывающей скобкой, которому следует / предшествует знак плюса или минуса, то перед / после этого знака добавляется символ с нулевым обозначением.  А если в подобном случае в место знака плюса или минуса имеется знак иной арифметической операции, то выводится об ошибке составления арифметического выражения
    {
        if ( expression.substring ( 0, 1 ).matches ( SystemNumeration.PLUS.concat ( "|".concat ( SystemNumeration.MINUS ) ) ) )  expression = this.ZERO.concat ( expression ); // В случае данное арифметическое выражение начинается со знака ‘минус’ или ‘плюс’, соответственно с начало этого выражения прибавляется нулевой символ
        if ( expression.substring ( expression.length ( ) - 1, expression.length ( ) ).matches ( SystemNumeration.PLUS.concat ( "|".concat ( SystemNumeration.MINUS ) ) ) )  expression = expression.concat ( this.ZERO );  // В случае данное арифметическое выражение заканчивается со знака ‘минус’ или ‘плюс’, соответственно с конца этого выражения прибавляется нулевой символ
        expression = expression.replaceAll ( SystemNumeration.LEFT_BRACKET + SystemNumeration.PLUS, SystemNumeration.LEFT_BRACKET.concat ( this.ZERO ).concat ( SystemNumeration.PLUS ) );  // В случае данное арифметическое выражение начинается с открывающей скобки, которому следует знак ‘плюс’, между этой скобкой и знаком добавляется нулевая цифра
        expression = expression.replaceAll ( SystemNumeration.LEFT_BRACKET + SystemNumeration.MINUS, SystemNumeration.LEFT_BRACKET.concat ( this.ZERO ).concat ( SystemNumeration.MINUS ) );  // В случае данное арифметическое выражение начинается с открывающей скобки, которому следует знак ‘минус’, между этой скобкой и знаком добавляется нулевая цифра
        expression = expression.replaceAll ( SystemNumeration.PLUS + SystemNumeration.RIGHT_BRACKET, SystemNumeration.PLUS.concat ( this.ZERO ).concat ( SystemNumeration.RIGHT_BRACKET ) );  // В случае данное арифметическое выражение заканчивается со знака ‘плюс’, с последующей закрывающей скобкой, между этим знаком и скобкой добавляется нулевая цифра
        expression = expression.replaceAll ( SystemNumeration.MINUS + SystemNumeration.RIGHT_BRACKET, SystemNumeration.MINUS.concat ( this.ZERO ).concat ( SystemNumeration.RIGHT_BRACKET ) );  // В случае данное арифметическое выражение заканчивается со знака ‘минус’, с последующей закрывающей скобкой, между этим знаком и скобкой добавляется нулевая цифра
        if ( expression.substring ( 0, 1 ).matches ( "\\W" )  &&  expression.charAt ( 0 ) != SystemNumeration.LEFT_BRACKET.charAt ( 0 )  &&  expression.charAt ( 0 ) != SystemNumeration.MINUS.charAt ( 0 )  &&  expression.charAt ( 0 ) != SystemNumeration.PLUS.charAt ( 0 ) ) { out.println ( "\u001B[35m A T T E N T I O N _ The first sybbol of this arithmetic expression \u001b[0m '" + expression + "' \u001b[35m is not a ciphers_count \u001B[35m" );  return null; }  // В случае данное арифметическое выражение начинается с символа, НЕ являющийся знаком ‘минус’, ‘плюс’ или же открывающей скобки, возникает исключение
        if ( expression.substring ( expression.length ( ) - 1, expression.length ( ) ).matches ( "\\W" )  &&  expression.charAt ( expression.length ( ) - 1 ) != RIGHT_BRACKET.charAt ( 0 ) ) { out.println ( "\u001B[35m A T T E N T I O N _ The last sybbol of this arithmetic expression is not a cipher" );  return null; }  // В случае данное арифметическое выражение заканчивается с символа, НЕ являющийся знаком ‘минус’, ‘плюс’ или же открывающей скобки или знаком степени / отрицательной степени десяточной части, возникает исключение

        try
        {
            if ( expression.substring ( 0, 2 ).matches ( SystemNumeration.LEFT_BRACKET.concat ( "\\W" ) )  &&  expression.charAt ( 1 ) != SystemNumeration.LEFT_BRACKET.charAt ( 0 )  &&  expression.charAt ( 1 ) != SystemNumeration.MINUS.charAt ( 0 )  &&  expression.charAt ( 1 ) != SystemNumeration.PLUS.charAt ( 0 ) ) { out.println ( "\u001B[35m A T T E N T I O N _ The second sybbol of this arithmetic expression \u001b[0m '" + expression + "' \u001b[35m is illegal \u001B[0m" );  return null; }  // В случае данное арифметическое выражение начинается с открывающей скобки, которой следует знак, НЕ являющий ‘минусом’ или же ‘плюсом’, возникает исключение
            if ( expression.substring ( expression.length ( ) - 2, expression.length ( ) ).matches ( "\\W".concat ( SystemNumeration.RIGHT_BRACKET ) )  &&  expression.charAt ( expression.length ( ) - 2 ) != SystemNumeration.DEGREE.charAt ( 0 )  &&  expression.charAt ( expression.length ( ) - 2 ) != SystemNumeration.DEGREE_NEGATIVE.charAt ( 0 )  &&  expression.charAt ( expression.length ( ) - 2 ) != SystemNumeration.RIGHT_BRACKET.charAt ( 0 )  &&  expression.charAt ( expression.length ( ) - 2 ) != SystemNumeration.DEGREE.charAt ( 0 )  &&  expression.charAt ( expression.length ( ) - 2 ) != SystemNumeration.DEGREE_NEGATIVE.charAt ( 0 ) )  { out.println ( "\u001B[35m A T T E N T I O N _ The penultimate symbol of this arithmetic expression \u001b[0m '" + expression + "' \u001b[35m is illegal \u001B[0m" );  return null; }  // В случае данное арифметическое выражение заканчивается с закрывающей скобки, которой предшествует знак, НЕ являющий ‘минусом’ лм же ‘плюсом’ а так же положительной или же отрицательной степенью десяточной части, возникает исключение
        }
        catch ( StringIndexOutOfBoundsException ex )  { }
        return this.ZERO.concat ( SystemNumeration.PLUS.concat ( expression.concat ( SystemNumeration.PLUS.concat ( this.ZERO ) ) ) );  // Тут с начало имеющегося арифметического выражения добавляется с начало данного арифметического выражения прибавляется под-выражение ‘0+’, с целью корректного срабатывания функции ‘addDegree ( … )’ описания этого же класса, и с конца арифметического выражения так же добавляется под-выражение ‘+0’ с целью действие функции ‘addDegree ( )’ было исполнено полноценно – для конечного числа с десяточной частью в том числе, по скольку в противном случае по имеющийся регулярному выражению ‘add_degree’ и ‘add_degree_negative’ действие по отношению конечного числа с десяточной частью НЕ выполняется
    }

    String addDegree ( String expression ) throws NullPointerException  // Это функция нужна для того, что бы в случае в арифметической выражении имеется подвыражение ‘°’ с неким последующим числом, то после этого числа добавляется тот же знак ‘°’ с целью это «некое» последующее число не стал участвовать в арифметической операции по знаку арифметической операции, последующий ее
    {
        try
        {
            Matcher matcher = matcher = Pattern.compile ( this.add_degree ).matcher ( expression );
            StringBuilder built = null;
            while ( matcher.find ( ) )
            {
                int index = matcher.end ( );
                built = new StringBuilder ( expression );
                built.insert ( ( index - 1), SystemNumeration.DEGREE );
                expression = built.toString ( );
                matcher = Pattern.compile ( this.add_degree ).matcher ( expression );
            }

            matcher = Pattern.compile ( this.add_degree_negative ).matcher ( expression );
            built = null;
            while ( matcher.find ( ) )
            {
                int index = matcher.end ( );
                built = new StringBuilder ( expression );
                built.insert ( ( index - 1), SystemNumeration.DEGREE_NEGATIVE );
                expression = built.toString ( );
                matcher = Pattern.compile ( this.add_degree_negative ).matcher ( expression );
            }
            return expression;
        }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: addDegree ( " + Colors.YELLOW + expression + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    private String checkBracketsCount ( String expression ) throws NullPointerException  // Это функция выполняет проверку равенства количеств левых и правых скобок в данном арифметическом выражении
    {

        Matcher left_bracket_match = Pattern.compile ( SystemNumeration.LEFT_BRACKET ).matcher ( expression ),  right_bracket_match = Pattern.compile ( SystemNumeration.RIGHT_BRACKET ).matcher ( expression );
        boolean left_match = left_bracket_match.find ( ),  right_match = right_bracket_match.find ( );
        while ( left_match  &&  right_match ) { left_match = left_bracket_match.find ( );  right_match = right_bracket_match.find ( ); }
        if ( left_match ) { out.println ( "\u001B[35m A T T E N T I O N _ There is an extra left bracket in this expression \u001b[0m " + expression );   return null; }  if ( right_match ) { out.println ( "\u001b[35m A T T E N T I O N _ There is an extra right bracket in this expression \u001b[0m " + expression );   return null; }
        return expression;
    }

    private StringBuilder addZero ( StringBuilder expression, String regex, boolean before_after ) throws NullPointerException  // Это функция нужна для того, что бы в состав данного арифметического выражения, определяющийся первым параметром перед или после всех подвыражений, определяющийся вторым параметром, добавить нулевую цифру
    {
        Pattern pattern = Pattern.compile ( regex );
        Matcher matcher = pattern.matcher ( expression.toString ( ) );
        while ( matcher.find ( ) )
        {
            int index = -1;
            index = ( before_after ? matcher.start ( ) + 1 :  matcher.end ( ) - 1 );
            expression.insert ( index, this.ZERO );
            matcher = pattern.matcher ( expression.toString ( ) );
        }
        return expression;
    }

    //--- O B J E C T ---
    @Override
    public int hashCode ( ) 
	{
		StringBuilder built = new StringBuilder ( );
		for ( String str : this.numeration_array )  built.append ( str );
		return Objects.hash ( built.toString ( ), this.ciphers_count );
	}

    @Override
    public boolean equals ( Object obj )
    {
        if ( obj == null  ||  ! obj.getClass ( ).equals ( this.getClass ( ) ) )  return false;
        SystemNumeration system = (SystemNumeration)obj;
        if ( this.numeration_array.length != system.getNumerationArray ( ).length )  return false;
        for ( int index = 0;  index < this.numeration_array.length;  ++ index )  if ( ! this.numeration_array [ index ].equals ( system.getNumerationArray ( ) [ index ] ) )  return false;
		return this.ciphers_count.equals ( system.getCiphersCount ( ) );
    }

    @Override
    public SystemNumeration clone ( ) throws CloneNotSupportedException
    {
        SystemNumeration system = (SystemNumeration)super.clone ( );
        return system;
    }

    @Override
    public String toString ( )
    {
        StringBuilder built = new StringBuilder ( );
        for ( String str : this.numeration_array )  built.append ( str );
        return built.toString ( );
    }

    @Override
    public int compareTo ( SystemNumeration another_system ) throws NullPointerException  { try { return this.numeration_array.length - another_system.getNumerationArray ( ).length; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: compareTo ( " + Colors.YELLOW + another_system + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); } }

    //--- O T H E R S ---
    String addBackZeroSymbols ( String number_1, String number_2 ) throws NullPointerException  // Действием этой функции с конца состава числа, определяющийся вторым параметром, добавляются нулевые цифры в количестве, соответствующее разницу количество составных цифр числа определяющийся первым параметром и количество составных цифр числа, определяющийся вторым параметром. Это функция вызывается при выполнении операции деления.
    {
        int count = 0;
        try { count = number_1.length ( ) - number_2.length ( ) - 1; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: addBackZeroSymbols ( " + Colors.YELLOW + number_1 + ", " + number_2 + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        StringBuilder built = new StringBuilder ( number_2 );
        while ( ( count -- ) > 0 )  built.append ( this.ZERO );
        return built.toString ( );
    }
}


