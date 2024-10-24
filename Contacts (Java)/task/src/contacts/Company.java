package contacts;

import java.util.List;

public class Company extends Contact {
    private String address;

    public Company(String name, String number, String address) {
        super(name, number);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        updateEvent();
        this.address = address;
    }

    @Override
    public String toString() {
        String phone = "[no number]";
        if (hasNumber()) {
            phone = getNumber();
        }

        return "Organization name: " + getName() + "\n" +
                "Address: " + this.address + "\n" +
                "Number: " + phone + "\n" +
                "Time created: " + getCreatedAt() + "\n" +
                "Time last edit: " + getUpdatedAt();
    }

    @Override
    public List<Fields> getEditableFields() {
        return List.of(Fields.ADDRESS, Fields.NUMBER);
    }

    @Override
    public void editField(Fields field, String newValue) {
        switch (field) {
            case ADDRESS -> setAddress(newValue);
            case NUMBER -> setNumber(validateNumber(newValue));
        }
    }
}
