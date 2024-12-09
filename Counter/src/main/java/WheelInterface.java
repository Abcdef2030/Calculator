import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.in;
import java.util.function.Consumer;
import java.util.function.Predicate;


interface WheelInterface extends Comparable<WheelInterface>, Cloneable  // Классы, по которым будут создаваться колесо и счетчик в целом, будут реализовать этот интерфейс. То есть это соглашение о том, для каких действий над колесом и над счетчиком какие именно функции будут вызваны
{
	//--- S E T T E R S ---
	void setStatement ( String statement );
	void setZero ( );  // Под пониманием 'zero' подразумевается нулевое цифровое значение по данной системе счисления. Для счетчика действие будет выполнятся над всеми составными колесами. Это функция предназначена для случае, когда с целью применения кодировки в вычислении нулевым значением в данной системе счисления будет являться символьное значение, отлично от обычного символьного значения, с нулевым обозначением
	void setMaxStatement ( );  // Устанавливается нулевое показание по данной системе счисления. Это функция предназначена для упрощения установления максимального показания колеса, а так же счетчика в целом, по данной системе счислению на пользовательском уровне
	void reset ( );
	void setOriginalStatement ( String statement );
	
	//--- G E T T E R S ---
	SystemNumeration getSystemNumeration ( );  // Вывод значения системы счисления по классу 'Counter' будет одно значение, а по классу 'CounterDifferentSystems' будет стек значений, соответственно стеку колес данного счетчика
	Object getCurrentStatement ( );  // Вывод массив индексов показаний по данной системе счисления
	Object getStartStatement ( );
	Object getOriginalStatement ( );
	
	//--- B O O L E A N S ---
	boolean isZero ( );
	boolean isMaximum ( );
	
	//--- O B J E C T ---   // Эти функция объявляются в этом интерфейсе, по сколку некоторые функции описания арифметического класса 'ArithmeticCounter' имеют параметры, объявленные по этому интерфейсу
	boolean equals ( Object obj );
	WheelInterface clone ( ) throws CloneNotSupportedException;
	String toString ( );  // Вывод показания 
	int compareTo ( WheelInterface another_counter ) throws NullPointerException;
	
	//--- M O V I N G ---
	WheelInterface statementUp ( );
	WheelInterface statementDown ( );
	
	// Исполнение этой функции следующее – крутится ( в одном из направлении, определяющийся по первому параметру ) данное колесо / данный счетчик в месте с иным колесом / счетчиком, определяющийся по второму параметру, до выполнения определенной условии, относящийся ко второму колесу счетчику – допустим до достижения нулевого показания, или до достижения показания, эквивалентно некому текстовому значению.  Цель – выполнения арифметических операций по сложению, вычитанию и умножению. При умножении это функция будет вызвана композиционно
	default void moveUntil ( Predicate<WheelInterface> lambda, WheelInterface another ) throws NullPointerException  { try { while ( ! lambda.test ( another ) )  { } }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The second argument of the function " + Colors.WHITE + "'WheelInterface :: moveUntil ( " + Colors.YELLOW + "'lambda', " + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); } }
	default void moveUpUntil ( WheelInterface another ) throws NullPointerException  { try { this.moveUntil ( ( value ) -> { if ( ! value.isZero ( ) )  this.statementUp ( );  value.statementDown ( );  return value.isZero ( ); }, another );  }  catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + Colors.NOCOLOR + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelInterface :: moveUpUntil ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); } }  // Это функция предназначена для выполнения арифметических операций по сложению числовых показаний двух счетчиков.
	default void moveDownUntil ( WheelInterface another ) throws NullPointerException  // Это функция предназначена для выполнения арифметических операций по вычитанию числовых показаний ( она понадобится для определения функции с предназначением выполнения арифметических операций по делению ) данного и другого счетчика. Функция выполняется следующим образом – показание данного и того другого счетчика параллельно уменьшаются ( то есть оба счетчика крутятся в отрицательном направлении ) до достижения нулевого показания того другого счетчика. Но по сколку в случае показание данного счетчика изначально может оказаться меньше показания того другого счетчика, нулевому показанию раньше может достичь данный счетчик. И в таком случае он заново начинает движение уже в положительном направлении, при продолжении движения того другого счетчика в отрицательном направлении. 
	{
		try { this.moveUntil ( ( value ) -> { if ( ! this.isZero ( )  &&  ! value.isZero ( ) )  { this.statementDown ( );  value.statementDown ( ); }  return ( this.isZero ( )  ||  value.isZero ( ) ); }, another ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + Colors.NOCOLOR + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelInterface :: moveDownUntil ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
		try { if ( this.isZero ( )  &&  ! another.isZero ( ) )  this.moveUntil ( ( value ) -> { this.statementUp ( );  value.statementDown ( );  return value.isZero ( ); }, another ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + Colors.NOCOLOR + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'WheelInterface :: moveDownUntil ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }  // В случае показание того другого счетчика было больше показания данного счетчика и данное колесо / счетчик достиг нулевого показания, при то, что показание второго колеса / счетчика отличается от нулевого, данное колесо / счетчик будет крутится в положительном направлении, пока показание другого счетчика не аннулируется.   
	}
	 
	//--- O T H E R S ---
	String printFull ( );
}