package Entities;



public class Rubrique {
    
    int idrubrique;
    String codeRubrique;
    String ordreEdition;
    String libelle;
    String codeax;
    int idannexe;

    public Rubrique(String codeRubrique, String ordreEdition, String libelle,
                    int idannexe) {
        super();
        this.codeRubrique = codeRubrique;
        this.ordreEdition = ordreEdition;
        this.libelle = libelle;
        this.idannexe = idannexe;
    }

    public Rubrique() {
    }


    public int getIdrubrique() {
        return idrubrique;
    }

    public void setCodeRubrique(String codeRubrique) {
        this.codeRubrique = codeRubrique;
    }

    public String getCodeRubrique() {
        return codeRubrique;
    }

    public void setOrdreEdition(String ordreEdition) {
        this.ordreEdition = ordreEdition;
    }

    public String getOrdreEdition() {
        return ordreEdition;
    }

    public void setIdrubrique(int idrubrique) {
        this.idrubrique = idrubrique;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdannexe(int idannexe) {
        this.idannexe = idannexe;
    }

    public int getIdannexe() {
        return idannexe;
    }

    public void setCodeax(String codeax) {
        this.codeax = codeax;
    }

    public String getCodeax() {
        return codeax;
    }
}
