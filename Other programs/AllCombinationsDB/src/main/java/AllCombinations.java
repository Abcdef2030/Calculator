import static java.lang.System.out;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.Iterator;


public class AllCombinations
{
    int columns_count;
    String table_name = null;
    static Logger logger = Logger.getAnonymousLogger ( );
    private static String HOST = "localhost";
    private static String DATABASENAME = "postgres";
    private static String USERNAME = "postgres";
    private static String PASSWORD = "123";
    private static String URL = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement prepared_statement = null;
    private static String sql = null;
    static
    {
        logger.setLevel ( Level.FINE );
        ConsoleHandler handler = new ConsoleHandler ( );
        handler.setLevel ( Level.FINE );
        logger.addHandler ( handler );
        try
        {
            connection = DriverManager.getConnection ( URL );
            statement = connection.createStatement ( );
            logger.fine ( "The database is connected" );
        }
        catch ( SQLException ex )  { logger.warning ( "The connection with the database was not created" ); }

        createFunctionReplaceColumne ( );
    }
    AllCombinations ( int columns_count, String table_name ) throws IllegalArgumentException
    {
        if ( columns_count < 1  ||  columns_count > 24 )  throw new IllegalArgumentException ( "The first argument of the constructor 'AllCombinations ( " + columns_count + " )' is illegal" );
        this.columns_count = columns_count;
        this.table_name = table_name;
    }
    void createAlphabetTable ( ) throws SQLException
    {
        Wheel counter = new Wheel ( 10, 34 );
        sql = "drop table if exists alphabet";
        statement.execute ( sql );
        sql = "create table alphabet ( id serial, symbol varchar ( 3 ) )";
        statement.execute ( sql );
        for ( int i = 0;  i < columns_count;  ++ i )
        {
            sql = "insert into alphabet ( symbol ) values ( '" + counter.toString ( ) + "' )";
            statement.execute ( sql );
            counter.statementUp ( );
        }
        logger.fine ( "The table 'alphabet' is created" );
    }
    static void createFunctionReplaceColumne ( )
    {
        sql =
                "create or replace procedure replaceColumnName ( id_1 int, id_2 int ) as\n" +
                        "$$\n" +
                        "declare str_1 text; str_2 text;\n" +
                        "begin\n" +
                        "str_1 = ( select symbol from alphabet where id = id_1 );\n" +
                        "str_2 = ( select symbol from alphabet where id = id_2 );\n" +
                        "execute 'alter table bt rename column ' || str_1 || ' to ' || str_2;\n" +
                        "end;\n" +
                        "$$ language plpgsql;";
        try
        {
            statement.execute ( sql );
            logger.fine ( "The sql_function 'replaceColumnName' is created" );
        }
        catch ( SQLException ex )  { logger.warning ( "The sql_function 'replaceColumnName' is not created" ); }
    }
    void createCombinationsTable ( Set<String> set ) throws IllegalArgumentException
    {
        if ( set.size ( ) < 1  ||  set.size ( ) > 24 )  throw new IllegalArgumentException ( "The argument of the function 'createCombinationsTable () Set ' is illegal" );
        try
        {
            this.createAlphabetTable ( );

            sql = "drop table if exists key_value";
            statement.execute ( sql );
            sql = "drop table if exists tb";
            statement.execute ( sql );
            sql = "drop table if exists bt";
            statement.execute ( sql );
            sql = "create table tb ( A varchar ( 10 ) )";
            statement.execute ( sql );
            sql = "create table bt ( A varchar ( 10 ) )";
            statement.execute ( sql );
            Iterator<String> iter = set.iterator ( );
            while ( iter.hasNext ( ) )
            {
                String insert_value = iter.next ( );
                sql = "insert into tb values ( '" + insert_value + "' );\n insert into bt values ( '" + insert_value + "' );";
                statement.execute ( sql );
            }
            sql = "drop table if exists ttbb";
            statement.execute ( sql );
            for ( int i = 1;  i < columns_count;  ++ i )
            {
                sql = "call replaceColumnName ( " + i + ", " + ( i + 1 ) + " )";
                statement.execute ( sql );
                sql = "create table ttbb as ( select * from tb join bt on true )";
                statement.execute ( sql );
                sql = "drop table tb";
                statement.execute ( sql );
                sql = "create table tb as ( select * from ttbb )";
                statement.execute ( sql );
                sql = "drop table ttbb";
                statement.execute ( sql );
            }
            sql = "alter table tb rename to " + this.table_name;
            statement.execute ( sql );
        }
        catch ( SQLException ex )  { logger.warning ( "The combination table was not created\n" + ex.getMessage ( ) ); }
    }
}
