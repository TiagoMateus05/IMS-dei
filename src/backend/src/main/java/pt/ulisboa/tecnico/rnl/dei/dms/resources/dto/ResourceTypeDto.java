package pt.ulisboa.tecnico.rnl.dei.dms.resources.dto;

import jakarta.persistence.*;

import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.ResourceType;


public class ResourceTypeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String _name;

    public ResourceTypeDto() {}

    public ResourceTypeDto(String name) {
        _name = name;
    }

    public ResourceTypeDto(ResourceType resourceType) {
        _id = resourceType.getId();
        _name = resourceType.getName();
    }

    @Override
    public String toString() {
        return "ResourceType{" +
                "id=" + _id + // Updated field reference
                ", name='" + _name + '\'' + // Updated field reference
                '}';
    }

    public Long getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setId(Long id) {
        _id = id;
    }
}
