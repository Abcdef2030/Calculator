import java.util.Scanner;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Objects;

import static java.lang.System.*;



public class Device
{
    protected ArithmeticTable system_numeration = null;  // Для данного устройство ( по классу 'Number' ) имеется своя система счисления
    protected ArithmeticBoard board = null;  // Для данного устройство имеется своя доска, на которую записывается арифметическое выражение, будучи решенным методами этого устройстлво ( дпо классу 'Number' )
    //--- Потоковые элементы ---
    protected PipedReader reader = null;  // С помощью этого элемента и элемента описания класса ‘ArithmeticBoard, созданный по классу ‘PipedWriter’, создается потоковый элемент, по которому простейшие арифметические выражения поступают к элемент, созданный по классу ‘Number’ для вычисления их значений
    protected PipedWriter writer = null;  // С помощью этого элемента и элемента описания класса ‘ArithmeticBoard, созданный по классу ‘PipedReader’, создается потоковый элемент, по которому результаты вычисления простейших арифметических выражений поступают к элементу, созданный по классу ‘ArithmeticBoard’.
    protected Scanner scanner = null;
    static Scanner input = new Scanner ( in );
    public static Map<String, Operation> map_operation = new HashMap ( );  // Статистический коллекционный элемент описания класса ‘Device’ заполняется в среде определения интерфейса ‘Operation’, по скольку все реализующие этот интерфейс классы имеют доступ только в файловом пространстве этого определения .   В составе этой карты будут разные элементы, объявленные по одному и тому же интерфейсу, и каждый из них будет иметь метод выполнения того или иного простейшего прифметическ3ого выражения. Смысл этой карты в том, что бы вызвать эти методы с помощью тех составных элементов по их ключевым значениям, представляющие текстовые представления знаков простейших арифметических операций
    protected long time_1 = 0, time_2 = 0;
	private static Map<ArithmeticTable, Device> map_device = new HashMap ( );

    //--- C O N S T R U C T O R S ---  К созданию элемента по этому классу применяется шаблон архитектуры под названием ‘одиночка’, по скольку нету смысла создавать разные элементы-дивайсы  по одной и той же системе счисления.  Архитектура создания элемента-дивайса следующая – имеется некая карта элементов-дивайсов, ключевыми значениями элементов которой составляются по классу ‘SystemNumeration’. В эту карту набираются все созданные по разным системам счислениям элементы-дивайсы, и в случае есть необходимость создать элемент-дивайс по систему счислению, по которому в карту уже имеется созданный элемент-дивайс, то в место создания нового элемента, статистическим функциональным элементом ‘getDevice ( … )’ будет выведен элемент, созданный по указанной системе счислению, то есть будет выведен элемент, с указанным ключевым значением, составленное по классу ‘SystemNumeration’
	Device ( )  { this ( new ArithmeticTable ( 10, 10 ) ); }
	
	private Device ( int numeration, int ciphers_count )  { this ( new ArithmeticTable ( numeration, ciphers_count ) ); }
	
    private Device ( int numeration ) throws IllegalArgumentException  { this ( new ArithmeticTable ( numeration, 10 ) ); }
	
    Device ( ArithmeticTable system_numeration ) throws NullPointerException
    {
        this.system_numeration = system_numeration;
        this.board = new ArithmeticBoard ( this.system_numeration );
        reader = new PipedReader ( );
        writer = new PipedWriter ( );
        scanner = new Scanner ( reader );
        try { this.reader.connect ( this.board.getWriter ( ) );  this.writer.connect ( this.board.getReader ( ) ); }  catch  ( IOException ex ) { out.println ( "\u001B[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on the line " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\u001B[0m" ); }
        // На этом месте вызывается функция определения интерфейса ‘Operation’ для заполнения карты ‘map_operation’. То есть функция заполнения этой карты определяется в среде определения интерфейса ‘Operation’, по скольку карта заполняется элементами, которые создаются по классам, имеющие доступ только в той среде. А выполняется это действие при создании элемента по классу ‘Device’, то есть в среде определения класса ‘Device’ же

		try { Operation.fullMap ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + " -- When try to call the function " + Colors.WHITE + "'Operation :: fullMap ( )'  " + Colors.RED + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
    }
    //-----------------------

    //--- G E T T E R S ---
	public static Device getDevice ( )
	{
		ArithmeticTable system_numeration = new ArithmeticTable ( );
		return getDevice ( system_numeration );
	}
	
	public static Device getDevice ( int numeration, int ciphers_count ) throws IllegalArgumentException
	{
		try 
		{
			ArithmeticTable system_numeration = new ArithmeticTable ( numeration, ciphers_count );
			return getDevice ( system_numeration );
		}
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + Colors.RED + "\nE X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Device.getDevice ( " + Colors.YELLOW + numeration + ", " + ciphers_count + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L -  " + Colors.WHITE + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
	}
	
	public static Device getDevice ( int numeration ) throws IllegalArgumentException
	{
		try
		{
			ArithmeticTable system_numeration = new ArithmeticTable ( numeration );
			return getDevice ( system_numeration );						
		}
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + Colors.RED + "\nE X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Device.getDevice ( " + Colors.YELLOW + numeration + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L -  " + Colors.WHITE + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
	}
	
	synchronized public static Device getDevice ( ArithmeticTable system_numeration ) throws NullPointerException
	{
		try
		{
			Device device = map_device.get ( system_numeration );
			if ( device == null )  device = new Device ( system_numeration );
			map_device.put ( system_numeration, device );
			return device;			
		}
		catch ( NullPointerException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + Colors.RED + "\nE X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Device.getDevice ( " + Colors.YELLOW + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L +  " + Colors.WHITE + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
	}
	
    ArithmeticTable getSystemNumeration ( ) { return this.system_numeration; }
    
	Map<String, Operation> getMap ( ) { return Device.map_operation; }
	
	synchronized public void runCalculator ( )
	{
        while ( true )
        {
			try
			{
				this.enterExpression ( null );
				this.startCompute ( true );
				this.time_2 = System.currentTimeMillis ( );
				this.printTimePast ( );				
			}
			catch ( NullPointerException ex ) 
			{
				out.println ( "\u001B[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on the line " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\u001B[0m" );
				this.board.printHistory ( );
			}
			catch ( ArithmeticException exc ) { out.println ( exc.getMessage ( ) + new Throwable ( ).getStackTrace ( ) [ 0 ] + Colors.NOCOLOR ); }
        } 
	}
	
	synchronized public String runCalculator ( String exception )
	{
		this.board.setArithmeticExpression ( exception );
        this.board.optimazeExpression ( );
		this.startCompute ( true );
		return this.board.getArithmeticExpression ( );
	}
    
	protected int printTimePast ( )
    {
        int seconds = (int)( this.time_2 - this.time_1 ) / 1000;
        out.println ( "\u001B[34m It took \u001B[32m" + seconds + "\u001B[34m seconds \u001B[0m" );
        return seconds;
    }
	//-----------------------

    protected void startCompute ( boolean print ) throws ArithmeticException
    {
		this.time_1 = System.currentTimeMillis ( );
        while ( this.board.primeExpression ( ) )  // Пока в составе данного арифметического выражения находятся простые арифметические под-выражения
        {
            String value_1 = scanner.nextLine ( ), operation = scanner.nextLine ( ), value_2 = scanner.nextLine ( );
            if ( print )  out.println ( "\u001B[40m \u001B[37m Now it is computing the expression __ " + value_1 + operation + value_2 + "\u001B[0m" );
            String minus = "";
            if ( SystemNumeration.isNegative ( value_1 ) )
            {
                value_1 = value_1.replaceFirst ( SystemNumeration.MINUS, "" );
				if ( operation.equals ( SystemNumeration.MINUS ) )  { operation = SystemNumeration.PLUS;  minus = SystemNumeration.MINUS; }
				else if ( operation.equals ( SystemNumeration.PLUS ) )  { operation = SystemNumeration.MINUS;  String temp_value = value_1;  value_1 = value_2;  value_2 = temp_value; }
				else if ( operation.indexOf ( SystemNumeration.LESS ) != -1  ||  operation.indexOf ( SystemNumeration.MORE ) != -1  ||  operation.indexOf ( SystemNumeration.EVEN ) != -1 )  { operation = SystemNumeration.MINUS.concat ( operation ); }
				else  { minus = SystemNumeration.MINUS; }
            }

            try
            {
                Operation obj = Device.map_operation.get ( operation );  if ( obj == null )  { out.println ( "\u001B[35mA T T E N T I O N -- there is no element with key  \u001b[0m '" + operation + "' \u001B[31m in the map \u001b[33m'Device.map_operation' \u001b[0m" ); }
                this.writer.write ( minus.concat ( obj.arithmeticOperation ( value_1, value_2, this.system_numeration, print ).concat ( "\n" ) ).toCharArray ( ) );
                try { this.board.insertResult ( print ); }  catch ( StringIndexOutOfBoundsException ex ) { out.println ( "\u001B[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on the line " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\u001B[0m" ); }
                if ( ! this.board.isCompleted ( ) )  break;  // В случае в составе арифметического выражения, имеющийся на данный момент, не имеются арифметические знаки, цикл по поиску очередного простейшего арифметического выражения прервется
            }
            catch ( IOException ex ) { out.println ( "\u001B[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on the line " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\u001B[0m" ); }
            catch ( NullPointerException exc ) { out.println ( Colors.RED + exc.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\u001B[0m" ); }
        }
		this.board.setResultInHistory ( );
    }

    protected void enterExpression ( String nothing ) throws NullPointerException  // Это функция осуществляет пользовательский ввод с консоля
    {
        out.print ( "Input arithmetic expression __ " );
        String str = input.nextLine ( );

        if ( str.length ( ) == 0 )  throw new NullPointerException ( );  // В случае с консоли ни какого выражения НЕ внесено, возникает исключение, с приостановкой выполнения программы

        this.board.setArithmeticExpression ( str );
        this.board.optimazeExpression ( );
    }
	
	@Override
	public boolean equals ( Object obj )
	{
		if ( obj == null )  return false;
		if ( ! obj.getClass ( ).getName ( ).equals ( this.getClass ( ).getName ( ) ) )  return false;
		Device object = (Device)obj;
		return this.system_numeration.equals ( object.getSystemNumeration ( ) );
	}
	
	@Override
	public int hashCode ( )
	{
		return Objects.hash ( this.system_numeration );
	}
}


class FileDevice extends Device
{
	private static Map<ArithmeticTable, FileDevice> map_device = new HashMap ( );
	
	//--- C O N S T R U C T O R S ---  К созданию элемента по этому классу применяется шаблон архитектуры под названием ‘одиночка’, по скольку нету смысла создавать разные элементы-дивайсы  по одной и той же системе счисления.  Архитектура создания элемента-дивайса следующая – имеется некая карта элементов-дивайсов, ключевыми значениями элементов которой составляются по классу ‘SystemNumeration’. В эту карту набираются все созданные по разным системам счислениям элементы-дивайсы, и в случае есть необходимость создать элемент-дивайс по систему счислению, по которому в карту уже имеется созданный элемент-дивайс, то в место создания нового элемента, статистическим функциональным элементом ‘getFileDevice ( … )’ будет выведен элемент, созданный по указанной системе счислению, то есть будет выведен элемент, с указанным ключевым значением, составленное по классу ‘SystemNumeration’
	private FileDevice ( )  { super ( ); }
	
	private FileDevice ( int numeration, int ciphers_count )  { this ( new ArithmeticTable ( numeration, ciphers_count ) ); }
	
	private FileDevice ( int numeration ) throws IllegalArgumentException  { this ( new ArithmeticTable ( numeration, 10 ) ); }
	
	private FileDevice ( ArithmeticTable system_numeration ) throws NullPointerException  { super ( system_numeration ); }
	
    //--- G E T T E R S ---
	public static FileDevice getFileDevice ( )
	{
		ArithmeticTable system_numeration = new ArithmeticTable ( );
		return getFileDevice ( system_numeration );
	}
	
	public static FileDevice getFileDevice ( int numeration, int ciphers_count ) throws IllegalArgumentException
	{
		try 
		{
		ArithmeticTable system_numeration = new ArithmeticTable ( numeration, ciphers_count );
		return getFileDevice ( system_numeration );			
		}
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + Colors.RED + "\nE X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileDevice.getFileDevice ( " + Colors.YELLOW + numeration + ", " + ciphers_count + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L -  " + Colors.WHITE + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
	}
	
	public static FileDevice getFileDevice ( int numeration ) throws IllegalArgumentException
	{
		try 
		{
		ArithmeticTable system_numeration = new ArithmeticTable ( numeration );
		return getFileDevice ( system_numeration );
		}
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + Colors.RED + "\nE X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileDevice.getFileDevice ( " + Colors.YELLOW + numeration + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L -  " + Colors.WHITE + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
	}
	
	synchronized public static FileDevice getFileDevice ( ArithmeticTable system_numeration ) throws NullPointerException
	{
		try 
		{
			FileDevice device = map_device.get ( system_numeration );
			if ( device == null )  device = new FileDevice ( system_numeration );
			map_device.put ( system_numeration, device );
			return device;
		}
		catch ( NullPointerException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + Colors.RED + "\nE X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileDevice.getFileDevice ( " + Colors.YELLOW + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L +  " + Colors.WHITE + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
	}
	
	
	@Override
	synchronized public void runCalculator ( )
	{
		new FileArithmeticBoard ( ).getStream ( ).forEach ( a ->
			{
				try
				{
					this.enterExpression ( a );
					this.startCompute ( false );
					this.time_2 = System.currentTimeMillis ( );
					this.printTimePast ( );				
				}
				catch ( NullPointerException ex ) { out.println ( "\u001B[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on the line " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\u001B[0m" ); }
				catch ( ArithmeticException exc ) { out.println ( exc.getMessage ( ) + new Throwable ( ).getStackTrace ( ) [ 0 ] + Colors.NOCOLOR ); }
			}
		);
	}

	@Override
	protected void enterExpression ( String formula ) throws NullPointerException
	{
        if ( formula.length ( ) == 0 )  throw new NullPointerException ( );  // В случае с консоли ни какого выражения НЕ внесено, возникает исключение, с приостановкой выполнения программы

        this.board.setArithmeticExpression ( formula );
        this.board.optimazeExpression ( );		
	}
}