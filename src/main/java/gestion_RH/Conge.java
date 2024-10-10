package gestion_RH;

import java.util.Date;

public class Conge {
    private int id;
    private int idEmploye;
    private Date dateDebut;
    private Date dateFin;
    private String raison;
    private String statut;

    // Constructeurs

    public Conge() {
        // Constructeur par défaut
    }

    public Conge(int id, int idEmploye, Date dateDebut, Date dateFin, String raison, String statut, String commentaireAdmin) {
        this.id = id;
        this.idEmploye = idEmploye;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.raison = raison;
        this.statut = statut;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

}

