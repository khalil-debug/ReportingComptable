package Entities;

import java.sql.Date;

public class Mouvement {
    
    int idmouvement;
    float montant;
    int idannexe;
    int idcolonne;
    int idrubrique;
    Date dateMVT;
    int idbanque;
    
    public Mouvement() {
        super();
    }

    public int getIdmouvement() {
        return idmouvement;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public float getMontant() {
        return montant;
    }

    public void setIdannexe(int idannexe) {
        this.idannexe = idannexe;
    }

    public int getIdannexe() {
        return idannexe;
    }

    public void setIdcolonne(int idcolonne) {
        this.idcolonne = idcolonne;
    }

    public int getIdcolonne() {
        return idcolonne;
    }

    public void setIdrubrique(int idrubrique) {
        this.idrubrique = idrubrique;
    }

    public int getIdrubrique() {
        return idrubrique;
    }

    public void setDateMVT(Date dateMVT) {
        this.dateMVT = dateMVT;
    }

    public Date getDateMVT() {
        return dateMVT;
    }

    public void setIdmouvement(int idmouvement) {
        this.idmouvement = idmouvement;
    }

    public void setIdbanque(int idbanque) {
        this.idbanque = idbanque;
    }

    public int getIdbanque() {
        return idbanque;
    }
}
