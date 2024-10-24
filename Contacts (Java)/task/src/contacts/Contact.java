package contacts;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Contact {
    private String name;
    private String number;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private final static String numberPattern = "^\\+?(?:\\([A-Za-z0-9]+\\)(?:[ -][A-Za-z0-9]{2,})*|[A-Za-z0-9]+[ -]\\([A-Za-z0-9]{2,}\\)(?:[ -][A-Za-z0-9]{2,})*|[A-Za-z0-9]+(?:[ -][A-Za-z0-9]{2,})*)";

    public Contact(String name, String number) {
        this.name = name;
        setNumber(number);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    abstract public List<Fields> getEditableFields();
    abstract public void editField(Fields field, String newValue);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        updateEvent();
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        updateEvent();
        this.number = number;
    }

    public boolean hasNumber() {
        return this.number != null && !this.number.isBlank();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateEvent() {
        this.updatedAt = LocalDateTime.now();
    }

    public static String validateNumber(String number) {
        if (!number.matches(numberPattern)) {
            System.out.println("Wrong number format!");
            return "";
        } else {
            return number;
        }
    }

    public static String validateBirthDate(String birthDate) {
        if (birthDate.isBlank()) {
            System.out.println("Bad birth date!");
            return "";
        } else {
            return birthDate;
        }
    }

    public static String validateGender(String gender) {
        if (gender.isBlank() || !gender.matches("[MF]")) {
            System.out.println("Bad gender!");
            return "";
        } else {
            return gender;
        }
    }
}
