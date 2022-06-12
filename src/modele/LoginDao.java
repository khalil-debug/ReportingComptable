package modele;


import DB.Crypto;
import DB.DbUtil;

import Entities.LoginBean;
import Entities.Role;
import Entities.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao {
    private Connection connection;


    public boolean validate(LoginBean bean)

    {
        Crypto crypto = new Crypto();

        boolean status = false;

        String query =
            "SELECT * FROM UTILISATEUR WHERE MATRICULE = ? AND MOT_DE_PASSE =?";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, bean.getPseudo());
            ps.setString(2, crypto.encryptWord(bean.getMotAcces()));
            ResultSet rs = ps.executeQuery();
            status = rs.next();
        } catch (SQLException e) {

            System.out.println("Erreur au niveau de methode validate " + e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return status;
    }
    
    public LoginBean session(LoginBean bean){
        Crypto crypto = new Crypto();
        String query = " SELECT * FROM UTILISATEUR WHERE MATRICULE = ? AND MOT_DE_PASSE =?  ";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, bean.getPseudo());
            ps.setString(2, crypto.encryptWord(bean.getMotAcces()));
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                bean.setIdrole(rs.getInt("ID_ROLE"));
                bean.setPseudo(rs.getString("MATRICULE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return bean;
        }
    }


