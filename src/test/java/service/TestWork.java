package service;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestWork {
    static String token;
    /*父部门id*/
    static int parentDepartID=4;
    //用例每次执行之前都会首先执行BeforeAll里的代码，getToken会存下来token；
    @BeforeAll
    public static void getToken(){
        /*String token是为了让它返回token*/
        token=given()
                /*访问地址需要带的参数*/
                .param("corpid","ww6f1c69864c7fb4b6")
                .param("corpsecret","Q68mwHwSL6h8nMkJHEvwkxSequwxqj2VHzsTX_oZm1g")

        .when()
                .log().all()
                /*访问的地址； 是get还是post*/
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                /*断言*/
        .then()
                .log().all()
                .body("errcode",equalTo(0))
                /*把获取到的数据导出*/
        .extract().body().path("access_token");
        System.out.println(token);
    }
    @Test
    /*创建部门*/
    public void departCreate(){
        /*4.创建我们的HashMap*/
        HashMap<String,Object> data=new HashMap<>();
        data.put("name","部门1");
        /*由于parentDepartID需要再多出使用，所以使用全局静态变量定义一下*/
        data.put("parentid",parentDepartID);
        /*1.由于我们这个接口需要到请求数据*/
        given()
                /*由于是post发送的请求，所以换一个更好的api：queryParam*/
                .queryParam("access_token",token)
                /*2.首先指名json，因为它是contentType自带的一个变量*/
                .contentType(ContentType.JSON)
                /*5.发送我们请求*/
                .body(data)
        .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
        .then().log().all()
                .body("errcode",equalTo(0));

                //todo：需要用List接口校验，但是如果编写List的请求，会导致代码冗余会带来维护问题，所以引入PO思想
    }

    @Test
    /*获取部门列表*/
    public void departList(){
        given()
                .queryParam("access_token",token)
                .queryParam("id",parentDepartID)
        .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
        .then().log().all()
                .body("errcode",equalTo(0));
    }

}
