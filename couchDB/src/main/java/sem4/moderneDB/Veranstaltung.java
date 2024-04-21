package sem4.moderneDB;

import org.ektorp.support.CouchDbDocument;
import java.util.List;

public class Veranstaltung extends CouchDbDocument {
    private String typ;
    private String datum;
    private String beginn;
    private String ende;
    private String veranstaltungname;
    private Dozent dozent;
    private String gruppe;
    private List<Historie> historie;
    private List<Anwesenheit> anwesenheit;

    public Veranstaltung() {
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getBeginn() {
        return beginn;
    }

    public void setBeginn(String beginn) {
        this.beginn = beginn;
    }

    public String getEnde() {
        return ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }

    public String getVeranstaltungname() {
        return veranstaltungname;
    }

    public void setVeranstaltungname(String veranstaltungname) {
        this.veranstaltungname = veranstaltungname;
    }

    public Dozent getDozent() {
        return dozent;
    }

    public void setDozent(Dozent dozent) {
        this.dozent = dozent;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public List<Historie> getHistorie() {
        return historie;
    }

    public void setHistorie(List<Historie> historie) {
        this.historie = historie;
    }

    public List<Anwesenheit> getAnwesenheit() {
        return anwesenheit;
    }

    public void setAnwesenheit(List<Anwesenheit> anwesenheit) {
        this.anwesenheit = anwesenheit;
    }
}
