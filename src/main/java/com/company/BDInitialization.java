package com.company;

import com.company.storage.DBStorage;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BDInitialization {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/musiclib334";
            String dbUserName = "postgres";
            String dbPassword = "092327asd";
            Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new BufferedReader(new FileReader("init.sql"));
            sr.runScript(reader);
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users VALUES('admin',?,1)");
            preparedStatement.setString(1,DBStorage.getHash("admin"));
            preparedStatement.execute();
        } catch (SQLException | NoSuchAlgorithmException throwable) {
            throwable.printStackTrace();
        }
    }
}
