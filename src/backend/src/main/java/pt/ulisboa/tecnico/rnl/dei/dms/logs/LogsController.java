package pt.ulisboa.tecnico.rnl.dei.dms.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.logs.domain.Logs;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogService logService;

    // Get all logs
    @GetMapping("/all")
    public ResponseEntity<List<Logs>> getAllLogs() {
        List<Logs> logs = logService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    // Get logs by resource
    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Logs>> getLogsByResource(@PathVariable Long resourceId) {
        List<Logs> logs = logService.getLogsByResource(resourceId);
        return ResponseEntity.ok(logs);
    }

    // Get logs by person
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<Logs>> getLogsByPerson(@PathVariable Long personId) {
        List<Logs> logs = logService.getLogsByPerson(personId);
        return ResponseEntity.ok(logs);
    }

    // Get logs by resource and person
    @GetMapping("/resource/{resourceId}/person/{personId}")
    public ResponseEntity<List<Logs>> getLogsByResourceAndPerson(@PathVariable Long resourceId, @PathVariable Long personId) {
        List<Logs> logs = logService.getLogsByResourceAndPerson(resourceId, personId);
        return ResponseEntity.ok(logs);
    }
}
