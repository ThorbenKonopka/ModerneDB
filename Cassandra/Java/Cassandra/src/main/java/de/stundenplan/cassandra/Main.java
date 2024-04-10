package de.stundenplan.cassandra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        CassandraDAO cassandraDAO = new CassandraDAO();

        cassandraDAO.createKeyspace();
        cassandraDAO.createDozentTermineTable();
        cassandraDAO.createStudentTermineTable();

        String[] dozenten = {"Thorben", "Lukas", "Koenigsmann", "Max", "David", "Lisa", "Stefan", "Daniel", "Kaktus", "XE64", "IDK"};

        Termin termin = new Termin();

        Set<Integer> studentIds = new HashSet<>();

        for(int i = 1; i <= 30; i++) {
            studentIds.add(i);
        }

        termin.setStudenten(studentIds);
        termin.setDozent("Königsmann");
        termin.setDozentId(99);
        termin.setTerminId(13);
        termin.setBeginn(LocalTime.of(8, 30));
        termin.setEnde(LocalTime.of(16, 35));
        termin.setBezeichnung("NoSQL Datenbanken");
        termin.setDatum(LocalDate.of(2025, Month.JANUARY, 12));
        termin.setTyp("Übungen");

        cassandraDAO.insertTermin(termin);

//        Long startTime = System.currentTimeMillis();
//
//        for (int i = 0; i < 1000; i++) {
//            int dozentId = random.nextInt(dozenten.length);
//            String dozent = dozenten[dozentId];
//
//        }
//
//        Long stopTime = System.currentTimeMillis();
//
//        System.out.println("Die Speicherung hat " + (stopTime - startTime) + " gedauert.");

        cassandraDAO.destroy();

//        try(Cluster cluster = Cluster.builder().addContactPointsWithPorts(nodes).withoutJMXReporting().build()) {
//            try(Session session = cluster.connect("stundenplan")) {
//                MappingManager mappingManager = new MappingManager(session);
//                Mapper<DozentTermine> mapper = mappingManager.mapper(DozentTermine.class);
//                LocalDate date = LocalDate.fromYearMonthDay(2020, 3, 8);
//
//                Long start = new LocalTime(8, 30, 0).toDateTimeToday().getMillis();
//                Long end = new LocalTime(16, 45, 0).toDateTimeToday().getMillis();
//
//                DozentTermine dozentTermine = new DozentTermine("Thorben", 10, date, start, end);
//
//                mapper.delete(dozentTermine);
//                mapper.save(dozentTermine);
//                //mapper.get("Thorben");
//                ResultSet resultSet = session.execute(mapper.getQuery("Thorben"));
//                resultSet.forEach(row -> System.out.println(row.getColumnDefinitions()));
//                mapper.delete(dozentTermine);
//            }
//        }
    }
}