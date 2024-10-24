package contacts;

import java.util.List;

public class Person extends Contact {
    private String surname;
    private String gender;
    private String birthDate;

    public Person(String name, String surname, String number, String gender, String birthDate) {
        super(name, number);
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        updateEvent();
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        updateEvent();
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        updateEvent();
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        String phone = "[no number]";
        if (hasNumber()) {
            phone = getNumber();
        }
        String birthDate = "[no data]";
        if (!this.birthDate.isBlank()) {
            birthDate = this.birthDate;
        }
        String gender = "[no data]";
        if (!this.gender.isBlank()) {
            gender = this.gender;
        }

        return "Name: " + getName() + "\n" +
                "Surname: " + this.surname + "\n" +
                "Birth date: " + birthDate + "\n" +
                "Gender: " + gender + "\n" +
                "Number: " + phone + "\n" +
                "Time created: " + getCreatedAt() + "\n" +
                "Time last edit: " + getUpdatedAt();
    }

    @Override
    public List<Fields> getEditableFields() {
        return List.of(Fields.NAME, Fields.SURNAME, Fields.BIRTH, Fields.GENDER, Fields.NUMBER);
    }

    @Override
    public void editField(Fields field, String newValue) {
        switch (field) {
            case NAME -> setName(newValue);
            case SURNAME -> setSurname(newValue);
            case NUMBER -> setNumber(validateNumber(newValue));
            case BIRTH -> setBirthDate(validateBirthDate(newValue));
            case GENDER -> setGender(validateGender(newValue));
        }
    }
}
