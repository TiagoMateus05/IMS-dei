package pt.ulisboa.tecnico.rnl.dei.dms.person.domain;

import jakarta.persistence.*;

import pt.ulisboa.tecnico.rnl.dei.dms.person.dto.PersonDto;

@Entity
@Table(name = "Persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String _name;
    private int _age;
    private String _istID;
    private String _email;

    @Enumerated(EnumType.STRING)
    private PersonType _type;

    public Person() {}

    public Person(String name, int age, String istID, String email, PersonType type) {
        _name = name;
        _age = age;
        _istID = istID;
        _email = email;
        _type = type;
    }

    public Person(PersonDto personDto) {
        _name = personDto.getName();
        _age = personDto.getAge();
        _istID = personDto.getIstID();
        _email = personDto.getEmail();
        _type = personDto.getType();
    }

    public Long getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getAge() {
        return _age;
    }

    public String getIstID() {
        return _istID;
    }

    public String getEmail() {
        return _email;
    }

    public PersonType getType() {
        return _type;
    }

    public void setId(Long id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setAge(int age) {
        _age = age;
    }

    public void setIstID(String istID) {
        _istID = istID;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public void setType(PersonType type) {
        _type = type;
    }

    public boolean isStudent() {
        return _type == PersonType.STUDENT;
    }

    public boolean isTeacher() {
        return _type == PersonType.TEACHER;
    }

    public boolean isWorker() {
        return _type == PersonType.WORKER;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + _id +
                ", name='" + _name + '\'' +
                ", age=" + _age +
                ", istID='" + _istID + '\'' +
                ", email='" + _email + '\'' +
                ", type=" + _type +
                '}';
    }
}
