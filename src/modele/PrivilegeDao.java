package modele;

import DB.DbUtil;

import Entities.Privilege;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class PrivilegeDao {
    
    private Connection connection;
    /* 
     * crd des privileges (create , read, delete; c'est tous) : 
     */
    
    public ArrayList<Privilege> getPrivilegesByRole(int idrole) {
        
        ArrayList<Privilege> lstprv= new ArrayList<Privilege>();
        String query =
            " SELECT p.* FROM PRIVILEGE p , ROLE r, RELATION_RP rrp WHERE r.ID_ROLE= rrp.ID_ROLE and " +
            "p.ID_SECTION= rrp.ID_SECTION and r.ID_ROLE=" + idrole + "";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Privilege privilege = new Privilege();
                privilege.setLibprivilege(rs.getString("LIB_SECTION"));
                privilege.setIdprivilege(rs.getInt("ID_SECTION"));
                lstprv.add(privilege);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllRoles " + e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return lstprv;
    }
    
    public ArrayList<Integer> getIdPrivilegesByRole(int idrole) {
        
        ArrayList<Integer> lstprv= new ArrayList<Integer>();
        String query =
            " SELECT p.ID_SECTION FROM PRIVILEGE p , ROLE r, RELATION_RP rrp WHERE r.ID_ROLE= rrp.ID_ROLE and " +
            "p.ID_SECTION= rrp.ID_SECTION and r.ID_ROLE=" + idrole + "";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                lstprv.add(rs.getInt("ID_SECTION"));
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllRoles " + e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return lstprv;
    }
    
    public ArrayList<Privilege> getAllPrivileges() {
        
        ArrayList<Privilege> lstprv= new ArrayList<Privilege>();
        String query =
            " SELECT * FROM PRIVILEGE order by id_section asc";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Privilege privilege = new Privilege();
                privilege.setIdprivilege(rs.getInt("ID_SECTION"));
                privilege.setLibprivilege(rs.getString("LIB_SECTION"));
                lstprv.add(privilege);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllPrivileges " + e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return lstprv;
    }
    
    public int deletePrivilegeRole(int idrole) {
        String statement = "DELETE FROM RELATION_RP WHERE ID_ROLE=?";
        int state =0;
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, idrole);
            state =  ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return state;
    }
    
    public int deletePrivilege(int idprivilege) {
        String statement = "DELETE FROM RELATION_RP WHERE ID_SECTION=?";
        int state =0;
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, idprivilege);
            state =  ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return state;
    }
    
    public boolean addPrivileges(int idPrivilege, int idrole) {
        boolean status = false; 
        String statement = "INSERT INTO RELATION_RP (id_role, id_section) values ("+idrole+","+idPrivilege+") ";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.executeUpdate();
            status = true;
        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return status;
    }
}
