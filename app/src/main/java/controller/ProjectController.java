/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author Particular
 */
public class ProjectController {

    public void save(Project project) {

        String sql = ("INSERT INTO projects(name, description, createdAt, updatedAt)"
                + "VALUES(?, ?, ?, ?)");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //Preparando a query
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            //executando a query
            statement.execute();
            //Setando os valores do statement
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Project project) {

        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //Preparando a query
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            //executando a query
            statement.execute();
            //Setando os valores do statement
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o projeto ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Project> getAll() {

        String sql = "SELECT * FROM projects";

        List<Project> projects = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        //Classe que vai recuperar os dados do banco de dados
        ResultSet resultSet = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            resultSet = statement.executeQuery();

            //Preparando a query
            while (resultSet.next()) {

                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);

            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return projects;

    }

    public void removeById(int idProject) {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar projeto", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

}
