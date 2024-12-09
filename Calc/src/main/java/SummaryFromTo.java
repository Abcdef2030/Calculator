import static java.lang.System.out;


public class SummaryFromTo  // Тут выполняется операция ‘∑’, где формула дается с переменной ‘x’ – обязательно, в смысле иная буква будет считаться ошибкой. Вычисление выполняется во время создания элемента по этому классу, и для вывода результата необходимо просто печатать созданный элемент.
{
    String from = null;
    
	String to = null;
    
	String expression = null;
    
	ArithmeticTable system_numeration = null;
    
	Counter counter = null;
    
	String result = null;
    
	Device device = null;
    
	private void computeSummary ( String statement )
    {
        String formula = this.expression.replaceAll ( "x", statement );
        String new_expression = this.result.concat ( "+".concat ( formula ) );
        this.result = this.device.runCalculator ( new_expression );

        if ( statement.equals ( this.to ) )  return;  // Конец рекурсии

        computeSummary ( this.counter.statementUp ( ).toString ( ) );
    }
	
    SummaryFromTo ( String from, String to, String expression, ArithmeticTable system_numeration ) throws NullPointerException
    {
        try
        {
            this.system_numeration = system_numeration;
            if ( system_numeration.compareNumbers ( from, to ) <= 0 )
            {
                this.from = from;
                this.to = to;
            }
            else
            {
                this.from = to;
                this.to = from;
            }

            this.expression = expression;
            this.counter = new Counter ( from, system_numeration );
            this.result = system_numeration.ZERO;
            this.device = Device.getDevice ( system_numeration );

            this.computeSummary ( this.from );
        }
        catch ( NullPointerException ex )  { throw new IllegalArgumentException ( ex.getMessage ( ) + Colors.RED + "\nE X C E P T I O N -- The argument of the constructor " + Colors.WHITE + "'SummaryFromTo ( " + Colors.YELLOW + from + ", " + "to" + ", " + expression + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L -  " + Colors.WHITE + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
    }
    
	SummaryFromTo ( String from, String to, String expression, int numeration )  { this ( from, to, expression, new ArithmeticTable ( numeration ) ); }
    
	SummaryFromTo ( String from, String to, String expression )  { this ( from, to, expression, new ArithmeticTable ( 5 ) ); }
    
	SummaryFromTo ( String to, String expression )  { this ( "0", to, expression, new ArithmeticTable ( 5 ) ); }
    
	@Override
	public String toString ( )  { return this.result; }
    
    public static void main ( String [ ] args )
    {
        SummaryFromTo obj = new SummaryFromTo ( "5", "x ^ 2" );
        out.println ( obj );
        obj = new SummaryFromTo ( "5", "x ^ 2" );
        out.println ( obj );
        obj = new SummaryFromTo ( "5", "x ^ 2" );
        out.println ( obj );
    }
}
