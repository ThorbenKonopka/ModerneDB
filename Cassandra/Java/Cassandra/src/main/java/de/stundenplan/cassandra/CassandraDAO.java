package de.stundenplan.cassandra;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverOption;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.DefaultBatchType;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class CassandraDAO {
    private final Set<InetSocketAddress> NODES = Set.of(new InetSocketAddress("127.0.0.7", 9042), new InetSocketAddress("127.0.0.7", 9043), new InetSocketAddress("127.0.0.7", 9044));

    private final CqlSession CQL_SESSION = new CqlSessionBuilder()
            .addContactPoints(NODES)
            .withLocalDatacenter("datacenter1")
            .build();

    private final String keyspace = "stundenplan";

    public void init() {
        createKeyspace();
        createDozentTermineTable();
        createStudentTermineTable();
    }

    public void createKeyspace() {

        String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspace + " WITH replication = {" + "'class': 'NetworkTopologyStrategy', " + "'datacenter1': 3};";
        CQL_SESSION.execute(query);
    }

    public void createDozentTermineTable() {

        String query = "CREATE TABLE IF NOT EXISTS " + keyspace + "." + "dozent_termine (dozent_id  int, termin_id int, datum date, beginn time, ende time, bezeichnung text, typ text, PRIMARY KEY((dozent_id), termin_id));";

        CQL_SESSION.execute(query);
    }

    public void createStudentTermineTable() {

        String query = "CREATE TABLE IF NOT EXISTS " + keyspace + "." + "student_termine (student_id  int, termin_id int, datum date, beginn time, ende time, bezeichnung text, typ text, dozent text, teilnahmestatus text, PRIMARY KEY((student_id), termin_id));";

        CQL_SESSION.execute(query);
    }

    public void insertTermin(Termin termin) {
        BatchStatement batchStatement = BatchStatement.newInstance(DefaultBatchType.LOGGED);

        Set<SimpleStatement> simpleStatements = createTerminInsertStatements(termin);

        batchStatement = batchStatement.addAll(simpleStatements);
        batchStatement = batchStatement.setKeyspace(keyspace);
        batchStatement = batchStatement.setConsistencyLevel(ConsistencyLevel.ALL);
        //long startTime = System.currentTimeMillis();
        ResultSet resultSet = CQL_SESSION.execute(batchStatement);
        //long endTime = System.currentTimeMillis();
//        System.out.println(resultSet.getExecutionInfo().getErrors().size());
//        System.out.println("It took only " + (endTime - startTime) + "ms :D");
//        System.out.println("Number of Statements executed: " + simpleStatements.size());
    }

    private Set<SimpleStatement> createTerminInsertStatements(Termin termin) {
        Set<SimpleStatement> simpleStatements = new HashSet<>();

        int terminId = termin.getTerminId();
        int dozentId = termin.getDozentId();
        String dozent = termin.getDozent();
        Set<Integer> studentIds = termin.getStudenten();

        LocalDate date = termin.getDatum();
        String formattedDate = date.format(DateTimeFormatter.ISO_DATE);

        LocalTime startTime = termin.getBeginn();
        String formattedStartTime = startTime.format(DateTimeFormatter.ISO_TIME);

        LocalTime endTime = termin.getEnde();
        String formattedEndTime = endTime.format(DateTimeFormatter.ISO_TIME);

        String bezeichnung = termin.getBezeichnung();
        String type = termin.getTyp();

        simpleStatements.addAll(createTerminInsertForStudents(studentIds, terminId, formattedDate, formattedStartTime, formattedEndTime, bezeichnung, type, dozent));
        simpleStatements.add(createTerminInsertForDozent(dozentId, terminId, formattedDate, formattedStartTime, formattedEndTime, bezeichnung, type));

        return simpleStatements;
    }

    private SimpleStatement createTerminInsertForDozent(int dozentId, int terminId, String formattedDate, String formattedStartTime, String formattedEndTime, String bezeichnung, String type) {
        return SimpleStatement.newInstance("INSERT INTO " +
                keyspace + ".dozent_termine(dozent_id, termin_id, datum, beginn, ende, bezeichnung, typ)" +
                "VALUES(" + dozentId + "," +
                terminId + "," +
                "'" + formattedDate + "'," +
                "'" + formattedStartTime + "'," +
                "'" + formattedEndTime + "'," +
                "'" + bezeichnung + "'," +
                "'" + type + "');").setConsistencyLevel(ConsistencyLevel.ALL);
    }

    private Set<SimpleStatement> createTerminInsertForStudents(Set<Integer> studentIds, int terminId, String formattedDate, String formattedStartTime, String formattedEndTime, String bezeichnung, String type, String dozent) {
        Set<SimpleStatement> statements = new HashSet<>();
        for (int studentId : studentIds) {
            statements.add(createTerminInsertForStudent(studentId, terminId, formattedDate, formattedStartTime, formattedEndTime, bezeichnung, type, dozent));
        }
        return statements;
    }

    private SimpleStatement createTerminInsertForStudent(int studentId, int terminId, String formattedDate, String formattedStartTime, String formattedEndTime, String bezeichnung, String type, String dozent) {
        return SimpleStatement.newInstance("INSERT INTO " +
                keyspace + ".student_termine(student_id, termin_id, datum, beginn, ende, bezeichnung, typ, dozent)" +
                "VALUES(" + studentId + "," +
                terminId + ", " +
                "'" + formattedDate + "'," +
                "'" + formattedStartTime + "'," +
                "'" + formattedEndTime + "'," +
                "'" + bezeichnung + "'," +
                "'" + type + "'," +
                "'" + dozent + "');").setConsistencyLevel(ConsistencyLevel.ALL);
    }

    public void destroy() {
        CQL_SESSION.close();
    }
}
