package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbUtil {


    private static Connection conn = null;

    public void OpenConnection() {
        try {
            conn = getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (conn != null)
                return conn;
            else {

                try {
                    //Le pilote d�une base de donn�es est tout simplement une classe Java instance de l�interface java.sql.DriverManager.
                    // il est possible de charger une classe Java explicitement dans le code, par appel de la m�thode Class.forName().
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    //Ensuite ouverture de la connexion proprement dite :
                    //Connection con = DriverManager.getConnection(chaineDeConnexion, user, password) ;
                    //L�objet cha�neDeConnexion est de la classe String. C�est une concat�nation des �l�ments suivants :
                    // jdbc:oracle:thin : indique le protocole utilis� pour acc�der � la base de donn�e. Ce protocole est propre � chaque base.ex : jdbc:mysql
                    // localhost:1521 : indique le nom d'h�te qui h�berge la base de donn�es, ainsi que le port d'acc�s.
                    // nom_de_la_base : le nom de la base de donn�es � laquelle on souhaite se connecter.


                    Connection conn =
                        DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
                                                    "c##khalil", "1234");
                    //La connexion � la base consiste � demander au DriverManager un objet de type Connection, par l�appel de la m�thode getConnection().
                    //L� il n�y a qu�une seule fa�on de le faire : passer l�URL de la base, un nom de connexion et un mot de passe valides.
                    //L�appel de cette m�thode renvoie un objet instance d�une classe qui impl�mente l�interface Connection.

                    //http://blog.paumard.org/cours/jdbc/chap03-connexion-connexion.html

                    conn.setAutoCommit(false);
                    return conn;


                } catch (Exception e) {
                    System.err.println("Error looking up Data Source from Factory: " +
                                       e.getMessage());
                }
                return conn;


            }

        } catch (Exception e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection checkConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                //System.out.println("OPEN CONNECTION ");
                Class.forName("oracle.jdbc.driver.OracleDriver");

                Connection conn =
                    DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
                                                "c##khalil", "1234");
                conn.setAutoCommit(false);

                return conn;
            } else
                return conn;
        } catch (SQLException jndie) {
            jndie.printStackTrace();
            System.out.println("EXCEPTION--->" + jndie.getMessage());
            return conn;
        } catch (Exception e) {
            System.err.println("Error looking up Data Source from Factory: " +
                               e.getMessage());
        }


        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                //System.out.println(" close Connection");
                System.err.println("SQLException closeConnection : " +
                                   e.getMessage());
            }

            finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) { /* handle close exception, quite usually ignore */

                        System.err.println("Error looking up Data Source from Factory: " +
                                           e.getMessage());
                    }
                }
            }


            conn = null;
        }


    }

    public static Connection checkConnection(Connection conn) {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn =
DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "c##khalil",
                            "1234");
                conn.setAutoCommit(false);
                return conn;
            } else
                return conn;


        }

        catch (SQLException jndie) {
            jndie.printStackTrace();
            System.out.println("EXCEPTION--->" + jndie.getMessage());
            return conn;
        } catch (Exception e) {
            System.err.println("Error looking up Data Source from Factory: " +
                               e.getMessage());

        }


        return conn;
    }

    public static void cleanup(Connection con) {
        try {
            //  Connection est une interface.
            // L�objet que nous obtenons est donc une impl�mentation de cette interface par une classe que nous ne connaissons pas,
            // et que nous n�avons pas besoin de conna�tre. Cette classe est propre � chaque serveur de base de donn�es,
            // et en g�n�ral �crite par les d�veloppeurs de cette base de donn�es.

            // L�ouverture et la fermeture d�une connexion � une base de donn�es sont deux choses qui doivent �tre trait�es avec attention.
            // L�ouverture est un processus co�teux. Sur certains serveurs, elle peut prendre plusieurs secondes.
            // Une connexion ouverte sur une base de donn�es est une ressource consomm�e.
            // Comme toutes les ressources d�un syst�me d�exploitation, elle est � rare �, en tout cas,
            // il existe un nombre maximal de connexions ouvertes � un instant donn�. Cela rend indispensable la fermeture de toute connexion ouverte.


            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanup(ResultSet rs, Statement ps) {
        try {

            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
