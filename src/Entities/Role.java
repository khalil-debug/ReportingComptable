package Entities;

import java.util.ArrayList;

public class Role {
    
    int idrole;
    String librole;
    ArrayList<Privilege> sections;
    
    public Role() {
        super();
    }

    public int getIdrole() {
        return idrole;
    }

    public void setLibrole(String librole) {
        this.librole = librole;
    }

    public String getLibrole() {
        return librole;
    }

    public void setIdrole(int idrole) {
        this.idrole = idrole;
    }

    public void setSections(ArrayList<Privilege> sections) {
        this.sections = sections;
    }

    public ArrayList<Privilege> getSections() {
        return sections;
    }
}
