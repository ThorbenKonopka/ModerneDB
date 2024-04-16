package de.stundenplan.cassandra;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class Main {
    private static LocalDate date = LocalDate.of(2024, Month.JUNE, 1);
    private static int terminId = 1;
    private static int veranstalltungId = 1;
    private static int modulId = 1;
    public static void main(String[] args) {
        ConnectionPool.init();
        Set<Termin> termine = new HashSet<>();
        termine.addAll(createTestdaten());
        //termine.forEach(termin -> System.out.println(termin));

        MySqlDAO mySqlDAO = new MySqlDAO();
        long startTimeMysql = System.currentTimeMillis();
        try {
            MySqlDAO.initDatabase(termine);
            MySqlDAO.insertTermine(termine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        long endTimeMysql = System.currentTimeMillis();

        System.out.println("Mysql hat zum speichern: " + (endTimeMysql - startTimeMysql) + "ms gebraucht. :D");

        CassandraDAO cassandraDAO = new CassandraDAO();
        cassandraDAO.init();

        long startTimeCassandra = System.currentTimeMillis();

        for(Termin termin : termine) {
            cassandraDAO.insertTermin(termin);
        }

        long endTimeCassandra = System.currentTimeMillis();

        System.out.println("Cassandra hat zum speichern: " + (endTimeCassandra - startTimeCassandra) + "ms gebraucht. :D");

        cassandraDAO.destroy();
        ConnectionPool.destroy();
    }

    private static Set<Termin> createTestdaten() {
        Set<Termin> termine = new HashSet<>();
        termine.addAll(createTermineForJahrgang1());
        termine.addAll(createTermineForJahrgang2());
        termine.addAll(createTermineForJahrgang3());
        termine.addAll(createTermineForJahrgang4());
        termine.addAll(createTermineForJahrgang5());
        termine.addAll(createTermineForJahrgang6());
        termine.addAll(createTermineForJahrgang7());
        termine.addAll(createTermineForJahrgang8());
        termine.addAll(createTermineForJahrgang9());
        termine.addAll(createTermineForJahrgang10());
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang1() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 1; i <= 30; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang2() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 31; i <= 60; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang3() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 61; i <= 90; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang4() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 91; i <= 120; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang5() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 121; i <= 150; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang6() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 151; i <= 180; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang7() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 181; i <= 210; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang8() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 211; i <= 240; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang9() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 241; i <= 270; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Set<Termin> createTermineForJahrgang10() {
        Set<Termin> termine = new HashSet<>();

        String[] moduleSemester1 = {
                "Internetbasierte Anwendungen",
                "Grundlagen OO-Programmiersprachen",
                "Datenbankgrundlagen",
                "Mathematik für Informatiker 1",
                "Grundlagen betriebswirtschaftlicher Entscheidungstatbestaende",
                "Arbeits-, Lern- und Praesentationstechnik 1",
                "Englisch 1"
        };

        Set<Integer> studenten = new HashSet<>();
        for(int i = 271; i <= 300; i++) {
            studenten.add(i);
        }

        for(String modul : moduleSemester1) {
            int mId = modulId++;
            Random random = new Random();
            int dozent = random.nextInt(11) + 1;
            for(int i = 0; i < 4; i++) {
                termine.add(createTermin(dozent, modul, "Vorlesungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Praktika", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
            for(int i = 0; i < 3; i++) {
                termine.add(createTermin(dozent, modul, "Uebungen", studenten, veranstalltungId, mId));
            }
            veranstalltungId++;
        }
        return termine;
    }

    private static Termin createTermin(int dozentId, String modul, String typ, Set<Integer> studenten, int veranstaltungId, int modulId) {
        LocalTime beginn = LocalTime.of(8, 30);
        LocalTime ende = LocalTime.of(15, 45);
        String[] dozenten = {"Thorben", "Lukas", "Koenigsmann", "Max", "David", "Lisa", "Stefan", "Daniel", "Kaktus", "XE64", "TEE"};

        Termin termin = new Termin();
        termin.setTerminId(terminId++);
        termin.setBeginn(beginn);
        termin.setEnde(ende);
        termin.setDozentId(dozentId);
        termin.setDozent(dozenten[dozentId - 1]);
        termin.setBezeichnung(modul);
        termin.setTyp(typ);
        termin.setStudenten(studenten);
        termin.setDatum(date);
        date = date.plusDays(2);
        termin.setVeranstaltungId(veranstaltungId);
        termin.setModulId((modulId%7)+1);

        return termin;
    }
}