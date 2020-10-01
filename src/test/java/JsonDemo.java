import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class JsonDemo {
    @Test
    public void testJson(){
        given().when().get("http://127.0.0.1:8000/demo.json")
                .then()
                /*我们通过hasItms来断言第一本书是否叫reference*/
                .body("store.book.category",hasItems("reference"))
                /*我们也可以断言``store.book``数组里面的第一个作者是否是Nigel Rees*/
                .body("store.book[0].author",equalTo("Nigel Rees"))
                .body("store.book.findAll { book -> book.price >= 5 && book.price <= 15 }",equalTo(""))
                /*查找第一本书的价格是否等于8.95*/
                .body("store.book.find { book -> book.price == 8.95f }.price",equalTo(8.95));

    }
    @Test
    public void testXML(){
        given()
                .when()
                .get("")
                .then()
                .body("shopping.category.item[0].name",equalTo("Chocolate"))
                /*找出特定类型下的数量有多少个*/
                .body("shopping.category.item.size()",equalTo(5))
                /*找出shopping.category，且type等于groceries下面有几个分类*/
                .body("shopping.category.findAll { it.@type == 'groceries' }.size",equalTo(1))
                /*查找price等于20的名字是否是coffee*/
                .body("**.findAll{ it.price == 20}.name",equalTo("Coffee"));
    }
}
