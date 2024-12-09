// По классам этого файла создаются элементы, по которым выполняется следующее: имеются несколько файлов, являющийся досками для написания формул с параметрами и со значениями этих параметров. Синтаксис записи параметров такова – с новой строки с учетом регистра записывается выражение ‘parameter’, после пробела перечисляются целочисленные значения, которые этот параметр будет принимать в любой системе счисления. Значения эти друг от друга разделяются так же пробелами. Значения эти могут из себя представлять диапазон, который указывается знаком ‘-’ В этом случае шаг перебора значенйи с данного диапазона есть ‘jlby’.  Синтаксис записи формулы такова –с новой строки с учетом регистра записывается выражение ‘formula’, за тем после пробела записывается формула. То есть записей формул и параметров со своими значениями может быть несколько. При работе программы эти записи извлекаются, создается один лист формул и одна карта параметров. В состав карты входит элемент, ключевым значениями которого является наименования параметра, а обычным значением является лист, с состав которого входят все значения, которые данный параметр будет принимать. После заполнения вышеупомянутого листа ( формулы ) и карты ( параметры ) выполняется действие по вставки значений параметров в формулы. Тут необходимо, что бы количество значений всех параметров были одинаковыми, то есть каждое очередное значение данного параметра вставляется с очередным значением другого параметра.  После вставки очередных значений всех параметров в данную формулу, это формула уже является сформированным с целочисленных значений, и в виде текстового значения добавляется в некий лист под название ‘all_formules’. Далее с этого листа эти сформированные формулы в виде текстового значения будут поступать в среду элемента, созданный по классу ‘Device’, с целью выполнения вычисления их результатов.
import static java.lang.System.out;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List; 
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;


public class FileArithmeticBoard  //  Этим образом создается лист элементов-локаций тех файлов, являющийся досками для записи формул, по каждой из этих элементов-локаций выполняется извлечение записей с доски с целью формирования листа новых форму, состоящий с целочисленных значений.  По каждому элементу локации действия выполняются отдельным процессорным потоком.
{
	private Path path_dir = Path.of ( "boards" );  // Это есть локация директории, в которой находятся все файлы-доски
	private SystemNumeration system_numeration = SystemNumeration.standart_system;
	private Stream<String> stream = Stream.empty ( );  // Этот элемент-поток из себя представляет конвейер, на котором будет в качестве текстовых значений оказаться все сформированные формулы с целочисленных значений
	
	FileArithmeticBoard ( )  { this.streamByAllPaths ( ); }
	
	Stream<String> getStream ( )  { return this.stream; }
	
	void streamByAllPaths ( )
	{
		try { Files.createDirectory ( path_dir ); }  catch ( IOException ex ) { }
		FilePathsList obj = new FilePathsList ( path_dir );  // Создание листа элементов-локаций будет выполняться по этому элементу
		List<Path> list_paths = obj.getPathList ( );
		Iterator<Path> iter_path = list_paths.iterator ( );
		List<Thread> list_thread = new LinkedList ( );  // Это есть лист элементов-потоков, по каждому из которых будут выполнятся действия формирования листа формул с целочисленных значений
		while ( iter_path.hasNext ( ) )
		{
			Path path = iter_path.next ( );  // Это итерация выполняется с целью создания и добавления в лист столько элементов-потоков, сколько имеется элементов-локаций для файлов-досок
			list_thread.add ( new Thread ( )  { public void run ( )  
			{ 
				Stream stream_to_concat = FormulaParameters.getObject ( path, system_numeration ).getAllFormulas ( ).stream ( );
				synchronized ( FileArithmeticBoard.class )  { stream = Stream.concat ( stream, stream_to_concat ); }
			} } );  // В этой процедуре будет выполнятся извлечение записей с файла-доски по данной локации, а затем по этим извлечениям будет сформирован лист формул с целочисленными значениями
		}
		Iterator<Thread> iter_thread = list_thread.iterator ( );  // По этому элементу-итератору будут запущены все те потоки, имеющийся в составе листа 'list_paths'
		while ( iter_thread.hasNext ( ) )  iter_thread.next ( ).start ( );
		iter_thread = list_thread.iterator ( );
		try { while ( iter_thread.hasNext ( ) )  iter_thread.next ( ).join ( ); }  catch ( InterruptedException ex ) { }
	}
	
	public static void main ( String [ ] args ) 
	{
		new FileArithmeticBoard ( ).getStream ( ).forEach ( a -> out.print ( a + "  " ) );	
	}
}


class FormulaParameters  // Этим образом с фалов, являющийся досками для записи формул с начало извлекаются все записи формул и их параметров со своими значениями, с целью за тем вставить значения этих параметров в эти формулы, формируя новую формулу с целочисленных значений. Извлечение формул с фалов-досок из себя представляет добавление данной формулы в виде текстового значения в лист ‘list_formulas’, таким образом формируя лист формул, формированный с параметров. А извлечение параметров со своими значениями есть – создание одной карты для всех параметров, ключевым значением составного элемента которого есть наименование данного параметра, а обычное значение этого элемента из себя представляет лист всех значений параметра. После извлечения методом ‘getAllFormulas’ поочередно вставляются все значения всех параметров во все формулы, формируя новый формулы с составом целочисленных значений. Вставка значений всех параметров в данную формулы выполняется по следующему – предварительно создаются элементы-итераторы на те листы, которые из себя представляют коллекцию всех значений для каждого параметра. За тем создается элемент итератор на эти уже созданные элементы-итераторы, и выполняется перебор значений параметров с последующей вставкой в данную формулу. Когда в данную формулу вставлены значения всех параметров, это формула в качестве текстового значения добавляется в лист ‘all_Formulas’.
{
	private Path path = null;
	private BufferedReader stream = null;;
	private Map<String, List<String>> map = null;  // Это есть та карта, содержащий все параметры со своими значениями
	private ArrayList<String> list_formulas = null;  // Это есть коллекция всех формул с данного файла
	private static final String FORMULA = "formula", PARAMETER = "parameter";
	protected SystemNumeration system_numeration;
	private ArithmeticCounter counter = null;
	private String parameter = null;
	
	private FormulaParameters ( Path path, SystemNumeration system_numeration )  
	{
		this.system_numeration = system_numeration;
		this.counter = new ArithmeticCounter ( this.system_numeration );
		this.path = path; 
		try { this.stream = new BufferedReader ( new InputStreamReader ( Files.newInputStream ( this.path ), "UTF8" ) ); }  catch ( IOException ex ) { }  // На конвейере этого потока содержатся все строки данного файла, являющийся доскою для формул со своими параметрами 
// try { out.println ( new BufferedReader ( new InputStreamReader ( Files.newInputStream ( this.path ), "UTF8" ) ).readLine ( ) ); }  catch ( IOException ex ) { }
		this.list_formulas = new ArrayList ( );
		this.map = new HashMap ( );
	}
	
	void fillMap ( )
	{
		String str = null;  // Этот элемент будет нести значение очередной строки с данного файла, являющийся доской
		try  // Попытка вывода с файла, являющийся доской
		{
			do  // Циклический вывод строк с данного файла, являющийся доской
			{
				str = this.stream.readLine ( );  // Это есть одна строка с одного файла, являющийся доской
				String [ ] str_array = str.split ( " " );  // Выводимую строку необходимо разделить по «словам», что бы разобраться с наименованием переменной и ее значениями
				switch ( str_array [ 0 ] )  // На данный момент вывелось строка – или формулы, или параметра
				{
					case FORMULA :  { this.list_formulas.add ( str_array [ 1 ] );  break; }  // Все формулы с параметрами добавляются в дек, для дальнейшего составления формул со значениями параметров
					case PARAMETER :  // Строка параметра содержит наименование параметра, и возможно не один диапазон значений, или же значения НЕ в диапазоне
					{
						this.parameter = str_array [ 1 ];
						if ( this.map.get ( this.parameter ) == null )  this.map.put ( this.parameter, new LinkedList ( ) );  // В случае элемент карты для данного параметра НЕ создан, он создается и добавляется в карту
						int index = 2;  // Начиная с этого индекса в массивном элементе имеются значения данного параметра
						try 
						{
							while ( true )  // Циклическая обработка значений, записанные на доске – для каждого значения выясняется является ли оно диапазоном. В случае является, то с методом счетчика выводятся все значения с данного диапазона и добавляются в лист, являющийся элементом карты для данной переменной / параметра. В случае НЕ является диапазоном, при попытке разделить на «слова» возникает исключение
							{
								try 
								{
									String [ ] value_split = str_array [ index ].split ( "-" );
									this.counter.getStream ( value_split [ 0 ], value_split [ 1 ] ).forEach ( a -> this.map.get ( this.parameter ).add ( a ) );
								}
								catch ( ArrayIndexOutOfBoundsException exc )  { this.map.get ( this.parameter ).add ( str_array [ index ] ); }
								finally { ++ index; };
							}							
						}
						catch ( ArrayIndexOutOfBoundsException ex ) { }
						break;
					}
				}
			}
			while ( str != null );
		}
		catch ( IOException | NullPointerException ex ) { }
		finally { try { this.stream.close ( ); }  catch ( IOException ex ) { } }
	}
	
	List<String> getAllFormulas ( )
	{
		this.fillMap ( );
		List<String> all_Formulas = new ArrayList ( );  // Это коллекция всех формул со вставленными значениями параметров. То есть после того как в копии формулы вставляются значения всех параметров, это копия добавляется в эту коллекцию

		if ( this.map.isEmpty ( ) )  // Ы случае на данной доске не имеются записи о параметрах, то есть данные на доске формулы состоят только с целочисленных значений
		{
			this.list_formulas.iterator ( ).forEachRemaining ( a -> all_Formulas.add ( a ) );
			return all_Formulas;
		}
		
		Set<String> set_parameters = this.map.keySet ( );
		Iterator<String> iter_parameters = set_parameters.iterator ( );
		List<Iterator<String>> list_iterators = new LinkedList ( );  // То есть это лист итераторов на листы, являющийся значениями той карты
		while ( iter_parameters.hasNext ( ) )  list_iterators.add ( this.map.get ( iter_parameters.next ( ) ).iterator ( ) );
		Iterator<Iterator<String>> iter_list_values = list_iterators.iterator ( );
		try
		{
			List<String> Formulas_clone = null;  // Это коллекция всех формул, в которые будут вставлены значения параметров
			while ( true )
			{
				if ( ! iter_parameters.hasNext ( ) )  // Это условие на самом начале цикла выполняется, по скольку итератор 'iter_parameters' уже перебрал крайний элемент своей коллекции ( несколько строк выше )
				{
					iter_parameters = set_parameters.iterator ( );
					// Тут выполняется процедура добавления копии формулы, в которую уже вставлены значения всех параметров, в тот лист, после чего будет создана еще одна копия
					try { for ( int index = 0;  index < Formulas_clone.size ( );  ++ index )  all_Formulas.add ( Formulas_clone.get ( index ) ); }  catch ( NullPointerException exec ) { }  // На самом начале цикла элемент 'Formulas_clone' носит пустое значение, по этому возникает исключение
					Formulas_clone = (List<String>)this.list_formulas.clone ( );
				}
				if ( ! iter_list_values.hasNext ( ) )  iter_list_values = list_iterators.iterator ( );  // На этом считается что вставлены очередные значения уже всех параметров, и формула из чисел готова
				
				String parameter = iter_parameters.next ( );  // Вывод очередного параметра
				String value = iter_list_values.next ( ).next ( );  // Вывод очередного значения этого "очередного" параметра
				// На этом месте выполняется процедура вставки значений параметров в формулу
				for ( int index = 0;  index < Formulas_clone.size ( );  ++ index )  
				{
					String str = Formulas_clone.get ( index );
					str = str.replaceAll ( parameter, value );
					Formulas_clone.remove ( index );
					Formulas_clone.add ( index, str );
				}
			}
		}
		catch ( NoSuchElementException ex ) { }

		return all_Formulas;
	}
	
	/*synchronized*/ static FormulaParameters getObject ( Path path, SystemNumeration system_numeration )  { return new FormulaParameters ( path, system_numeration ); }
}


class FilePathsList  // Этим образом создается лист локаций всех текстовых ( .txt ) файловых элементов, находящийся в каталоге по данной локации. Наименования этих фалов должны быть целочисленные значения, то есть являться простой сквозной нумерацией
{
	private Path path_dir = null;
	private List<Path> path_list = new LinkedList ( );
	private StringBuilder file_name = null;  // Значение этого элемента будет из себя представлять полное наименование файлового элемента, в которой будет записано уравнение, то есть который будет являться доскою
	
	FilePathsList ( Path path_dir )  
	{
		this.path_dir = path_dir;
		this.file_name = new StringBuilder ( path_dir.getFileName ( ).toString ( ).concat ( "/" ) );
		this.fillPathList ( );
	}
	
	FilePathsList ( String path )  { this ( Path.of ( path ) ); }
	
	void fillPathList ( )
	{
		int name = 1;  // Это есть начальный номер фалов, имеющийся в данной директории
		this.file_name.append ( String.valueOf ( name ) );  // Составляется полное наименование первого файла
		Path path_file = Path.of ( this.file_name.toString ( ) );  // Составляется локация для первого файла. То есть этот элемент-локация далее будет нести значения, относящийся остальным существующим в данной директории файлам. Предназначение этого элемента - в первую очередь проверить наличие файлового элемента. В случае элемент по данной локации имеется, данный элемент-локация добавляется в лист, что бы в дальнейшем записанная формула в файле была извлечена для калькулятора
		while ( Files.exists ( path_file ) )
		{
			this.path_list.add ( path_file );
			++ name;
			this.file_name.delete ( this.file_name.indexOf ( "/" ) + 1,  this.file_name.length ( ) );
			this.file_name.append ( String.valueOf ( name ) );
			path_file = Path.of ( this.file_name.toString ( ) );
		}
	}
	
	List<Path> getPathList ( )  { return this.path_list; }
}
