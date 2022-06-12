package Entities;

import java.sql.Date;

import java.util.List;

public class Declaration {
    
    String codeRub;
    String libRub;
    double[] montants;


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

    public void setMontants(double[] montants) {
        this.montants = montants;
    }

    public double[] getMontants() {
        return montants;
    }
}
