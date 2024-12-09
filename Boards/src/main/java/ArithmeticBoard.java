import static java.lang.System.out;
import static java.lang.System.in;
import java.util.Scanner;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class ArithmeticBoard
{
    private SystemNumeration system_numeration;
    private String arithmetic_expr;
    private ArrayList<String> prime_expr;
	static private List<String> regex_list = null;  // Этот лист на основании статистических элементов описания класса ‘SystemNumeration’ ( знаки арифметических операций ) содержит все возможные комбинации простых арифметических знаков со знаком ‘минуса' и в месте со знаками степеней ( положительный и отрицательный ) десяточной части. По этому элементу будет выполнена вычисление конкретной арифметической операции с имеющегося арифметического выражения для вызова функции ‘arithmeticOperation ( … )’ описания соответственновычисленной арифметической операции класса, реализующий интерфейс ‘Operation’
    //--- Некоторые стандартные регулярные выражения ---
    static private final String CONFIG_1 = "(?x";
    static private final String CONFIG_2 = "(?-i)";
    static private final String QMS = "\\Q", EMS = "\\E";
    //--- Значимые элементы ---
    private int insert_index = -1;
    //--- Потоковые элементы ---
    private PipedReader reader = new PipedReader ( );
    private PipedWriter writer = new PipedWriter ( );
    private Scanner scanner = new Scanner ( reader );
	// Элементы создания и записи истории вычислений.  
	private ArrayDeque<Object [ ]> history = new ArrayDeque<Object [ ]> ( );
	private History obj_history = new HistoryExcel ( );

    public ArithmeticBoard ( int numeration ) throws IndexOutOfBoundsException  { this ( new SystemNumeration ( numeration ) ); }

    //--- C O N S T R U C T O R S ---
    public ArithmeticBoard ( SystemNumeration system_numeration )  // Это для случае, когда некая определенная система счисления уже имеется
    {
        this.system_numeration = system_numeration;  // Для данного табло определяется система счисления

		// Эти регулярные выражения составляются для обнаружений простейших НЕ логических выражений. Тут начальный индекс есть ‘3’, по скольку первые две элементы носят значения, относящийся к скобкам, а третий элемент носит значение, относящийся к дробному символу, для которого регулярные выражения определяются предварительно 
		List<String> original_list = new ArrayList ( );
		
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( SystemNumeration.RIGHT_BRACKET ).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( SystemNumeration.LEFT_BRACKET ).append ( system_numeration.NUMBER ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.RIGHT_BRACKET).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( "_" ).append ( system_numeration.NUMBER).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( "_" ).append ( system_numeration.NUMBER).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( "_" ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE ).append ( system_numeration.NUMBER ).append ( SystemNumeration.DEGREE_NEGATIVE).toString ( ) );
		original_list.add ( new StringBuilder ( ).append ( system_numeration.NUMBER ).append ( "_" ).append ( system_numeration.NUMBER).toString ( ) );

		ArithmeticBoard.regex_list = new ArrayList ( );		
		Iterator<String> operator_iter = SystemNumeration.getArithmeticOperations ( ).iterator ( );
		operator_iter.forEachRemaining ( ( value_1 ) ->
			{
				Iterator<String> original_iter = original_list.iterator ( );
				original_iter.forEachRemaining ( ( value_2 ) -> { regex_list.add ( value_2.replaceFirst ( "_", value_1 ) ); } );
					
			} );    
	}
    //-----------------------

    //--- S E T T E R S ---
    void setArithmeticExpression ( String arithmetic_expr )  throws NullPointerException
    {
        if ( arithmetic_expr == null )  throw new NullPointerException ( "The entered arithmetic expression was 'null'  " );
		// Это есть процедура записи истории вычисления данного арифметического выражения
		Object [ ] obj_array = new Object [ 2 ];
		obj_array [ 0 ] = arithmetic_expr;
		obj_array [ 1 ] = null;
        history.add ( obj_array );
        this.arithmetic_expr = this.system_numeration.checkExpression ( arithmetic_expr );
    }
	
	void setResultInHistory ( )
	{
		this.history.peekLast ( ) [ 1 ] = this.arithmetic_expr.replaceFirst ( SystemNumeration.MINUS, "-" ).replaceFirst ( SystemNumeration.PLUS, "+" );
//		StringBuilder built = new StringBuilder ( (String)this.history.peekLast ( ) [ 0 ] ).append ( " = " ).append ( this.history.peekLast ( ) [ 1 ] ).append ( "   " );
		this.obj_history.writeHistoryToFile ( this.history.peekLast ( ) );
		out.println ( "\u001B[46m \u001B[30m The result is __ " + this.arithmetic_expr + "\u001B[0m" );
	}
    //-----------------------

    //--- G E T T E R S ---
    String getArithmeticExpression ( ) { return this.arithmetic_expr; }
    SystemNumeration getSystemsNumeration ( ) { return this.system_numeration; }
    @Override
    public String toString ( ) { return this.arithmetic_expr; }
    PipedReader getReader ( ) { return this.reader; }
    PipedWriter getWriter ( ) { return this.writer; }
    //-----------------------
	
	//--- P R I N T E R S ---
	void printHistory ( )
	{
		Iterator<Object [ ]> iter = this.history.iterator ( );
		try 
		{
			while ( true )
			{
				Object [ ] obj_array = iter.next ( );
				StringBuilder built = new StringBuilder ( );
				built.append ( (String)obj_array [ 0 ] ).append ( " = " ).append ( (String)obj_array [ 1 ] );
				String to_write = built.toString ( ).replaceAll ( "№", "√" );
				out.println ( to_write );
			}
		}
		catch ( NoSuchElementException ex )  { out.println ( "It is the end of history !" ); }
	}
	//-----------------------
	
    //--- B O O L E A N S ---
	boolean isCompleted ( ) { return Pattern.compile ( "\\W" ).matcher ( this.arithmetic_expr ).find ( ); }  // Это функция понадобится всякий раз после выполнения очередной простейшей арифметической операции для выяснения – является ли она крайним, с целью предотвращения дальнейшего поиска простейших арифметических знаков в составе имеющегося на данный момент арифметического выражения
	//-----------------------

    void optimazeExpression ( )
    {  
        // Выполняется оптимизация подвыражений арифметических операций - для примера если имеется подвыражение '++', оно заменяется подвыражением '+', а если имеется '+-' или '-+', то заменяается '-'
        String simbol_array [ ] = { SystemNumeration.LEFT_BRACKET,  SystemNumeration.PLUS,  SystemNumeration.MINUS,  SystemNumeration.MINUS,  SystemNumeration.PLUS,  SystemNumeration.ROOT,  SystemNumeration.MULTIPLY,  SystemNumeration.DIVIDE,  SystemNumeration.PERSENT,  SystemNumeration.REST,  SystemNumeration.POWER,  SystemNumeration.LESS,  SystemNumeration.MORE,  SystemNumeration.EVEN,  SystemNumeration.NOTEVEN };
        String regex_array [ ] = { Pattern.quote ( SystemNumeration.LEFT_BRACKET.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.PLUS.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.PLUS.concat ( SystemNumeration.MINUS ) ),  Pattern.quote ( SystemNumeration.MINUS.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.MINUS.concat ( SystemNumeration.MINUS ) ),  Pattern.quote ( SystemNumeration.ROOT.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.MULTIPLY.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.DIVIDE.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.PERSENT.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.REST.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.POWER.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.LESS.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.MORE.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.EVEN.concat ( SystemNumeration.PLUS ) ),  Pattern.quote ( SystemNumeration.NOTEVEN.concat ( SystemNumeration.PLUS ) ) };
        for ( int index = 0;  index < regex_array.length;  ++ index )  this.arithmetic_expr = this.arithmetic_expr.replaceAll ( regex_array [ index ], simbol_array [ index ] );
		
        // В данной арифметической выражении находится подвыражения, представляющие числа, взятые в скобки, и их заменяют выражения тех же числа без скобок.
        String regex = SystemNumeration.LEFT_BRACKET + this.system_numeration.NUMBER + SystemNumeration.RIGHT_BRACKET;
        String sub_string = null;
        while ( ( sub_string = this.expressionByRegex ( regex ) ) != null )  this.arithmetic_expr = this.arithmetic_expr.replaceFirst ( regex, sub_string );
		
        regex = SystemNumeration.LEFT_BRACKET + this.system_numeration.NUMBER + SystemNumeration.DEGREE + this.system_numeration.NUMBER + SystemNumeration.DEGREE + SystemNumeration.RIGHT_BRACKET;
        while ( ( sub_string = this.expressionByRegex ( regex ) ) != null )  this.arithmetic_expr = this.arithmetic_expr.replaceFirst ( regex, sub_string );
		
        regex = SystemNumeration.LEFT_BRACKET + this.system_numeration.NUMBER + SystemNumeration.DEGREE_NEGATIVE + this.system_numeration.NUMBER + SystemNumeration.DEGREE_NEGATIVE + SystemNumeration.RIGHT_BRACKET;
        while ( ( sub_string = this.expressionByRegex ( regex ) ) != null )  this.arithmetic_expr = this.arithmetic_expr.replaceFirst ( regex, sub_string );
    }

    //--- Следующие две функции предназначены для выполнения этой процедуры - Имеется арифметическое вырвжение, необходимо найти простое выражение по регулярному выражению ( этих регулярных выражений несколько ), ее извлечь из данного выражения, решить и результат и вернуть "на место" ---
    boolean primeExpression ( )  // Это функция возвращает текстовое значение, являющийся под-текстовым выражением данного арифметического выражения, и представляющийся из себя некое простейшее арифметическое выражение, которое удаляется с состава данного арифметического выражения с целью на ее место вписать результат ее вычисления
    {
        String prime_expression = null;  // Этот функциональный элемент находит простейшее арифметическое выражение, извлекает ее с состава данного арифметического выражения, отправляет ее по потоковой «трубе», с целью в другой среде это выражение было решено. Булево значение, которое он носит, относится к удачной находке
        Iterator<String> iter = this.regex_list.iterator ( );
        try
        {
            while ( true )  // Действие отправки простейшего под-выражения выполняется в бесконечном, каждый раз отправляя по три текстовых значений – первое число, знак операции, второе число
            {
                String regex = iter.next ( );	
                if ( ( prime_expression = this.expressionByRegex ( regex ) ) != null )
                {
					String minus = "";  if ( prime_expression.substring ( 0, 1 ).equals ( SystemNumeration.MINUS ) ) { minus = SystemNumeration.MINUS;  prime_expression = prime_expression.replaceFirst ( SystemNumeration.MINUS, "" ); }  // В случае арифметическое выражение начинается со знака 'минус', этот знак отнимается, сохраняется и в конце вычисления снова вставляется
                    String arithm_symbol = this.getArithmeticSymbol ( prime_expression );  // Это знак арифметического под-выражения, вычисленный этой функцией, с целью отправки по потоковой «трубе» ‘writer’
					String [ ] numbers_array = prime_expression.split ( "[".concat ( arithm_symbol ).concat ( "]{1,3}" ) );  // Это массив двух текстовых значений, представляющее два числа, участвующие в найденной арифметической под-выражении. Второе число в себе может содержать выражение со степенью, по этому это «второе» выражение так же будет разделено в два подвыражение, разделяющийся друг от друга знаком ‘°’ или ‘¯’.
					String degree_1 = this.getArithmeticDegree ( numbers_array [ 0 ] ),  degree_2 = this.getArithmeticDegree ( numbers_array [ 1 ] );  // В простейшей арифметической выражении участвуют два числа – так каждый их них может содержать некую степень. Эти два текстовые значения описывают подобные содержания, в смысле – в случае оно НЕ содержит, текстовое значение ровняется пустому текстовому значению
					String arithm_expressions = degree_1.concat ( arithm_symbol.concat ( degree_2 ) );  // Это текстовое значение описывает данное простейшее арифметическое выражение, в том числе содержание степеней обеих чисел, участвующие в ней 
					try { this.writer.write ( minus.concat ( numbers_array [ 0 ] ).concat ( "\n" ).concat ( arithm_expressions.concat ( "\n" ) ).concat ( numbers_array [ 1 ].concat ( "\n" ) ).toCharArray ( ) ); }  catch ( IOException ex ) { /**/ }
                    this.arithmetic_expr = this.arithmetic_expr.replaceFirst ( regex, "" );  // Извлечение простейшего арифметического под-выражения, совпадающая с одним из регулярных выражений из коллекции ‘regex_list’, с целью в дальнейшем на ее место вставить результат ее решения
					return true;
                }
            }
        }
		catch ( NoSuchElementException exc )  { return false; }  // Это исключение вызывается в случае было введено одно число в место какого не будь арифметического выражения, или же итоговый результат вычисления данного арифметического выражения есть отрицательный
        catch (  NullPointerException | IllegalStateException ex ) { out.println ( "\u001b[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on line ___ " + new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }  
    }

    private String getArithmeticSymbol ( String expression )
    {
        Matcher matcher = Pattern.compile ( "[" + SystemNumeration.ROOT.concat ( SystemNumeration.REST ).concat ( SystemNumeration.PERSENT ).concat ( SystemNumeration.POWER ).concat ( SystemNumeration.MULTIPLY ).concat ( SystemNumeration.DIVIDE ).concat ( SystemNumeration.MINUS ) + "]{2}|[" + SystemNumeration.POINT.concat ( SystemNumeration.FACTORIAL ).concat ( SystemNumeration.ROOT ).concat ( SystemNumeration.REST ).concat ( SystemNumeration.PERSENT ).concat ( SystemNumeration.POWER ).concat ( SystemNumeration.MULTIPLY ).concat ( SystemNumeration.DIVIDE ).concat ( SystemNumeration.PLUS ).concat ( SystemNumeration.MINUS ) + "]|[" + SystemNumeration.LESS.concat ( SystemNumeration.MORE ).concat ( SystemNumeration.EVEN ).concat ( SystemNumeration.NOTEVEN ).concat ( SystemNumeration.MINUS ) + "]{1,3}" ).matcher ( expression );
        matcher.find ( );
        try { return expression.substring ( matcher.start ( ), matcher.end ( ) ); }  catch ( IllegalStateException ex ) { out.println ( "\u001b[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on line ___ " + new Throwable ( ).getStackTrace ( ) [ 0 ] );  return null; }
    }

    private String getArithmeticDegree ( String expression )
    {
		Matcher matcher = Pattern.compile ( "[".concat ( SystemNumeration.DEGREE.concat ( SystemNumeration.DEGREE_NEGATIVE ) ).concat ( "]" ) ).matcher ( expression );
		matcher.find ( );
		try { return String.valueOf ( expression.charAt ( matcher.start ( ) ) ); }  catch ( IllegalStateException ex ) { return ""; }
    }

    private String expressionByRegex ( String regex )  // С состава данного арифметического выражения возвращает простейшее арифметическое выражение, найденное первым ( при поиске с лево на право ), совпадающее с данным регулярным выражением. Если в случае нахождения имеются скобки, они удаляются. В случае НЕ нахождения возвращает пустой указатель
    { 
        Matcher first_match = Pattern.compile ( regex ).matcher ( this.arithmetic_expr );
        first_match.find ( );
		try
        {
            this.insert_index = first_match.start ( );
            String expression = this.arithmetic_expr.substring ( this.insert_index, first_match.end ( ) ).replaceAll ( "[()]{1}", "" );
			expression =  expression.replaceAll ( SystemNumeration.LEFT_BRACKET.concat ( "|".concat ( SystemNumeration.RIGHT_BRACKET ) ), "" );
            return ( expression != "" ? expression : null );
        }
		catch ( IllegalStateException ex ) { return null; }
    }

	private boolean isNotEnded ( )  // Возвращаемое значение относится к тому, что сложное арифметическое выражение целиком решено
	{
		String regex = "[".concat ( QMS ).concat ( SystemNumeration.LEFT_BRACKET ).concat ( SystemNumeration.RIGHT_BRACKET ).concat ( SystemNumeration.FACTORIAL ).concat ( SystemNumeration.ROOT ).concat ( SystemNumeration.REST ).concat ( SystemNumeration.REST ).concat ( SystemNumeration.PERSENT ).concat ( SystemNumeration.POWER ).concat ( SystemNumeration.MULTIPLY ).concat ( SystemNumeration.DIVIDE ).concat ( SystemNumeration.MINUS ).concat ( SystemNumeration.PLUS ).concat ( EMS ).concat ( "]" );  // Для выяснения содержится ли какой один из знаков арифметических операций в имеющийся на данный момент арифметическом выражении целиком
		Matcher matcher = Pattern.compile ( regex ).matcher ( this.arithmetic_expr );
		if ( matcher.find ( ) ) { if ( matcher.find ( ) )  return matcher.start ( ) != 0; }  else return false;  // Результат данного арифметического выражения может быть отрицательным, и в таком случае в составе текстового представления этого результата будет иметься знак минуса – по индексу ‘0’. В таком случае так же считается, что вычисление результата завершено. В ином случае если просто методом элемента ‘matcher’ находится что либо по данному регулярному выражению, означает процесс вычисления не завершен
		return false;
	}

    void insertResult ( boolean print ) throws StringIndexOutOfBoundsException  // Это функция вносит полученный результат вычисления очередного простейшего арифметического выражения в текстовом представлении в данное арифметическое выражение под тем индексом ( с помощью элемента ‘insert_index’ ), под которым это простейшее арифметическое выражение содержалось до этого
    {
        StringBuilder expression = new StringBuilder ( this.arithmetic_expr );
        expression.insert ( this.insert_index, SystemNumeration.PLUS.concat ( scanner.nextLine ( ) ) );
        this.arithmetic_expr = expression.toString ( );
		if ( this.isNotEnded ( ) )  { this.arithmetic_expr = this.system_numeration.addDegree ( this.arithmetic_expr );  if ( print )  out.println ( Colors.BLACK + "\u001B[45m Now the arithmetic expression is __ " + this.arithmetic_expr + "\u001B[0m" ); }
		this.insert_index = -1;  // Это как уничтожение индексного значения, по которому может быть добавлено текстовое значениеи во избежания ошибочных добавлений
        this.optimazeExpression ( ); 
    }
}