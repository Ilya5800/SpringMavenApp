package ru.semin.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.semin.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
      return jdbcTemplate.query("Select * From Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) throws SQLException {


        return jdbcTemplate.query("SELECT * FROM Person WHERE id =?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) throws SQLException {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
//        PreparedStatement preparedStatement =
//                connection.prepareStatement("INSERT INTO Person VALUES (1, ?,?,?)");
//
//        preparedStatement.setString(1, person.getName());
//        preparedStatement.setInt(2,person.getAge());
//        preparedStatement.setString(3,person.getEmail());
//
//        preparedStatement.executeUpdate();

        jdbcTemplate.update("INSERT INTO Person (name,age,email)VALUES ( ?,?,?)", person.getName(), person.getAge(),person.getEmail());
    }

    public void update(int id, Person updatedPerson) throws SQLException {


        jdbcTemplate.update("UPDATE Person SET name=?,age=?,email=? Where id=?", updatedPerson.getName(), updatedPerson.getAge()
        ,updatedPerson.getEmail(), id);
//        PreparedStatement preparedStatement =
//                connection.prepareStatement("UPDATE Person SET name=?,age=?,email=? Where id=?");
//        preparedStatement.setString(1, updatedPerson.getName());
//        preparedStatement.setInt(2,updatedPerson.getAge());
//        preparedStatement.setString(3,updatedPerson.getEmail());
//        preparedStatement.setInt(4, id);
//        preparedStatement.executeUpdate();
//        Person personToBeUpdated = show(id);
//
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) throws SQLException {

        jdbcTemplate.update("DELETE FROM Person WHERE id=?",  id);
//        people.removeIf(p -> p.getId() == id);
//        PreparedStatement preparedStatement =
//                connection.prepareStatement("DELETE FROM Person WHERE id=?");
//        preparedStatement.setInt(1,id);
//        preparedStatement.executeUpdate();


    }
    /// ////////////////////////////////
    /// /Тестируем производительность вставки
    /// ///////////////////////////////
    public void testMultipleUpdate(){
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        for (Person person: people){
            jdbcTemplate.update("INSERT INTO Person VALUES (?, ?,?,?)", person.getId(),person.getName(), person.getAge(),person.getEmail());
        }
        long after = System.currentTimeMillis();

        System.out.println("time " + (after -before) );

    }

    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test"+i+"@mail.ru"));

        }
        return people;
    }
    public void testBatchUpdate() {
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES (?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();

        System.out.println("time " + (after - before));

    }
}
