package contacts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {
    enum Actions {ADD, DELETE, EDIT, COUNT, INFO, LIST, SEARCH, EXIT, BACK, AGAIN, MENU}

    enum Types {PERSON, ORGANIZATION}

    private final Scanner scanner;
    private final List<Contact> phoneBook;

    public App() {
        this.scanner = new Scanner(System.in);
        this.phoneBook = new ArrayList<>();
    }

    public void start() {
        menu();
    }

    public void menu() {
        System.out.print("[menu] Enter action (add, list, search, count, exit):");
        Actions action = Actions.valueOf(scanner.nextLine().toUpperCase());
        switch (action) {
            case ADD:
                add();
                menu();
                break;
            case LIST:
                list(phoneBook);
                System.out.println();
                int row = Integer.parseInt(scanner.nextLine());
                Contact contact = phoneBook.get(row - 1);
                System.out.println(contact);
                System.out.println();
                recordMenu(contact);
                break;
            case SEARCH:
                search();
                menu();
                break;
            case COUNT:
                count();
                System.out.println();
                menu();
                break;
            case EXIT:
                System.exit(0);
        }
    }

    private void search() {
        System.out.print("Enter search query:");
        Pattern query = Pattern.compile(scanner.nextLine(), Pattern.CASE_INSENSITIVE);
        List<Contact> filteredContacts = phoneBook.stream()
                .filter(c -> {
                    StringBuilder strBuilder = new StringBuilder();
                    if (c instanceof Person p) {
                        strBuilder
                                .append(p.getName())
                                .append(p.getSurname())
                                .append(p.getBirthDate())
                                .append(p.getGender())
                                .append(p.getNumber());
                        return query.matcher(strBuilder).find();
                    } else {
                        Company company = (Company) c;
                        strBuilder
                                .append(company.getName())
                                .append(company.getAddress())
                                .append(company.getNumber());
                        return query.matcher(strBuilder).find();
                    }
                })
                .toList();
        System.out.println("Found " + filteredContacts.size() + " results:");
        list(filteredContacts);
        System.out.println();
        System.out.print("[search] Enter action ([number], back, again):");
        String input = scanner.nextLine().toUpperCase();
        if (input.matches("\\d+")) {
            int row = Integer.parseInt(input);
            Contact contact = filteredContacts.get(row - 1);
            System.out.println(contact.toString());
            System.out.println();
            recordMenu(contact);
        } else {
            Actions action = Actions.valueOf(input);
            switch (action) {
                case AGAIN -> search();
                case BACK -> menu();
            }
        }
    }

    private void recordMenu(Contact contact) {
        System.out.print("[record] Enter action (edit, delete, menu):");
        Actions action = Actions.valueOf(scanner.nextLine().toUpperCase());
        switch (action) {
            case EDIT -> {
                edit(contact);
                recordMenu(contact);
            }
            case DELETE -> remove(contact);
            case MENU -> menu();
        }
    }

    private void list(List<Contact> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i) instanceof Person p) {
                System.out.printf("%d. %s %s%n", i + 1, p.getName(), p.getSurname());
            } else {
                System.out.printf("%d. %s%n", i + 1, contacts.get(i).getName());
            }
        }
    }

    private void remove(Contact contact) {
        phoneBook.remove(contact);
        System.out.println("The record removed!");
    }

    private void edit(Contact contact) {
        String editableFields = contact.getEditableFields().stream()
                .map(f -> f.name().toLowerCase())
                .collect(Collectors.joining(", ", "(", ")"));
        System.out.print("Select a field " + editableFields + ":");
        Fields field = Fields.valueOf(scanner.nextLine().toUpperCase());
        updateField(field, contact);
        System.out.println("Saved");
        System.out.println(contact);
        System.out.println();
    }

    private void updateField(Fields field, Contact contact) {
        System.out.printf("Enter %s:", field.name().toLowerCase());
        String value = scanner.nextLine();
        contact.editField(field, value);
    }

    private void count() {
        System.out.println("The Phone Book has " + phoneBook.size() + " records.");
    }

    private void add() {
        System.out.print("Enter the type (person, organization):");
        Types type = Types.valueOf(scanner.nextLine().toUpperCase());

        Contact contact = switch (type) {
            case PERSON -> addPerson();
            case ORGANIZATION -> addCompany();
        };

        phoneBook.add(contact);

        System.out.println("The record added.");
        System.out.println();
    }

    private Contact addPerson() {
        System.out.print("Enter the name:");
        String name = scanner.nextLine();
        System.out.print("Enter the surname:");
        String surname = scanner.nextLine();
        System.out.print("Enter the birth date:");
        String birthDate = Contact.validateBirthDate(scanner.nextLine());
        System.out.print("Enter the gender (M, F):");
        String gender = Contact.validateGender(scanner.nextLine());
        System.out.print("Enter the number:");
        String number = Contact.validateNumber(scanner.nextLine());
        return new Person(name, surname, number, gender, birthDate);
    }

    private Contact addCompany() {
        System.out.print("Enter the organization name:");
        String name = scanner.nextLine();
        System.out.print("Enter the address:");
        String address = scanner.nextLine();
        System.out.print("Enter the number:");
        String number = Contact.validateNumber(scanner.nextLine());
        return new Company(name, number, address);
    }
}
