package internal.andreiva.concursmotociclism.repository.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private final Properties jdbcProps;

    private static final Logger logger= LogManager.getLogger();

    private static Connection connection_instance = null;

    public JdbcUtils(Properties props){
        jdbcProps=props;
    }

    private Connection getNewConnection(){
        logger.traceEntry();

        var url_lin = jdbcProps.getProperty("db.url_linux");
        var url_win = jdbcProps.getProperty("db.url_windows");
        var user = jdbcProps.getProperty("db.user");
        var pass = jdbcProps.getProperty("db.pass");

        String url;
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            url = url_win;
        }
        else{
            url = url_lin;
        }

        logger.info("Trying to connect to database ... {}",url);
        Connection connection = null;

        try {
            if (user!=null && pass!=null)
                connection = DriverManager.getConnection(url, user, pass);
            else
                connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
        }
        return connection;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if (connection_instance == null || connection_instance.isClosed())
                connection_instance = getNewConnection();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(connection_instance);
        return connection_instance;
    }
}
