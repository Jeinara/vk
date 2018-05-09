package work.SecondProcess;

import work.record.Record;

import java.sql.*;

public class DataBase {
    private final String url = "jdbc:mysql://127.0.0.1:3306/vk?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    private final String user = "root";
    private final String password = "root";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;

    DataBase() throws SQLException {
        System.out.println("Creating connection to database...");
        con = DriverManager.getConnection(url, user, password);
        System.out.println("Successfully");
        stmt = con.createStatement();
        createTables();
    }

    public void connect(){
        try {
            System.out.println("Creating connection to database...");
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            createTables();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

    public  void createRecord(Record rec) throws SQLException {
        String queryForPost = "INSERT INTO post (post_id, text) \n" +
                " VALUES (' "+rec.getId()+" ',' "+rec.getText()+" ');";
        String queryForURL = "INSERT INTO url (post_id, url) \n" +
                " VALUES (' "+rec.getId()+" ',' ";
        String queryForImg = "INSERT INTO img (post_id, img) \n" +
                " VALUES (' "+rec.getId()+" ',' ";
        stmt.executeUpdate(queryForPost);
        for (String url: rec.getHref()) {
            stmt.executeUpdate(queryForURL+url+" ');");
        }
        for (String img: rec.getImg()) {
            stmt.executeUpdate(queryForImg+img+" ');");
        }
    }

    private void createTables() throws SQLException {
        String postTable = "CREATE TABLE IF NOT EXISTS vk.post\n" +
                "(\n" +
                "id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "post_id VARCHAR(50) UNIQUE,\n" +
                "text LONGTEXT\n" +
                ");";
        String imgTable = "CREATE TABLE IF NOT EXISTS `vk`.`img`\n" +
                "(\n" +
                "post_id varchar(50),\n" +
                "FOREIGN KEY (post_id) REFERENCES `vk`.`post`(post_id),\n" +
                "img VARCHAR(10000)\n" +
                ");";
        String urlTable = "CREATE TABLE IF NOT EXISTS `vk`.`url`\n" +
                "(\n" +
                "post_id varchar(50),\n" +
                "FOREIGN KEY (post_id) REFERENCES `vk`.`post`(post_id),\n" +
                "url VARCHAR(10000)\n" +
                ");";
        String getId = "SELECT post_id FROM post";
        stmt.execute(postTable);
        stmt.execute(imgTable);
        stmt.execute(urlTable);
        rs = stmt.executeQuery(getId);
        while (rs.next()) {
            String str = rs.getString(1);
            Main.idInDB.add(str.substring(1,str.length()-1));
        }
    }
}
