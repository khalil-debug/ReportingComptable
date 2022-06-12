package Entities;

import java.security.Key;

public class LoginBean extends Utilisateur{
    private String pseudo, motAcces;  
    int idrole;
      
    public String getPseudo() {  
        return pseudo;  
    }  
      
    public void setPseudo(String pseudo) {  
        this.pseudo = pseudo;  
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }

    public int getIdrole() {
        return idrole;
    }

    public void setMotAcces(String motAcces) {
        this.motAcces = motAcces;
    }

    public String getMotAcces() {
        return motAcces;
    }
}
