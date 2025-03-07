package pt.ulisboa.tecnico.rnl.dei.dms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.ResourceType;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.repository.ResourceTypeRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.dto.ResourceTypeDto;

import java.util.List;

@Service
public class ResourceTypesService {
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    public List<ResourceType> getAllResourceTypes() {
        return resourceTypeRepository.findAll();
    }

    public ResourceType createResourceType(ResourceTypeDto type) {

        if (resourceTypeRepository.existsByName(type.getName())) {
            throw new IllegalArgumentException("Resource type already exists");
        }
        ResourceType resourceType = new ResourceType();
        resourceType.setName(type.getName());
        return resourceTypeRepository.save(resourceType);
    }
}

