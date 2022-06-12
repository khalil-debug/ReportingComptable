package Entities;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class Utilisateur {

    int idutilisateur;
    String nom;
    String prenom;
    String email;
    String motAcces;
    String departement;
    int idrole;
    String librole;
    String matricule;

    public Utilisateur() {
        super();
    }

    public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    


    
 

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }

    public int getIdrole() {
        return idrole;
    }

    public void setIdutilisateur(int idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setLibrole(String librole) {
        this.librole = librole;
    }

    public String getLibrole() {
        return librole;
    }

    public String getMotAcces() {
        return motAcces;
    }

    public void setMotAcces(String motAcces) {
        this.motAcces = motAcces;
    }
}
