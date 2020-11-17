package sample.entity;

public class Doctor extends Person {
    public String specialty;

    public Doctor(int id, String surname, String name, String middleName,String specialty, String note)
    {
        super(id, surname, name, middleName, note);
        this.specialty = specialty;
    }

    public Doctor(String []data)
    {
        super(Integer.parseInt(data[0]),data[1],data[2],data[3],data[5]);
        this.specialty = data[4];
    }
    public Doctor()
    {
        super();
        this.specialty = "";
    }
    public Doctor(int id)
    {
        super(id);
        this.specialty = "";
    }

    public String getSpecialty() { return specialty; }

    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getString()
    {
        String res = ";"+getSurname()+";"+getName()+";"+getMiddleName()+";"+getSpecialty()+";"+getNote()+";";
        return res;
    }

}
