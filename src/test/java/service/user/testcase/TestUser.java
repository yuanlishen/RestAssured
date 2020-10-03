package service.user.testcase;

import org.junit.jupiter.api.Test;
import service.user.api.User;

import java.io.*;
import java.util.HashMap;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;



import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;

public class TestUser {
    /*info：获取信息
    * 注释：获取用户信息，需要一个userid*/
    @Test
    public void info(){
        User user = new User();
        user.get("Test_100").then()
                .body("name",equalTo("Test_100"));
    }

    /*updata：更新一个用户【修改用户信息】*/
    @Test
    public void update(){
        User user = new User();
        String userid="Test_100";
        String nameNew="name_for_test";
//        user.update("xxx","xxx","xxx");
        HashMap<String,Object> date = new HashMap<>();
        date.put("name",nameNew);
        date.put("adress","address_for_test");
        user.update(userid,date);
        user.get(userid).then().body("name",equalTo(nameNew));
    }

    @Test
    /*创建用户*/
    public void create(){
        String nameNew="name for test";
        /*userid增加一个时间戳；System.currentTimeMillis()：时间戳*/
        String userid="Test_100"+System.currentTimeMillis();

        HashMap<String,Object> date = new HashMap<>();
        date.put("name",nameNew);
        date.put("department",new int[]{1});
        /*输入手机号*/
//        date.put("mobile","18518760123");
        /*随机获取手机号，获取的手机号是把时间戳切片*/
        date.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));
        date.put("adress","address_for_test");
        /*创建create*/
        User user = new User();
        user.create(userid,date)
                /*断言结果是否正确*/
                .then().body("errcode",equalTo(0));
        /*创建完成后，断言结果是否正确;断言userid是否存在*/
        user.get(userid).then().body("name",equalTo(nameNew));
    }

    @Test
    public void cloneUser(){
    /*    获取模板信息
     *    User user=new User();
     *    user.get("tester-01");*/
        String nameNew="name for test";
        String userid="Test_100"+System.currentTimeMillis();

        HashMap<String,Object> date = new HashMap<>();
        date.put("name",nameNew);
//        date.put("department",new int[]{1});
        /*随机获取手机号，获取的手机号是把时间戳切片*/
        date.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));
        /*创建create*/
        User user = new User();
        user.Clone(userid,date)
                .then().body("errcode",equalTo(0));
        user.get(userid).then().body("name",equalTo(nameNew));

    }
    //删除用户
    @Test
    public void delete(){
        String nameNew="name for test";
        String userid="Test_100"+System.currentTimeMillis();
        HashMap<String,Object> date = new HashMap<>();
        date.put("name",nameNew);
//        date.put("department",new int[]{1});
        /*随机获取手机号，获取的手机号是把时间戳切片*/
        date.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));
        /*创建create*/
        User user = new User();
        user.Clone(userid,date).then().log().all().body("errcode",equalTo(0));
        /*删除一个用户*/
        user.delete(userid).then().log().all().body("errcode",equalTo(0));
        /*获取一个不存在的用户*/
        user.get(userid).then().log().all().body("errcode",not(equalTo(0)));
    }
    //todo：获取部门成员列表
    //todo：批量删除

//    @Test
//    public void template() throws IOException {
//        HashMap<String, Object> data = new HashMap<String, Object>();
//        data.put("name", "Mustache");
//        data.put("mobile",String.valueOf(System.currentTimeMillis()).substring(0,11));
//        data.put("userid",System.currentTimeMillis());
//
//        Writer writer = new StringWriter();
//        MustacheFactory mf = new DefaultMustacheFactory();
//        Mustache mustache = mf.compile(this.getClass().getResource("/service/user/api/user.json").getPath());
//        mustache.execute(writer, data);
//        writer.flush();
//        System.out.println(writer.toString());
//    }
}
