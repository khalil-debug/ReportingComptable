package Entities;

public class Banque {

    int idbanque;
    String codebanque;
    String libBanque;
    String abrvbanque;


    public Banque() {
        super();
    }

    public Banque(String codebanque, String libBanque, String abrvbanque) {
        this.codebanque=codebanque;
        this.libBanque=libBanque;
        this.abrvbanque=abrvbanque;
    }

    public int getIdbanque() {
        return idbanque;
    }

    public void setCodebanque(String codebanque) {
        this.codebanque = codebanque;
    }

    public String getCodebanque() {
        return codebanque;
    }

    public void setLibBanque(String libBanque) {
        this.libBanque = libBanque;
    }

    public String getLibBanque() {
        return libBanque;
    }

    public void setIdbanque(int idbanque) {
        this.idbanque = idbanque;
    }

    public void setAbrvbanque(String abrvbanque) {
        this.abrvbanque = abrvbanque;
    }

    public String getAbrvbanque() {
        return abrvbanque;
    }
}
