package ru.semin.springcourse.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semin.springcourse.models.Mood;
import ru.semin.springcourse.models.Person;
import ru.semin.springcourse.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);

    }
    @Transactional
    public void save(Person person){


        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson){
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
    public void test(){
        System.out.println("Testing here with debug. Inside Hibernate Transaction");

    }
}
