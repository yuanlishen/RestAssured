package service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

//获取token；因为token是全局使用的，所以放在最外层  ，公共的类
public class Work {

    /*这个work变量里存储我们所需要的数据*/
    private static Work work;
    String token;

    /*单例模式；在这个方法里面我们可以生成一个唯一的实例，这个实例里面可以完成对Work类的一个实例化*/
    public static Work getInstance(){
        if(work==null){
            work=new Work();
        }
        return work;
    }

    public String getToken(){
        if (token==null) {
            token=given()
                    /*访问地址需要带的参数*/
                    .param("corpid", "ww6f1c69864c7fb4b6")
                    .param("corpsecret", "Q68mwHwSL6h8nMkJHEvwkxSequwxqj2VHzsTX_oZm1g")
                    .when()
                    .log().all()
                    /*访问的地址； 是get还是post*/
                    .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                    /*断言*/
                    .then()
                    .log().all()
                    .body("errcode", equalTo(0))
                    /*把获取到的数据导出*/
                    .extract().body().path("access_token");
            System.out.println(token);
        }
        return token;
    }
}
