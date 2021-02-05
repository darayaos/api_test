package specifications;

import helpers.RequestHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification generateToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        String token = RequestHelper.getUserToken();

        requestSpecBuilder.addHeader("Authorization", "Bearer " + token);
        return requestSpecBuilder.build();
    };

    public static RequestSpecification generateFakeToken(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addHeader("Authorization", "Bearer eyJhbGcaOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NfdXVpZCI6ImE4MDVkNjIwLWFmNzItNGQyMy04NTU1LTkxOTJkYTQ0NDdmNSIsImF1dGhvcml6ZWQiOnRydWUsImV4cCI6MTYxMjQ4NjEyMSwidXNlcl9pZCI6ODl9.2EgPPzWH2vOtLKr-FVkwmu0SsdI757yyONiHX6O7lF8");
        return requestSpecBuilder.build();
    };

}
