import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.in;
import java.util.stream.Stream;
import java.util.function.UnaryOperator;
import java.util.HashMap;
import java.util.Objects;



public class FileOfPoints<T> implements Cloneable  // Это есть образ создания некой цепочки с элементов, созданные по классу ‘SeparatePoint’, с целью выполнить некий перебор этих элементов, вывода их значений, добавлений и удалений указанных элементов
{
    private SeparatePoint<T> start_point = null;  // Этот элемент является некой отметкой о начале цепочки, тем более если данная цепочка будет замкнутой
    private SeparatePoint<T> current_point = null;  // С помощью этого элемента будут выполняться перебор элементов данной цепочки, созданный по классу ‘FileOfPoints’. То есть этот элемент является своего рода указателем на текущий элемент с данной цепочки, в плане прицепить к нему новый элемент, или же удалить имеющийся после или перед ним иной элемент, или же выводить значение текущего элемента, а так же определить – является ли текущий элемент начальным
	private Integer numeration = 0;
	private SeparatePoint<T> original_statement = null;
	private HashMap<SeparatePoint<T>, Boolean> point_map = null;  // Это карта необходим для проверки данного элемента класса ‘SeparatePoint’ на предмет принадлежности к составу данного элемента класса ‘FileOfPoints’.  Булево значение составных элементов этой карты относятся к тому, что эти элементы были добавлены при создании данного элемента по классу ‘FileOfPoints’
    
	//--- C O N S T R U C T O R S ---
	FileOfPoints ( Stream<T> stream ) throws IllegalStateException, NullPointerException  // Этот конструктор строит цепочку с элементов в указанном количестве, значения которых бывают целочисленными, сквозными и начинаются с нуля
    {
		++ this.numeration;
		this.point_map = new HashMap ( );
        try { stream.forEach ( value -> this.addNew ( value ) ); }  
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the constructor " + Colors.WHITE + "'FileOfPoints ( " + Colors.YELLOW + "'Stream<T>'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the constructor " + Colors.WHITE + "'FileOfPoints ( " + Colors.YELLOW + "'Stream<T>'" + Colors.WHITE + " )'  " + Colors.NOCOLOR ); }
		this.original_statement = this.start_point;
		this.current_point = this.start_point;
    }
    
	FileOfPoints ( T [ ] array ) throws NullPointerException
    {
        try { for ( int index = 0;  index < array.length;  ++ index )  this.addNew ( array [ index ] ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the constructor " + Colors.WHITE + "'FileOfPoints ( " + Colors.YELLOW + "'T [ ]'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		this.original_statement = this.start_point;
    }
	
	FileOfPoints ( Stream<T> stream, T value ) throws IllegalArgumentException, IllegalStateException, NullPointerException
	{
		this ( stream );
		try { this.setOriginalStatement ( value ); }  
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'FileOfPoints ( " + Colors.YELLOW + value + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
		catch ( NullPointerException exc )  { throw new NullPointerException ( exc.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'FileOfPoints ( " + Colors.YELLOW + "'T'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	FileOfPoints ( T [ ] array, T value ) throws IllegalArgumentException
	{
		this ( array );
		try { this.setOriginalStatement ( value ); }  
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'FileOfPoints ( " + Colors.YELLOW + "'T [ ]'" + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
		catch ( NullPointerException exc )  { throw new NullPointerException ( exc.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The second argument of the constructor " + Colors.WHITE + "'FileOfPoints ( " + Colors.YELLOW + "'T [ ]'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	//--- S E T T E R S ---
	void setCurrent ( SeparatePoint<T> current_point ) throws IllegalArgumentException, NullPointerException 
	{
		if ( current_point == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: setCurrent ( " + Colors.YELLOW + current_point + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );  this.current_point = current_point;
		this.current_point = this.start_point;
		do
		{
			if ( this.current_point == current_point )  return;
			try { this.current_point = this.current_point.getNext ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "  When calling the function " + Colors.WHITE + " 'FileOfPoints :: setCurrent  ( " + Colors.YELLOW + current_point + Colors.WHITE + " )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
		}
		while ( this.current_point != this.start_point );
		throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: setCurrent ( " + Colors.YELLOW + current_point + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );
	}
	
	void setCurrentValue ( T value ) throws IllegalArgumentException, NullPointerException
	{
		if ( value == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'ArithmeticCounter :: setStatement ( " + Colors.YELLOW + "'T'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
		SeparatePoint<T> ptr = this.start_point;
		try { do { ptr = ptr.getNext ( ); }  while ( ptr != this.start_point  &&  ! ptr.getValue ( ).equals ( value ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "  When calling the function " + Colors.WHITE + " 'FileOfPoints :: setCurrentValue  ( " + Colors.YELLOW + value + Colors.WHITE + " )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
		if ( ptr.getValue ( ).equals ( value ) )  this.current_point = ptr;  else throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: setCurrentValue ( " + Colors.YELLOW + value + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );
	}
    
	void setCurrentNext ( ) throws NullPointerException  { try { this.current_point = this.current_point.getNext ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: setCurrentNext ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); } }
    
	void setCurrentPreview ( ) throws NullPointerException  { try { this.current_point = this.current_point.getPreview ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: setCurrentPreview ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); } }
    
	private void addNew ( T value ) throws NullPointerException  // Действием этого элемента после конечного элемента данной цепочки добавляется новый элемент, и на этом добавленном элементе цепочка замыкается с ее начальным элементом.  В случае данная цепочка пуста, добавляется начальный элемент, и нем цепочка замыкается.  
    {
		SeparatePoint<T> new_point = null;
        try { new_point = new SeparatePoint ( value ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: addNew ( " + Colors.YELLOW + "'T'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		SeparatePoint<T> temp_point = null;
		try { temp_point = this.start_point.getPreview ( ); }
		catch ( NullPointerException ex )
		{
			++ this.numeration;
			this.point_map = new HashMap ( );
			this.current_point = this.start_point = new_point;
			this.point_map.put ( new_point, true );
			this.enClose ( new_point );
			return;
		}
		temp_point.setNext ( new_point );
		new_point.setPreview ( temp_point );
		this.point_map.put ( new_point, false );
		this.enClose ( new_point );
		++ this.numeration;
    }
	
	void reset ( )  { this.current_point = this.original_statement; }
	
	void setOriginalStatement ( T value ) throws IllegalArgumentException, NullPointerException
	{
		this.original_statement = this.start_point;
		try { do { if ( this.original_statement.getValue ( ).equals ( value ) )  return;  this.original_statement = this.original_statement.getNext ( ); }  while ( this.original_statement != this.start_point ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "  When calling the function " + Colors.WHITE + " 'FileOfPoints :: setOriginalStatement  ( " + Colors.YELLOW + value + Colors.WHITE + " )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
		try { throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: setOriginalStatement ( " + Colors.YELLOW + value.toString ( ) + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: setOriginalStatement ( " + Colors.YELLOW + "'T'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
	}
	
	//--- G E T T E R S ---
    static FileOfPoints<Integer> getPointsByCount ( Integer count ) throws IllegalArgumentException, IllegalStateException, NullPointerException  // Действием этой функции с помощью конструктора ‘FileOfPoints ( Stream )’ создается и возвращается цепочка с элементами ‘SeparatePoint’ в указанном количестве.  В случае значение аргумента меньше единицы, создается пустая цепочка
    {
        Stream<Integer> stream = null;
		try { stream = Stream.iterate ( 0, UnaryOperatorPlus.identity ( ) ).takeWhile ( value -> value < count ); }  
		catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: getPointsByCount ( " + Colors.YELLOW + count + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
		catch ( IllegalStateException ex )  { throw new IllegalStateException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: getPointsByCount ( " + Colors.YELLOW + count + Colors.WHITE + " )'  " + Colors.NOCOLOR ); }
		return new FileOfPoints ( stream );
    }
	
    private interface UnaryOperatorPlus extends UnaryOperator<Integer>  { static UnaryOperatorPlus identity ( )  { return t -> t + 1; } }
    
	SeparatePoint<T> getStart ( )  { return this.start_point; }
	
	SeparatePoint<T> getEnd ( ) throws NullPointerException  { try { return this.start_point.getPreview ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: getEnd ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); } }
    
	SeparatePoint<T> getCurrent ( )  { return this.current_point; }
    
	T getNext ( ) throws NullPointerException
	{
		try { this.current_point.getNext ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: getNext ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }  
		try { return this.current_point.getValue ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + Colors.NOCOLOR + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: getNext ( )'   " + Colors.NOCOLOR  + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
	}
    
	T getPreview ( ) throws NullPointerException
	{
		try { this.current_point.getPreview ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: getPreview ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }  
		try { return this.current_point.getValue ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'FileOfPoints :: getPreview ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }  
	}
	
	Integer getNumeration ( )  { return this.numeration; }
	
	T getCurrentStatement ( )  { return this.current_point.getValue ( ); }
	
	T getStartStatement ( )  { return this.start_point.getValue ( ); }
	
	T getOriginalStatement ( )  { return this.original_statement.getValue ( ); }
	
	//--- O B J E C T ---
	@Override
	public int hashCode ( )  { return Objects.hash ( this.numeration, this.current_point, this.original_statement ); }
	
	@Override
	public boolean equals ( Object obj ) throws NullPointerException
	{
		if ( obj == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: equals ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
		FileOfPoints<T> another_obj = (FileOfPoints<T>)obj;
		SeparatePoint<T> ptr_1 = this.start_point,  ptr_2 = another_obj.getStart ( );
		do  
		{
			try { if ( ! ptr_1.equals ( ptr_2 ) )  return false; }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The element " + Colors.YELLOW + "'ptr_1' " + Colors.RED + " in the bothy of the function " + Colors.WHITE + " 'FileOfPoints :: equals  ( )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" ); }
			try { ptr_1 = ptr_1.getNext ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The element " + Colors.YELLOW + "'ptr_1' " + Colors.RED + " in the bothy of the function " + Colors.WHITE + " 'FileOfPoints :: equals  ( Object )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" ); }
			try { ptr_2 = ptr_2.getNext ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The element " + Colors.YELLOW + "'ptr_2' " + Colors.RED + " in the bothy of the function " + Colors.WHITE + " 'FileOfPoints :: equals  ( Object )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" ); }
		}
		while ( ptr_1 != this.start_point );
		return true;
	}
	
    @Override
    public FileOfPoints clone ( ) throws CloneNotSupportedException, NullPointerException
    {
        FileOfPoints obj_clone = null;
        obj_clone = (FileOfPoints)super.clone ( );
        SeparatePoint<T> ptr = this.start_point;
        try { obj_clone.start_point = new SeparatePoint ( this.start_point.getValue ( ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The element " + Colors.YELLOW + "'ptr' " + Colors.RED + " in the bothy of the function " + Colors.WHITE + " 'FileOfPoints :: clone  ( )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" ); }
		obj_clone.enClose ( obj_clone.start_point );
        obj_clone.current_point = obj_clone.start_point;
        try { ptr = ptr.getNext ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  When calling the function " + Colors.WHITE + "'FileOfPoints :: clone ( )'   " + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
        while ( ptr != this.start_point )
        {
            try { obj_clone.addNew ( ptr.getValue ( ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  When calling the function " + Colors.WHITE + "'FileOfPoints :: clone ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }  
            ptr = ptr.getNext ( );
        }
		obj_clone.numeration = this.numeration;
		obj_clone.setCurrentValue ( this.getCurrentStatement ( ) );  // Это процедура того, что клонированное колесо имел то же указание что и оригинал
        return obj_clone;
    }
    
	@Override
	public String toString ( ) throws NullPointerException
    {
        StringBuilder built = new StringBuilder ( );
		SeparatePoint<T> temp_point = this.start_point;
        do
        {
			if ( temp_point == this.original_statement )  { built.append ( Colors.BLUE );  built.append ( temp_point );  built.append ( " " + Colors.NOCOLOR ); }
			else if ( temp_point == this.current_point )  { built.append ( Colors.GREEN );  built.append ( temp_point );  built.append ( " " + Colors.NOCOLOR ); }
            else { built.append ( temp_point );  built.append ( " " ); }
            try { temp_point = temp_point.getNext ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The element " + Colors.YELLOW + "'temp_point' " + Colors.RED + " in the bothy of the function " + Colors.WHITE + " 'FileOfPoints :: toString  ( )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" ); }
        }
        while ( ! temp_point.equals ( this.start_point ) );
        return built.toString ( );
    }
	
	//--- O T H E R S ---	
    private void enClose ( SeparatePoint<T> end_point ) throws IllegalArgumentException  // Действием этой функции замыкается созданная цепочка точек
    {
		if ( this.point_map.get ( end_point ) == null )  throw new IllegalArgumentException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'FileOfPoints :: enClose ( " + Colors.YELLOW + end_point + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR );
        end_point.setNext ( this.start_point );
		this.start_point.setPreview ( end_point );
    }
}



class SeparatePoint<T extends Object> implements Cloneable  //  Это есть образ создания элемента, имеющий возможность оказаться в связки с элементами подобно себе, а так же иметь конструкционный элемент, носивший какое не будь значение, которое может принимать участие в каких не будь действиях ( в НЕ среде определения этого класса ). Возможность связки с элементами себе подобных имеется как правостороннее, так и левостороннее, и эти «себе подобные» элементы соответственно просматриваются как следующий и предыдущий. Этот образ составляется с целью определения еще одного образа, которым подобные элементы будучи в связке, станут полезными в качестве некой цепочки элементов, имеющие в своих конструкциях элементы, значения которых будут пригодными для всяких вычислений в выполнении большой программы.
{
    private T value = null;  // Это есть элемент, значение которого может участвовать в каких не будь действиях
    private SeparatePoint next = null;  // Это элемент, с помощью которого будет реализована правостаронняя связка с иным элементом класса 'SeparatePoint', или можно сказать - будет реализована связка со следующим элементом класса 'SeparatePoint'
    private SeparatePoint preview = null;  // ...
    
	//--- C O N S T R U C T O R S ---
	SeparatePoint ( T value ) throws NullPointerException  { if ( value == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the constructor " + Colors.WHITE + "'SeparatePoint ( " + Colors.YELLOW + value + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );  this.value = value; }
    
	//--- S E T T E R S ---
	void setNext ( SeparatePoint<T> next ) throws NullPointerException  { if ( next == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SeparatePoint :: setNext ( " + Colors.YELLOW + "'SeparatePoint<T>'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );  this.next = next; }
    
	void setPreview ( SeparatePoint<T> preview ) throws NullPointerException  { if ( preview == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SeparatePoint :: setPreview ( " + Colors.YELLOW + "'SeparatePoint<T>'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );  this.preview = preview; }
    
	//--- G E T T E R S ---
    T getValue ( )  { return this.value; }
    
	SeparatePoint<T> getNext ( )  { return this.next; }
    
	SeparatePoint<T> getPreview ( )  { return this.preview; }
	
	//--- O B J E C T ---
	@Override
	public int hashCode ( )  { return Objects.hash ( this.value ); }

    @Override
    public boolean equals ( Object obj ) throws NullPointerException
    {
        if ( obj == null )  throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SeparatePoint :: equals ( " + Colors.YELLOW + "'Object'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR );
        SeparatePoint<T> another_obj = (SeparatePoint<T>)obj;
        return this.value.equals ( another_obj.getValue ( ) );
    }
	
    @Override
    public SeparatePoint clone ( ) throws CloneNotSupportedException
    {
        SeparatePoint obj_clone = null;
        obj_clone = (SeparatePoint)super.clone ( );
		obj_clone.value = this.value;
        return obj_clone;
    }	
    
	@Override
    public String toString ( )  { return this.value.toString ( ); }
}