package service.department.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.Work;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Department {
    public int parentDepartId=4;

    //列举部门，需要创建一个获取getToken的方法
    public Response list(int id){
        return given()
                .queryParam("access_token", Work.getInstance().getToken())
                .queryParam("id",parentDepartId)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all()
                .body("errcode",equalTo(0))
                .extract().response();
    }

    //创建部门需要创建一个create的一个方法
    public Response create(String name, int parentid){
        /*4.创建我们的HashMap*/
        HashMap<String,Object> data=new HashMap<>();
        data.put("name",name);
        /*由于parentDepartID需要再多出使用，所以使用全局静态变量定义一下*/
        data.put("parentid",parentid);
        /*1.由于我们这个接口需要到请求数据*/
        return given()
                /*由于是post发送的请求，所以换一个更好的api：queryParam*/
                .queryParam("access_token",Work.getInstance().getToken())
                /*2.首先指名json，因为它是contentType自带的一个变量*/
                .contentType(ContentType.JSON)
                /*5.发送我们请求*/
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .body("errcode",equalTo(0))
        .extract().response();
    }
    public Response create(String name){
        return create(name,parentDepartId);
    }
//删除一个部门的api
    public Response delete(int id){
       return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .queryParam("id",id)
        .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
        .then().log().all()
                .body("errcode",equalTo(0))
        .extract().response();
    }
}
