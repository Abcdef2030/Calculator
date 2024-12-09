import static java.lang.System.out;
import static java.lang.System.setOut;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BinaryOperator;


public class TransformerMap
{
    static final String [ ] array = { "65537", "983055", "50529027", "252645135", "255", "4369", "3342387", "16711935", "5", "65535", "16843009", "4294967297", "771", "21845", "858993459", "5570645", "3", "85", "1114129", "51", "3855", "84215045", "15", "327685", "1", "286331153", "1285", "196611", "13107", "1431655765", "4294967295" };
    static Stream<String> stream = null;  // Поток целых чисел в текстовом представлении
    static Function<String, String> function = null;  // Действие, которое будет выполнено над этими числами для получения их двоичных представлений в текстовом виде, с целью заполнения карты
    static SystemNumeration system_numeration_1 = new SystemNumeration ( 10 );  // Десятиричная система счисления
    static SystemNumeration system_numeration_2 = new SystemNumeration ( 2 );  // ...
    private static Map<String, String> map = null;  // Ключевыми значениями являются те числа с потока в текстовом представлении, а обычными значениями являются значения этих чисел в двоичном системе счисления в текстовом представлении

    private static void fillMap ( )  // Вычисляются двоичные значения всех чисел с потока и по ходу заполняется карта
    {
        stream = Arrays.stream ( array );
        Function<String, String> function = a -> Operation.transformationFromToSystem ( a, system_numeration_1, system_numeration_2 );
        Collector<String,?, Map<String, String>> colllector = Collectors.toMap ( a -> a, function, ( a, b ) -> { return a; }, HashMap::new );
        map = stream.collect ( colllector );
    }
    static Stream<String> getSortedStream ( )  // Составляется отсортированный поток тех чисел в двочной системе счисления, в текстовом представлении
    {
        TransformerMap.fillMap ( );
        return map.values ( ).stream ( ).sorted ( ( a, b ) -> system_numeration_1.compareNumbers ( a, b ) );
    }
}
