package sem4.moderneDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Historie {
    private String aenderungsart;
    private String aenderungsdatum;

    public Historie() {
    }

    public Historie(String aenderungsart, String aenderungsdatum) {
        this.aenderungsart = aenderungsart;
        this.aenderungsdatum = aenderungsdatum;
    }

    public String getAenderungsart() {
        return aenderungsart;
    }

    public void setAenderungsart(String aenderungsart) {
        this.aenderungsart = aenderungsart;
    }

    public String getAenderungsdatum() {
        return aenderungsdatum;
    }

    public void setAenderungsdatum(String aenderungsdatum) {
        this.aenderungsdatum = aenderungsdatum;
    }
}
