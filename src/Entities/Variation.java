package Entities;

public class Variation {
    public Variation() {
        super();
    }
    
    String codeRub;
    String libRub;
    String[] montants;

    public void setCodeRub(String codeRub) {
        this.codeRub = codeRub;
    }

    public String getCodeRub() {
        return codeRub;
    }

    public void setLibRub(String libRub) {
        this.libRub = libRub;
    }

    public String getLibRub() {
        return libRub;
    }

    public void setMontants(String[] montants) {
        this.montants = montants;
    }

    public String[] getMontants() {
        return montants;
    }
}
