
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import model.RandomPost;
import specifications.RequestSpecs;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

public class PostTest extends BaseTest {

    private static String post = "/v1/post";

    private static String JSONData = "" +
            "{\"title\":\"Automation API\"," +
            "\"content\":\"randomLoremSentence\"}";

    private static Integer my_post_id = 0;

    @BeforeGroups("create_post")
    public void A_createPost(){
       // RandomPost random = new RandomPost(helpers.RandomPost.generateRandomTitle(), helpers.RandomPost.generateRandomContent());
        // pregunta para el profe
        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(JSONData)
                // .body(random)
                .post(post);
        JsonPath jsonPathEvaluator = response.jsonPath();
        my_post_id = jsonPathEvaluator.get("id");
    }

    @Test
    public void print()
    {
        System.out.println("IDDDD  "+my_post_id);
    }

    @Test
    public void B_Get_Post(){
        given()
                .spec(RequestSpecs.generateToken())
                .get(post+"s")
                .then()
                .header("Content-Type",equalTo("application/json; charset=utf-8"))
                .and()
                .header("Access-Control-Allow-Credentials",equalTo("true"))
                .statusCode(200);
    }

    @Test
    public void B_Get_Post_Negative(){
        given()
                .spec(RequestSpecs.generateToken())
                .get(post)
                .then()
                .body(containsString("404 Page"))
                .statusCode(404);
    }

    @Test
    public void C_Get_Post_ID(){
        given()
                .spec(RequestSpecs.generateToken())
                .get(post + '/' + my_post_id.toString())
                .then()
                .body(containsString("Automation API"))
                .statusCode(200);
    }

    @Test
    public void C_Get_Post_ID_Negative(){
        given()
                .spec(RequestSpecs.generateToken())
                .get(post + '/' + 99999)
                .then()
                .body(containsString("Post not found"))
                .statusCode(404);
    }

    @Test
    public void D_Put_Post_ID(){
        given()
                .spec(RequestSpecs.generateToken())
                .body(JSONData)
                .put(post + '/' + my_post_id.toString())
                .then()
                .body(containsString("Post updated"))
                .statusCode(200);
    }

    @Test
    public void D_Put_Post_ID_Negative(){
        given()
                .spec(RequestSpecs.generateToken())
                .body(JSONData)
                .put(post + '/' + 99999)
                .then()
                .body(containsString("Post could not be updated"))
                .statusCode(406);
    }

    @Test
    public void E_Delete_Post_ID(){

        given()
                .spec(RequestSpecs.generateToken())
                .delete(post + '/' + my_post_id.toString())
                .then()
                .body(containsString("Post deleted"))
                .statusCode(200);
    }

    @Test
    public void E_Delete_Post_ID_Negative(){

        given()
                .spec(RequestSpecs.generateToken())
                .delete(post + '/' + 99999)
                .then()
                .body(containsString("Post not found"))
                .statusCode(406);
    }
}
