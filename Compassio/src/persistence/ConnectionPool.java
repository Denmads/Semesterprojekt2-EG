/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Pools database connections in order to speed up access.
 * 
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
 */
public class ConnectionPool {
    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private static final int POOL_SIZE = 10;
    
    public static ConnectionPool create(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(POOL_SIZE);
        for (int i = 0; i <POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new ConnectionPool(url, user, password, pool);
    }
    
    public ConnectionPool(String url, String user, String password, List<Connection> pool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = pool;
    }
    

    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < POOL_SIZE) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                throw new RuntimeException("Pool size limit reached");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }
     
    public boolean release(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }
     
    private static Connection createConnection(
      String url, String user, String password) 
      throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
     
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}