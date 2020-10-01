import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class Xueqiu {
    public static String code;
    @BeforeClass
    public static void Login(){
        //由于是个https所以要加一个
        useRelaxedHTTPSValidation();
        RestAssured.proxy("127.0.0.1",7777);
//        LoginXueqiu();
    }
    public static void LoginXueqiu(){
        code=given()
                .header("User-Agent","Xueqiu Android 11.32.2")
                .queryParam("_t","1HUAWEI3aa1285127be8371a4d5cb223d9764e9.1830768458.1601350637166.1601350785841")
                .queryParam("_s","31285d")
                .cookie("u","1830768458")
                .cookie("xq_a_token","aed7a83e9d51f72a63c16d7d38f995738b77ade8")
                //请求类型，在Request里的Text内容
                .formParam("grant_type","password")
                .formParam("telephone","18518760789")
                .formParam("password","e9032ba11ae185fabb532566ca8306b9")
                .formParam("areacode","86")
                .formParam("captcha","")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("sid","1HUAWEI3aa1285127be8371a4d5cb223d9764e9")
                .when().post("https://api.xueqiu.com/provider/oauth/token")
                .then().log().all()
                .statusCode(400).body("error_code",equalTo("20082"))
                .extract().path("error_code");
        System.out.println(code);
    }
    @Test
    public void testSearch(){
        /*让它去信任我们发送的那个证书*/
//        useRelaxedHTTPSValidation();
        given()
                /*打印log信息*/
                .log().all()
                /*proxy：走代理模式
                * 本机已开启Charles,且端口号为7777*/
//                .proxy("127.0.0.1",7777)
                /*请求头信息*/
                .queryParam("code","sogo")
                .header("Cookie","xq_a_token=636e3a77b735ce64db9da253b75cbf49b2518316; xqat=636e3a77b735ce64db9da253b75cbf49b2518316; xq_r_token=91c25a6a9038fa2532dd45b2dd9b573a35e28cfd; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTYwMjY0MzAyMCwiY3RtIjoxNjAwOTMzNzg2NTUzLCJjaWQiOiJkOWQwbjRBWnVwIn0.mZUzMYfgtnbLDEHz_64gewLdV9ByQzBlqi1wGJs2AATzefRBJZbggUXgX-A8h-F8Gntn9TMi4XA2Siv95CAIur4JsQowzOiYjUqL8S5DYJ5AYUPCy673HwBns7DzYfeUIwceTEYmIqVQNMzt6jcSx4rsfaK9IETFd_NPCBh2CNxjmQhCqCOFiaIuEwTebHpyuVg6wBzjrLMGVg44njA_H4WiInROLvL0TKSpObXWFwpVoR89wS1hrgFuKBUmtFEYE7mI86iG5IOy-Nztxm7x28y9HiDlSh8HdJ7VDpLP7ndIX1jqzQi8hL4iwOlv-X3QcEwLidsu8a9G5d3ctXo6wg; u=251600933844269; device_id=24700f9f1986800ab4fcc880530dd0ed; Hm_lvt_1db88642e346389874251b5a1eded6e3=1600933848; acw_tc=2760820e16012951718381327efe5b333db3966f56b160e65e928d32510456; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1601295758")
                /*出发条件*/
                .when()
                .get("https://xueqiu.com/stock/search.json")
                /*断言*/
                .then()
                .log().all()
                .statusCode(200)
                /*断言body里面stocks.name是否为搜狗*/
                .body("stocks.name",hasItems("搜狗"))
                .body("stocks.code",hasItem("SOGO"));
    }
    @Test
    public void testLogin2(){
        //由于是个https所以要加一个
        useRelaxedHTTPSValidation();
        //把代理地址设置为全局变量
        RestAssured.proxy("127.0.0.1",7777);
                Response response=given()
                .header("User-Agent","Xueqiu Android 11.32.2")
                .queryParam("_t","1HUAWEI3aa1285127be8371a4d5cb223d9764e9.1830768458.1601350637166.1601350785841")
                .queryParam("_s","579373")
                .cookie("u","1830768458")
                .cookie("xq_a_token","aed7a83e9d51f72a63c16d7d38f995738b77ade8")
                //请求类型，在Request里的Text内容
                .formParam("grant_type","password")
                .formParam("telephone","18518760789")
                .formParam("password","b538c6fd231ef0fbf7f09fe393663cf8")
                .formParam("areacode","86")
                .formParam("captcha","")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("sid","1HUAWEI3aa1285127be8371a4d5cb223d9764e9")
                .when().post("https://api.xueqiu.com/provider/oauth/token")
                .then().log().all()
                .statusCode(200)
                //.body("uid",equalTo(1119821405))
                .extract().response();
        System.out.println(response.header("Trace-Id"));

    }
    @Test
    public void testPostJson(){
        HashMap<String,Object> map =new HashMap<String, Object>();
        map.put("a",1);
        map.put("b","yuanls");
        map.put("array",new String[]{"111","222"});
        given()
                .contentType(ContentType.JSON)
                .body(map)
                .when().post("http://www.baidu.com")
                .then()
                .log().all()
                .statusCode(200);
    }
}
