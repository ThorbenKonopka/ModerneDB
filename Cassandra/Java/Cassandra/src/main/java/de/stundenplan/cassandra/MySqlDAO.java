package de.stundenplan.cassandra;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MySqlDAO {

    public static void initDatabase(Set<Termin> termine) throws SQLException {
        createModule();
        createDozenten();
        insertTyp();
        insertJahrgang();
        insertSemester();
        insertVeranstaltungen(termine);
        insertGruppe();
        insertStudents();
        insertBelegteVeranstaltung(termine);
    }

    public static void createModule() {
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO Modul(modulId, name, pflichtveranstaltung)" +
                    "VALUES (1, 'Internetbasierte Anwendungen', 0)," +
                    "(2, 'Grundlagen OO-Programmiersprachen', 0)," +
                    "(3, 'Datenbankgrundlagen', 0)," +
                    "(4, 'Mathematik für Informatiker 1', 0)," +
                    "(5, 'Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende', 0)," +
                    "(6, 'Arbeits-, Lern- und Praesentationstechnik 1', 0)," +
                    "(7, 'Englisch 1', 0);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDozenten() {
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO Dozent(dozentId, name, vorname)" +
                    "VALUES (1, 'Thorben', 'Thorben')," +
                    "(2, 'Lukas', 'Lukas')," +
                    "(3, 'Koenigsmann', 'Koenigsmann')," +
                    "(4, 'Max', 'Max')," +
                    "(5, 'David', 'David')," +
                    "(6, 'Lisa', 'Lisa')," +
                    "(7, 'Stefan', 'Stefan')," +
                    "(8, 'Daniel', 'Daniel')," +
                    "(9, 'Kaktus', 'Kaktus')," +
                    "(10, 'XE64', 'XE64')," +
                    "(11, 'TEE', 'TEE');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertTyp() {
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO D_Typ (typ)\n" +
                    "    VALUES (\"Vorlesungen\"),\n" +
                    "        (\"Praktika\"),\n" +
                    "                (\"Uebungen\");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertVeranstaltungen(Set<Termin> termine) {

        List<Integer> blacklist = new ArrayList<>();

        String query = "INSERT INTO Veranstaltung(veranstaltungId, dozentId, typ, semester, modulId, jahrgang) VALUES";
        Integer veranstaltungId = null;
        for(Termin termin : termine) {
            if(veranstaltungId == null) {
                veranstaltungId = termin.getVeranstaltungId();
                query += "(" + veranstaltungId + ", " + termin.getDozentId() + ", '" + termin.getTyp() + "', 'TEST', " + termin.getModulId() + ", 1)";
                blacklist.add(veranstaltungId);
            } else {
                if(!blacklist.contains(termin.getVeranstaltungId())) {
                    veranstaltungId = termin.getVeranstaltungId();
                    query += ", (" + veranstaltungId + ", " + termin.getDozentId() + ", '" + termin.getTyp() + "', 'TEST', " + termin.getModulId() + ", 1)";
                    blacklist.add(veranstaltungId);
                }
            }
        }
        query += ";";
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertSemester() {
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO D_Semester (semester)\n" +
                    "    VALUES ('TEST');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertJahrgang() {
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO D_Jahrgang (jahrgang)\n" +
                    "    VALUES (1);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertGruppe() {
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO D_Gruppe (gruppe)\n" +
                    "    VALUES ('G-L');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertStudents() {
        String query = "INSERT INTO Student(matrikelnummer, gruppe, jahrgang) VALUES";
        boolean first = true;
        for(int i = 1; i <= 300; i++) {
            if(first) {
                query += "(" + i + ", " + "'G-L', 1)";
                first = false;
            } else {
                query += ", (" + i + ", " + "'G-L', 1)";
            }
        }
        query += ";";
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertBelegteVeranstaltung(Set<Termin> termine) {
        String query = "INSERT INTO BelegteVeranstaltung(veranstaltungId, matrikelnummer) VALUES";

        boolean first = true;

        Integer veranstaltungId = null;
        for(Termin termin : termine) {
            if(veranstaltungId == null) {
                veranstaltungId = termin.getVeranstaltungId();
                for(Integer student : termin.getStudenten()) {
                    if(first) {
                        query += "(" + veranstaltungId + ", " + student + ")";
                        first = false;
                    } else {
                        query += ", (" + veranstaltungId + ", " + student + ")";
                    }
                }
            } else {
                if((veranstaltungId != termin.getVeranstaltungId())) {
                    veranstaltungId = termin.getVeranstaltungId();
                    for(Integer student : termin.getStudenten()) {
                        query += ", (" + veranstaltungId + ", " + student + ")";
                    }
                }
            }
        }
        query += ";";
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        try(Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertTermine(Set<Termin> termine) throws SQLException {
        Connection connection = ConnectionPool.getConnectionPool().getRootConnection();
        connection.setAutoCommit(false);
        for(Termin termin : termine) {
            LocalDate date = termin.getDatum();
            String formattedDate = date.format(DateTimeFormatter.ISO_DATE);

            LocalTime startTime = termin.getBeginn();
            String formattedStartTime = startTime.format(DateTimeFormatter.ISO_TIME);

            LocalTime endTime = termin.getEnde();
            String formattedEndTime = endTime.format(DateTimeFormatter.ISO_TIME);

            try(Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO Termin(terminId, datum, beginn, ende, veranstaltungId) VALUES " +
                        "(" + termin.getTerminId() + ", '" + formattedDate + "', '" + formattedStartTime + "', '" + formattedEndTime + "', " + termin.getVeranstaltungId() + ");");
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        connection.setAutoCommit(true);
    }

}
