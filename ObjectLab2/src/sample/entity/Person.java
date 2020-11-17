package sample.entity;

public abstract class Person {
    private int id;
    private String surname;
    private String name;
    private String middleName;
    private String note;
    public Person(int id, String surname, String name, String middleName, String note)
    {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.note = note;
    }
    public Person() {
        this.id = 0;
        this.surname = "";
        this.name = "";
        this.middleName = "";
    }
    public Person(int id) {
        this.id = id;
        this.surname = "";
        this.name = "";
        this.middleName = "";
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
