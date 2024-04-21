package sem4.moderneDB;

public class Anwesenheit {
    private String matrikelnummer;
    private String name;
    private String status;

    public Anwesenheit() {
    }

    public Anwesenheit(String matrikelnummer, String name, String status) {
        this.matrikelnummer = matrikelnummer;
        this.name = name;
        this.status = status;
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}