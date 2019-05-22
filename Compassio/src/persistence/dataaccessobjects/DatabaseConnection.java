package persistence.dataaccessobjects;

import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Morten Kargo Lyngesen
 */
public class DatabaseConnection {
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    private static BasicDataSource connectionPool;
    
    //Database connection parameters
    //#TODO: Move to a secure location
    private final String dbIP = "jdbc:postgresql://68.183.68.65:5432/compassio";
    private final String username = "postgres";
    private final String password = "software-f19-4";
    
    private DatabaseConnection () {
        connectionPool = new BasicDataSource();
        connectionPool.setUsername(this.username);
        connectionPool.setPassword(this.password);
        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(this.dbIP);
        connectionPool.setInitialSize(10);
    }
    
    public static BasicDataSource getConnectionPool (){
        return connectionPool;
    }
    
    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }
}