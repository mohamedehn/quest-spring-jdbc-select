package com.wildcodeschool.wildandwizard.repository;

import com.wildcodeschool.wildandwizard.entity.School;
import com.wildcodeschool.wildandwizard.util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolRepository {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    public List<School> findAll() {
        // TODO : find all schools
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //tentative de connexion à la DB + Try/Catch car les requêtes peuvent lever des exceptions
        try {
             connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            //préparation d'une instruction SQL
             statement = connection.prepareStatement(
                    "SELECT * FROM school;"
            );
            //récupération du résultat de la requête SQL
             resultSet = statement.executeQuery();

            // on stocker les wizards dans une list
            List<School> schools = new ArrayList<>();

            //boucle while pour récupérer la liste en fonction de l'id, firstname et lastname
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long capacity = resultSet.getLong("capacity");
                String country = resultSet.getString("country");
                schools.add(new School(id, name, capacity, country));
            }
            //renvoi la list des écoles
            return schools;
        } catch (SQLException e) {
            e.printStackTrace();
            // fermeture des ressources
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public School findById(Long id) {
        // TODO : find a school by id
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
             connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            //préparation d'une instruction SQL
             statement = connection.prepareStatement(
                    "SELECT * FROM school WHERE id LIKE ?;"
            );
            statement.setLong(1, id);
            //récupération du résultat de la requête SQL
            resultSet = statement.executeQuery();
            //on recherche si une école correspond et si c'est le cas on renvoi un nouvel objet school
            if (resultSet.next()) {
                Long schoolId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long capacity = resultSet.getLong("capacity");
                String country = resultSet.getString("country");
                return new School(schoolId, name, capacity, country);
            }
        } catch (SQLException e){
            e.printStackTrace();
            //fermeture des ressources
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    public List<School> findByCountry(String country) {
        // TODO : search schools by country
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //connexion à la DB
        try {
             connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            //préparation d'une instruction SQL
             statement = connection.prepareStatement(
                    "SELECT * FROM school WHERE country LIKE ?;"
            );
            statement.setString(1, country);
            //récupération du résultat de la requête SQL
            resultSet = statement.executeQuery();

            List<School> schools = new ArrayList<>();

            //on recherche si une école correspond à la recherche et si c'est le cas on créer un nouvel objet school
            while (resultSet.next()) {
                Long schoolId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long capacity = resultSet.getLong("capacity");
                String schoolCountry = resultSet.getString("country");
                schools.add(new School(schoolId, name, capacity, schoolCountry));
            }
            return schools;
        } catch (SQLException e){
            e.printStackTrace();
            //fermeture des ressources JDBC
        }finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }
}
