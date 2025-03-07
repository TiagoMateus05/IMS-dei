package pt.ulisboa.tecnico.rnl.dei.dms.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/all",
                produces = "application/json; charset=utf-8")
    public List<PersonDto> getPersons() {
      return personService.getPersons();
    }

    @PostMapping("/create")
    public PersonDto createPerson(@RequestBody PersonDto personDto) {
        return personService.createPerson(personDto);
    }

    @PutMapping("/update/{id}")
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        personDto.setId(id);
        return personService.updatePerson(personDto);
    }

    @DeleteMapping("/delete/{id}")
    public List<PersonDto> deletePerson(@PathVariable Long id) {
        return personService.deletePerson(id);
    }
}
