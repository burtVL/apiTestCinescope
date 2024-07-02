package cinescopeTest;

import cinescopeApi.payload.UserPayload;
import com.github.javafaker.Faker;
import conditions.BodyFieldCondition;
import conditions.Conditions;
import io.restassured.RestAssured;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.UserApiService;

import java.util.Locale;

import static org.hamcrest.Matchers.*;


public class usersTest {

    private final UserApiService userApiService = new UserApiService();
    private static Faker faker;

    @BeforeAll
    static void setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        faker = new Faker(new Locale(config.locale()));
        RestAssured.baseURI = config.baseUrl();
    }

    @Test
    void testCanRegisterNewUser() {
        UserPayload user = new UserPayload()
                .email(faker.internet().emailAddress())
                .fullName(faker.name().fullName())
                .password("v0?#tuXA")
                .passwordRepeat("v0?#tuXA");

        userApiService.registerUser(user)
                .shouldHave(Conditions.statusCode(201))
                //.asPojo(UserRegistryResponse.class);
                //response.getId();
                .shouldHave(new BodyFieldCondition("id",not(emptyString())));
                //.then().log().all()
                //.assertThat()
                //.statusCode(201)
                //.body("id",not(isEmptyString()));

    }

    @Test
    void testCanNotRegisterSameUserTwice() {
        UserPayload user = new UserPayload()
                .email(faker.internet().emailAddress())
                .fullName(faker.name().fullName())
                .password("v0?#tuXA")
                .passwordRepeat("v0?#tuXA");

        userApiService.registerUser(user)
                .shouldHave(Conditions.statusCode(201))
                .shouldHave(new BodyFieldCondition("id",not(emptyString())));


        userApiService.registerUser(user)
                .shouldHave(Conditions.statusCode(409));

    }

    @Config.Sources({"classpath:config.properties"})
    public static interface ProjectConfig extends Config {

        String baseUrl();

        @DefaultValue("en")
        String locale();
    }
}
