package pt.ulisboa.tecnico.rnl.dei.dms.resources.domain;

import jakarta.persistence.*;

import pt.ulisboa.tecnico.rnl.dei.dms.person.domain.Person;

@Entity
@Table(name = "Resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String _name;
    private String _description;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ResourceType _type;
    
    private String _location;

    @ManyToOne
    @JoinColumn(name = "current_user_id")
    private Person _currentUser = null;
    private String _status = "Available";
    private String _problem = "Sem Problemas";

    public Resource() {}

    public Resource(String name, String description, ResourceType type, String location) {
        _name = name;
        _description = description;
        _type = type;
        _location = location;
    }

    public Resource(Resource resource) {
        _id = resource.getId();
        _name = resource.getName();
        _description = resource.getDescription();
        _type = resource.getType();
        _location = resource.getLocation();
        _currentUser = resource.getCurrentUser();
        _status = resource.getStatus();
        _problem = resource.getProblem();
    }

    public Long getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public ResourceType getType() {
        return _type;
    }

    public String getLocation() {
        return _location;
    }

    public Person getCurrentUser() {
        return _currentUser;
    }

    public String getStatus() {
        return _status;
    }

    public String getProblem() {
        return _problem;
    }

    public Long getTypeId() {
        return _type.getId();
    }

    public Long getUserId() {
        if (_currentUser != null) {
            return _currentUser.getId();
        }
        return null;
    }

    public void setId(Long id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public void setType(ResourceType type) {
        _type = type;
    }

    public void setLocation(String location) {
        _location = location;
    }

    public void setCurrentUser(Person currentUser) {
        _currentUser = currentUser;
    }

    public void setStatus(String status) {
        _status = status;
    }

    public void setProblem(String problem) {
        _problem = problem;

        if (_problem.equals("Sem Problemas") && _currentUser == null) {
            setStatus("Available");
        } else if (!_problem.equals("Sem Problemas")) {
            setStatus("Broken");
        } else {
            setStatus("Being Used");
        }
    }

    @Override
    public String toString() {
        return "Resource{" +
               "id=" + _id +
               ", name='" + _name + '\'' +
               ", description='" + _description + '\'' +
               ", type='" + (_type != null ? _type.getName() : "null") + '\'' +
               ", currentUser='" + (_currentUser != null ? _currentUser.getName() : "null") + '\'' +
               ", status='" + _status + '\'' +
               ", problem='" + _problem + '\'' +
               '}';
    }

    public void checkAvailabity() {
        if (_problem.equals("Sem Problemas") && _currentUser == null) {
            setStatus("Available");
        } else if (!_problem.equals("Sem Problemas")) {
            setStatus("Broken");
        } else {
            setStatus("Being Used");
        }
    }

    public boolean isAvailable() {
        return _status.equals("Available");
    }

    public boolean isUnavailable() {
        return _status.equals("Unavailable");
    }

    public boolean isBroken() {
        return _status.equals("Broken");
    }

    public boolean isBeingUsed() {
        return _status.equals("Being Used");
    }

    public boolean hasProblem() {
        return _problem != null;
    }

    public boolean hasNoProblem() {
        return _problem == null;
    }
}
