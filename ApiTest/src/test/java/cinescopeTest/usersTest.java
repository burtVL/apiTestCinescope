package cinescopeTest;

import cinescopeApi.payload.UserPayload;
import conditions.BodyFieldCondition;
import conditions.Conditions;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProfilePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class usersTest extends baseTest {
    private final LoginPage loginPage = new LoginPage();
    private final ProfilePage profilePage = new ProfilePage();

    @Test
    void createNewUser() {
        UserPayload user = new UserPayload()
                .email(faker.internet().emailAddress())
                .fullName(faker.name().fullName())
                .password("v0?#tuXA")
                .passwordRepeat("v0?#tuXA");

       String userId = userApiService.registerUser(user)
                .shouldHave(Conditions.statusCode(201))
                .shouldHave(response -> {
                    response.then()
                            .assertThat()
                            .body("id", matchesRegex("^[0-9a-fA-F-]{36}$"));

                })
                .extractId();

        loginPage.openLoginPage();
        loginPage.setEmail(user.email());
        loginPage.setPassword(user.password());
        loginPage.clickLoginButton();
        profilePage.clickProfileButton();
        String uiUserId = profilePage.getUserId();
        assertThat(uiUserId, equalTo(userId));

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
