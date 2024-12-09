import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.in;
import java.util.Objects;



class Wheel implements WheelInterface  // Это образ создания колес по определенной системе счисления
{
	private SystemNumeration system_numeration;  // Каждое колесо может быть создано по разному системе счисления ( это метод кодировки чисел )
	private FileOfPoints<Integer> wheel = null;

	//--- C O N S T R U C T O R S ---
	Wheel ( )  { this ( SystemNumeration.standart_system ); }
	
	Wheel ( Integer numeration ) throws IllegalArgumentException, NullPointerException  { this ( new SystemNumeration ( numeration ) ); }
	
	Wheel ( SystemNumeration system_numeration ) throws NullPointerException  { this ( 0, system_numeration ); }
	
	Wheel ( Integer original_statement, Integer numeration ) throws IllegalArgumentException, NullPointerException  { this ( original_statement, new SystemNumeration ( numeration ) ); }
	
	Wheel ( Integer original_statement, SystemNumeration system_numeration ) throws IllegalArgumentException, NullPointerException
	{
		this.system_numeration = system_numeration;
		try { this.wheel = FileOfPoints.getPointsByCount ( this.system_numeration.getNumerationArray ( ).length ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'Wheel ( " + Colors.YELLOW + original_statement + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		try { this.wheel.setOriginalStatement ( original_statement ); }  
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'Wheel ( " + Colors.YELLOW + original_statement + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  -- It must be " + ( original_statement < 0 ? "NOT negative" : "LESS then " + system_numeration.getNumeration ( ) + "  " ) + Colors.NOCOLOR ); }
		catch ( NullPointerException exc )  { throw new NullPointerException ( exc.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'Wheel ( " + Colors.YELLOW + "'Integer', " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		this.setStatement ( original_statement );
	}
	 
	//--- S E T T E R S ---	
	@Override
	public void setStatement ( String statement ) throws IllegalArgumentException, NullPointerException
	{
		Integer statement_ = null;
		statement_ = this.system_numeration.symbol_map.get ( statement );
		try { this.setStatement ( statement_ ); }  catch ( IllegalArgumentException | NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: setStatement ( String " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L " + Colors.NOCOLOR ); }
	}
	
	void setStatement ( Integer statement ) throws IllegalArgumentException, NullPointerException
	{
		try { if ( statement < 0  ||  statement >= this.system_numeration.getNumeration ( ) )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: setStatement ( Integer " + Colors.YELLOW + statement + Colors.WHITE + " )' " + Colors.RED + "is  I L L E G A L -- it must be " + ( statement == null ? "not null " : ( statement < 0 ? "not negative " : "not more then " + Colors.YELLOW + ( this.system_numeration.getNumeration ( ) - 1 ) ) ) + "  " + Colors.NOCOLOR ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: setStatement ( Integer " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		while ( ! this.wheel.getCurrentStatement ( ).equals ( statement ) )  this.wheel.setCurrentNext (  );		
	}
	
	void setStatement ( Character symbol )  throws IllegalArgumentException  { try { setStatement ( String.valueOf ( symbol ) ); }  catch ( IllegalArgumentException | NullPointerException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: setStatement ( Character " + Colors.YELLOW + symbol + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); } }
	
	@Override
	public void setZero ( )  { this.setStatement ( 0 ); }
	
	@Override
	public void setMaxStatement ( )  { this.wheel.setCurrent ( this.wheel.getStart ( ) );  this.wheel.setCurrentPreview ( ); }
	
	@Override
	public void reset ( )  { this.wheel.reset ( ); }
	
	void setOriginalStatement ( Integer statement ) throws IllegalArgumentException  { try { this.wheel.setOriginalStatement ( statement ); }  catch ( IllegalArgumentException | NullPointerException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: setOriginalStatement ( Integer " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); } }

	@Override	
	public void setOriginalStatement ( String original_statement ) throws IllegalArgumentException  { try { this.setOriginalStatement ( this.system_numeration.symbol_map.get ( original_statement ) ); }  catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: setOriginalStatement ( String " + Colors.YELLOW + original_statement + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); } }
	
	//--- G E T T E R S ---	
	@Override
	public SystemNumeration getSystemNumeration ( )  { return this.system_numeration; }
	
	@Override
	public Integer getCurrentStatement ( )  { return this.wheel.getCurrentStatement ( ); }  // Возвращается массив с одним элементом с целью применения полиморфизма над колесом и над счетчиком по вызову этой функции
	
	@Override
	public Integer getStartStatement ( )  { return this.wheel.getStartStatement ( ); }
	
	@Override
	public String getOriginalStatement ( )  { return this.system_numeration.map_symbol.get ( this.wheel.getOriginalStatement ( ) ); }
	
	FileOfPoints<Integer> getWheel ( )  { return this.wheel; }
	
	//--- B O O L E A N S ---
	@Override
	public boolean isZero ( )  { return this.wheel.getCurrent ( ).equals ( this.wheel.getStart ( ) ); }
	
	@Override
	public boolean isMaximum ( )  { return this.wheel.getCurrent ( ).getNext ( ) == this.wheel.getStart ( ); }
	
	boolean isNewCycle ( )  { return this.wheel.getStart ( ) == this.wheel.getCurrent ( ); }
	
	//--- O B J E C T ---
	@Override
	public int hashCode ( )  { return Objects.hash ( this.wheel, this.system_numeration ); }
	
	@Override
	public boolean equals ( Object obj ) throws NullPointerException
	{
		if ( obj == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: equals ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
		Wheel wheel_obj = (Wheel)obj;
		if ( ! this.system_numeration.equals ( wheel_obj.getSystemNumeration ( ) ) )  return false;
		return ( this.wheel.getCurrentStatement ( ).equals ( wheel_obj.getWheel ( ).getCurrentStatement ( ) ) );
	}
	
	@Override
	public Wheel clone ( ) throws CloneNotSupportedException, NullPointerException
	{
		Wheel clone_obj = null;
		clone_obj = (Wheel)super.clone ( );  // Получение пока что не полноценная копия данного элемента-колеса
		try { clone_obj.wheel = this.wheel.clone ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'Wheel :: clone ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
		return clone_obj;
	}

	@Override
	public String toString ( )  { return this.system_numeration.map_symbol.get ( this.wheel.getCurrentStatement ( ) ); }
	
	@Override
	public int compareTo ( WheelInterface another ) throws NullPointerException  
	{
		try
		{
			if ( another.getClass ( ).equals ( this.getClass ( ) ) )  return ( this.getCurrentStatement ( ).compareTo ( (Integer)another.getCurrentStatement ( ) ) );
			else  return this.system_numeration.compareNumbers ( String.valueOf ( this.getCurrentStatement ( ) ), String.valueOf ( another.getCurrentStatement ( ) ) );			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Wheel :: compareTo ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	//--- M O V I N G ---	
	@Override
	public WheelInterface statementUp ( ) throws NullPointerException  { try { this.wheel.setCurrentNext ( );  return this; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'Wheel :: statementUp ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); } } 
	@Override
	public WheelInterface statementDown ( )  { try { this.wheel.setCurrentPreview ( ); return this; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'Wheel :: statementDown ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); } }
	
	//--- O T H E R S ---
	@Override
	public String printFull ( )  // Полноценный вывод колеса в текстовом представлении
	{
		StringBuilder built = new StringBuilder ( );
		int color_index = 0;
		built.append ( Colors.colors_array [ color_index ] + this.wheel.getStart ( ).toString ( ) + " " );
		SeparatePoint<Integer> ptr = this.wheel.getStart ( );
		ptr = ptr.getNext ( );
		do
		{
			if ( ( ++ color_index ) == Colors.colors_array.length )  color_index = 0;
			built.append ( Colors.colors_array [ color_index ] + ptr.toString ( ) + " " + Colors.NOCOLOR );
			ptr = ptr.getNext ( );
		}
		while ( ptr != this.wheel.getStart ( ) );
		return built.toString ( );		
	}
}


class WheelSingle implements Cloneable, WheelInterface
{
	private SystemNumeration system_numeration = null;
	private Integer index = 0;  // Это значение относится к массивному элементу, являющийся основой данной системы счисления
	private Integer number = 0;  // Это значение относится к номеру данного колеса
	private Boolean start = true;  // Это значение относится к тому, что показание данного колеса есть начальное
	private Boolean end = true;  // ...
	private Integer max_index = 0;
	private Integer original_statement = 0;
	
	//--- C O N S T R U C T O R S ---
	WheelSingle ( )  { this ( 0, 0, SystemNumeration.standart_system ); }
	
	WheelSingle ( SystemNumeration system_numeration ) throws NullPointerException  { this ( 0, 0, system_numeration ); }
	
	WheelSingle ( Integer number, SystemNumeration system_numeration ) throws IllegalArgumentException, NullPointerException  { this ( number, 0, system_numeration ); }
	
	WheelSingle ( Integer number ) throws IllegalArgumentException, NullPointerException  { this ( number, 0, SystemNumeration.standart_system ); }
	
	WheelSingle ( Integer number, Integer index ) throws IllegalArgumentException, NullPointerException  { this ( number, index, SystemNumeration.standart_system ); }
	
	WheelSingle ( Integer number, Integer index, SystemNumeration system_numeration ) throws IllegalArgumentException, NullPointerException
	{
		try
		{
			if ( number < 0 )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'WheelSingle ( " + Colors.YELLOW + number + ", " + index + ", " + "'SystemNumeration'" + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );
			if ( index >= system_numeration.getNumeration ( ) )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'WheelSingle ( " + Colors.YELLOW + number + ", " + index + ", " + "'SystemNumeration'" + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- One of the arguments of the constructor " + Colors.WHITE + "'WheelSingle ( " + Colors.YELLOW + number + ", " + index + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		this.system_numeration = system_numeration;
		this.index = index;
		this.number = number;	
		this.max_index = ( system_numeration.getNumeration ( ) - 1 );
		this.start = this.index == 0;
		this.end = this.index == this.max_index;
		this.original_statement = this.index;
	}

	//--- S E T T E R S ---
	
	@Override
	public void setStatement ( String statement ) throws IllegalArgumentException  { this.index = this.system_numeration.symbol_map.get ( statement );  try { boolean i = this.index.equals ( 5 ); }  catch ( NullPointerException ex )  { throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: setStatement ( String " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); } }
	
	void setStatement ( Integer statement ) throws IllegalArgumentException  { this.index = statement;  try { boolean i = this.system_numeration.map_symbol.get ( statement ).equals ( "A" ); }  catch ( NullPointerException ex )  { throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: setStatement ( Integer " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); } }
	
	@Override 
	public void setZero ( )  { this.index = 0; }
	
	@Override
	public void setMaxStatement ( )  { this.index = this.max_index; }
	
	@Override
	public void reset ( )  { this.index = this.original_statement; }	
	
	@Override
	public void setOriginalStatement ( String statement ) throws IllegalArgumentException  { this.original_statement = this.system_numeration.symbol_map.get ( statement );  try { boolean i = this.original_statement.equals ( 5 ); }  catch ( NullPointerException ex )  { throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: setOriginalStatement ( String " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); } }
	
	void setOriginalStatement ( int statement ) throws IllegalArgumentException  { this.original_statement = statement;  try { boolean i = this.system_numeration.map_symbol.get ( this.original_statement ).equals ( "5" ); }  catch ( NullPointerException ex )  { throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: setOriginalStatement ( Integer " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); } }
	
	void setNumber ( int number ) throws IllegalArgumentException  { if ( number < 0 )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: setNumber ( " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );  this.index = number; }

	void setIndex ( int index ) throws IllegalArgumentException  { try { boolean i = this.system_numeration.map_symbol.get ( index ).equals ( "5" );  this.index = index;  this.start = this.index == 0; }  catch ( NullPointerException ex )  { throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: setIndex ( " + Colors.YELLOW + index + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); } }
	
	//--- G E T T E R S ---
	@Override
	public SystemNumeration getSystemNumeration ( )  { return this.system_numeration; }
	
	@Override
	public Integer getCurrentStatement ( )  { return this.index; }
	
	@Override
	public Integer getStartStatement ( )  { return 1; }
	
	@Override
	public Integer getOriginalStatement ( )  { return this.original_statement; }
	
	public Object getWheel ( )  { return null; }
	
	public Integer getNumeration ( )  { return this.system_numeration.getNumeration ( ); }
	
	int getNumber ( )  { return this.number; }
	
	boolean getStart ( )  { return this.start; }
	
	boolean getEnd ( )  { return this.end; }
	
	String getValue ( )  { return this.system_numeration.getNumerationArray ( ) [ this.index ]; }
	
	
	//--- B O O L E A N S ---	
	@Override
	public boolean isZero ( )  { return this.index == 0; } 
	
	@Override
	public boolean isMaximum ( )  { return this.index == this.max_index; } 
	
	//--- O B J E C T ---
	@Override
	public int hashCode ( )  { return Objects.hash ( this.index, this.number, this.start, this.end, this.original_statement ); }
	
	@Override
	public boolean equals ( Object obj ) throws NullPointerException
	{
		try { if ( ! this.getClass ( ).equals ( obj.getClass ( ) ) )  return false; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: equals ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		WheelSingle wheel_obj = (WheelSingle)obj;
		return ( this.system_numeration.equals ( wheel_obj.getSystemNumeration ( ) )  &&  this.index.equals ( wheel_obj.getCurrentStatement ( ) ) );
	}
		
	@Override
	public WheelSingle clone ( ) throws CloneNotSupportedException
	{
		WheelSingle obj = (WheelSingle)super.clone ( );
		obj.index = 0;
		obj.number = ( this.number + 1 );
		obj.max_index = this.max_index;
		obj.start = this.start;
		obj.end = this.end;
		return obj;
	}
		
	@Override
	public String toString ( )  { return String.valueOf ( this.index ); }
	
	@Override
	public int compareTo ( WheelInterface obj ) throws NullPointerException
	{
		WheelSingle ptr = (WheelSingle)obj;
		try { return this.number - ptr.number; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelSingle :: compareTo ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	//--- M O V I N G ---
	@Override
	public WheelSingle statementUp ( )
	{
		++ this.index;  
		this.start = false;  
		if ( this.index > this.max_index )
		{
			this.index = 0;  
			this.start = true;
			this.end = false;
		}
		else if ( this.index == max_index )  this.end = true;
		return this;
	}
	
	@Override
	public WheelSingle statementDown ( )
	{
		-- this.index;  
		this.end = false;
		if ( this.index < 0 )
		{
			this.index = this.max_index;  
			this.start = false; 
			this.end = true;
		}
		else if ( this.index == 0 )  this.start = true;
		return this;
	}
	
	//--- O T H E R S ---
	String nextValue ( )  { try { this.start = false;  return this.system_numeration.getNumerationArray ( ) [ ++ this.index ]; }  catch ( ArrayIndexOutOfBoundsException ex )  { this.start = true;  this.index = 0;  return this.system_numeration.getNumerationArray ( ) [ this.index ]; } }
	
	@Override
	public String printFull ( )
	{
		StringBuilder built = new StringBuilder ( );
		for ( int i = 0;  i < this.system_numeration.getNumeration ( );  ++ i )  built.append ( String.valueOf ( i ) + " " );
		return built.toString ( );
	}
}

 /*
class TestWheel 
{
	static SystemNumeration system = new SystemNumeration ( 16, 10 );
	
	static boolean testClassWheel ( )
	{
        int original = 5;
        int original_another = 3;
        String statement_str = "B";
        int statement_int = 12;
		
        String [ ] test_results = new String [ 100 ];
		int text_index = 0;

// --- S E T T E R S ---
        Wheel wheel = new Wheel ( original, system );
		test_results [ text_index ++ ] = wheel.toString ( );
		wheel.setStatement ( 'B' );
        test_results [ text_index ++ ] = wheel.toString ( );
		wheel.setStatement ( 12 );
        test_results [ text_index ++ ] = wheel.toString ( );
		wheel.setZero ( );
        test_results [ text_index ++ ] = wheel.toString ( );
		wheel.setMaxStatement ( );
        test_results [ text_index ++ ] = wheel.toString ( );
		wheel.reset ( );
        test_results [ text_index ++ ] = wheel.toString ( );
		try { wheel.setOriginalStatement ( "3" ); }  catch ( IllegalArgumentException ex )  { out.println ( ex.getMessage ( ) + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
		wheel.reset ( );
		test_results [ text_index ++ ] = wheel.toString ( );
		test_results [ text_index ++ ] = String.valueOf ( wheel.getStartStatement ( ) );
		wheel.statementUp ( );
		test_results [ text_index ++ ] = String.valueOf ( wheel.statementUp ( ) );
		test_results [ text_index ++ ] = String.valueOf ( wheel.statementDown ( ) );
// ---------------------------------------------------

		text_index = 0;
		if ( ! test_results [ text_index ++ ].equals ( String.valueOf ( original ) ) )  out.println ( Colors.RED + wheel );
		else if ( ! test_results [ text_index ++ ].equals ( statement_str ) )  out.println ( Colors.YELLOW + "wheel.setStatement ( " + statement_str + " ); ___ " + Colors.RED + wheel );
		else if ( ! test_results [ text_index ++ ].equals ( system.map_symbol.get ( statement_int ) ) )  out.println ( Colors.YELLOW + "wheel.setStatement ( " + statement_int + " ); ___ " + Colors.RED + wheel );
		else if ( ! test_results [ text_index ++ ].equals ( "0" ) )  out.println ( Colors.YELLOW + "wheel.setZero ( ); ___ " + Colors.RED + wheel );
		else if ( ! test_results [ text_index ++ ].equals ( system.map_symbol.get ( system.getNumeration ( ) - 1 ) ) )  out.println ( Colors.YELLOW + "wheel.setMaxStatement ( ); ___ " + Colors.RED + wheel );
		else if ( ! test_results [ text_index ++ ].equals ( system.map_symbol.get ( original ) ) )  out.println ( Colors.YELLOW + "wheel.reset ( ); ___ " + Colors.RED + wheel );
		else if ( ! test_results [ text_index ].equals ( system.map_symbol.get ( original_another ) ) )  out.println ( Colors.YELLOW + "wheel.setOriginalStatement ( " + original_another + " ); ___ wheel.reset ( ); ___ " + Colors.RED + wheel );
		else if ( ! test_results [ text_index ].equals ( system.map_symbol.get ( original_another ) ) )  out.println ( Colors.YELLOW + "wheel.getOriginalStatement ( ) ___ " + Colors.RED + wheel.getOriginalStatement ( ) );
		else if ( test_results [ text_index ].equals ( String.valueOf ( 0 ) ) )  out.println ( Colors.YELLOW + "wheel.isZero ( ) ___ " + Colors.RED + wheel.isZero ( ) );
		else if ( ! test_results [ text_index ++ ].equals ( String.valueOf ( original_another ) ) )  out.println ( Colors.YELLOW + "wheel.getCurrentStatement ( ) ___ " + Colors.RED + wheel.getCurrentStatement ( ) );
		else if ( ! test_results [ text_index ++ ].equals ( String.valueOf ( 0 ) ) )  out.println ( Colors.YELLOW + "wheel.getStartStatement ( ) ___ " + Colors.RED + wheel.getStartStatement ( ) );
		else if ( ! test_results [ text_index ++ ].equals ( String.valueOf ( 5 ) ) )  out.println ( Colors.YELLOW + "wheel.statementUp ( ) ___ " + Colors.RED + wheel );
		else if ( ! test_results [ text_index ++ ].equals ( String.valueOf ( 4 ) ) )  out.println ( Colors.YELLOW + "wheel.statementDown ( ) ___ " + Colors.RED + wheel );
		else return true;
		out.println ( wheel.printFull ( ) );
		return false;
	}
	
	public static void main ( String [ ] args )
	{
		if ( testClassWheel ( ) )  out.println ( Colors.BLUE + "OK !" + Colors.NOCOLOR );
	}
}
*/