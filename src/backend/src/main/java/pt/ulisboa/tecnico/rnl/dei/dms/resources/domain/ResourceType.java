package pt.ulisboa.tecnico.rnl.dei.dms.resources.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "ResourceTypes") // Table name remains the same
public class ResourceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Updated field name to match getter/setter

    @Column(unique = true, nullable = false)
    private String name; // Updated field name to match getter/setter

    public ResourceType() {}

    public ResourceType(String name) {
        this.name = name; // Updated field reference
    }

    public ResourceType(ResourceType resourceType) {
        this.id = resourceType.getId(); // Updated field reference
        this.name = resourceType.getName(); // Updated field reference
    }

    @Override
    public String toString() {
        return "ResourceType{" +
                "id=" + id + // Updated field reference
                ", name='" + name + '\'' + // Updated field reference
                '}';
    }

    public Long getId() {
        return id; // Updated getter
    }

    public String getName() {
        return name; // Updated getter
    }

    public void setName(String name) {
        this.name = name; // Updated setter
    }

    public void setId(Long id) {
        this.id = id; // Updated setter
    }
}
