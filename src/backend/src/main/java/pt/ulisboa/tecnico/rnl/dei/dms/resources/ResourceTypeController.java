package pt.ulisboa.tecnico.rnl.dei.dms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.ResourceType;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.dto.ResourceTypeDto;

import java.util.List;

@RestController
@RequestMapping("/resource-types")
public class ResourceTypeController {
    @Autowired
    private ResourceTypesService resourceTypeService;

    @GetMapping(value = "/all", produces = "application/json; charset=utf-8")
    public ResponseEntity<List<ResourceType>> getAllResourceTypes() {
        return ResponseEntity.ok(resourceTypeService.getAllResourceTypes());
    }

    @PostMapping("/create")
    public ResponseEntity<ResourceType> createResourceType(@RequestBody ResourceTypeDto type) {
        ResourceType resourceType = resourceTypeService.createResourceType(type);
        return ResponseEntity.ok(resourceType);
    }
}

