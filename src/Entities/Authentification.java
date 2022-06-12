package Entities;

public class Authentification {
    
    int idsession;
    String debutSession;
    String finSession;
    String periodicite;
    int idutilisateur;
    
    
    public Authentification() {
        super();
    }

    public int getIdsession() {
        return idsession;
    }

    public void setDebutSession(String debutSession) {
        this.debutSession = debutSession;
    }

    public String getDebutSession() {
        return debutSession;
    }

    public void setFinSession(String finSession) {
        this.finSession = finSession;
    }

    public String getFinSession() {
        return finSession;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public void setIdutilisateur(int idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdsession(int idsession) {
        this.idsession = idsession;
    }
}
