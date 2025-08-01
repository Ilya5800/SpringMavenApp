package ru.semin.springcourse.controllers;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.semin.springcourse.dao.PersonDAO;
import ru.semin.springcourse.models.Person;
import ru.semin.springcourse.services.ItemService;
import ru.semin.springcourse.services.PeopleService;


import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    private final PeopleService peopleService;
   // private final ItemService itemService;

        @Autowired
    public PeopleController(PersonDAO personDAO, PeopleService peopleService) {
            this.personDAO = personDAO;
            this.peopleService = peopleService;
           // this.itemService = itemService;
        }



    @GetMapping()
    public String index(Model model) {
//        model.addAttribute("people", peopleService.findAll());
//
//        itemService.findByItemName("Airpods");
//        itemService.findByOwner(peopleService.findAll().get(0));
//        peopleService.test();
        personDAO.testNPlus1();
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) throws SQLException {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) throws SQLException {

        if(bindingResult.hasErrors()) return "people/new";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id) throws SQLException {

        if(bindingResult.hasErrors())return "people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        peopleService.delete(id);
        return "redirect:/people";
    }


}