import static java.lang.System.out;
import static java.lang.System.in;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.EmptyFileException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;



public abstract class History  // По этом классу выполняется запись историй вычислений арифметических выражений в файл. Пере записью в файл, создается папка ‘History’, в которой будет храниться этот файл. В этой папке так же создается папка ‘Bucket’, в которую будут перемещены файлы с содержанием прошедших историй вычислений. То есть перед тем как записать историю вычисления в файл, с начало проверяется наименование имеющегося в папке ‘History’  файла, которое выражает дату, и в случае это дата устаревшая, этот имеющийся файл удаляется в папку ‘Bucket’, и в место него создается файл с соответствующей датой.
{
	static protected final String history_dir = "History";
	protected File dir_history = new File ( history_dir );
	protected File dir_bucket = new File ( dir_history, "Bucket" );
	
	History ( )  
	{
		this.dir_history.mkdir ( );  
		this.dir_bucket.mkdir ( );
	}
	
	protected boolean updateHistoryFile ( String new_date )  // Этим методом выполняется создание нового файла соответствующий к новой дате
	{
		Path path = Path.of ( history_dir.concat ( "/".concat ( new_date ) ) );
		if ( ! Files.exists ( path ) )
		{
			try { Files.createFile ( path ); }  catch ( IOException ex ) { }
			return true;
		}
		else return false;
	}
	
	protected void replaceOldFile ( String new_date )  // Этим методом выполняется перемещение имеющегося устаревшего файла с папки ‘History’ в папку ‘Bucket’
	{
		String [ ] all_files = this.dir_history.list ( );
		for ( String name : all_files )  if ( ! name.equals ( new_date ) ) 
		{
			Path path_move = Path.of ( this.dir_history.toString ( ).concat ( "/".concat ( name ) ) );
			Path move_to = Path.of ( this.dir_bucket.toString ( ).concat ( "/".concat ( name ) ) );
			try { Files.move ( path_move, move_to ); }  catch ( IOException ex ) { }
		}		
	}
	
	abstract void writeHistoryToFile ( Object [ ] history_parts );  // Этим методом выполняется запись истории вычисления в созданный новый файл
}


class HistoryText extends History  // По этому классу создается элемент-история при начале программы. По этому элементу-историю создается директория ‘History’ в которой создается директория ‘Bucket’ для хранения файлов, содержащие историю вычислений прошедших дней. Так же создается файл с наименованием текущей даты, для хранения истории вычислений текущего дня. История вычислений в файле хранится следующим образом – записывается внесенная формула, вставляется знак равенства, за тем результат вычисления, после чего три пробела, которым следует запись времени до секунды. Запись истории вычисления данной формулы в этот файл выполняется методом элемента-доски, созданный по классу ‘ArithmethicBoard’.
{
	private String today_data = null;
	private PrintStream print = null;

	HistoryText ( )
	{
		this.today_data = new SimpleDateFormat ( "dd MMM yy" ).format ( Calendar.getInstance ( ).getTime ( ) );
		try { this.print = new PrintStream ( new FileOutputStream ( this.dir_history.toString ( ).concat ( "/".concat ( this.today_data ) ), true ), true ); }  catch ( IOException ex ) { }
	}

	@Override
	synchronized void writeHistoryToFile ( Object [ ] history_parts )  // Функциональный элемент, по действию которого данное текстовое значение, представляющее из себя историю очередного вычисления записывается в файл историй текущего дня. То есть в момент записи текущий файл историй может быть удален в карзину, в место него создаваться новый, и очередное значение-история будет записано в него
	{
		this.updateHistoryFile ( this.today_data );  // Перед тем как внести запись, с начало проверяется имеющийся файл историй по дате
		this.replaceOldFile ( this.today_data );
		String history = ( (String)history_parts [ 0 ] ).concat ( " = ".concat ( (String)history_parts [ 1 ] ) );
		String result_history = new StringBuilder ( history ).append ( "   " ).append ( new SimpleDateFormat ( "hh:mm:ss" ).format ( Calendar.getInstance ( ).getTime ( ) ) ).toString ( );
		this.print.println ( result_history );
	}
}


class HistoryExcel extends History  // По этому классу запись выполняется в ‘Excel’ файл следующим образом: каждый ‘Excel’ файл содержит записи текущего года. То есть наименование файла есть текущий год в формате «yyyy» Листы этого файла создаются для ежедневной записи историй вычислений, по этому наименование каждого листа составляется с текущего месяца и текущего дня ( через точку ). Так же перед записью новой истории лист предыдущего месяца скрывается. А файл предыдущего года перед записью истории перемещается в папку ‘Bucket’.
{
	Path file_path = null;
	String file_name = null;
	String this_mounth = null;
	String today = null;
	Workbook book = null;
	Sheet today_sheet = null;
	
	HistoryExcel ( )
	{
		Calendar calendar = Calendar.getInstance ( );
		
		this.file_name = String.valueOf ( calendar.get ( Calendar.YEAR ) ).concat ( ".xlsx" );
		this.this_mounth = new SimpleDateFormat ( "MM" ).format ( calendar.getTime ( ) );
		this.today = new SimpleDateFormat ( "dd" ).format ( calendar.getTime ( ) );
	}
	
	private void createBook ( )
	{
		this.book = new XSSFWorkbook ( );
		this.c1reateNewSheet ( );
	}
	
	private void c1reateNewSheet ( )
	{
		this.today_sheet = this.book.createSheet ( this_mounth.concat ( ".".concat ( today ) ) );
		Row row = this.today_sheet.createRow ( 0 );
		Cell cell = row.createCell ( 0 );
		cell.setCellValue ( "Expression" );
		cell = row.createCell ( 1 );
		cell = row.createCell ( 2 );
		cell.setCellValue ( "Result" );
		cell = row.createCell ( 3 );	
		cell.setCellValue ( "Time" );
			
		try { this.book.write ( new FileOutputStream ( History.history_dir.concat ( "/".concat ( this.file_name ) ) ) ); }  catch ( IOException ex ) { } 		
	}
	
	@Override
	synchronized void writeHistoryToFile ( Object [ ] history_parts )
	{
		this.createNewFile ( );
		this.replaceOldFile ( this.file_name );
		this.hideOldSheets ( );

		Row row = this.getNewRow ( );
		Cell cell = row.createCell ( 0 );
		cell.setCellValue ( (String)history_parts [ 0 ] );
		cell = row.createCell ( 1 );
		cell.setCellValue ( "=" );
		cell = row.createCell ( 2 );
		cell.setCellValue ( (String)history_parts [ 1 ] );
		cell = row.createCell ( 3 );
		cell.setCellValue ( new SimpleDateFormat ( "hh.mm.ss" ).format ( Calendar.getInstance ( ).getTime ( ) ) );

		try { this.book.write ( new FileOutputStream ( History.history_dir.concat ( "/".concat ( this.file_name ) ) ) ); }  catch ( IOException ex ) { }
	}
	
	private void createNewFile ( )
	{
		if ( this.updateHistoryFile ( this.file_name ) )  this.createBook ( );  
		else  
		{
			try { this.book = new XSSFWorkbook ( new FileInputStream ( History.history_dir.concat ( "/".concat ( this.file_name ) ) ) ); }  catch ( IOException ex ) { }
			if ( ( this.today_sheet = this.book.getSheet ( this_mounth.concat ( ".".concat ( today ) ) ) ) == null )  this.c1reateNewSheet ( );  
		}		
	}
	
	private void hideOldSheets ( ) 
	{
		Iterator<Sheet> sheet_iter = this.book.sheetIterator ( );
		int sheet_index = 0;
		try { while ( true )  if ( ! sheet_iter.next ( ).getSheetName ( ).substring ( 0, 2 ).equals ( this.this_mounth ) )  this.book.setSheetHidden ( sheet_index ++, true ); }  catch ( NoSuchElementException ex )  { try { this.book.write ( new FileOutputStream ( History.history_dir.concat ( "/".concat ( this.file_name ) ) ) ); }  catch ( IOException exc ) { } }
 	}
	
	private Row getNewRow ( )
	{
		int row_index = 0;
		Iterator<Row> iter_row = this.today_sheet.iterator ( );
		try 
		{
			while ( true )
			{
				iter_row.next ( );
				row_index ++;
			}
		}
		catch ( NoSuchElementException ex )  { }
		Row row = this.today_sheet.createRow ( row_index );
		try { this.book.write ( new FileOutputStream ( History.history_dir.concat ( "/".concat ( this.file_name ) ) ) ); }  catch ( IOException ex ) { }
		return row;
	}
}
