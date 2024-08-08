package cinescopeTest;

import cinescopeApi.payload.UserPayload;
import conditions.BodyFieldCondition;
import conditions.Conditions;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class usersTest extends baseTest {


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
        open("https://dev-cinescope.store/login");
        $("#email").setValue(user.email());
        $("#password").setValue(user.password());
        $("[data-qa-id='login_submit_button']").click();

        // Step 3: Verify successful login
        $("[data-qa-id='profile_page_button']").shouldBe(visible).click();
        String uiUserId = $(By.xpath("/html/body/div[2]/main/div[1]/div[2]/ul/li[1]")).getText();
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
