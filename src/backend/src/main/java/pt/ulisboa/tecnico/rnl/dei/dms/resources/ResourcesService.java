package pt.ulisboa.tecnico.rnl.dei.dms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.Resource;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.ResourceType;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.dto.ResourceDto;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.repository.ResourceRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.repository.ResourceTypeRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.logs.LogService;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;
import pt.ulisboa.tecnico.rnl.dei.dms.person.repository.PersonRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.logs.repository.LogsRepository;

import java.util.List;

@Service
public class ResourcesService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceTypeRepository  resourceTypeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private LogsRepository logRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public Resource createResource(ResourceDto resourceDto, Long typeId) {
        ResourceType type = resourceTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("ResourceType not found with ID: " + typeId));
        Resource resource = new Resource(resourceDto.getName(), resourceDto.getDescription(), type, resourceDto.getLocation());
        return resourceRepository.save(resource);
    }
    

    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id).orElse(null);
    }

    public void deleteResource(Long id) {
        // Nullify resource_id in logs referencing the resource
        logRepository.nullifyResourceReferences(id);
        resourceRepository.deleteById(id);
    }

    public Resource updateResource(Long id, String name, String description, ResourceType type, Long userid, String problem) {
        Resource resource = resourceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Resource not found with ID: " + id));
        Resource oldResource = resource;
        Person currentUser = null;
    
        if (userid != null && userid != 0) {
            currentUser = personRepository.findById(userid)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with ID: " + userid));
        }
    
        resource.setName(name);
        resource.setDescription(description);
        resource.setType(type);
        resource.setCurrentUser(currentUser);
        resource.setProblem(problem);
        
         if (!"Sem Problemas".equals(problem) && problem.equals(oldResource.getProblem())) {
            logService.logAction("Error", "Resource Problem: " + resource.getProblem(), currentUser, resource);
         } else if (currentUser != null && oldResource.getCurrentUser() == null) {
            logService.logAction("Resource Updated with new user", "New User: " + resource.getCurrentUser().getName(), currentUser, resource);
        } else if (currentUser != null && oldResource.getCurrentUser() != null &&
                   oldResource.getCurrentUser().getName().equals(resource.getCurrentUser().getName())) {
            logService.logAction("Resource Updated changed user", " New User: " + resource.getCurrentUser().getName(), currentUser, oldResource);
        } else if (userid == null) {
            logService.logAction("Resource Updated with new values", "Old->New:\n" + "Name: " + oldResource.getName() + " -> " + resource.getName() + "\nDescription: " + oldResource.getDescription() + " -> " + resource.getDescription() + "\nLocation: " + oldResource.getLocation() + " -> " + resource.getLocation() + "\nType: " + oldResource.getType().getName() + " -> " + resource.getType().getName() , null, resource);
        } else if (oldResource.getCurrentUser() != null && userid.equals(oldResource.getCurrentUser().getId())) {
            logService.logAction("Resource Updated with new values", "Old->New:\n" + "Name: " + oldResource.getName() + " -> " + resource.getName() + "\nDescription: " + oldResource.getDescription() + " -> " + resource.getDescription() + "\nLocation: " + oldResource.getLocation() + " -> " + resource.getLocation() + "\nType: " + oldResource.getType().getName() + " -> " + resource.getType().getName() , resource.getCurrentUser(), resource);
        }
    
        return resourceRepository.save(resource);
    }

    public Resource fixedResource(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found with ID: " + id));
        resource.setProblem("Sem Problemas");
        resource.checkAvailabity();
        logService.logAction("Error" , "Resource " + resource.getName() + " Fixed Problem", resource.getCurrentUser(), resource);
        return resourceRepository.save(resource);
    }

    public Resource removePersonResource(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found with ID: " + id));

        logService.logAction("Updated Resource Removed User", "Old User: " + resource.getCurrentUser().getName(), resource.getCurrentUser(), resource);
        resource.setCurrentUser(null);
        resource.checkAvailabity();


        return resourceRepository.save(resource);
    }

    public void removePersonFromResources(Long personId) {
        // Fetch all resources associated with the person
        List<Resource> resources = resourceRepository.findByCurrentUserId(personId);

        // Iterate through each resource and set the person to null
        for (Resource resource : resources) {
            resource.setCurrentUser(null);
            resource.checkAvailabity();
            resourceRepository.save(resource);
        }
    }
    
}
