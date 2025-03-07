package pt.ulisboa.tecnico.rnl.dei.dms.person;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.logs.repository.LogsRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.ResourcesService;

@Service
public class PersonService {
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LogsRepository logRepository;

    @Autowired
    private ResourcesService resourcesService;

    public PersonDto createPerson(PersonDto personDto) {
        Person person = new Person(personDto);
        personRepository.save(person);
        return new PersonDto(person);
    }

    public List<PersonDto> getPersons() {
        return personRepository.findAll().stream().map(PersonDto::new).collect(Collectors.toList());
    }

    public PersonDto updatePerson(PersonDto personDto) {
        Person person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new RuntimeException("Person not found"));
    
        person.setName(personDto.getName());
        person.setAge(personDto.getAge());
        person.setIstID(personDto.getIstID());
        person.setEmail(personDto.getEmail());
        person.setType(personDto.getType());
    
        personRepository.save(person);
    
        return new PersonDto(person);
    }
    public List<PersonDto> deletePerson(Long id) {
        resourcesService.removePersonFromResources(id);

        logRepository.nullifyPersonReferences(id);
        personRepository.deleteById(id);
        return getPersons();
    }
}
