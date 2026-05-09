package util;
import net.datafaker.Faker;

public class DataFaker {
    public static final Faker FAKER = new Faker();

    public static String firstName=DataFaker.FAKER.name().firstName();
    public static String lastName=DataFaker.FAKER.name().lastName();
    public static String name = firstName + " " + lastName;
    public static String id = DataFaker.FAKER.number().digits(6);
    public static String userName= DataFaker.FAKER.regexify("[a-zA-Z]{5,10}");
    public static String userPassword=DataFaker.FAKER.regexify("[A-Z]{1}[a-z]{5}[0-9]{2}[@#$%]{1}");


    /*
    * How to use
    *
    * String employeeFirstName=DataFaker.firstName;
    *
    * */
}