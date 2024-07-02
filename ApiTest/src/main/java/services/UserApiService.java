package services;

import assertions.AssertableResponse;
import cinescopeApi.payload.UserPayload;
import io.qameta.allure.Step;


public class UserApiService extends ApiService {

    @Step
    public AssertableResponse registerUser(UserPayload user) {
       return  new AssertableResponse(setup()
                .body(user)
                .when()
                .post("register"));

    }
}
