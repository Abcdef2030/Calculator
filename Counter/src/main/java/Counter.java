import static java.lang.System.out;
import static java.lang.System.in;
import java.util.Stack;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;



class Counter implements WheelInterface  // Это такой образ составления текстового представления числового значения некого счетчика, который состоит с колес в количестве цифровых символов данного числа, при том, что каждое колесо имеет все цифровые символы данной системы счисления последовательно. По этому классу отрицательных числовых показаний не бывают
{
	protected SystemNumeration system_numeration;  // Счетчик создается на основании определенной системе счисления, то есть его колеса носят значения по данному систему счисления
	protected Stack<Wheel> counter;  // Это коллекция колес данного счетчика. То есть при составлении числа образом ‘Counter’ с левого конца к данному счетчику будут добавляться новые колеса. А при выводе этого числа перебор этих составных колес будет выполнятся в право на лево.
	protected String original_statement;  // Этот элемент без дальнейших изменений инициализируется для фиксации изначального показания данного счетчика, в случае этот счетчик был создан по данному числу в текстовом представлении ( он имеет тренировочное предназначение – смысл – процедура клонирования данного счетчика было схоже с процедурой клонирования колес )
	// Числовой знак результата арифметических операций определяется НЕ по классу ‘Counter’, а так же НЕ по классу ‘ArithmeticCounter’
	
	//--- C O N S T R U C T O R S ---
	Counter ( SystemNumeration system_numeration ) throws NullPointerException  // Пустой счетчик, в смысле без колес
	{
		this.system_numeration = system_numeration;
		this.counter = new Stack<Wheel> ( );
		try { this.counter.push ( new Wheel ( system_numeration ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the constructor " + Colors.WHITE + "'Counter ( " + Colors.YELLOW + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	Counter ( String number ) throws NullPointerException  { this ( number, SystemNumeration.standart_system ); }
	
	Counter ( Integer numeration ) throws IllegalArgumentException, NullPointerException  { this ( new SystemNumeration ( numeration ) ); }

	Counter ( String number, Integer numeration ) throws IllegalArgumentException, NullPointerException  { this ( number, new SystemNumeration ( numeration ) ); }
	
	Counter ( String number, SystemNumeration system_numeration ) throws IllegalArgumentException, NullPointerException
	{
		this ( system_numeration );
		try { this.counter.get ( 0 ).setOriginalStatement ( number.substring ( 0, 1 ) ); }  catch ( NullPointerException | IllegalArgumentException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'Counter ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
		try { this.counter.get ( 0 ).setStatement ( number.substring ( number.length ( ) - 1, number.length ( ) ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'Counter ( " + Colors.YELLOW + "'String', " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		try { for ( int index = number.length ( ) - 2;  index >= 0;  -- index )  this.counter.push ( new Wheel ( this.system_numeration.symbol_map.get ( number.substring ( index, index + 1 ) ), this.system_numeration ) ); }  catch ( NullPointerException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'Counter ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
		this.setOriginalStatement ( number );
	}
	
	Counter ( )  { this ( SystemNumeration.standart_system ); }  // Конструктор по умолчанию создает счетчик с одним колесом по десятеричному систему счислению, с нулевым показанием	
	
	Counter ( Counter another_counter ) throws NullPointerException  // Конструктор копии отличается от функции 'clone ( )' тем, что в результате изначальное показание созданного счетчика роно текущему показанию данного копируемого счетчика
	{
		try { this.system_numeration = another_counter.getSystemNumeration ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the constrwuctor " + Colors.WHITE + "'Counter ( " + Colors.YELLOW + "'Counter'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		this.original_statement = another_counter.toString ( );
		this.counter = new Stack<Wheel> ( );
		try { for ( Wheel wheel : another_counter.getCounter ( ) )  this.counter.push ( wheel.clone ( ) ); }  catch ( CloneNotSupportedException ex )  { /**/ }
	}	
	
	//--- S E T T E R S ---
	@Override
	public void setStatement ( String number ) throws IllegalArgumentException, NullPointerException
	{
		try { while ( this.counter.size ( ) > number.length ( ) )  this.counter.pop ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: setStatement ( " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		while ( this.counter.size ( ) < number.length ( ) )  this.counter.push ( new Wheel ( this.system_numeration ) );
		try { for ( int index = 0;  index < number.length ( );  ++ index )  this.counter.get ( this.counter.size ( ) - index - 1 ).setStatement ( number.substring ( index, index + 1 ) ); }  catch ( NullPointerException ex )  { throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: setStatement ( " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
	}

	@Override
	public void setZero ( )  { this.counter.iterator ( ).forEachRemaining ( value -> value.setZero ( ) ); }
	
	@Override
	public void setMaxStatement ( )  { collectionOverkill ( ( value ) -> { value.setMaxStatement ( ); } ); }  // То есть показания всех колес являются максимальными по системе счисления каждого колеса
	
	@Override
	public void reset ( )  { this.setStatement ( this.original_statement ); }

	@Override
	public void setOriginalStatement ( String statement ) throws IllegalArgumentException, NullPointerException
	{
		try { if ( ! this.system_numeration.checkNumber ( statement ) )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: setOriginalStatement ( " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: setOriginalStatement ( " + Colors.YELLOW + "'String'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		this.original_statement = statement;
	}
	
	//--- G E T T E R S ---
	@Override
	public SystemNumeration getSystemNumeration ( )  { return this.system_numeration; }
	
	@Override
	public String getCurrentStatement ( )  
	{
		StringBuilder built = new StringBuilder ( );
		this.collectionOverkillReverse ( value -> built.append ( value ) );
		return built.toString ( );
	}
	
	Stack<Wheel> getCounter ( )  { return this.counter; }
	
	@Override
	public String getStartStatement ( )  
	{
		StringBuilder built = new StringBuilder ( );
		for ( int i = 0;  i < this.counter.size ( );  ++ i )  built.append ( this.system_numeration.ZERO );
		return built.toString ( );
	}
	
	@Override
	public String getOriginalStatement ( )  { return this.original_statement; }
	
	Stream<String> getStream ( ) throws IllegalStateException
	{
		try 
		{
			Stream<String> stream = null;
			Stream.Builder<String> build = Stream.builder ( );
			this.collectionOverkillReverse ( value -> build.add ( value.toString ( ) ) );
			stream = build.build ( );
			return stream;			
		}
		catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'Counter :: getStream ( )'  " + Colors.NOCOLOR ); }
	}
 	
	//--- B O O L E A N S ---	
	@Override
	public boolean isZero ( )  { return collectionOverkillIf ( ( value ) -> ( ! value.isZero ( ) ) ); }
	
	boolean isEmpty ( )  { return this.counter == null; }
	
	@Override
	public boolean isMaximum ( )
	{
		Iterator<Wheel> iter = this.counter.iterator ( );
		try { while ( true )  if ( ! iter.next ( ).isMaximum ( ) )  return false; }  catch ( NoSuchElementException ex )  { return true; }
	}
	
	//--- F O R   B E A U T Y ---	
	void collectionOverkill ( Consumer<Wheel> lambda ) throws NullPointerException  { int index = 0;  while ( index < this.counter.size ( ) )  try { lambda.accept ( this.counter.get ( index ++ ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: collectionOverkill ( " + Colors.YELLOW + "'Consumer<Wheel>'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); } }  // Это функция предназначена для выполнения действий, определенные по параметру, являющийся лямбда выражением, над составными колесами данного счетчика. Смысл – красивое цикличное выполнение действий над колесами, всякий раз не составляя дублированный код по заведению циклического перебора составных колес
	
	boolean collectionOverkillIf ( Predicate<Wheel> lambda ) throws NullPointerException  { int index = 0;  while ( index < this.counter.size ( ) )  try { if ( lambda.test ( this.counter.get ( index ++ ) ) )  return false; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: collectionOverkillIf ( " + Colors.YELLOW + "'Predicate<Wheel>'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }  return true; }  // ...
	
	void collectionOverkillReverse ( Consumer<Wheel> lambda ) throws NullPointerException  { int index = this.counter.size ( ) - 1;  while ( index > -1 )  try { lambda.accept ( this.counter.get ( index -- ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: collectionOverkillReverse ( " + Colors.YELLOW + "'Consumer<Wheel>'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); } }  // ...
	
	//--- M O V I N G ---	
	@Override
	public WheelInterface statementUp ( )
	{
		int index = 0;
		this.counter.get ( index ).statementUp ( );
		try { while ( this.counter.get ( index ).isZero ( ) )  this.counter.get ( ++ index ).statementUp ( ); }  catch ( IndexOutOfBoundsException ex )  { this.counter.push ( new Wheel ( 1, this.system_numeration ) ); }
		return this;
	}
	
	@Override
	public WheelInterface statementDown ( )
	{
		if ( this.isZero ( ) )  return this;
		int index = 0;
		this.counter.get ( index ).statementDown ( );
		try { while ( this.counter.get ( index ).isMaximum ( ) )  { this.counter.get ( ++ index ).statementDown ( ); } }  catch ( IndexOutOfBoundsException ex )  { this.counter.pop ( ); }
		return this;
	}
	
	//--- O B J E C T ---
	@Override
	public int hashCode ( )  { return Objects.hash ( this.counter, this.system_numeration ); }
	
	@Override
	public boolean equals ( Object obj ) throws NullPointerException
	{
		if ( obj == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: equals ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
		Counter another_counter = (Counter)obj;
		return this.system_numeration.equals ( another_counter.getSystemNumeration ( ) )  &&  this.getCurrentStatement ( ).equals ( another_counter.getCurrentStatement ( ) );
	}
	
	@Override
	public Counter clone ( ) throws CloneNotSupportedException
	{
		Counter obj_clone = null;
		obj_clone = (Counter)super.clone ( );  // Получение пока что не полноценная копия данного элемента-колеса
		obj_clone.counter = new Stack<Wheel> ( );
		for ( Wheel wheel : this.counter )  obj_clone.counter.push ( wheel.clone ( ) );
		return obj_clone;
	}
	
	@Override
	public String toString ( )
	{
		StringBuilder built = new StringBuilder ( );
		this.collectionOverkillReverse ( ( value ) -> { built.append ( value ); } );
		return built.toString ( );
	}
	
	@Override
	public int compareTo ( WheelInterface another_counter ) throws NullPointerException 
	{
		if ( another_counter == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Counter :: compareTo ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
		int defference = this.system_numeration.getNumerationArray ( ).length - another_counter.getSystemNumeration ( ).getNumerationArray ( ).length;
		if ( ( defference == 0 )  &&  this.getCurrentStatement ( ).equals ( another_counter.getCurrentStatement ( ) ) )  return this.toString ( ).compareTo ( another_counter.toString ( ) );
		else  return defference;
	}
	
	//--- O T H E R S  ---
	Counter optimizeCounter ( )  // По арифметической логике начальными нулевыми цифрами данного числа НЕ формируется значение этого числа
	{
		while ( this.counter.size ( ) != 1  &&  this.counter.peek ( ).isZero ( ) )  this.counter.pop ( );  // Удаление начальных колес с нулевыми показаниями будет циклично-последовательно. Удаляться НЕ будет только - с точки зрения представляемого физического счетчика - крайнее первое колесо ( это в случае показание данного счетчика будет нулевым )
		return this;
	}	
	
	@Override
	public String printFull ( )  // Вывода счетчика целиком в текстовом представлении
	{
		StringBuilder built = new StringBuilder ( );
		String number = this.getCurrentStatement ( );
		built.append ( Colors.YELLOW );
		for ( int i = 0;  i < this.counter.size ( );  ++ i )  built.append ( "--" );  built.append ( Colors.NOCOLOR );  built.append ( "\n" );
		SeparatePoint<Integer> [ ] ptr_array = new SeparatePoint [ this.counter.size ( ) ];
		int index = this.counter.size ( ) - 1;
		for ( int i = 0;  i < this.counter.size ( );  ++ i )  ptr_array [ i ] = this.counter.get ( index -- ).getWheel().getStart();
		for ( int j = 0;  j < this.system_numeration.getNumerationArray ( ).length;  ++ j )
		{
			for ( int i = 0;  i < this.counter.size ( );  ++ i )
			{
				if ( this.system_numeration.getSymbol ( ptr_array [ i ].getValue ( ) ).charAt ( 0 ) == number.charAt ( i ) )  built.append ( Colors.BLUE );
				built.append ( this.system_numeration.getSymbol ( ptr_array [ i ].getValue ( ) ) + " " );
				built.append ( Colors.NOCOLOR );
				ptr_array [ i ] = ptr_array [ i ].getNext ( );
			}
			built.append ( "\n" );
		}
		for ( int i = 0;  i < this.counter.size ( );  ++ i )  built.append ( "==" );
		built.append ( Colors.NOCOLOR );
		
		return built.toString ( );
	}
	
	protected void deleteSignMinus ( )  { this.original_statement = this.original_statement.replaceFirst ( SystemNumeration.MINUS, "" ); }  // При том, что показание счетчика не описывается отрицательным знаком по классу 'Counter', это функция определена в этой среде в связи с тем, что элемент 'original_statement' имеет закрытый доступ, и в методах класса 'ArithmeticCounter', производный от этого класса 'Counter', и по которому показание счетчика могут описаться отрицательным знаком, изначально необходимо извлечь этот отрицательный знак 
}



class ArithmeticCounter extends Counter implements Cloneable  // Методами этого класса с помощью счетчиков будут выполнятся арифметические операции, через которых по библиотечному классу ‘Property’ будут составляться таблицы сложения, умножения, вычитания и деления описания класса ‘SystemNumerationWithTable’, соответственно данной системе счислению. То есть определение этого класса считается продолжением определения класса ‘Counter’ в плане функций, выполняющие простейшие арифметические операции над числами в текстовом представлении, являющийся показаниями двух счетчиков
{
	//--- C O N S T R U C T O R S ---
	ArithmeticCounter ( SystemNumeration system_numeration ) throws NullPointerException  { super ( system_numeration ); } 
	ArithmeticCounter ( String number ) throws IllegalArgumentException, NullPointerException  { super ( number ); }
	ArithmeticCounter ( String number, Integer numeration ) throws IllegalArgumentException, NullPointerException  { super ( number, numeration ); }
	ArithmeticCounter ( String number, SystemNumeration system_numeration ) throws IllegalArgumentException, NullPointerException  { super ( number, system_numeration ); }
	ArithmeticCounter ( )  { super ( ); }  // То есть создается счетчик по стандартной десятеричной системе с нулевым показанием
	ArithmeticCounter ( ArithmeticCounter counter ) throws NullPointerException  { super ( counter ); }
	
	//--- A R I T H M E T I C S ---
	// Продолжение определения функций описания этого класса выполняется по следующей логике – простейшая арифметическая операция выполняется над первым числом, с участием второго. Так, первое число представляет первый счетчик, а второе может представлять или еще один счетчик, или одно колесо. Этот первый счетчик в какой то среде, в не среды определения этого класса, создается по этому классу ‘ArithmeticCounter’, а действия по четырем арифметическим операциям выполняют четыре функции, список параметров которых состоит с единственного параметра, объявленный по интерфейсу ‘WheelInterface’.  В результате выполнения действий этих четырех функций аргумент подвергается изменений – показание счетчика-аргумента / колеса-аргумента аннулируется 
	ArithmeticCounter summary ( WheelInterface number ) throws NullPointerException  // Тут условие к тому, что аргумент не имел нулевое показание, в случае этот аргумент из себя представляет счетчик, затратно и не уместно, судя по тому, как он работает. По этому в выражении ‘if ( … )’ добавляется условие о том, что данный аргумент из себя представляет колесо
	{
		try { if ( ! number.isZero ( ) )  this.moveUpUntil ( number ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: summary ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		return this;
	}
	
	ArithmeticCounter multipling ( WheelInterface number ) throws NullPointerException
	{		
		WheelInterface this_copy = new ArithmeticCounter ( this );
		this.setZero ( );
		try
		{
			while ( ! number.isZero ( ) )
			{
				this.moveUpUntil ( this_copy );
				this_copy.reset ( );
				number.statementDown ( );
			}
			return this;			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: multipling ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	ArithmeticCounter subtraction ( WheelInterface number ) throws NullPointerException
	{
		try { if ( ! number.isZero ( ) )  this.moveDownUntil ( number ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: subtraction ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }  
		return this;
	}
	
	ArithmeticCounter dividion ( WheelInterface number ) throws NullPointerException  // Возвращаемое значение этой функции является текстовым представлением результата деление, числовой остаток выражается в показании данного счетчика.  
	{ 
		try { if ( number.isZero ( ) )  return null; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: dividion ( " + Colors.YELLOW + "'WheelInterface'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }  // В случае показание аргумента нулевое
		if ( this.isZero ( ) )  return this;  // В случае показание данного счетчика нулевое
		// Этот счетчик будет крутится в положительном направлении при каждом выполнении арифметической операции по вычитанию с данного числа число, определяющийся по параметру
		ArithmeticCounter result = null;
		try { result = new ArithmeticCounter ( this.getSystemNumeration ( ) ); }  catch ( NullPointerException ex )  { result = new ArithmeticCounter ( ); }
		// Показание данного счетчика убывается параллельно убыванию показания другого счетчика, при том, что после обретения нулевого показания изначальное показание другого счетчика восстанавливается. Это процедура продолжается до тех пор, пока показание данного счетчика не станет нулевым. И для того чтобы засечь его показание перед тем как оно станет нулевым, перед выполнением каждой процедуры это показание фиксируется с помощью текстового параметра
		String current_this = null;  // Этот тот текстовой параметр. То есть его значение в текстовом виде описывает остаток от деления
		while ( true )
		{
			current_this = this.toString ( );  // Это та фиксация текущего показания
			String start_number = number.toString ( );  // Это для восстановления показания того другого счетчика всякий раз при аннулировании его показания
			this.moveUntil ( ( value ) -> { this.statementDown ( ); value.statementDown ( ); return ( this.isZero ( )  ||  number.isZero ( ) ); }, number );  // Процедура должна продолжаться пока показание обеих счетчиков не нулевое
			if ( this.isZero ( )  &&  number.isZero ( ) )  
			{
				result.statementUp ( );
				current_this = this.toString ( );
				break;
			}
			else if ( number.isZero ( ) )  // И если нулевому показанию достиг не данный счетчик, результат деления становится на единицу больше
			{
				result.statementUp ( );
				number.setStatement ( start_number );  // Для повторении процедуры
			}
			if ( this.isZero ( ) )  break;  // То есть в случае данный счетчик обрел нулевое показание раньше другого счетчика, значит очередной раз текущее показание данного счетчика было меньше изначального показания другого счетчика - процедура по делению прекращается
		}
		this.setStatement ( current_this );
		// Остаток от деления вычисляется выражением 'this.toString ( )'
		return result;
	}
	
	ArithmeticCounter summOfAllSymbols ( )  // Вычисляется сумма всех цифр показания данного счетчика, и предписывается как показание возвращаемого счетчика
	{
		ArithmeticCounter summ = new ArithmeticCounter ( super.getSystemNumeration ( ) );
		this.collectionOverkill ( next_wheel -> { try { if ( ! next_wheel.isZero ( ) )  summ.moveUpUntil ( next_wheel.clone ( ) ); }  catch ( CloneNotSupportedException ex )  { /**/ } } );
		return summ;
	}
	
	ArithmeticCounter computeNumericalRoot ( )  // Вычисляется цифровой корень показания данного счетчика, и предписывается как показание возвращаемого счетчика
	{
		ArithmeticCounter numerical_root = this.summOfAllSymbols ( );
		do { numerical_root = numerical_root.summOfAllSymbols ( ); }  while ( numerical_root.getCounter ( ).size ( ) > 1 );
		return numerical_root;
	}
	
	ArithmeticCounter addLeftOne ( )  // Это функция вызывается при составлении таблицы вычитания - в случае с меньшего числа вычитается большее число, к этому меньшему числу предварительно плюсуется значение десять по данному системе счислению 
	{
		Wheel wheel = new Wheel ( 1, super.getSystemNumeration ( ) );
		this.counter.push ( wheel );			
		return this;
	}
	
	ArithmeticCounter addRightZero ( )  // Это функция вызывается при составлении таблицы вычитания - в случае с меньшего числа вычитается большее число, к этому меньшему числу предварительно плюсуется значение десять по данному системе счислению 
	{
		Wheel wheel = new Wheel ( super.getSystemNumeration ( ) );
		this.counter.insertElementAt ( wheel, 0 );
		return this;
	}	
	
	ArithmeticCounter transformBySystem ( SystemNumeration system_numeration ) throws NullPointerException  // Создается новый счетчик по иной системе счислению, указанный параметром, который обретает показание по тому иному системе счислению – идентичное показанию данного счетчика, при том, что показание данного счетчика аннулируется 
	{
		ArithmeticCounter counter = null;
		try { counter = new ArithmeticCounter ( system_numeration.ZERO, system_numeration ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: transformBySystem ( " + Colors.YELLOW + "'SystemNumeration'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		while ( ! this.isZero ( ) )
		{
			this.statementDown ( );
			counter.statementUp ( );
		}
		return counter;
	}
	
	//--- O B J E C T ---
	@Override
	public ArithmeticCounter clone ( ) throws CloneNotSupportedException  { return (ArithmeticCounter)super.clone ( ); }
	
	//--- S T R E A M S ---
	Stream<String> getStream ( String start_number, String end_number ) throws IllegalStateException, NullPointerException  // Действием этой функции возвращается поток всех показаний данного счетчика от значения, определяющийся первым параметром ДО значения, определяющийся вторым параметром. То есть в случае первое значение будет больше второй, то колеса счетчик будут двигаться в обратном направлении 
	{
		try  // Тут для проверки текущего показания счетчика вызывается функция ‘equals ( … )’ описания класса ‘String’, и по этому предварительно необходимом избавится от начальных нулевых составных цифр обеих чисел 
		{
			start_number = this.system_numeration.checkNumberFirstZeros ( start_number );
			end_number = this.system_numeration.checkNumberFirstZeros ( end_number );			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: getStream ( " + Colors.YELLOW + start_number + ", " + end_number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }

		boolean is_less = this.system_numeration.compareNumbers ( start_number, end_number ) <= 0;  // Значение этого элемента описывает направление движения колес данного счетчика
		this.setStatement ( start_number );
		Consumer<Boolean> lambda = ( value ) -> { if ( value )  this.statementUp ( );  else  this.statementDown ( ); };
		
		try 
		{
			Stream.Builder<String> stream = Stream.builder ( );
			while ( ! this.toString ( ).equals ( end_number ) )
			{
				stream.add ( this.toString ( ) );
				lambda.accept ( is_less );
			}
			stream.add ( this.toString ( ) );
			
			return stream.build ( );			
		}
		catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'ArithmeticCounter :: getStream ( " + Colors.YELLOW + start_number + ", " + end_number + Colors.WHITE + " )'  " + Colors.NOCOLOR ); }
	}
	
	Stream<String> getStreamUp ( String start_number, String end_number ) throws IllegalStateException, NullPointerException  // Действием этой функции возвращается поток всех показаний данного счетчика от значения, определяющийся первым параметром ДО значения, определяющийся вторым параметром. То есть в случае первое значение будет больше второй, то колеса счетчик будут двигаться в обратном направлении 
	{
		try  // Тут для проверки текущего показания счетчика вызывается функция ‘equals ( … )’ описания класса ‘String’, и по этому предварительно необходимом избавится от начальных нулевых составных цифр обеих чисел 
		{
			start_number = this.system_numeration.checkNumberFirstZeros ( start_number );
			end_number = this.system_numeration.checkNumberFirstZeros ( end_number );			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: getStreamUp ( " + Colors.YELLOW + start_number + ", " + end_number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }

		if ( this.system_numeration.compareNumbers ( start_number, end_number ) > 0 )  { out.println ( Colors.PURPURE + "A T T E N T I O N -- The returned stream by the function " + Colors.WHITE + "'ArithmeticCounter :: getStreamUp ( " + Colors.YELLOW + start_number + ", " + end_number + Colors.WHITE + " )'" + Colors.PURPURE + " is  E M P T Y " );  return Stream.empty ( ); }
		this.setStatement ( start_number );
		
		try
		{
			Stream.Builder<String> stream = Stream.builder ( );
			while ( ! this.toString ( ).equals ( end_number ) )  
			{
				stream.add ( this.toString ( ) );
				this.statementUp ( );
			}
			stream.add ( this.toString ( ) );
			
			return stream.build ( );			
		}
		catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'ArithmeticCounter :: getStreamUp ( " + Colors.YELLOW + start_number + ", " + end_number + Colors.WHITE + " )'  " + Colors.NOCOLOR ); }
	}
}



class CounterSingleWheel implements WheelInterface
{
	private SystemNumeration system_numeration;
	private TreeMap<Integer, WheelSingle> wheel = null;
	private String original_statement;
	
	//--- C O N S T R U C T O R S ---
	CounterSingleWheel ( String number ) throws NullPointerException  { this ( number, SystemNumeration.standart_system ); }
	
	CounterSingleWheel ( )  { this ( new SystemNumeration ( 10, 0 ) ); }
	
	CounterSingleWheel ( SystemNumeration system_numeration ) throws NullPointerException  { this ( system_numeration.getSymbol ( 0 ), system_numeration ); }
	
	CounterSingleWheel ( String number, Integer numeration ) throws NullPointerException  { this ( number, new SystemNumeration ( numeration ) ); }
	
	CounterSingleWheel ( String number, SystemNumeration system_numeration ) throws NullPointerException
	{
		if ( system_numeration == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'CounterSingleWheel ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
		this.system_numeration = system_numeration;
		this.wheel = new TreeMap ( );
		try { for ( int i = 0;  i < number.length ( );  ++ i )  this.wheel.put ( i, new WheelSingle ( i, this.system_numeration.symbol_map.get ( number.substring ( i, i + 1 ) ), system_numeration ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The first argument of the constructor " + Colors.WHITE + "'CounterSingleWheel ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		this.original_statement = number;
	}
	
	CounterSingleWheel ( CounterSingleWheel another_counter ) throws NullPointerException  { this ( another_counter.toString ( ), another_counter.getSystemNumeration ( ) ); }
	
	//--- S E T T E R S ---
	@Override
	public void setStatement ( String number ) throws NullPointerException
	{
		try { while ( this.wheel.size ( ) < number.length ( ) )  this.wheel.put ( this.wheel.size ( ), new WheelSingle ( this.wheel.size ( ), this.system_numeration ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'CounterSingleWheel :: setStatement ( " + Colors.YELLOW + number + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		while ( this.wheel.size ( ) > number.length ( ) )  this.wheel.remove ( ( this.wheel.size ( ) - 1 ) );
		for ( int i = 0;  i < number.length ( );  ++ i )  this.wheel.get ( i ).setIndex ( this.system_numeration.symbol_map.get ( number.substring ( i, i + 1 ) ) );
	}
		
	@Override
	public void setZero ( )  { this.wheel.keySet ( ).iterator ( ).forEachRemaining ( value -> this.wheel.get ( value ).setZero ( ) ); }
	
	@Override
	public void setMaxStatement ( )  { for ( int i = 0;  i < this.wheel.size ( );  ++ i )  this.wheel.get ( i ).setMaxStatement ( ); }
	
	@Override
	public void reset ( )  { this.wheel.keySet ( ).iterator ( ).forEachRemaining ( value -> this.wheel.get ( value ).reset ( ) ); }
	
	@Override
	public void setOriginalStatement ( String statement ) throws NullPointerException  { if ( statement == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'CounterSingleWheel :: setOriginalStatement ( " + Colors.YELLOW + statement + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );  this.original_statement = statement; }
	
	//--- G E T T E R S ---
	@Override
	public SystemNumeration getSystemNumeration ( )  { return this.system_numeration; }
	
	@Override
	public String getCurrentStatement ( )
	{
		StringBuilder built = new StringBuilder ( );
		this.wheel.keySet ( ).iterator ( ).forEachRemaining ( value -> built.append ( this.wheel.get ( value ).getCurrentStatement ( ) ) );
		return built.toString ( );
	}
	
	@Override
	public String getStartStatement ( ) 
	{
		StringBuilder built = new StringBuilder ( );
		this.wheel.keySet ( ).iterator ( ).forEachRemaining ( value -> built.append ( this.wheel.get ( value ).getStartStatement ( ) ) );
		return built.toString ( );
	}
	
	@Override
	public String getOriginalStatement ( )
	{
		StringBuilder built = new StringBuilder ( );
		this.wheel.keySet ( ).iterator ( ).forEachRemaining ( value -> built.append ( this.wheel.get ( value ).getOriginalStatement ( ) ) );
		return built.toString ( );
	}
	
	public TreeMap<Integer, WheelSingle> getWheel ( )  { return this.wheel; }
	
	//--- B O O L E A N S ---
	@Override
	public boolean isZero ( )  { int key = 0;  try { while ( true )  if ( ! this.wheel.get ( key ++ ).isZero ( ) )  break; }  catch ( NullPointerException ex )  { return true; }  return false; }
	
	public boolean isMaximum ( )  { int key = 0;  try { while ( true )  if ( ! this.wheel.get ( key ++ ).isMaximum ( ) )  break; }  catch ( NullPointerException ex )  { return true; }  return false; }
	
	//--- O B J E C T ---
	@Override
	public int hashCode ( )  { return Objects.hash ( this.system_numeration, this.wheel, this.original_statement ); }
	
	@Override
	public boolean equals ( Object obj ) throws NullPointerException
	{
		try { if ( ! this.getClass ( ).equals ( obj.getClass ( ) ) )  return false; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'CounterSingleWheel :: equals ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		CounterSingleWheel counter_obj = (CounterSingleWheel)obj;
		if ( ! this.system_numeration.equals ( counter_obj.getSystemNumeration ( ) ) )  return false;
		if ( ! this.getCurrentStatement ( ).equals ( counter_obj.getCurrentStatement ( ) ) )  return false;
		return true;
	}
	
	@Override
	public CounterSingleWheel clone ( ) throws CloneNotSupportedException
	{
		CounterSingleWheel obj_clone = null;
		obj_clone = (CounterSingleWheel)super.clone ( );  // Получение пока что не полноценная копия данного элемента-колеса
		obj_clone.wheel = new TreeMap<Integer, WheelSingle> ( );
		int index = 0;  try { while ( true )  obj_clone.wheel.put ( index, this.wheel.get ( index ) ); }  catch ( IndexOutOfBoundsException ex )  { }
		obj_clone.original_statement = this.original_statement;
		return obj_clone;
	} 
	
	@Override
	public String toString ( ) throws IllegalStateException
	{
		StringBuilder built = new StringBuilder ( );
		try { this.wheel.keySet ( ).stream ( ).forEach ( value -> { built.append ( this.wheel.get ( value ).getValue ( ) ); } ); }  catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'CounterSingleWheel :: toStrring ( )'  " + Colors.NOCOLOR ); }
		return built.toString ( );
	}
	
	@Override
	public int compareTo ( WheelInterface obj )
	{
		CounterSingleWheel wheel_obj = (CounterSingleWheel)obj;
		try { return this.getCurrentStatement ( ).compareTo ( wheel_obj.getCurrentStatement ( ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'CounterSingleWheel :: compareTo ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	//--- M O V I N G ---
	@Override
	public CounterSingleWheel statementUp ( )
	{
		int key = ( this.wheel.size ( ) - 1 );
		this.wheel.get ( key ).statementUp ( );
		try { while ( this.wheel.get ( key -- ).isZero ( ) )  this.wheel.get ( key ).statementUp ( ); }  catch ( NullPointerException ex )  { this.wheel.put ( this.wheel.size ( ), new WheelSingle ( this.wheel.size ( ), 0, this.system_numeration ) ); }
		return this;
	}
	
	@Override
	public CounterSingleWheel statementDown ( )
	{
		if ( this.isZero (  ) )  return this;
		int key = ( this.wheel.size ( ) - 1 );
		this.wheel.get ( key ).statementDown ( );
		try { while ( this.wheel.get ( key -- ).isMaximum ( ) )  this.wheel.get ( key ).statementDown ( ); }  catch ( NullPointerException ex )  { this.wheel.remove ( this.wheel.size ( ) ); }
		return this;
	}
	
	CounterSingleWheel statementUpUnique ( )  // Это функция действует в случае количество колес данного счетчика фиксировано.  Цель исполнения этой функции является составление все возможные уникальные группы цифр, имеющийся в определенном количестве ( то есть в это есть количество колес данного счетчика )
	{  // Логика исполнения состоит в том, что после того, как данное промежуточное колесо крутится на единицу, все колеса с право от нее устанавливаются на том же показании что и данное колесо обрело после своего кружения на единицу. Таким образом повторных групп из показаний колес НЕ возникает
		int key = ( this.wheel.size ( ) - 1 );
		try 
		{
			while ( this.wheel.get ( key -- ).isMaximum ( ) ) { }
			++ key;
			this.wheel.get ( key ).statementUp ( );
			try { while ( true )  this.wheel.get ( ++ key ).setStatement ( this.wheel.get ( ( key - 1 ) ).getCurrentStatement ( ) ); }  catch ( NullPointerException ex ) { }
		}
		catch ( NullPointerException ex )  {  }
		
		return this;
	}
	
	//--- O T H E R S ---
	@Override
	public String printFull ( )  
	{
		StringBuilder built = new StringBuilder ( );
		return built.toString ( );
	}

	Stream<Stream<String>> getStreamUniqueGroups ( Integer wheel_count ) throws IllegalArgumentException, NullPointerException
	{
		try { if ( wheel_count < 1 )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'CounterSingleWheel :: getStreamUniqueGroups ( " + Colors.YELLOW + wheel_count + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L -- It must be no less then 'one'  " + Colors.NOCOLOR ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: getStreamUniqueGroups ( " + Colors.YELLOW + "'Integer'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		StringBuilder built = new StringBuilder ( );
		for ( int i = 0;  i < wheel_count;  ++ i )  built.append ( this.system_numeration.ZERO );
		this.setStatement ( built.toString ( ) );
		Stream.Builder<Stream<String>> build = Stream.builder ( );
		build.add ( this.getStatementsStream ( ) );
		while ( ! this.toString ( ).equals ( this.statementUpUnique ( ).toString ( ) ) )  build.add ( this.getStatementsStream ( ) );
		return build.build ( );
	}
	
	Stream<String> getStatementsStream ( )
	{
		Stream.Builder<String> build = Stream.builder ( );
		int index = 0;
		try { while ( true )  build.add ( this.system_numeration.getSymbol ( this.wheel.get ( index ++ ).getCurrentStatement ( ) ) ); }  catch ( NullPointerException ex )  { }
		return build.build ( );
	}
}


/*
class TestCounters
{
    static SystemNumeration system = new SystemNumeration ( 16, 10 );
    static String original_statement = "123456", statement = "987654";
    static int count = 123456;
	
    static boolean testCounters ( WheelInterface counter )
    {
        if ( ! counter.toString ( ).equals ( original_statement ) )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        counter.setStatement ( statement );
        if ( ! counter.toString ( ).equals ( statement ) )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        counter.setZero ( );
        if ( ! counter.toString ( ).equals ( "000000" ) )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        for ( int i = 0;  i < count;  ++ i )  counter.statementUp ( );
        if ( ! counter.toString ( ).equals ( String.valueOf ( "01E240" ) ) )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        counter.reset ( );
        if ( ! counter.toString ( ).equals ( original_statement ) )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        counter.setStatement ( statement );
        if ( ! counter.toString ( ).equals ( statement ) )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        counter.reset ( );
        if ( system.compareNumbers ( counter.toString ( ), original_statement ) != 0 )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        for ( int i = count;  i > 0;  -- i )  counter.statementDown ( );
        if ( ! counter.toString ( ).equals ( "105216" ) )  { out.println ( new Throwable ( ).getStackTrace ( ) [ 0 ] );  return false; }
        out.println ( "By the class " + "\u001b[33m" + counter.getClass ( ).getName ( ) + "  \u001b[34mIt is OK !!!\u001b[0m" );
        return true;
    }
	
    public static void main ( String [ ] args )  throws IllegalArgumentException
    {
        testCounters ( new Counter ( original_statement, system ) );
        testCounters ( new CounterSingleWheel ( original_statement, system ) );

    }
}
*/
 