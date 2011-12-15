package com.example.addressforms.backend;

import java.util.ArrayList;
import java.util.List;

import com.example.addressforms.data.Person;

public class Backend {

    private List<Person> persons;
    int idCount = 0;

    public Backend() {
        persons = Util.createRandomPersons(5);
        for (Person person : persons) {
            person.setId(idCount++);
        }
    }

    public List<Person> getPersons() {
        try {
            // Clone the list to mimic more a real db where changing the bean
            List<Person> clone = new ArrayList<Person>(persons.size());
            for (Person person : persons) {
                clone.add(person.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            // Person.clone() doesn't throw this...
            e.printStackTrace();
        }
        return null;
    }

    public void storePerson(Person person) throws NameClashException,
            UpdatingNonexistantPersonException {
        Person foundDbPerson = null;
        int personId = person.getId();
        if (personId != -1) {
            foundDbPerson = findPersonWithId(personId);
            if (foundDbPerson == null) {
                throw new UpdatingNonexistantPersonException(
                        "A person with id "
                                + personId
                                + " was being updated but the ID doesn't exist.");
            }
        } else {
            for (Person dbPerson : persons) {
                if (person.getFirstName().equals(dbPerson.getFirstName())
                        && person.getLastName().equals(dbPerson.getLastName())) {
                    if (person.getId() != dbPerson.getId()) {
                        throw new NameClashException(
                                "Two persons with the same name is not allowed");
                    } else {
                        foundDbPerson = dbPerson;
                        break;
                    }
                }
            }
        }
        if (foundDbPerson != null) {
            persons.remove(foundDbPerson);
        }
        if (person.getId() == -1) {
            person.setId(idCount++);
        }
        try {
            Person clonedPerson = person.clone();
            persons.add(clonedPerson);
        } catch (CloneNotSupportedException e) {
            // Person doesn't throw a CloneNotSupportedException
        }
    }

    private Person findPersonWithId(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public void deletePerson(Person person) {
        persons.remove(person);
    }

    public class NameClashException extends Exception {
        public NameClashException(String message) {
            super(message);
        }
    }

    public class UpdatingNonexistantPersonException extends Exception {
        public UpdatingNonexistantPersonException(String message) {
            super(message);
        }
    }
}
