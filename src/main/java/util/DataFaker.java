package util;
import net.datafaker.Faker;

import java.util.concurrent.ThreadLocalRandom;

public class DataFaker {
    public static final Faker FAKER = new Faker();

    public static String firstName=DataFaker.FAKER.name().firstName();
    public static String middleName=DataFaker.FAKER.name().firstName();
    public static String lastName=DataFaker.FAKER.name().lastName();
    public static String name = firstName + " " + lastName;
    public static String id = DataFaker.FAKER.number().digits(6);
    public static String userName= DataFaker.FAKER.regexify("[a-zA-Z]{5,10}");
    public static String userPassword=DataFaker.FAKER.regexify("[A-Z]{1}[a-z]{5}[0-9]{2}[@#$%]{1}");

    // ID FIELDS   ThreadLocalRandom (unique number)
    public static String employeeId = "EMP" + ThreadLocalRandom.current().nextInt(1000, 9999);
    public static String otherId    = "OTH" + ThreadLocalRandom.current().nextInt(1000, 9999);


    // LICENCE FIELDS
    public static String licenseNumber     = "DL" + FAKER.regexify("[0-9]{7}");
    public static String licenseExpiryDate = FAKER.regexify("202[7-9]-(0[1-9]|[12][0-9]|30)-(0[1-9]|1[0-2])");



    /*
    * How to use
    *
    * String employeeFirstName=DataFaker.firstName;
    *
    * */
}