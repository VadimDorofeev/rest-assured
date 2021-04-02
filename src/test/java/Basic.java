import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basic {

    public static void main(String[] args) {

        baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-type","application/json")
        .body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
        .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
        .header("server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        given().log().all().queryParam("key","qaclick123").header("Content-type","application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"70 Summer walk, USA\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                when().put("/maps/api/place/add/json")
                        .then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
    }

}
