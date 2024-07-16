package cinescopeTest;

import cinescopeApi.payload.UserPayload;
import com.github.javafaker.Faker;
import conditions.BodyFieldCondition;
import conditions.Conditions;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.UserApiService;

import java.nio.channels.ConnectionPendingException;
import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
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
                .shouldHave(new BodyFieldCondition("id",not(emptyString())));



    }

    @Test
    void testCanLoginWithRegisteredUser() {
        // Register a new user
        UserPayload user = new UserPayload()
                .email(faker.internet().emailAddress())
                .fullName(faker.name().fullName())
                .password("v0?#tuXA")
                .passwordRepeat("v0?#tuXA");

        userApiService.registerUser(user)
                .shouldHave(Conditions.statusCode(201))
                .shouldHave(new BodyFieldCondition("id", not(emptyString())));

        // Open the login page
        open("https://dev-cinescope.store/login");

        // Fill in the login form
        $("#email").setValue(user.email());
        $("#password").setValue(user.password());
        $("[data-qa-id='login_submit_button']").click();

        // Verify successful login
        $("[data-qa-id='profile_page_button']").shouldBe(visible);
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

}
