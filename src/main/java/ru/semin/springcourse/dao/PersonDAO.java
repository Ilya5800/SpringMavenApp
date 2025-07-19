package ru.semin.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.semin.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static final Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person show(int id) throws SQLException {
        Person person = null;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Person WHERE id =?");
        preparedStatement.setInt(1,id);
       ResultSet resultSet =preparedStatement.executeQuery();
       resultSet.next();
       person = new Person();
       person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email" ));

        return person;
    }

    public void save(Person person) throws SQLException {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO Person VALUES (1, ?,?,?)");

        preparedStatement.setString(1, person.getName());
        preparedStatement.setInt(2,person.getAge());
        preparedStatement.setString(3,person.getEmail());

        preparedStatement.executeUpdate();
    }

    public void update(int id, Person updatedPerson) throws SQLException {

        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE Person SET name=?,age=?,email=? Where id=?");
        preparedStatement.setString(1, updatedPerson.getName());
        preparedStatement.setInt(2,updatedPerson.getAge());
        preparedStatement.setString(3,updatedPerson.getEmail());
        preparedStatement.setInt(4, id);
        preparedStatement.executeUpdate();
//        Person personToBeUpdated = show(id);
//
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) throws SQLException {
//        people.removeIf(p -> p.getId() == id);
        PreparedStatement preparedStatement =
                connection.prepareStatement("DELETE FROM Person WHERE id=?");
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();


    }
}
