package cinescopeTest;


import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import services.UserApiService;

import java.util.Locale;

public abstract class baseTest {
    protected final UserApiService userApiService = new UserApiService();
    protected static Faker faker;

    @BeforeAll
    static void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        faker = new Faker(new Locale(config.locale()));
        RestAssured.baseURI = config.baseUrl();
    }
}
