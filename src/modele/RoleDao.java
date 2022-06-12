package modele;


import DB.DbUtil;

import Entities.Privilege;
import Entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


public class RoleDao {
    private Connection connection;
    private PrivilegeDao pdao = new PrivilegeDao();

    public ArrayList<Role> getAllRoles() {


        ArrayList<Role> listRoles = new ArrayList<Role>();
        String query = " SELECT * FROM ROLE ORDER BY ID_ROLE ASC";

        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Role role = new Role();
                role.setIdrole(rs.getInt("ID_ROLE"));
                role.setLibrole(rs.getString("LIB_ROLE"));
                role.setSections(pdao.getPrivilegesByRole(role.getIdrole()));
                listRoles.add(role);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllRoles " + e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listRoles;
    }
    

    public Role ajoutRole(Role r) {
        boolean status = false;
        String statement = "INSERT INTO ROLE (LIB_ROLE) values (?) ";
        
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, r.getLibrole());
            ps.executeUpdate();
            status = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return r;
    }

    public Role updateRole(Role r) {

        String statement = "UPDATE ROLE SET LIB_ROLE=? WHERE ID_ROLE=?";
        pdao.deletePrivilegeRole(r.getIdrole());
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, r.getLibrole());
            ps.setInt(2, r.getIdrole());
            ps.executeUpdate();
            for (int i =0; i< r.getSections().size(); i++){
                pdao.addPrivileges(r.getSections().get(i).getIdprivilege(), r.getIdrole());
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return r;
    }

    public int deleteRole(int id) {
        String statement = "DELETE FROM ROLE WHERE ID_ROLE=?";
        pdao.deletePrivilege(id);
        int state =0;
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, id);
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

    public Role getRoleById(int idrole) {

        String query = " SELECT * FROM ROLE WHERE ID_ROLE= " + idrole + "";
        Role role = new Role();
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                role.setIdrole(rs.getInt("ID_ROLE"));
                role.setLibrole(rs.getString("LIB_ROLE"));
                role.setSections(pdao.getPrivilegesByRole(role.getIdrole()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return role;
    }


    public boolean CheckNomRoleExist(String role) {

        boolean more = false;
        String searchQuery =
            "SELECT * FROM ROLE WHERE  LIB_ROLE = '" + role + "'";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);
            more = rs.next();
            if (!more) {
                System.out.println("Rôle doesn't exist");
                more = false;
                return more;

            } else {
                more = true;
                return more;

            }

        } catch (Exception e) {

            System.out.println("Erreur   CheckNomRoleExist " + e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return more;

    }
    
    public Role getRoleByLibelle(String libRole){
        
        String query= 
        "SELECT * FROM ROLE WHERE  LIB_ROLE = '" + libRole + "'";
        Role role = new Role();
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                role.setIdrole(rs.getInt("ID_ROLE"));
                role.setLibrole(rs.getString("LIB_ROLE"));
                role.setSections(pdao.getPrivilegesByRole(role.getIdrole()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return role;
    }
    
}
