package cinescopeTest;

import cinescopeApi.payload.UserPayload;
import conditions.BodyFieldCondition;
import conditions.Conditions;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.Matchers.*;


public class usersTest extends baseTest {

    // Пока оставил, не понимаю для чего именно новый сетод если есть testCanRegisterNewUser()
    /*@Test
    void createNewUser() {
        UserPayload user = new UserPayload()
                .email(faker.internet().emailAddress())
                .fullName(faker.name().fullName())
                .password("acHlCgV0")
                .passwordRepeat("acHlCgV0");


    }
*/

    @Test
    void testCanRegisterNewUser() {
        UserPayload user = new UserPayload()
                .email(faker.internet().emailAddress())
                .fullName(faker.name().fullName())
                .password("v0?#tuXA")
                .passwordRepeat("v0?#tuXA");

        userApiService.registerUser(user)
                .shouldHave(Conditions.statusCode(201))
                .shouldHave(response -> {
                    response.then()
                            .assertThat()
                            .body("id", matchesRegex("^[0-9a-fA-F-]{36}$"));

                })
                .extractId();// в этом тесте я хотел проверить что id при решистрации нужного формата и потом записываю его, вот только как его использовать в дальнейшем
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
                //.shouldHave(new BodyFieldCondition("id", not(emptyString())));
                .shouldHave(response -> {
                    response.then()
                            .assertThat()
                            .body("id", matchesRegex("^[0-9a-fA-F-]{36}$"));

                });

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
