package Entities;

public class Colonne {
    
    int idcolonne;
    String codecolonne;
    String libcolonne;
    String codeax;
    int idannexe;
    
    public Colonne() {
        super();
    }

    public Colonne(String codeColonne, String libColonne) {
        this.codecolonne=codeColonne;
        this.libcolonne=libColonne;
    }

    public int getIdcolonne() {
        return idcolonne;
    }

    public void setCodecolonne(String codecolonne) {
        this.codecolonne = codecolonne;
    }

    public String getCodecolonne() {
        return codecolonne;
    }

    public void setLibcolonne(String libcolonne) {
        this.libcolonne = libcolonne;
    }

    public String getLibcolonne() {
        return libcolonne;
    }
    
    public void setIdcolonne(int idcolonne) {
        this.idcolonne = idcolonne;
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
