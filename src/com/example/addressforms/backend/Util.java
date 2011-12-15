package com.example.addressforms.backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.example.addressforms.data.Address;
import com.example.addressforms.data.Person;

public class Util {

    private static String[] firstNames = { "Quinro", "Kain", "Phelix", "Dagon",
            "Spike", "Winoc", "Britton", "Argon", "Agni", "Dazbog", "Enyo",
            "Helene", "Isolde", "Jaya", "Kalliope", "Marduk", "Nudd",
            "Christopher", "Daniel", "Paul", "Mark", "Donald", "George",
            "Kenneth", "Steven", "Edward", "Brian", "Ronald", "Anthony",
            "Kevin", "Jason", "Matthew", "Gary", "Frances", "Ann", "Joyce",
            "Diane", "Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria" };
    private static String[] lastNames = { "Langbroek", "Fabre", "Ueda",
            "MacCarrick", "Radic", "Wahner", "Baarda", "Sacco", "Osborne",
            "Doyle", "Gill", "Alvarado", "Nunez", "Padilla", "Klein", "Briggs",
            "Pratt", "McBride", "Zimmerman", "Copeland", "Roy", "Waters",
            "Skinner", "Ayala", "Hood", "Chase", "Richard", "Atkins", "Luna",
            "Summers", "Nash", "Bruce", "Monroe", "Sawyer", "Burnett", "Pitts",
            "Phelps", "Pace", "Hull", "Leblanc", "Leach", "Chang", "Noble",
            "Foley" };
    private static String[] cityNames = { "Abgood", "Brilliant", "Coffeeville",
            "Five Forks", "Rainbow", "Shades Run", "Baarda", "Eek", "Kake" };
    private static String[] groupNames = { "Squirrels", "Vipers", "Beavers",
            "Wombats", "Foxes", "Cougars", "Canaries", "Kittens", "Mules",
            "Weasels" };

    private static String[] street1 = { "Amber", "Anchor", "Blue", "Apple",
            "Bright", "Autumn", "Broad", "Barn", "Burning", "Beacon", "Cinder",
            "Bear", "Clear", "Berry", "Colonial", "Blossom", "Cotton", "Bluff",
            "Cozy", "Branch" };
    private static String[] street2 = { "Acres", "Arbor", "Avenue", "Bank",
            "Bend", "Canyon", "Chase", "Circle", "Corner", "Court" };
    private static final String[] RCODE = { "M", "CM", "D", "CD", "C", "XC",
            "L", "XL", "X", "IX", "V", "IV", "I" };
    private static final int[] BVAL = { 1000, 900, 500, 400, 100, 90, 50, 40,
            10, 9, 5, 4, 1 };

    public static void createRandomPerson(List<Person> persons) {
        Random generator = new Random();
        Person person = new Person();

        Address address = new Address();
        address.setStreet(generator.nextInt(100) + " "
                + street1[generator.nextInt(street1.length)] + " "
                + street2[generator.nextInt(street2.length)]);
        address.setCity(cityNames[generator.nextInt(cityNames.length)]);
        address.setZip((generator.nextInt(9000) * 10 + 10000));
        address.setCountry("Finland");
        person.setAddress(address);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1950);
        long val1 = calendar.getTimeInMillis();
        calendar.set(Calendar.YEAR, 2000);
        long val2 = calendar.getTimeInMillis();
        person.setDateOfBirth(new Date(
                (long) (generator.nextDouble() * (val2 - val1)) + val1));

        person.setPhoneNumber("+" + (generator.nextInt(900) + 100) + " "
                + (generator.nextInt(9000) + 1000) + " "
                + (generator.nextInt(900) + 100));
        String firstName = firstNames[generator.nextInt(firstNames.length)];
        String originalLastName = lastNames[generator.nextInt(lastNames.length)];
        String lastName = originalLastName;
        int i = 1;
        while (nameExists(persons, firstName, lastName)) {
            lastName = originalLastName + " " + intToRoman(i);
            i++;
            if (i > 20) {
                break;
            }
        }
        if (!nameExists(persons, firstName, lastName)) {
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setEmail(firstName.toLowerCase() + "_"
                    + lastName.replaceAll(" ", "_").toLowerCase()
                    + "@company.com");
            persons.add(person);
        }
    }

    public static List<Person> createRandomPersons(int count) {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < count; i++) {
            createRandomPerson(persons);
        }
        return persons;
    }

    public static boolean nameExists(List<Person> persons, String firstName,
            String lastName) {
        for (Person dbPerson : persons) {
            if (firstName.equals(dbPerson.getFirstName())
                    && lastName.equals(dbPerson.getLastName())) {
                return true;
            }
        }
        return false;
    }

    public static String intToRoman(int number) {
        if (number <= 0 || number >= 4000) {
            throw new NumberFormatException(
                    "Value outside roman numeral range.");
        }
        String roman = ""; // Roman notation will be accumualated here.

        // Loop from biggest value to smallest, successively subtracting,
        // from the binary value while adding to the roman representation.
        for (int i = 0; i < RCODE.length; i++) {
            while (number >= BVAL[i]) {
                number -= BVAL[i];
                roman += RCODE[i];
            }
        }
        return roman;
    }
}
