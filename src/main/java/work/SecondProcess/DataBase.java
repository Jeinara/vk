package work.SecondProcess;

import work.record.Record;

import java.sql.*;

public class DataBase implements AutoCloseable {
    private Connection con;
    private Statement stmt;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/vk?autoReconnect=true&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL_TABLE = "CREATE TABLE IF NOT EXISTS `vk`.`url`\n" +
            "(\n" +
            "post_id varchar(50),\n" +
            "FOREIGN KEY (post_id) REFERENCES `vk`.`post`(post_id),\n" +
            "url VARCHAR(10000)\n" +
            ");";
    private static final String IMG_TABLE = "CREATE TABLE IF NOT EXISTS `vk`.`img`\n" +
            "(\n" +
            "post_id varchar(50),\n" +
            "FOREIGN KEY (post_id) REFERENCES `vk`.`post`(post_id),\n" +
            "img VARCHAR(10000)\n" +
            ");";
    private static final String POST_TABLE = "CREATE TABLE IF NOT EXISTS vk.post\n" +
            "(\n" +
            "id INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "post_id VARCHAR(50) UNIQUE,\n" +
            "text LONGTEXT\n" +
            ");";
    public static final String GET_ID = "SELECT post_id FROM post";

    DataBase() throws SQLException {
        System.out.println("Creating connection to database...");
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Successfully");
        stmt = con.createStatement();
        createTables();
    }

    public void connect(){
        try {
            System.out.println("Creating connection to database...");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
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
                " VALUES (' "+rec.getId()+" ',' "+rec.getText().replace("'","\\'")+" ');";
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
        stmt.execute(POST_TABLE);
        stmt.execute(IMG_TABLE);
        stmt.execute(URL_TABLE);
        try (ResultSet rs = stmt.executeQuery(GET_ID)) {
            while (rs.next()) {
                String str = rs.getString(1);
                Main.idInDB.add(str.substring(1, str.length() - 1));
            }
        }
    }

    @Override
    public void close() throws SQLException {
        con.close();
        stmt.close();
    }
}
