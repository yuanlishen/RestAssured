package service.department.testcase;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.Work;
import service.department.api.Department;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/*部门测试用例，涉及到增删改查*/
public class TestDepartment {

    static Department department=new Department();
    /*创建部门之前需要先清理数据*/
    @BeforeAll
    public static void beforeAll(){
        /*数据清理*/
        ArrayList<Integer> ids = department.list(department.parentDepartId).then()
                .extract().body().path("department.findAll {d->d.parentid==" + department.parentDepartId + "}.id");
        System.out.println(ids);
        ids.forEach(id -> department.delete(id));
    }

    //列举部门
    @Test
    public void list(){
        //列举部门需要depart.list(4)还需要一个断言assert()
        /*把department模型化，以api的形式提供服务*/
        department.list(department.parentDepartId).then().body("errmsg",equalTo("ok"));
    }
    //创建一个部门
    @Test
    public void create(){
        String name = "部门2";
        department.create(name).then().body("errmsg",equalTo("created"));
        department.list(department.parentDepartId)
                .then()
                .body("department.findAll {d->d.name=='" + name + "'}.id",hasSize(1));
    }

    //删除一个部门
    @Test
    public void delete(){
        int id=department.create("部门3").then().body("errmsg",equalTo("created"))
                .extract().body().path("id");
        System.out.println(id);
        department.delete(id).then().body("errmsg",equalTo("deleted"));

    }
}
