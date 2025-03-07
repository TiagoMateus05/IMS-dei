package pt.ulisboa.tecnico.rnl.dei.dms.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ulisboa.tecnico.rnl.dei.dms.logs.domain.Logs;
import pt.ulisboa.tecnico.rnl.dei.dms.logs.repository.LogsRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.Resource;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogsRepository logRepository;

    public void logAction(String action, String details, Person person, Resource resource) {
        Logs log = new Logs();
        log.setDetails(details);
        log.setAction(action);
        log.setPerson(person); // This can now be null if allowed by the entity
        log.setResource(resource);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
    
        logRepository.save(log);
    }

    public List<Logs> getAllLogs() {
        return logRepository.findAll();
    }

    public List<Logs> getLogsByResource(Long resourceId) {
        return logRepository.findBy_resource_Id(resourceId);
    }

    public List<Logs> getLogsByPerson(Long personId) {
        return logRepository.findBy_person_Id(personId);
    }

    public List<Logs> getLogsByResourceAndPerson(Long resourceId, Long personId) {
        return logRepository.findByPersonAndResource(resourceId, personId);
    }
}
