import helpers.RandomComment;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Comment;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
public class BasicAuthTests extends BaseTest {

    private static String PostBasic = "/v1/comments/1";
    private static String Comment = "/v1/comment/1";
    private static String user = "testuser";
    private static String pass = "testpass";
    private static Integer createdComment = 0;

    @BeforeGroups("create_comment")
    public void Test_Create_Post_Success(){
        Comment comment = new Comment(RandomComment.generateRandomName(),RandomComment.generateRandomContent());
        Response response = given()
                .auth()
                .basic(user, pass)
                .body(comment)
                .post(Comment);

        JsonPath jsonPathEvaluator = response.jsonPath();
        createdComment = jsonPathEvaluator.get("id");
    }

    @Test
    public void Test_Create_Post_Success_Negative(){
        Comment comment = new Comment(RandomComment.generateRandomName(),RandomComment.generateRandomContent());
        given()
                .auth()
                .basic("invalid", pass)
                .body(comment)
                .post(Comment)
                .then()
                .body(containsString("Please login first"));

    }

    @Test(groups = "create_comment")
    public void A_Get_Comment(){
        given()
                .auth()
                .basic(user, pass)
                .when()
                .get(Comment +'/' + createdComment.toString())
                .then()
                .header("Access-Control-Allow-Origin",equalTo("http://localhost"))
                .and()
                .header("Access-Control-Allow-Credentials",equalTo("true"))
                .and()
                .body(containsString("@testemail.com"))
                .and()
                .statusCode(200);
    }

    @Test

    public void A_Get_Comment_Negative(){
        given()
                .auth()
                .basic(user, pass)
                .when()
                .get(Comment +'/' + 9999999)
                .then()
                .header("Access-Control-Allow-Origin",equalTo("http://localhost"))
                .and()
                .header("Access-Control-Allow-Credentials",equalTo("true"))
                .and()
                .body(containsString("Comment not found"))
                .and()
                .statusCode(404);
    }

    @Test(groups = "create_comment")
    public void B_Put_Comment(){
        Comment comment = new Comment(RandomComment.generateRandomName(),RandomComment.generateRandomContent());
        given()
                .auth()
                .basic(user, pass)
                .body(comment)
                .put(Comment + '/' +createdComment.toString())
                .then()
                .statusCode(200);
    }
    @Test
    public void B_Put_Comment_Negative(){
        Comment comment = new Comment(RandomComment.generateRandomName(),RandomComment.generateRandomContent());
        given()
                .auth()
                .basic(user, pass)
                .body(comment)
                .put(Comment + '/' + 9999999)
                .then()
                .statusCode(406);
    }

    @Test
    public void C_Get_Comments(){
        given()
                .auth()
                .basic(user, pass)
                .when()
                .get(PostBasic)
                .then()
                .header("Access-Control-Allow-Origin",equalTo("http://localhost"))
                .and()
                .header("Access-Control-Allow-Credentials",equalTo("true"))
                .and()
                .body(containsString("Testing Soft"))
                .and()
                .statusCode(200);
    }

    @Test
    public void C_Get_Comments_Negative(){
        given()
                .auth()
                .basic("admin", pass)
                .when()
                .get(PostBasic)
                .then()
                .statusCode(401);
    }

    @Test(groups = "create_comment")
    public void D_Delete_Comment(){
        given()
                .auth()
                .basic(user, pass)
                .delete(Comment + '/' + createdComment.toString())
                .then()
                .statusCode(200);
    }

    @Test
    public void D_Delete_Comment_Negative(){
        given()
                .auth()
                .basic(user, pass)
                .delete(Comment + '/' + 9999999)
                .then()
                .statusCode(406)
                .and()
                .body(containsString("Comment not found"));
    }
}