package pt.ulisboa.tecnico.rnl.dei.dms.logs.domain;

import java.sql.Timestamp;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.resources.domain.Resource;
import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

@Entity
@Table(name = "Logs")
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(length = 1000) // Increased length
    private String _action;

    @Column(length = 1000) // Increased length
    private String _details;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = true)
    private Person _person;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = true)
    private Resource _resource;

    private Timestamp _timestamp;

    public Logs() {}

    public Logs(String action, String details, Resource resource, Person person) {
        _action = action;
        _details = details;
        _resource = resource;
        _person = person;
        _timestamp = new Timestamp(System.currentTimeMillis()); // Automatically set timestamp
    }

    public Long getId() {
        return _id;
    }

    public String getAction() {
        return _action;
    }

    public String getDetails() {
        return _details;
    }

    public Timestamp getTimestamp() {
        return _timestamp;
    }

    public Resource getResource() {
        return _resource;
    }

    public Person getPerson() {
        return _person;
    }

    public void setId(Long id) {
        _id = id;
    }

    public void setAction(String action) {
        _action = action;
    }

    public void setDetails(String details) {
        _details = details;
    }

    public void setTimestamp(Timestamp timestamp) {
        _timestamp = timestamp;
    }

    public void setResource(Resource resource) {
        _resource = resource;
    }

    public void setPerson(Person person) {
        _person = person;
    }
}
