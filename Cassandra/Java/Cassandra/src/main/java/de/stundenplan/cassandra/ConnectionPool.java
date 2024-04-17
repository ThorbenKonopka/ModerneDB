package de.stundenplan.cassandra;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class ConnectionPool {
    private LinkedList<Connection> dozentConnections = new LinkedList<>();
    private LinkedList<Connection> verwalterConnections = new LinkedList<>();
    private LinkedList<Connection> rootConnections = new LinkedList<>();

    private static ConnectionPool connectionPool;

    private final static String URL = "jdbc:mysql://localhost:3306/Stundenplan?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";

    private ConnectionPool() {
        dozentConnections = createDozentConnection();
        verwalterConnections = createVerwalterConnection();
        rootConnections = createRootConnection();
    }

    private LinkedList<Connection> createDozentConnection() {
        LinkedList<Connection> connections = new LinkedList<>();
        try {
            connections.addFirst(DriverManager.getConnection(URL, "dozent", "password"));
            connections.addFirst(DriverManager.getConnection(URL, "dozent", "password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connections;
    }

    private LinkedList<Connection> createVerwalterConnection() {
        LinkedList<Connection> connections = new LinkedList<>();
        try {
            connections.addFirst(DriverManager.getConnection(URL, "verwalter", "password"));
            connections.addFirst(DriverManager.getConnection(URL, "verwalter", "password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connections;
    }

    private LinkedList<Connection> createRootConnection() {
        LinkedList<Connection> connections = new LinkedList<>();
        try {
            connections.addFirst(DriverManager.getConnection(URL, "root", "({>V!>C4aCEK"));
            connections.addFirst(DriverManager.getConnection(URL, "root", "({>V!>C4aCEK"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connections;
    }

    public synchronized Connection getDozentConnection() {
        Connection connection = dozentConnections.removeFirst();
        dozentConnections.addLast(connection);

        validateNoConnectionClosed();

        return connection;
    }

    public synchronized Connection getVerwalterConnection() {
        Connection connection = verwalterConnections.removeFirst();
        verwalterConnections.addLast(connection);

        validateNoConnectionClosed();

        return connection;
    }

    public synchronized Connection getRootConnection() {
        Connection connection = rootConnections.removeFirst();
        rootConnections.addLast(connection);

        validateNoConnectionClosed();

        return connection;
    }

    private String getPasswordForRootUser() {
        File passwordFile = getPasswordFileForRootUser();

        try (FileInputStream fileInputStream = new FileInputStream(passwordFile)) {
            return new String(fileInputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private File getPasswordFileForRootUser() {
        return new File(getAbsolutPathOfRootProject() + "/DB/mysql/passwords/root.txt");
    }

    private String getAbsolutPathOfRootProject() {
        return getRootProjectFile().getParentFile().getParentFile().getAbsolutePath();
    }

    private File getRootProjectFile() {
        File file = new File("");
        return new File(file.getAbsolutePath());
    }

    public void closeConnections() {
        System.out.println("Alle Verbindungen werden geschlossen. :D");
        dozentConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        verwalterConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        rootConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void validateNoConnectionClosed() {
        dozentConnections.forEach(this::validateConnectionNotClosed);
        verwalterConnections.forEach(this::validateConnectionNotClosed);
        rootConnections.forEach(this::validateConnectionNotClosed);
    }

    private void validateConnectionNotClosed(Connection connection) {
        try {
            if(connection.isClosed()) {
                System.out.println("Eine Verbindung wurde unerwartet geschlossen. Es werden alle Connections geschlossen.");
                closeConnections();
                throw new IllegalStateException("Es wurde eine Verbindung unerwartet geschlossen. Bitte schließe keine Connections, die aus dem ConnectionPool stammen.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initAllConnections() {
        dozentConnections.forEach(this::initConnection);
        verwalterConnections.forEach(this::initConnection);
        rootConnections.forEach(this::initConnection);
    }

    private void initConnection(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            connection.setReadOnly(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ConnectionPool getConnectionPool() {
        if(connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public static void init() {
        if(connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        connectionPool.initAllConnections();
    }

    public static void destroy() {
        if(connectionPool != null) {
            connectionPool.closeConnections();
        }
    }
}