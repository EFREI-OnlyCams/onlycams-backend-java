package tp2.lsi.data;

public class Patient {

    private int socialSecurityNumber;
    private String firstName;
    private String name;
    private String yearOfBirth;
    private String gender;
    private String reference;

    public Patient(int id, String name, String firstName, String yearOfBirth, String gender, String Reference) {
        this.socialSecurityNumber = id;
        this.name = name;
        this.firstName = firstName;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.reference = Reference;
    }

    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    public String getName() {
        return name;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getYearOfBirth() {
        return yearOfBirth;
    }
    public String getGender() {
        return gender;
    }
    public String getReference() {
        return reference;
    }

    public void setSocialSecurityNumber(int socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setReference(String Reference) {
        this.reference = Reference;
    }
    @Override
    public String toString() { //retourne les informations sous forme de chaine de caracteres, quand on print un objet de type Patient
        return "Nom =" + name + ", prenom = " + firstName + "anneeNaissance= " + yearOfBirth
                + ", genre =" + gender + ", Refrence =" + reference;
    }
}
