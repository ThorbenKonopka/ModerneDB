package sem4.moderneDB;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        StdHttpClient.Builder builder = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("admin")
                .password("secret");

        CouchDbInstance dbInstance = new StdCouchDbInstance(builder.build());
        CouchDbConnector db = dbInstance.createConnector("stundenplan", true);

        try {
            Student student = new Student();
            Veranstaltung veranstaltung = new Veranstaltung();
            student.setName("Bubi");
            student.setGruppe("A-F");
            veranstaltung.setVeranstaltungname("Grundlagen OO-Programmiersprachen");
            veranstaltung.setDozent(new Dozent("Johannes Schmidt"));

            // CREATE Operation
            long start = System.nanoTime();
            db.create(student);
            db.create(veranstaltung);
            long ende = System.nanoTime();
            System.out.println("Dauer Create: " + (ende - start) / 1e6 + " ms.");

            // Nach dem Erstellen die IDs und Revisionen ausgeben
            System.out.println("Erstellt: student ID" + student.getId() + ", Rev: " + student.getRevision());
            System.out.println("Erstelt:  veranstaltung ID " + veranstaltung.getId() + ", Rev: " + veranstaltung.getRevision());

            // READ Operation
            start = System.nanoTime();
            Student readStudent = db.get(Student.class, student.getId());
            Veranstaltung readVeranstaltung = db.get(Veranstaltung.class, veranstaltung.getId());
            ende = System.nanoTime();
            System.out.println("Dauer Read: " + (ende - start) / 1e6 + " ms.");

            // UPDATE Operation
            start = System.nanoTime();
            readStudent.setName("Bubi Bubi");
            db.update(readStudent);
            ende = System.nanoTime();
            System.out.println("Dauer Update: " + (ende - start) / 1e6 + " ms.");

            // DELETE Operation
            start = System.nanoTime();
            db.delete(readStudent);
            db.delete(readVeranstaltung);
            ende = System.nanoTime();
            System.out.println("Dauer Delete:: " + (ende - start) / 1e6 + " ms.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            List<Student> erstellteStudenten = new ArrayList<>();
            long start = System.nanoTime();

            // Erstellen von 100 Studenten
            for (int i = 0; i < 100; i++) {
                Student student = new Student();
                student.setName("Student " + (i + 1));
                student.setGruppe("A-F");
                db.create(student);
                erstellteStudenten.add(student);
            }

            long ende = System.nanoTime();
            System.out.println("Dauer Create 100: " + (ende - start) / 1e6 + " ms.");

            // Löschen von 100 Studenten
            start = System.nanoTime();
            for (Student student : erstellteStudenten) {
                db.delete(student);
            }
            ende = System.nanoTime();
            System.out.println("Dauer Delete 100: " + (ende - start) / 1e6 + " ms.");

            // Bulk
             erstellteStudenten = new ArrayList<>();
             start = System.nanoTime();
            for (int i = 0; i < 100; i++) {
                Student student = new Student();
                student.setName("Student " + (i + 1));
                student.setGruppe("A-F");
                erstellteStudenten.add(student);
            }
            db.executeBulk(erstellteStudenten);
            ende = System.nanoTime();
            System.out.println("Dauer Create 100 Bulk " +  (ende - start) / 1e6 + " ms.");

            start = System.nanoTime();
            for(Student s : erstellteStudenten){
                s.markAsDeleted();
            }
            db.executeBulk(erstellteStudenten);
            ende = System.nanoTime();
            System.out.println("Dauer Delete 100 Bulk " +  (ende - start) / 1e6 + " ms.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

