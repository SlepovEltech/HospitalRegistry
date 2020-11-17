package sample.entity;

public class Patient extends Person {
    public String diagnos;

    public Patient()
    {
        super();
        this.diagnos = "";
    }
    public Patient(int id, String surname, String name, String middleName, String diagnos, String note)
    {
        super(id, surname, name, middleName,note);
        this.diagnos = diagnos;
    }
    public Patient(String []data)
    {
        super(Integer.parseInt(data[0]),data[1],data[2],data[3], data[5]);
        this.diagnos = data[4];

    }
    public Patient(int id)
    {
        super(id);
        this.diagnos = "";
    }
    public String getDiagnos() { return diagnos; }

    public void setDiagnos(String diagnos) { this.diagnos = diagnos; }

    public String getString()
    {
        String res = ";"+getSurname()+";"+getName()+";"+getMiddleName()+";"+getDiagnos()+";"+getNote()+";";
        return res;
    }
}
