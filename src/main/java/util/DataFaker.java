package util;
import net.datafaker.Faker;

public class DataFaker {
    public static final Faker FAKER = new Faker();

    /*
    * How to use
    *
    * String name = DataFaker.FAKER.name().firstName();
      System.out.println(name);
      * String password = DataFaker.FAKER.regexify("[A-Z]{1}[a-z]{5}[0-9]{2}[@#$%]{1}");
      * System.out.println(password);
    * */
}