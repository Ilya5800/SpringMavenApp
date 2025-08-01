package ru.semin.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semin.springcourse.models.Person;

import java.util.List;


@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
     List<Person> findByName(String name);
     List<Person> findByNameOrderByAge(String name);

}
