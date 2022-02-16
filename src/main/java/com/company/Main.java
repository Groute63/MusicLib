package com.company;

import com.company.ui.View;

import java.io.FileNotFoundException;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        new View().start();
    }
}
