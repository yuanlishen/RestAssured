import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kohsuke.rngom.parse.host.Base;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.equalTo;

public class filter {
    @BeforeClass
    public static void setup(){
        useRelaxedHTTPSValidation();
        RestAssured.baseURI="https:..testerhome.com";
        RestAssured.proxy("127.0.0.1",7777);

        //篡改数据
        RestAssured.filters((req, res, ctx)->{
            if (req.getURI().contains("xueqiu.com")){
                //模拟器在header部分增加一个新的内容
                req.header("testerhome_id","yuanls");
                req.cookie("xxxxxx");
            }

            //首先把请求发出去
            Response resOrigin=ctx.next(req,res);
            //然后返回
            return resOrigin;
        });
    }
    @Test
    public void testFilterAddCooike(){
        given()
            .queryParam("code","sogo")
            .when()
            .get("https://xuexiu.com/v4/stock/quote.json")
            .then().statusCode(200);
    }

    @Test
    public void testJsonInBase64(){
        given().filter((req, res, ctx)->{
            Response resOrigin=ctx.next(req, res);
            ResponseBuilder responseBuilder=new ResponseBuilder().clone(resOrigin);
            String decodeContent=new String(
                    Base64.getDecoder().decode(
                            resOrigin.body().asString().trim()
                    )
            );
            responseBuilder.setBody(decodeContent);
            Response resMew=responseBuilder.build();
            return resMew;
        }).get("127.0.0.1:7777/base64.json").prettyPeek()
                .then()
                .statusCode(200)
                .body("SOGO.name",equalTo("搜狗"));
    }
}


