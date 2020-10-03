package service.user.api;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.Work;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class User {
    /*获取userid API */
    public Response get (String userid){
        return given().queryParam("access_token", Work.getInstance().getToken())
                .queryParam("userid",userid)
                .when().log().all().get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
        .then().log().all()
                .extract().response();
    }

    /*输入userid后的返回信息的api*/
    public Response update(String userid, HashMap<String,Object> date){
        date.put("userid",userid);
        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .body(date)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().extract().response();
    }

    /*创建用户api*/
    public Response create(String userid, HashMap<String,Object> date){
        date.put("userid",userid);
        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .body(date)
                .when().log().all().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    /*使用模板创建用户api*/
    public Response Clone(String userid, HashMap<String,Object> date){
        date.put("userid",userid);
        //todo：使用模板技术
        String body=template("/service/user/api/user.json",date);
        /*对src-resources-service-user-api-user.json文件做修改*/

        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(body)
                .when().log().all().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    /*删除用户*/
    public Response delete(String userid){
        return given()
                .queryParam("access_token",Work.getInstance().getToken())
                .queryParam("userid",userid)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
                .then().extract().response();
    }



    /*String templatePath：模板路径*/
    public  String template(String templatePath,HashMap<String,Object> date){
        Writer writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(this.getClass().getResource(templatePath).getPath());
        mustache.execute(writer, date);
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }




}
