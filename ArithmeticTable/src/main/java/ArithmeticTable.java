import static java.lang.System.out;
import java.util.HashMap;



class ArithmeticTable extends SystemNumeration  //  По этому классу создается система счисления со своими таблицами сложения, умножения, вычитания и деления с указанием остатка.
{
    private HashMap<String, String> addition_table = null;  // Таблица из себя представляет некую карту по классу ‘Property’ в которой ключевые значения составных элементов из себя представляют два цифра, в виде двусимвольного текстового значения, участвующие в данной арифметической операции, а простые значения этих составных элементов являются текстовым представлением результат выполненной арифметической операции
    private HashMap<String, String> multiplication_table = null;  // ...
    private HashMap<String, String> subtractive_table = null;  // ...

    ArithmeticTable ( ) { this ( 10, 5 ); }

    ArithmeticTable ( Integer numeration ) throws IllegalArgumentException, NullPointerException  { this ( numeration, 5 ); }

    ArithmeticTable ( Integer numeration, Integer ciphers_count ) throws IllegalArgumentException, NullPointerException  // Тут исключение может возникнуть при вызове конструктора базового класса с не правильным аргументом
    {		
        super ( numeration, ciphers_count );
        // Эти картовые элементы инициализируются в среде определения конструктора, что бы в случае аргумент будет не правильным, они не стали зря инициализироваться
        addition_table = new HashMap ( );
        multiplication_table = new HashMap ( );
        subtractive_table = new HashMap ( );
        try { this.setMaps ( ); }
        catch  ( CloneNotSupportedException ex ) { }
        catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
    }

    private void setMaps ( ) throws CloneNotSupportedException, NullPointerException
    {
        for ( int index_horisontal = 0;  index_horisontal < getNumeration ( );  ++ index_horisontal )
            for ( int index_vertical = 0;  index_vertical < getNumeration ( );  ++ index_vertical )
            {
                StringBuilder table_key = new StringBuilder ( );  // Этот элемент в текстовом представлении будет нести ключевое значение для каждого из тех четырех будущих составных элементов. Это текстовое представление на уровне живого разума будет состоять с двух частей: 1) цифровой символ по строке всех четырех таблиц одинаково  2) цифровой символ по столбцу всех четырех таблиц одинаково
                // В этом двойном цикле будут выполнятся две процедуры:
                // 1) вычисление ключевых значений будущих составных элементов всех четырех карт, которые в текстовом представлении будут нести итоговые значения всех четырех простейших арифметических операций, соответственно карты, составным элементом которой будут являться  ( то есть эти ключевые значения, описывающие пару чисел, участвующие во всех четырех простейших арифметических операциях, и они будут одинаковыми для будущих составных элементов четырех карт).
                String first_number = this.numeration_array [ index_horisontal ];  // Это первая часть по стоке
                String second_number = this.numeration_array [ index_vertical ];  // Это вторая часть по столбцу
                table_key.append ( first_number );  table_key.append ( second_number );  //  Тут очередной элемент уже носит то ключевое значение целиком
                // 2) будут создаваться четыре будущих составных элементов для каждой из тех четырех карт ( на уровне живого разума представляющие из себя таблицы ), и к каждому из этих четырех элементов в текстовом виде будут присвоены итоговые значения четырех арифметических операций. Для вычисления этих итоговых значений необходимо с начало последовательно выполнить действия по этим четырем арифметическим операциям, с участием одного элемента-счетчика и одного элемента-колеса, показания которых будут меняться по данному двойному циклу.
                ArithmeticCounter first_value = new ArithmeticCounter ( first_number, this );
                WheelInterface second_value = new Wheel ( index_vertical, this );
                String addition_element = null;  // А это элемент, который будет в качестве показания счетчика нести итоговое значение простейшей арифметической операции по ПЕРВОЙ таблице, то есть по этому элементу будет выведено это итоговое значение в текстовом виде
                try { addition_element = first_value.clone ( ).summary ( second_value.clone ( ) ).toString ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'ArithmeticTable :: setMaps ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
                if ( addition_element.length ( ) < 2 )  addition_element = this.ZERO.concat ( addition_element );

                String multiplication_element = null;  // А это элемент, который будет в качестве показания счетчика нести итоговое значение простейшей арифметической операции по ВТОРОЙ таблице, то есть по этому элементу будет выведено это итоговое значение в текстовом виде
                try { multiplication_element = first_value.clone ( ).multipling ( second_value.clone ( ) ).toString ( ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'ArithmeticTable :: setMaps ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }
                if ( multiplication_element.length ( ) < 2 )  multiplication_element = this.ZERO.concat ( multiplication_element );

                String subtractive_element = null;
                if ( index_horisontal < index_vertical ) { subtractive_element = this.ONE;  first_value.addLeftOne ( ); }  else  subtractive_element = this.ZERO;
                try { subtractive_element = first_value.subtraction ( second_value ).optimizeCounter ( ).toString ( ).concat ( subtractive_element ); }  catch ( NullPointerException ex )  { throw new NullPointerException ( Colors.RED + "E X C E P T I O N -- " + ex.getMessage ( ) + "\n" + Colors.RED + "E X C E P T I O N -- When calling the function " + Colors.WHITE + "'ArithmeticTable :: setMaps ( )'   " + Colors.NOCOLOR + new Throwable ( ).getStackTrace ( ) [ 0 ] ); }

                addition_table.put ( table_key.toString ( ), addition_element );  multiplication_table.put ( table_key.toString ( ), multiplication_element );  subtractive_table.put ( table_key.toString ( ), subtractive_element );  // Тут эти четыре элементы добавляются - каждый в соответствующую карту
            }
    }

    //--- G E T T E R S ---
    HashMap<String, String> getAdditionalTable ( ) { return this.addition_table; }
    HashMap<String, String> getMultiplicationTable ( ) { return this.multiplication_table; }
    HashMap<String, String> getSubtractiveTable ( ) { return this.subtractive_table; }
    //-----------------------
}

