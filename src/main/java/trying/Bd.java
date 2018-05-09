package trying;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bd {

        // JDBC URL, username and password of MySQL server
        private static final String url = "jdbc:mysql://127.0.0.1:3306/test?autoReconnect=true&useSSL=false&serverTimezone=UTC";
        private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        private static final String user = "root";
        private static final String password = "root";

        // JDBC variables for opening and managing connection
        private static Connection con;
        private static Statement stmt;
        private static ResultSet rs;

        public static void main(String args[]) throws ClassNotFoundException {
            String query = "select count(*) from books";

            try {
                System.out.println("Registering JDBC driver...");
//                Class.forName(JDBC_DRIVER);
                // opening database connection to MySQL server
                System.out.println("Creating connection to database...");
                con = DriverManager.getConnection(url, user, password);

                // getting Statement object to execute query
                stmt = con.createStatement();

                // executing SELECT query
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Total number of books in the table : " + count);
                }

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                //close connection ,stmt and resultset here
                try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            }
        }
}

