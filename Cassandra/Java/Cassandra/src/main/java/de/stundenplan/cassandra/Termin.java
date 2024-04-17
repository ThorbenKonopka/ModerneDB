package de.stundenplan.cassandra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Termin {
    private int terminId;
    private int dozentId;
    private Set<Integer> studenten = new HashSet<>();
    private String dozent;
    private LocalDate datum;
    private LocalTime beginn;
    private LocalTime ende;
    private String bezeichnung;
    private String typ;
    private String teilnahmestatus;
    private int veranstaltungId;

    private int modulId;

    public int getModulId() {
        return modulId;
    }

    public void setModulId(int modulId) {
        this.modulId = modulId;
    }

    public int getVeranstaltungId() {
        return veranstaltungId;
    }

    public void setVeranstaltungId(int veranstaltungId) {
        this.veranstaltungId = veranstaltungId;
    }

    public String getTeilnahmestatus() {
        return teilnahmestatus;
    }

    public void setTeilnahmestatus(String teilnahmestatus) {
        this.teilnahmestatus = teilnahmestatus;
    }

    public int getTerminId() {
        return terminId;
    }

    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    public int getDozentId() {
        return dozentId;
    }

    public void setDozentId(int dozentId) {
        this.dozentId = dozentId;
    }

    public Set<Integer> getStudenten() {
        return studenten;
    }

    public void setStudenten(Set<Integer> studenten) {
        this.studenten = studenten;
    }

    public String getDozent() {
        return dozent;
    }

    public void setDozent(String dozent) {
        this.dozent = dozent;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getBeginn() {
        return beginn;
    }

    public void setBeginn(LocalTime beginn) {
        this.beginn = beginn;
    }

    public LocalTime getEnde() {
        return ende;
    }

    public void setEnde(LocalTime ende) {
        this.ende = ende;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @Override
    public String toString() {
        return "Termin{" +
                "terminId=" + terminId +
                ", dozentId=" + dozentId +
                ", studenten=" + studenten +
                ", dozent='" + dozent + '\'' +
                ", datum=" + datum +
                ", beginn=" + beginn +
                ", ende=" + ende +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", typ='" + typ + '\'' +
                ", teilnahmestatus='" + teilnahmestatus + '\'' +
                '}';
    }
}
