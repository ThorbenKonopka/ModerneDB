package sem4.moderneDB;

import org.ektorp.support.CouchDbDocument;
import java.util.List;

public class Student extends CouchDbDocument {
    private String typ;
    private String name;
    private String vorname;
    private String matrikelnummer;
    private String gruppe;
    private String jahrgang;
    private List<String> belegteVeranstaltungen;
    private boolean _deleted;

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public String getJahrgang() {
        return jahrgang;
    }

    public void setJahrgang(String jahrgang) {
        this.jahrgang = jahrgang;
    }

    public List<String> getBelegteVeranstaltungen() {
        return belegteVeranstaltungen;
    }

    public void setBelegteVeranstaltungen(List<String> belegteVeranstaltungen) {
        this.belegteVeranstaltungen = belegteVeranstaltungen;
    }

    public void markAsDeleted() {
        this._deleted = true;
    }

    public boolean is_deleted() {
        return _deleted;
    }
}

