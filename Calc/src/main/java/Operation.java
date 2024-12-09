import static java.lang.System.out;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



public interface Operation  // Этот интерфейс необходим для объявления указательных элементов, являющийся составными элементами некой карты, указывающие на объекты, в конструкции которых имеется функциональный элемент, с одним и тем же наименованием, с целью выполнения однозначного вызова для решения простейших арифметических выражений. То есть имеется некая карта, ключевые значения элементов которого есть текстовые представления знаков арифметических операций ( по данному систему счислению ). Цель этой карты следующее - по ключевому значению вызывается один из его составных элементов, который имеет метод решения соответствующего простейшего арифметического выражения, при получении в качество двух аргументов текстовые представления двух чисел, выводимые с потока, созданный с помощью элемента ‘reader’ описания класса ‘Device’
{ 
	Operation factorial = new Factorial ( );
    Root root = new Root ( );  // Этот элемент объявляется по классу 'Root' что бы возможно было вызвать функцию 'lessRootOperation ( ... )' для него в НЕ среде определения класса 'Root'
    Rest rest = new Rest ( );
    Operation percent = new Percent ( );
    Operation pow = new Pow ( );
    Operation multiply = new Multiply ( );
    Operation divide = new Divide ( );
    Operation summary = new Summary ( );
    Subtract subtract = new Subtract ( );
    Operation point = new Point ( );
    // Логические операторы для обычных чисел
    Operation less = new Less ( );
    Operation less_or_even = new LessOrEven ( );
    Operation more = new More ( );
    Operation more_or_even = new MoreOrEven ( );
    Operation even = new Even ( );
    Operation not_even = new NotEven ( );
    // Эти элементы бывают нужны в нескольких средах определения классов, реализующие интерфейс ‘Operation’, что бы они НЕ были созданы многократно, они создаются в среде определения интерфейса ‘Operation’ как статистические
    Operation root_d = new Root_D ( );   Operation root_dn = new Root_DN ( );   Operation dn_root = new DN_Root ( );   Operation dn_root_d = new DN_Root_D ( );   Operation dn_root_dn = new DN_Root_DN ( );   Operation d_root = new D_Root ( );   Operation d_root_d = new D_Root_D ( );   Operation d_root_dn = new D_Root_DN ( );   Operation percent_d = new Percent_D ( );
    Operation percent_dn = new Percent_DN ( );   Operation dn_percent = new DN_Percent ( );   Operation dn_percent_d = new DN_Percent_D ( );   Operation dn_percent_dn = new DN_Percent_DN ( );   Operation d_percent = new D_Percent ( );   Operation d_percent_d = new D_Percent_D ( );   Operation d_percent_dn = new D_Percent_DN ( );   Operation pow_dn = new Pow_DN ( );   Operation dn_pow = new DN_Pow ( );   Operation dn_pow_d = new DN_Pow_D ( );
    Operation dn_pow_dn = new DN_Pow_DN ( );   Operation pow_d = new Pow_D ( );   Operation d_pow = new D_Pow ( );   Operation d_pow_d = new D_Pow_D ( );   Operation d_pow_dn = new D_Pow_DN ( );   Operation multiply_d = new Multiply_D ( );   Operation multiply_dn = new Multiply_DN ( );   Operation dn_multiply = new DN_Multiply ( );   Operation d_multiply = new D_Multiply ( );   Operation d_multiply_dn = new D_Multiply_DN ( );   Operation divide_d = new Divide_D ( );
    Operation divide_dn = new Divide_DN ( );   Operation dn_divide = new DN_Divide ( );   Operation dn_divide_d = new DN_Divide_D ( );   Operation dn_divide_dn = new DN_Divide_DN ( );   Operation d_divide = new D_Divide ( );   Operation d_divide_d = new D_Divide_D ( );   Operation d_divide_dn = new D_Divide_DN ( );   Operation less_or_even_d = new LessOrEven_D ( );   Operation less_or_even_minus = new LessOrEvenMinus ( );    Operation minus_less_or_even = new MinusLessOrEven ( );
    Operation less_d = new Less_D ( );   Operation less_minus = new LessMinus ( );    Operation minus_less = new MinusLess ( );   Operation minus_more_oreven = new MinusMoreOrEven ( );   Operation more_or_even_d = new MoreOrEven_D ( );   Operation more_or_even_minus = new MoreOrEvenMinus ( );    Operation minus_more = new MinusMore ( );   Operation more_d = new More_D ( );   Operation more_minus = new MoreMinus ( );    Operation even_d = new Even_D ( );
    Operation even_minus = new EvenMinus ( );    Operation minus_even = new MinusEven ( );   Operation minus_even_d_minus = new MinusEvenMinus_D ( );   Operation minus_not_even_d_minus = new MinusNotEvenMinus_D ( );   Operation not_even_d = new NotEven_D ( );   Operation not_even_minus = new NotEvenMinus ( );
	
    public static void fullMap ( ) throws NullPointerException  // По действию этой функции создается некая коллекция, в состав которой входят те вышеописанные массивные элементы, созданные для всех определенных классов, реализующие интерфейс ‘Operation’.  Смысл создания подобной коллекции есть – доступность элементов, созданные по всем класса, со своими регулярными выражениями, в среде определения класса ‘Device’, а так же путем заведения цикла заполнить коллекционный элемент ‘map_operation’ описания того же класса
    {  
		try 
		{
			factorial.putOperations ( );   new Root ( ).putOperations ( );   new Rest ( ).putOperations ( );   percent.putOperations ( );   pow.putOperations ( );   multiply.putOperations ( );   divide.putOperations ( );   summary.putOperations ( );   
			subtract.putOperations ( );   less.putOperations ( );   less_or_even.putOperations ( );   more.putOperations ( );   more_or_even.putOperations ( );   even.putOperations ( );   not_even.putOperations ( );   point.putOperations ( );   
			new RootMinus ( ).putOperations ( );   new PowMinus ( ).putOperations ( );   new MultiplyMinus ( ).putOperations ( );   new DivideMinus ( ).putOperations ( );   minus_less.putOperations ( );   minus_less_or_even.putOperations ( );   minus_more.putOperations ( );   minus_more_oreven.putOperations ( );   
			minus_even.putOperations ( );   new MinusNotEven ( ).putOperations ( );   less_minus.putOperations ( );   less_or_even_minus.putOperations ( );   more_minus.putOperations ( );   more_or_even_minus.putOperations ( );   even_minus.putOperations ( );   not_even_minus.putOperations ( );   
			new MinusLessMinus ( ).putOperations ( );   new MinusLessOrEvenMinus ( ).putOperations ( );   new MinusMoreMinus ( ).putOperations ( );   new MinusMoreOrEvenMinus ( ).putOperations ( );   new MinusEvenMinus ( ).putOperations ( );   new MinusNotEvenMinus ( ).putOperations ( );   new D_Factorial ( ).putOperations ( );   new DN_Factorial ( ).putOperations ( );   
			root_d.putOperations ( );   root_dn.putOperations ( );   d_root.putOperations ( );   dn_root.putOperations ( );   d_root_d.putOperations ( );   dn_root_dn.putOperations ( );   d_root_dn.putOperations ( );   dn_root_d.putOperations ( );   
			new MinusRootMinus ( ).putOperations ( );   new MinusPowMinus ( ).putOperations ( );   new MinusMultiplyMinus ( ).putOperations ( );   new MinusDivideMinus ( ).putOperations ( );   new Rest_D ( ).putOperations ( );   new Rest_DN ( ).putOperations ( );   new D_Rest ( ).putOperations ( );   new DN_Rest ( ).putOperations ( );   
			new D_Rest_D ( ).putOperations ( );   new DN_Rest_DN ( ).putOperations ( );   new D_Rest_DN ( ).putOperations ( );   new DN_Rest_D ( ).putOperations ( );   percent_d .putOperations ( );   percent_dn.putOperations ( );   d_percent .putOperations ( );   dn_percent .putOperations ( );   
			d_percent_d .putOperations ( );   dn_percent_dn .putOperations ( );   d_percent_dn .putOperations ( );   dn_percent_d .putOperations ( );    pow_d.putOperations ( );   pow_dn .putOperations ( );   d_pow .putOperations ( );   dn_pow .putOperations ( );   
			d_pow_d.putOperations ( );   dn_pow_dn .putOperations ( );   d_pow_dn .putOperations ( );   dn_pow_d .putOperations ( );   multiply_d .putOperations ( );   multiply_dn .putOperations ( );   d_multiply .putOperations ( );   dn_multiply .putOperations ( );   
			new D_Multiply_D ( ).putOperations ( );   new DN_Multiply_DN ( ).putOperations ( );   d_multiply_dn.putOperations ( );   new DN_Multiply_D ( ).putOperations ( );   divide_d .putOperations ( );   divide_dn .putOperations ( );   d_divide .putOperations ( );   dn_divide .putOperations ( );   
			d_divide_d .putOperations ( );   dn_divide_dn  .putOperations ( );   d_divide_dn .putOperations ( );   dn_divide_d .putOperations ( );   new Subtract_D ( ).putOperations ( );   new Subtract_DN ( ).putOperations ( );   new D_Subtract ( ).putOperations ( );   new DN_Subtract ( ).putOperations ( );   
			new D_Subtract_D ( ).putOperations ( );   new DN_Subtract_DN ( ).putOperations ( );   new D_Subtract_DN ( ).putOperations ( );   new DN_Subtract_D ( ).putOperations ( );   new Summary_D ( ).putOperations ( );   new Summary_DN ( ).putOperations ( );   new D_Summary ( ).putOperations ( );   new DN_Summary ( ).putOperations ( );   
			new D_Summary_D ( ).putOperations ( );   new DN_Summary_DN ( ).putOperations ( );   new D_Summary_DN ( ).putOperations ( );   new DN_Summary_D ( ).putOperations ( );   new Point_D ( ).putOperations ( );   new Point_DN ( ).putOperations ( );   less_d.putOperations ( );   less_or_even_d.putOperations ( );   
			more_d.putOperations ( );   more_or_even_d.putOperations ( );   even_d.putOperations ( );   not_even_d.putOperations ( );   new Less_DN ( ).putOperations ( );   new LessOrEven_DN ( ).putOperations ( );   new More_DN ( ).putOperations ( );   new MoreOrEven_DN ( ).putOperations ( );   
			new Even_DN ( ).putOperations ( );   new NotEven_DN ( ).putOperations ( );   new D_Less ( ).putOperations ( );   new D_LessOrEven ( ).putOperations ( );   new D_More ( ).putOperations ( );   new D_MoreOrEven ( ).putOperations ( );   new D_Even ( ).putOperations ( );   new D_NotEven ( ).putOperations ( );   
			new DN_Less ( ).putOperations ( );   new DN_LessOrEven ( ).putOperations ( );   new DN_More ( ).putOperations ( );   new DN_MoreOrEven ( ).putOperations ( );   new DN_Even ( ).putOperations ( );   new DN_NotEven ( ).putOperations ( );   new D_Less_D ( ).putOperations ( );   new D_LessOrEven_D ( ).putOperations ( );   
			new D_More_D ( ).putOperations ( );   new D_MoreOrEven_D ( ).putOperations ( );   new D_Even_D ( ).putOperations ( );   new D_NotEven_D ( ).putOperations ( );   new D_Less_DN ( ).putOperations ( );   new D_LessOrEven_DN ( ).putOperations ( );   new D_More_DN ( ).putOperations ( );   new D_MoreOrEven_DN ( ).putOperations ( );   
			new D_Even_DN ( ).putOperations ( );   new D_NotEven_DN ( ).putOperations ( );   new DN_Less_D ( ).putOperations ( );   new DN_LessOrEven_D ( ).putOperations ( );   new DN_More_D ( ).putOperations ( );   new DN_MoreOrEven_D ( ).putOperations ( );   new DN_Even_D ( ).putOperations ( );   new DN_NotEven_D ( ).putOperations ( );   
			new DN_Less_DN ( ).putOperations ( );   new DN_LessOrEven_DN ( ).putOperations ( );   new DN_More_DN ( ).putOperations ( );   new DN_MoreOrEven_DN ( ).putOperations ( );   new DN_Even_DN ( ).putOperations ( );   new DN_NotEven_DN ( ).putOperations ( );   new RootMinus_D ( ).putOperations ( );   new RootMinus_DN ( ).putOperations ( );   
			new D_RootMinus ( ).putOperations ( );   new DN_RootMinus ( ).putOperations ( );   new D_RootMinus_D ( ).putOperations ( );   new DN_RootMinus_DN ( ).putOperations ( );   new D_RootMinus_DN ( ).putOperations ( );   new DN_RootMinus_D ( ).putOperations ( );   new PercentMinus_D ( ).putOperations ( );   new PercentMinus_DN ( ).putOperations ( );   
			new D_PercentMinus ( ).putOperations ( );   new DN_PercentMinus ( ).putOperations ( );   new D_PercentMinus_D ( ).putOperations ( );   new DN_PercentMinus_DN ( ).putOperations ( );   new D_PercentMinus_DN ( ).putOperations ( );   new DN_PercentMinus_D ( ).putOperations ( );   new PowMinus_D ( ).putOperations ( );   new PowMinus_DN ( ).putOperations ( );   
			new D_PowMinus ( ).putOperations ( );   new DN_PowMinus ( ).putOperations ( );   new D_PowMinus_D ( ).putOperations ( );   new DN_PowMinus_DN ( ).putOperations ( );   new D_PowMinus_DN ( ).putOperations ( );   new DN_PowMinus_D ( ).putOperations ( );   new MultiplyMinus_D ( ).putOperations ( );   new MultiplyMinus_DN ( ).putOperations ( );   
			new D_MultiplyMinus ( ).putOperations ( );   new DN_MultiplyMinus ( ).putOperations ( );   new D_MultiplyMinus_D ( ).putOperations ( );   new DN_MultiplyMinus_DN ( ).putOperations ( );   new D_MultiplyMinus_DN ( ).putOperations ( );   new DN_MultiplyMinus_D ( ).putOperations ( );   new DivideMinus_D ( ).putOperations ( );   new DivideMinus_DN ( ).putOperations ( );   
			new D_DivideMinus ( ).putOperations ( );   new DN_DivideMinus ( ).putOperations ( );   new D_DivideMinus_D ( ).putOperations ( );   new DN_DivideMinus_DN ( ).putOperations ( );   new D_DivideMinus_DN ( ).putOperations ( );   new DN_DivideMinus_D ( ).putOperations ( );   new PointMinus_D ( ).putOperations ( );   new PointMinus_DN ( ).putOperations ( );   
			new MinusRootMinus_D ( ).putOperations ( );   new MinusRootMinus_DN ( ).putOperations ( );   new MinusD_RootMinus ( ).putOperations ( );   new MinusDN_RootMinus ( ).putOperations ( );   new MinusD_RootMinus_D ( ).putOperations ( );   new MinusDN_RootMinus_DN ( ).putOperations ( );   new MinusD_RootMinus_DN ( ).putOperations ( );   new MinusDN_RootMinus_D ( ).putOperations ( );   
			new MinusPercentMinus_D ( ).putOperations ( );   new MinusPercentMinus_DN ( ).putOperations ( );   new MinusD_PercentMinus ( ).putOperations ( );   new MinusDN_PercentMinus ( ).putOperations ( );   new MinusD_PercentMinus_D ( ).putOperations ( );   new MinusDN_PercentMinus_DN ( ).putOperations ( );   new MinusD_PercentMinus_DN ( ).putOperations ( );   new MinusDN_PercentMinus_D ( ).putOperations ( );   
			new MinusPowMinus_D ( ).putOperations ( );   new MinusPowMinus_DN ( ).putOperations ( );   new MinusD_PowMinus ( ).putOperations ( );   new MinusDN_PowMinus ( ).putOperations ( );   new MinusD_PowMinus_D ( ).putOperations ( );   new MinusDN_PowMinus_DN ( ).putOperations ( );   new MinusD_PowMinus_DN ( ).putOperations ( );   new MinusDN_PowMinus_D ( ).putOperations ( );   
			new MinusMultiplyMinus_D ( ).putOperations ( );   new MinusMultiplyMinus_DN ( ).putOperations ( );   new MinusD_MultiplyMinus ( ).putOperations ( );   new MinusDN_MultiplyMinus ( ).putOperations ( );   new MinusD_MultiplyMinus_D ( ).putOperations ( );   new MinusDN_MultiplyMinus_DN ( ).putOperations ( );   new MinusD_MultiplyMinus_DN ( ).putOperations ( );   new MinusDN_MultiplyMinus_D ( ).putOperations ( );   
			new MinusDivideMinus_D ( ).putOperations ( );   new MinusDivideMinus_DN ( ).putOperations ( );   new MinusD_DivideMinus ( ).putOperations ( );   new MinusDN_DivideMinus ( ).putOperations ( );   new MinusD_DivideMinus_D ( ).putOperations ( );   new MinusDN_DivideMinus_DN ( ).putOperations ( );   new MinusD_DivideMinus_DN ( ).putOperations ( );   new MinusDN_DivideMinus_D ( ).putOperations ( );
			new MinusLess_D ( ).putOperations ( );   new MinusLessOrEven_D ( ).putOperations ( );   new MinusMore_D ( ).putOperations ( );   new MinusMoreOrEven_D ( ).putOperations ( );   new MinusEven_D ( ).putOperations ( );   new MinusNotEven_D ( ).putOperations ( );   new MinusLess_DN ( ).putOperations ( );   new MinusLessOrEven_DN ( ).putOperations ( );   
			new MinusMore_DN ( ).putOperations ( );   new MinusMoreOrEven_DN ( ).putOperations ( );   new MinusEven_DN ( ).putOperations ( );   new MinusNotEven_DN ( ).putOperations ( );   new MinusD_Less ( ).putOperations ( );   new MinusD_LessOrEven ( ).putOperations ( );   new MinusD_More ( ).putOperations ( );   new MinusD_MoreOrEven ( ).putOperations ( );   
			new MinusD_Even ( ).putOperations ( );   new MinusD_NotEven ( ).putOperations ( );   new MinusDN_Less ( ).putOperations ( );   new MinusDN_LessOrEven ( ).putOperations ( );   new MinusDN_More ( ).putOperations ( );   new MinusDN_MoreOrEven ( ).putOperations ( );   new MinusDN_Even ( ).putOperations ( );   new MinusDN_NotEven ( ).putOperations ( );   
			new MinusD_Less_D ( ).putOperations ( );   new MinusD_LessOrEven_D ( ).putOperations ( );   new MinusD_More_D ( ).putOperations ( );   new MinusD_MoreOrEven_D ( ).putOperations ( );   new MinusD_Even_D ( ).putOperations ( );   new MinusD_NotEven_D ( ).putOperations ( );   new MinusD_Less_DN ( ).putOperations ( );   new MinusD_LessOrEven_DN ( ).putOperations ( );   
			new MinusD_More_DN ( ).putOperations ( );   new MinusD_MoreOrEven_DN ( ).putOperations ( );   new MinusD_Even_DN ( ).putOperations ( );   new MinusD_NotEven_DN ( ).putOperations ( );   new MinusDN_Less_D ( ).putOperations ( );   new MinusDN_LessOrEven_D ( ).putOperations ( );   new MinusDN_More_D ( ).putOperations ( );   new MinusDN_MoreOrEven_D ( ).putOperations ( );   
			new MinusDN_Even_D ( ).putOperations ( );   new MinusDN_NotEven_D ( ).putOperations ( );   new MinusDN_Less_DN ( ).putOperations ( );   new MinusDN_LessOrEven_DN ( ).putOperations ( );   new MinusDN_More_DN ( ).putOperations ( );   new MinusDN_MoreOrEven_DN ( ).putOperations ( );   new MinusDN_Even_DN ( ).putOperations ( );   new MinusDN_NotEven_DN ( ).putOperations ( );   
			new LessMinus_D ( ).putOperations ( );   new LessOrEvenMinus_D ( ).putOperations ( );   new MoreMinus_D ( ).putOperations ( );   new MoreOrEvenMinus_D ( ).putOperations ( );   new EvenMinus_D ( ).putOperations ( );   new NotEvenMinus_D ( ).putOperations ( );   new LessMinus_DN ( ).putOperations ( );   new LessOrEvenMinus_DN ( ).putOperations ( );   
			new MoreMinus_DN ( ).putOperations ( );   new MoreOrEvenMinus_DN ( ).putOperations ( );   new EvenMinus_DN ( ).putOperations ( );   new NotEvenMinus_DN ( ).putOperations ( );   new D_LessMinus ( ).putOperations ( );   new D_LessOrEvenMinus ( ).putOperations ( );   new D_MoreMinus ( ).putOperations ( );   new D_MoreOrEvenMinus ( ).putOperations ( );   
			new D_EvenMinus ( ).putOperations ( );   new D_NotEvenMinus ( ).putOperations ( );   new DN_LessMinus ( ).putOperations ( );   new DN_LessOrEvenMinus ( ).putOperations ( );   new DN_MoreMinus ( ).putOperations ( );   new DN_MoreOrEvenMinus ( ).putOperations ( );   new DN_EvenMinus ( ).putOperations ( );   new DN_NotEvenMinus ( ).putOperations ( );   
			new D_LessMinus_D ( ).putOperations ( );   new D_LessOrEvenMinus_D ( ).putOperations ( );   new D_MoreMinus_D ( ).putOperations ( );   new D_MoreOrEvenMinus_D ( ).putOperations ( );   new D_EvenMinus_D ( ).putOperations ( );   new D_NotEvenMinus_D ( ).putOperations ( );   new D_LessMinus_DN ( ).putOperations ( );   new D_LessOrEvenMinus_DN ( ).putOperations ( );   
			new D_MoreMinus_DN ( ).putOperations ( );   new D_MoreOrEvenMinus_DN ( ).putOperations ( );   new D_EvenMinus_DN ( ).putOperations ( );   new D_NotEvenMinus_DN ( ).putOperations ( );   new DN_LessMinus_D ( ).putOperations ( );   new DN_LessOrEvenMinus_D ( ).putOperations ( );   new DN_MoreMinus_D ( ).putOperations ( );   new DN_MoreOrEvenMinus_D ( ).putOperations ( );   
			new DN_EvenMinus_D ( ).putOperations ( );   new DN_NotEvenMinus_D ( ).putOperations ( );   new DN_LessMinus_DN ( ).putOperations ( );   new DN_LessOrEvenMinus_DN ( ).putOperations ( );   new DN_MoreMinus_DN ( ).putOperations ( );   new DN_MoreOrEvenMinus_DN ( ).putOperations ( );   new DN_EvenMinus_DN ( ).putOperations ( );   new DN_NotEvenMinus_DN ( ).putOperations ( );   
			new MinusLessMinus_D ( ).putOperations ( );   new MinusLessOrEvenMinus_D ( ).putOperations ( );   new MinusMoreMinus_D ( ).putOperations ( );   new MinusMoreOrEvenMinus_D ( ).putOperations ( );   minus_even_d_minus.putOperations ( );   minus_not_even_d_minus.putOperations ( );   new MinusLessMinus_DN ( ).putOperations ( );   new MinusLessOrEvenMinus_DN ( ).putOperations ( );   
			new MinusMoreMinus_DN ( ).putOperations ( );   new MinusMoreOrEvenMinus_DN ( ).putOperations ( );   new MinusEvenMinus_DN ( ).putOperations ( );   new MinusNotEvenMinus_DN ( ).putOperations ( );   new MinusD_LessMinus ( ).putOperations ( );   new MinusD_LessOrEvenMinus ( ).putOperations ( );   new MinusD_MoreMinus ( ).putOperations ( );   new MinusD_MoreOrEvenMinus ( ).putOperations ( );   
			new MinusD_EvenMinus ( ).putOperations ( );   new MinusD_NotEvenMinus ( ).putOperations ( );   new MinusDN_LessMinus ( ).putOperations ( );   new MinusDN_LessOrEvenMinus ( ).putOperations ( );   new MinusDN_MoreMinus ( ).putOperations ( );   new MinusDN_MoreOrEvenMinus ( ).putOperations ( );   new MinusDN_EvenMinus ( ).putOperations ( );   new MinusDN_NotEvenMinus ( ).putOperations ( );   
			new MinusD_LessMinus_D ( ).putOperations ( );   new MinusD_LessOrEvenMinus_D ( ).putOperations ( );   new MinusD_MoreMinus_D ( ).putOperations ( );   new MinusD_MoreOrEvenMinus_D ( ).putOperations ( );   new MinusD_EvenMinus_D ( ).putOperations ( );   new MinusD_NotEvenMinus_D ( ).putOperations ( );   new MinusD_LessMinus_DN ( ).putOperations ( );   new MinusD_LessOrEvenMinus_DN ( ).putOperations ( );   
			new MinusD_MoreMinus_DN ( ).putOperations ( );   new MinusD_MoreOrEvenMinus_DN ( ).putOperations ( );   new MinusD_EvenMinus_DN ( ).putOperations ( );   new MinusD_NotEvenMinus_DN ( ).putOperations ( );   new MinusDN_LessMinus_D ( ).putOperations ( );   new MinusDN_LessOrEvenMinus_D ( ).putOperations ( );   new MinusDN_MoreMinus_D ( ).putOperations ( );   new MinusDN_MoreOrEvenMinus_D ( ).putOperations ( );   
			new MinusDN_EvenMinus_D ( ).putOperations ( );   new MinusDN_NotEvenMinus_D ( ).putOperations ( );   new MinusDN_LessMinus_DN ( ).putOperations ( );   new MinusDN_LessOrEvenMinus_DN ( ).putOperations ( );   new MinusDN_MoreMinus_DN ( ).putOperations ( );   new MinusDN_MoreOrEvenMinus_DN ( ).putOperations ( );   new MinusDN_EvenMinus_DN ( ).putOperations ( );   new MinusDN_NotEvenMinus_DN ( ).putOperations ( );   			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" ); }
    }	
	
	default void putOperations ( ) throws NullPointerException  { try { Device.map_operation.put ( this.getKey ( ), this ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( ex.getMessage ( ) + "  -- When try to call the function " + Colors.WHITE + "'Operation :: putOperations ( )'" + Colors.RED + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" ); } }  // Каждый очередной созданный элемент по классу, реализующий интерфейс ‘Operation’, на месте создания по вызову этой функции кладется в состав того карточного  элемента ‘map_operation’
	
    default String getKey ( )  // Это функция вызывается для вычисления ключевых значений для карточного элемента ‘map_operation’ описания класса ‘Device’, в котором собраны объекты по всем классам, реализующие интерфейс ‘Operation’. То есть Эти ключевые значения вычисляются с наименования данного класса
    {
        String class_name = this.getClass ( ).getName ( );

        class_name = class_name.replaceAll ( "Point", SystemNumeration.POINT );
        class_name = class_name.replaceAll ( "Minus", SystemNumeration.MINUS );
        class_name = class_name.replaceAll ( "DN_", SystemNumeration.DEGREE_NEGATIVE );
        class_name = class_name.replaceAll ( "_DN", SystemNumeration.DEGREE_NEGATIVE );
        class_name = class_name.replaceAll ( "D_", SystemNumeration.DEGREE );
        class_name = class_name.replaceAll ( "_D", SystemNumeration.DEGREE );
        class_name = class_name.replaceAll ( "Factorial", SystemNumeration.FACTORIAL );
        class_name = class_name.replaceAll ( "Root", SystemNumeration.ROOT );
        class_name = class_name.replaceAll ( "Rest", SystemNumeration.REST );
        class_name = class_name.replaceAll ( "Percent", SystemNumeration.PERSENT );
        class_name = class_name.replaceAll ( "Pow", SystemNumeration.POWER );
        class_name = class_name.replaceAll ( "Multiply", SystemNumeration.MULTIPLY );
        class_name = class_name.replaceAll ( "Divide", SystemNumeration.DIVIDE );
        class_name = class_name.replaceAll ( "Summary", SystemNumeration.PLUS );
        class_name = class_name.replaceAll ( "Subtract", SystemNumeration.MINUS );
        class_name = class_name.replaceAll ( "Point", SystemNumeration.POINT );
        class_name = class_name.replaceAll ( "Less", SystemNumeration.LESS );
        class_name = class_name.replaceAll ( "More", SystemNumeration.MORE );
        class_name = class_name.replaceAll ( "NotEven", SystemNumeration.NOTEVEN );
        class_name = class_name.replaceAll ( "Even", SystemNumeration.EVEN );
        class_name = class_name.replaceAll ( "Or", "" );
        class_name = class_name.replaceAll ( "LotsDegree", "lots_degree" );
        return class_name;
    }

    public String arithmeticOperation ( String value_1, String value_2, ArithmeticTable system_numeration,  boolean print_calling ) throws NullPointerException;
	
    static Iterator<String> makeListOfSymbols ( String number ) throws NullPointerException  // Действием этой функции создается лист, заполненный составными цифрами данного числа, и возвращает итератор на этот лист
    {
        List<String> list = new LinkedList ( );
        try { for ( int index = number.length ( ) - 1;  index > -1;  -- index )  list.add ( String.valueOf ( number.charAt ( index ) ) ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: makeListOfSymbols ( " + Colors.YELLOW + "'String'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        return list.listIterator ( );
    }

    static String [ ] addAFewZeros ( String number_1, String number_2, SystemNumeration system_numeration ) throws NullPointerException  // Перед тем как с одного числа вычитать другое число необходимо эти числа подготовить к этой операции в двух смыслах:  1) выяснить наименьший из них, с целю вычитать именно ее  2) в случае этот «наименьший» состоит с цифр, по количеству меньше составных цифр первого числа, с ее начало добавить символы с нулевым обозначением в соответствующим количестве.  Подготовка заключается в составлении массивного элемента с трех текстовых значений, первые два из которых представляют данные два числа в нужном порядке вычитания, а третий - в случае первое меньше второго – носит НЕ нулевое значение, что означает – результат вычитания будет отрицательным
    {
        try { while ( number_2.length ( ) < number_1.length ( ) )  number_2 = system_numeration.ZERO.concat ( number_2 );  while ( number_1.length ( ) < number_2.length ( ) )  number_1 = system_numeration.ZERO.concat ( number_1 ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: addAFewZeros ( " + Colors.YELLOW + number_1 + ", " + number_2 + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }  // Выравниваются количество составных цифр двух чисел - изначально цифр составных цифр меньше или в первом числе, или же во втором
        String str_array [ ] = { number_1, number_2, null };  // Изначально предполагается, что второе число меньше первого, следовательно результат вычитания будет положительным
        if ( system_numeration.compareNumbers ( number_1, number_2 ) < 0 ) { str_array [ 0 ] = number_2;  str_array [ 1 ] = number_1;  str_array [ 2 ] = SystemNumeration.MINUS; }
        return str_array;
    }

    static String [ ] getAllParts ( String number, SystemNumeration system_numeration ) throws NullPointerException  // Составляется массивный элемент с трех элементов, первый и третий из которых носят целую и десяточную часть данного числа в текстовых представлениях, а второй носит знак этой десяточной части, то есть – ‘DEGREE’ или ‘DEGREE_NEGATIVE‘
    {
		String [ ] str_array = null;
        try { str_array = number.split ( SystemNumeration.DEGREE + "|" + SystemNumeration.DEGREE_NEGATIVE ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The first argument of the function " + Colors.WHITE + "'Operation :: getAllParts ( " + Colors.YELLOW + "'String', " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }  // Это есть массив целой и десяточной частей данного числа, то есть в его составе по любому будет две текстовые представления чисел, в смысле – если данное число НЕ будет иметь десяточной части, то возвращаемое значение будет пустым ( ‘null’ ).
        String [ ] array_str = new String [ 3 ];  // Это тот массив с тремя элементами
        int index = 0;  // Предварительно рассматривается случае, если данное число будет отрицательным, и в таком случае набор целого и десяточного частей данного числа будет начат со второго элемента выводимого функцией ‘split ( … )’ описания класса ‘String’ текстового массива
        if ( SystemNumeration.isNegative ( number ) )  index = 1;  // В случае данное число отрицательное, перебор элементов массива ‘str_array’ начинается со второго элемента ( под индекс ‘1’ )
        try
        {
            array_str [ 0 ] = str_array [ index ];  // Первый элемент есть целая часть данного числа
            array_str [ 1 ] = ( number.indexOf ( SystemNumeration.DEGREE ) != -1 ? SystemNumeration.DEGREE : number.indexOf ( SystemNumeration.DEGREE_NEGATIVE ) != -1 ? SystemNumeration.DEGREE_NEGATIVE : SystemNumeration.POINT );  // Второй элемент выясняется через тернарный оператор – он будет либо знак положительной десяточной степени, либо отрицательной, либо будет дробным знаком
            array_str [ 2 ] = str_array [ index + 1 ];  // Третий элемент есть либо десяточная степень, либо дробная часть данного числа
        }
        catch ( ArrayIndexOutOfBoundsException ex )  // В случае данное число есть целое ( НЕ имеет ни десяточной степени, ни дробной части ), возникает исключение на вызов второго элемента массива ‘array_str’ создается массив с одним элементом, во избежание возникновения исключений по нулевому указателю
        {
            array_str [ 0 ] = str_array [ 0 ];
            array_str [ 1 ] = SystemNumeration.DEGREE;
            try { array_str [ 2 ] = system_numeration.ZERO; }  catch ( NullPointerException exc )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The second argument of the function " + Colors.WHITE + "'Operation :: getAllParts ( " + Colors.YELLOW + number + ", " + "'SystemNumeration'" + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        }
        return array_str;
    }

    static String getFullInteger ( String number, SystemNumeration system_numeration ) throws NullPointerException  // По исполнению этой функции возвращается число, являющийся полноценным целым представлением данного числа, в смысле с конца данного числа добавляются символы с нулевым обозначением, или же удаляются цифры в соответствующем количестве
    {
		try 
		{
			String [ ] str_array = str_array = number.split ( SystemNumeration.DEGREE );  // В случае данное число имеет положительную десяточную часть, этот массивный элемент будет состоять с двух элементов – текстовых представлений целой и десяточной части
			StringBuilder built = new StringBuilder ( str_array [ 0 ] );  // Этот элемент создается с целью составления текстового представления полной целой части данного числа, с учетом ее десяточной части
			try
			{
				Counter counter = counter = new Counter ( str_array [ 1 ], system_numeration );  // Этот счетчик имеется для того, что бы стало возможно подсчет количество символов с нулевыми обозначениями, добавляемые с конца данного числа для составления ее полной целой части, соответственно ее десяточной части
				while ( ! counter.isZero ( ) )  // Цикл убавления данного счетчика, сопровождающийся добавлениями символов с нулевыми обозначениями к данному числу
				{
					counter.statementDown ( );
					built.append ( system_numeration.ZERO );
				}
			}
			catch ( ArrayIndexOutOfBoundsException ex )  // Это исключение возникает в случае данное число НЕ имеет десяточной части – в этом случае в составе массива ‘str_array’ НЕ имеется второго элемента под индексом ‘1’
			{
				if ( number.indexOf ( SystemNumeration.DEGREE_NEGATIVE ) != -1 )  return Operation.getFullIntegerWhenNegative ( number, system_numeration );
				else return number;
			}
			return built.toString ( );			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: getFullInteger ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    static String getFullIntegerWhenNegative ( String number, SystemNumeration system_numeration ) throws NullPointerException  // По исполнению этой функции возвращается число, являющийся полноценным целым представлением данного числа, с отрицательной десяточной частью
    {
        String [ ] str_array = str_array = number.split ( SystemNumeration.DEGREE_NEGATIVE );  // Подобно выражении в теле предыдущей функции
		try 
		{
			StringBuilder built = null;
			try
			{
				built = new StringBuilder ( str_array [ 0 ] );  // Подобно выражении в теле предыдущей функции
				Counter counter = new Counter ( str_array [ 1 ], system_numeration );  // Подобно выражении в теле предыдущей функции
				while ( ! counter.isZero ( ) )  // Подобно выражении в теле предыдущей функции
				{
					counter.statementDown ( );
					try { built.delete ( built.length ( ) - 1, built.length ( ) ); }  catch ( StringIndexOutOfBoundsException ex ) { return system_numeration.ZERO; }
				}
			}
			catch ( ArrayIndexOutOfBoundsException ex )
			{
				if ( number.indexOf ( SystemNumeration.DEGREE ) != -1 )
				{
					out.println ( Colors.AZURE + "A T T E N T I O N  -- The argument of the function 'Operation.getFullIntegerWhenNegative ( " + number + " )' will be given to the function 'Operation.getFullInteger ( ... )' \u001B[0m" );
					return Operation.getFullInteger ( number, system_numeration );
				}
				else out.println ( "\u001B[31m E X C E P T I O N -- The argument of the function 'Operation.getFullIntegerWhenNegative ( " + number + " )' is  I N C O R R E C T " );
				return null;
			}

			return built.toString ( );			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: getFullIntegerWhenNegative ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    static String multiplyByTenAFewTimes ( String number_1, String number_2, StringBuilder built, ArithmeticTable system_numeration ) throws NullPointerException  // Логика в том, что в случае с большого числа вычитается маленькое число ( по количествам составных цифр ), это «маленькое» число может вместится в то большое число десять или несколько десяток раз, и в место многократного выполнения операции вычитания с целью вычисления результата на деления, в место того маленького числа вычитаться будет то же число, с конца которой в соответствующем количестве предварительно были добавлены символы с нулевым обозначением
    {
        built = new StringBuilder ( );
        try { for ( int count = 1;  count < number_1.length ( ) - number_2.length ( );  ++ count )  built.append ( system_numeration.ZERO ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: multiplyByTenAFewTimes ( " + Colors.YELLOW + number_1 + ", " + number_2 + ", " + built + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        number_2 = number_2.concat ( built.toString ( ) );
        return number_2;
    }

    static String optimazeByTenPow ( String number, SystemNumeration system_numeration ) throws NullPointerException  // Это функция вызывается с текстовым аргументом, представляющее число, имеющее конечные символы с нулевыми обозначениями – хоть с десяточной частью, хоть без десяточной части. Исполнение этой функции заключается в том, что эти конечные символы данного числа с нулевым обозначением убираются с последующей модернизацией ее десяточной части
    {
        boolean minus;
		Counter counter_end_zeros = null;  // Это счетчик создается для подсчета количество конечных символов данного числа, с нулевыми обозначениями
		try 
		{
			minus = number.indexOf ( SystemNumeration.MINUS ) != -1;
			counter_end_zeros = new Counter ( system_numeration );
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: optimazeByTenPow ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
        number = number.replaceFirst ( SystemNumeration.MINUS, "" );
        Counter counter_degree = null;  // Этот счетчик будет создан с показанием, описывающее десяточную часть данного числа
        String [ ] str_array = number.split ( "\\W" );  // Данное число – в случае имеет десяточную часть – разбирается на две части – первая – это целая часть, а вторая – это степень десяточной части
        try { counter_degree = new Counter ( str_array [ 1 ], system_numeration ); }  catch ( ArrayIndexOutOfBoundsException ex ) { }  // Счетчик, показание которого описывает десяточную часть данного числа, создается в случае это число имеет десяточную часть

        StringBuilder built = new StringBuilder ( str_array [ 0 ] );  // Постройка этого объекта в конечном итоге будет из себя представлять результат оптимизации – работы этой функции. То есть к этому элементу под конец исполнения функции будет прибавлен знак десяточной части, а так же сама десяточная часть
        char ch_zero = system_numeration.ZERO.charAt ( 0 );  // Этот элемент с подобным значением инициализируется с целью избежание многократного вызова функции ‘charAt ( 0 )’ описания класса ‘String’
        while ( built.charAt ( built.length ( ) - 1 ) == ch_zero  &&  built.length ( ) > 1 )  // Это есть циклическая процедура избавления конечных символов данного числа, с нулевым обозначением
        {
            counter_end_zeros.statementUp ( );  // При избавлении этих конечных символов с нулевым обозначением, введется их подсчет
            built.delete ( built.length ( ) - 1, built.length ( ) );
        }
        String degree_symbol = SystemNumeration.DEGREE;  // Знак десяточной части будет вычислен по тому, что это десяточная часть в итоге может быть положительной или же отрицательной
        String degree = null;  // Этот элемент представляет итоговый степень десяточной части
        if ( number.indexOf ( SystemNumeration.DEGREE ) != -1 )  // Случае, когда десяточная часть данного числа есть положительное
        {
            while ( ! counter_end_zeros.isZero ( ) )  //  Циклическая процедура подсчета итоговой степени положительной десяточной степени
            {
                counter_end_zeros.statementDown ( );
                counter_degree.statementUp ( );
            }
            degree = counter_degree.toString ( );
        }
        else if ( number.indexOf ( SystemNumeration.DEGREE_NEGATIVE ) != -1 )  // Случае, когда десяточная часть данного числа есть отрицательное
        {
            while ( ! counter_end_zeros.isZero ( )  &&  ! counter_degree.isZero ( ) )  // Циклическая процедура подсчета итоговой степени отрицательной десяточной степени
            {
                counter_end_zeros.statementDown ( );
                counter_degree.statementDown ( );
            }
            if ( counter_end_zeros.isZero ( ) ) { degree_symbol = SystemNumeration.DEGREE_NEGATIVE;  degree = counter_degree.toString ( ); }  else if ( counter_degree.isZero ( ) )  degree = counter_end_zeros.toString ( );  // То есть знак десяточной степени а так же сама десяточная степень вычисляется зависимо от того – десяточная степень данного числа больше или меньше количеств конечных символов, с нулевыми обозначениями этого же числа
        }
        else  { degree = counter_end_zeros.toString ( ); }  // В случае данное число НЕ имеет десяточную степень, ее итоговая десяточная степень просто ровняется количеству конечных символов с нулевыми обозначениями, данного числа

        if ( ! degree.equals ( system_numeration.ZERO ) )  // Тут десяточная степень прибавляется к конструкции итогового числа, в случае она НЕ нулевая
        {
            built.append ( degree_symbol );  // Тут знак десяточной степени вставляется повторно по формату, введенной при составлении данной программы
            built.append ( degree );
        }
        if ( minus )  built.insert ( 0, SystemNumeration.MINUS );

        return built.toString ( );
    }

    static String makePowByTenFromPoint ( String number, ArithmeticTable system_numeration ) throws NullPointerException  // На основании данного числа с дробной частью составляется целое число с десяточной степенью, соответствующее количество цифр дробной части
    {  
		try
		{
			if ( number.indexOf ( SystemNumeration.POINT ) == -1 )  return number;  // В случае в составе данного числа не имеется дробный символ, действие функции завершается
			else if ( system_numeration.isZero ( number ) )  return system_numeration.ZERO;  // В случае данное число имеет нулевое значение, действие функции завершается			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: makePowByTenFromPoint ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }

        // Следующие три элемента понадобятся в случае данное дробное число так же будет иметь некую десяточную часть со степенью.  Конкретно они нужны для того, что бы подобное число разбирать на простое дробное число и на десяточную часть со степенью
        int degree_index = 0;  // Этот элемент будет нести индекс соответствующего составного знака этого числа
        String degree_symbol = null;  // А этот элемент в том же случае будет нести это знак, с обозначением степени десяточной части
        String degree = null;  // А этот элемент будет нести значение степени десяточной части в текстовом представлении

        // Процедура обработки данного числа, в случае оно имеет так же некую степень десяточной части
        if ( ( degree_index = number.indexOf ( SystemNumeration.DEGREE ) ) != -1  ||  ( degree_index = number.indexOf ( SystemNumeration.DEGREE_NEGATIVE ) ) != -1 )
        {
            degree = number.substring ( degree_index, number.length ( ) );
            number = number.substring ( 0, degree_index );  // Этим выражением после сохранения степени десяточной части со своим знаком, данное число сокращается до простого дробного числа. В конце выполнения функции для составления окончательной степени десяточной части, будет учтен это сохранение
            degree_symbol = degree.substring ( 0, 1 );
            degree = degree.replaceAll ( SystemNumeration.DEGREE + "|" + SystemNumeration.DEGREE_NEGATIVE, "" );
        }
        number = system_numeration.checkNumberFirstZeros ( number );

        String ten_pow = SystemNumeration.DEGREE_NEGATIVE.concat ( String.valueOf ( number.substring ( number.indexOf ( SystemNumeration.POINT ) + 1, number.length ( ) ).length ( ) ) );  // Составляется текстовое представление десяточной степени данного дробного числа на основании количество цифр после дробного знака
        number =  number.replaceFirst ( SystemNumeration.POINT, "" );  // Составляется представление НЕ дробного числа на основании данного дробного числа, то есть – вынимается дробный знак
        if ( degree_symbol != null )  ten_pow = Operation.computeDegree ( ten_pow, degree_symbol.concat ( degree ), "multiply", system_numeration );  // Это процедура будет выполнена в случае данное число не имеет десятую часть со степенью

        return number.concat ( ten_pow ).concat ( ten_pow.substring ( 0, 1 ) );
    }

    static String computeDegreeSubtract ( String degree_1, String degree_2, ArithmeticTable system_numeration ) throws NullPointerException  // Исполнение этой функции заключается в том, что вычисляется итоговая степень десяточной части при имеющийся степени, определяющийся по первому параметру и по заданной степени в описании интерфейса ‘Operation’
    {
        String difference = null;
		try
		{
			if ( system_numeration.compareNumbers ( degree_1, degree_2 ) > 0 )
			{  //  Вычитается со степени десяточной части данного числа
				difference = Operation.subtract.arithmeticOperation ( degree_1, degree_2, system_numeration, false );
				return ( SystemNumeration.DEGREE.concat ( difference ) );
			}
			else if ( system_numeration.compareNumbers ( degree_1, degree_2 ) < 0 )
			{
				difference = Operation.subtract.arithmeticOperation ( degree_2, degree_1, system_numeration, false );
				return ( SystemNumeration.DEGREE_NEGATIVE.concat ( difference ) );
			}
			else return "";			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: computeDegreeSubtract ( " + Colors.YELLOW + degree_1 + ", " + degree_2 + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    static String addZeros ( String number_1, String degree, ArithmeticTable system_numeration ) throws NullPointerException  // По действию этой функции составляется число, начинающийся с цифры, определяющийся по первому параметру, с последующими нулями, в количестве, определяющийся по второму параметру
    {
		try
		{
			Counter counter = new Counter ( degree, system_numeration );
			while ( ! counter.isZero ( ) )
			{
				counter.statementDown ( );
				number_1 = number_1.concat ( system_numeration.ZERO );
			}
			return number_1;			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: addZeros ( " + Colors.YELLOW + number_1 + ", " + degree + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    static String rootOfTenPow ( String number, String degree, ArithmeticTable system_numeration )  // Это функция понадобится для вычисления результата операции корня со степенью, определяющийся по первому параметру, от единичного числа, имеющий степень десяточной части, определяющийся по второму параметру
    {
		try
		{
			String in_pow = Operation.rest.arithmeticOperation ( degree, number, system_numeration, false );
			int temp_ciphers_count = system_numeration.getCiphersCount ( );
			system_numeration.setCiphersCount ( 0 );
			String out_pow = Operation.divide.arithmeticOperation ( degree, number, system_numeration, false );
			system_numeration.setCiphersCount ( temp_ciphers_count );
			String number_part_2 = Operation.addZeros ( system_numeration.ONE, in_pow, system_numeration );
			number_part_2 = Operation.root.arithmeticOperation ( number, number_part_2, system_numeration, false );
			number_part_2 = Operation.makePowByTenFromPoint ( number_part_2, system_numeration );
			String [ ] str_array = Operation.getAllParts ( number_part_2, system_numeration );
			degree = Operation.subtract.arithmeticOperation ( str_array [ 2 ], out_pow, system_numeration, false );
			String degree_symbol = SystemNumeration.DEGREE_NEGATIVE;
			if ( SystemNumeration.isNegative ( degree ) ) { degree = degree.replaceFirst ( SystemNumeration.MINUS, "" );  degree_symbol = SystemNumeration.DEGREE; }
			String result = str_array [ 0 ].concat ( degree_symbol.concat ( degree ).concat ( degree_symbol ) );
			return result;			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: rootOfTenPow ( " + Colors.YELLOW + number + ", " + degree + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }

    }

    static String computeDegree ( String degree_1, String degree_2, String operation_expression/*'multiply' or 'divide'*/, ArithmeticTable system_numeration )
    {
		try
		{
			String [ ] str_array_1 = new String [ 2 ],  str_array_2 = new String [ 2 ];
			str_array_1 [ 0 ] = degree_1.substring ( 0, 1 );  str_array_1 [ 1 ] = degree_1.substring ( 1, degree_1.length ( ) );
			str_array_2 [ 0 ] = degree_2.substring ( 0, 1 );  str_array_2 [ 1 ] = degree_2.substring ( 1, degree_2.length ( ) );
			String degree = null, degree_symbol = SystemNumeration.DEGREE;
			Operation operation = Operation.summary;
			String first_degree = str_array_1 [ 1 ],  second_degree = str_array_2 [ 1 ];

			switch ( operation_expression )
			{
				case "multiply" :
				{
					if ( str_array_1 [ 0 ].equals ( str_array_2 [ 0 ] ) )  degree_symbol = str_array_1 [ 0 ];
					else
					{
						operation = Operation.subtract;
						if ( ( str_array_1 [ 0 ].equals ( SystemNumeration.DEGREE )  &&  system_numeration.compareNumbers ( str_array_1 [ 1 ], str_array_2 [ 1 ] ) < 0 )  ||  ( str_array_1 [ 0 ].equals ( SystemNumeration.DEGREE_NEGATIVE )  &&  system_numeration.compareNumbers ( str_array_2 [ 1 ], str_array_1 [ 1 ] ) < 0 ) )  degree_symbol = SystemNumeration.DEGREE_NEGATIVE;
					}
					break;
				}
				case "divide" :
				{
					if ( ! str_array_1 [ 0 ].equals ( str_array_2 [ 0 ] ) )  degree_symbol = str_array_1 [ 0 ];
					else
					{
						operation = Operation.subtract;
						if ( ( system_numeration.compareNumbers ( str_array_1 [ 1 ], str_array_2 [ 1 ] ) < 0  &&  str_array_2 [ 0 ].equals ( SystemNumeration.DEGREE ) )  ||  ( system_numeration.compareNumbers ( str_array_2 [ 1 ], str_array_1 [ 1 ] ) < 0  &&  str_array_1 [ 0 ].equals ( SystemNumeration.DEGREE_NEGATIVE ) ) )  degree_symbol = SystemNumeration.DEGREE_NEGATIVE;
					}
					break;
				}
				default : { out.println ( "\u001B[35m A T T E N T I O N __ The thired argument of the function 'Operation.computeDegree ( " + degree_1 + ", " + degree_2 + ", " + operation_expression + " )' at the line " + new Throwable ( ).getStackTrace ( ) [ 0 ] + " is incorrect." );  return null; }
			}

			degree = operation.arithmeticOperation ( str_array_1 [ 1 ], str_array_2 [ 1 ], system_numeration, false );
			if ( degree_symbol == null  &&  SystemNumeration.isNegative ( degree ) )  degree_symbol = SystemNumeration.DEGREE_NEGATIVE;  else if ( degree_symbol == null )  degree_symbol = SystemNumeration.DEGREE;
			degree = degree.replaceFirst ( SystemNumeration.MINUS, "" );

			return degree_symbol.concat ( degree );			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: computeDegree ( " + Colors.YELLOW + degree_1 + ", " + degree_2 + ", " + operation_expression + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }

    static String reunitNeighboringTenPow ( String number, ArithmeticTable system_numeration )  // По скольку функция описания класса ‘Divide’ в случае первое число меньше второго, возвращает результат с двумя десяточными частями, потому что в таком случае начальные цифры результата бывают нулевыми и они НЕ вписываются, возникает необходимость эти два десяточные части со степенями воссоединить в одно. Действием этой функции является нахождение подобных итоговых результатов операции деления на число с десяточной частью со степенью и воссоединить вышеупомянутые два десяточные части со степенями
    {
		try
		{
			String regex = ( "(" + SystemNumeration.DEGREE + "|" + SystemNumeration.DEGREE_NEGATIVE + ")" ).concat ( system_numeration.NUMBER );
			Pattern pattern = Pattern.compile ( regex );
			Matcher matcher = pattern.matcher ( number );
			String [ ] ten_pow_array = new String [ 2 ];
			ten_pow_array [ 1 ] = SystemNumeration.DEGREE.concat ( system_numeration.ZERO );
			int index = 0;
			try { while ( matcher.find ( ) )  ten_pow_array [ index ++ ] = number.substring ( matcher.start ( ), matcher.end ( ) ); }  catch ( ArrayIndexOutOfBoundsException ex ) { out.println ( "\u001B[31m E X C E P T I O N -- " + ex.getMessage ( ) + " on line " + new Throwable ( ).getStackTrace ( ) [ 0 ] + " \u001B[0m" ); }
			number = number.split ( SystemNumeration.DEGREE + "|" + SystemNumeration.DEGREE_NEGATIVE, 2 ) [ 0 ].concat ( Operation.computeDegree ( ten_pow_array [ 0 ], ten_pow_array [ 1 ], "multiply", system_numeration ) );
			return number;			
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + ex.getMessage ( ) + "  " + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: reunitNeighboringTenPow ( " + Colors.YELLOW + number + ", " + system_numeration + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
    }
	
	static String aroundNumber ( String number, int index, SystemNumeration system_numeration ) throws NullPointerException  // Тут значение второго параметра относится к индексу составного цифра данного числа, до которого оно будет округлена, и в случае данное число является дробным, то при определении этой цифры «до которого», составной дробный символ (‘запятая’) не считается, по скольку дробный символ во время вычисления округленного числа временно удаляется. Для того, что бы округление было выполнено до дробного символа данного по первому параметру числа, второй аргумент должен нести значение '-1'
	{
		StringBuilder built = null;  // С помощью этого элемента будет собрано округленное число данного по первому параметру числа
		try
		{
			int around_index = index;  // Индекс составной цифры, до которого данной число будет округлено, фиксируется, по скольку в случае данный по второму параметру индекс ровен ‘-1’, ( это есть случае, когда округление будет выполнено до дробного символа данного числа ), значение второго параметра остается НЕ изменным, а индексное значение дробного составного символа данного числа будет определено
			int point_index = number.indexOf ( SystemNumeration.POINT );  // В случае данное число будет дробным
			if ( index == -1  ||  around_index == point_index  ||  index == point_index - 1 )  { around_index = point_index - 1;  index = -1; }  // В случае значение второго аргумента ровно ‘-1’, или же ровно индексному значению дробного составного символа данного числа, или же ровно индексному значению составного цифра, предшествующего дробный составной символ данного числа, округление будет выполнено до составного цифра, предшествующая составной дробный символ данного числа
			String around_number = number.replaceFirst ( SystemNumeration.POINT, "" );  // С состава данного числа удаляется дробный символ, с целью создания счетчика, составные колеса которого будут нести показания составных цифр данного числа, начиная с начальной цифры, до указанного по второму параметру цифры. То есть перед созданием счетчика с вышеупомянутым показанием, учитывается, что значение второго аргумента может указать на составную цифру, оказавшийся НЕ непосредственно после составного дробного символа данного числа ( а может оказаться еще где угодно )
			if ( index + 1 == around_number.length ( ) )  return number;  // В случае значение второго аргумента НЕ соответствует ни одному из составных цифр ( или же составному дробному символу ) данного числа, возвращается данное первым параметром число, без выполнения действии по округлению
			Counter counter = new Counter ( around_number.substring ( 0, around_index + 1 ), system_numeration );  // Этот счетчик создается с целью выполнения увеличения указанного по второму параметру под-числа данного числа на единицу, в качестве округления данного по первому параметру числа, в случае это действие по округлению считается логичным
			if ( system_numeration.numeration_array.length % 2 == 0 )  // Вообще округление данного числа до данной составной цифры выполняется по среднему по значению цифры данной системы счисления. Тут рассматриваются две случае – 1) данная система счисления имеет четное количество цифр.  2) данная система счисления имеет НЕ четное количество цифр. В первом случае если последующие цифра данной цифре, определяющийся по второму параметру, ровны среднему по значению цифре данной системы счисления, для выяснения округления данного числа перебираются следующие составные цифра, пока НЕ найдется цифра, по значению больше или меньше цифры, являющийся по значению средней в данной системе счисления. 
			{
				int current_index = around_index + 1;
				try
				{
					while ( around_number.substring ( current_index, current_index + 1 ).equals ( system_numeration.numeration_array [ system_numeration.numeration_array.length / 2 ] ) ) { ++ current_index; }
					if ( system_numeration.compareNumbers ( around_number.substring ( current_index, current_index + 1 ), system_numeration.numeration_array [ system_numeration.numeration_array.length / 2 ] ) > 0 )  counter.statementUp ( );
				}
				catch ( StringIndexOutOfBoundsException ex )  { counter.statementUp ( ); }  // Исключение возникает, в случае все последующие цифра, данному по второму параметру цифра по значению являются средним в данной системе счисления. В этом случае округление логично выполнить путем прибавления единицы к данному под-числу, определяющийся по второму параметру, на основании первого параметра, и являющийся показанием данного созданного счетчика.
			}
			else  try { if ( system_numeration.symbol_map.get ( around_number.substring ( index + 1, index + 2 ) ) > system_numeration.numeration_array.length / 2 )  counter.statementUp ( ); }  catch ( StringIndexOutOfBoundsException ex ) { }		
			built = new StringBuilder ( counter.toString ( ) );
			try { if ( index != -1 )  built.insert ( point_index, SystemNumeration.POINT ); }  catch ( StringIndexOutOfBoundsException exc ) { }  // В случае данное по первому параметру число было дробным, и ее округлить необходимо было до составной цифры, занимающая в составе данного числа позицию после дробного символа, дробный символ необходимо вернуть на свою позицию в составе округленного числа данного числа. Но возможно случае, когда составная цифра данного числа, до которой это число будет округлено, в составе данного числа занимает позицию после дробного символа, и тогда вернуть дробный символ в состав уже округленного числа будет НЕ возможно. Поэтому в любом случае выполняется попытка возврата дробного символа на свою позицию в составе округленного числа, если данное по первому параметру число был дробным.
		}
		catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'SystemNumeration :: aroundNumber ( " + Colors.YELLOW + number + ", " + index + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		catch ( StringIndexOutOfBoundsException exc )  { return number; }  // Это исключение возникнет, в случае значение второго аргумента НЕ будет соответствовать к позициям ни одного из составных цифр данного по первому параметру числа
		return built.toString ( );
	}
	
	static String transformationFromToSystem ( String number, SystemNumeration system_numeration_1, SystemNumeration system_numeration_2 ) throws IllegalArgumentException, NullPointerException
	{
		ArithmeticCounter counter = null;
		try { counter = new ArithmeticCounter ( number, system_numeration_1 ); }  
		catch ( IllegalArgumentException ex )  { throw new IllegalArgumentException ( Colors.RED + ex.getMessage ( ) + "   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- The argument of the function " + Colors.WHITE + "'Operation :: transformationFromToSystem ( " + Colors.YELLOW + number + ", " + system_numeration_1 + ", " + system_numeration_2 + Colors.WHITE + " )'" + Colors.RED + " is  I L L E G A L  " + Colors.NOCOLOR ); }
		catch ( NullPointerException exc )  { throw new IllegalArgumentException ( Colors.RED + exc.getMessage ( ) + "   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] + "\n" + Colors.RED + "E X C E P T I O N -- One of the arguments of the function " + Colors.WHITE + "'Operation :: transformationFromToSystem ( " + Colors.YELLOW + number + ", " + system_numeration_1 + ", " + system_numeration_2 + Colors.WHITE + " )'" + Colors.RED + " is  N U L L  " + Colors.NOCOLOR ); }
		ArithmeticCounter counter_transfomed = counter.transformBySystem ( system_numeration_2 );
		return counter_transfomed.toString ( );
	}
	
	public static String transformationFromToSystem ( String number, int numeration_1, int numeration_2 )  { return Operation.transformationFromToSystem ( number, new SystemNumeration ( numeration_1 ), new SystemNumeration ( numeration_2 ) ); }
	
	default void testingClass ( ) { }  //  В описании каждого класса имеется определение этой функции для тестирования данного класса. В этой среде она определяется для того, что бы в случае со среды определения какого не будь класса его переопределение было убрано, тестирование прошло без ошибок по поводу этого «было убрано»
}
