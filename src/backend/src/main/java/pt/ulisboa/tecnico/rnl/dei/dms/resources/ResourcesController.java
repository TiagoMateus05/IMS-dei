package pt.ulisboa.tecnico.rnl.dei.dms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.Resource;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.dto.ResourceDto;


import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourcesController {
    @Autowired
    private ResourcesService resourcesService;

    @GetMapping("/all")
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourcesService.getAllResources());
    }

    @PostMapping("/create")
    public ResponseEntity<Resource> createResource(@RequestBody ResourceDto resourceDto) {
        Resource createdResource = resourcesService.createResource(resourceDto, resourceDto.getTypeId());
        return ResponseEntity.ok(createdResource);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody ResourceDto resourceDto) {
        Long userId = null;
        if (resourceDto.getCurrentUser() != null) {
            userId = resourceDto.getCurrentUser().getId();
        }
        Resource updatedResource = resourcesService.updateResource(id, resourceDto.getName(), 
                                                                    resourceDto.getDescription(), 
                                                                    resourceDto.getType(), 
                                                                    userId, 
                                                                    resourceDto.getProblem());
        
        if (updatedResource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        resourcesService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/fixed/{id}")
    public ResponseEntity<Resource> fixedResource(@PathVariable Long id) {
        Resource fixedResource = resourcesService.fixedResource(id);
        if (fixedResource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fixedResource);
    }

    @PutMapping("/remove-person/{id}")
    public ResponseEntity<Resource> removePerson(@PathVariable Long id) {
        Resource removedPerson = resourcesService.removePersonResource(id);
        if (removedPerson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(removedPerson);
    }
}
