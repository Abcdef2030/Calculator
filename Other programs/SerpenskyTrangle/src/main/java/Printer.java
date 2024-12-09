import static java.lang.System.out;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;



public class Printer
{
    static Workbook book = new XSSFWorkbook ( );
    static Sheet sheet = null;  // Это лист Excel файла
    static String file_name = "Book.xlsx";
    static int max_length = 0;  // Длина двоичного представления в текстовом виде самого большого числа для красивой печати
    static
    {
        sheet = book.createSheet ( "Tringles" );
    }
    static List<String> list = null;  // Этот лист будет заполнен числами двоичной системы счисления, имеющийся в карте, для последовательного перебора

    static void getMaxLength ( )  // Тут заполняется лист, и его помощью вычисляется максимальная длина двоичных чисел в текстовом представлении для красивой печати
    {
        Printer.list = TransformerMap.getSortedStream ( ).collect ( Collectors.toList ( ) );
        max_length = list.get ( list.size ( ) - 1 ).length ( );
    }
    static void printValues ( ) throws IOException // Печатует все числа по двоичной системе счислению по возрастанию построчно, чтобы все было похоже на триугольник
    {
        getMaxLength ( );
        int row_index = 0;
        Row row = sheet.createRow ( row_index );

        Iterator<String> iter = Printer.list.iterator ( );
        try
        {
            while ( true )
            {
                String str = iter.next ( );
                int cell_index = ( max_length - str.length ( ) ) / 2;
                for ( int i = 0;  i < str.length ( ); i ++ )  row.createCell ( cell_index ++ ).setCellValue ( String.valueOf ( str.charAt ( i ) ) );
                row = sheet.createRow ( ++ row_index );
            }
        }
        catch ( NoSuchElementException ex ) { book.write ( new FileOutputStream ( file_name ) ); }
    }

    public static void main ( String [ ] args ) throws IOException
    {
        printValues ( );
    }
}
