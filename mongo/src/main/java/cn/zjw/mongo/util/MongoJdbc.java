package cn.zjw.mongo.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * MongoJDBC
 *
 * @author zjw
 * @createTime 2021/1/7 20:44
 */
public class MongoJdbc {

    private static Properties pro;
    private static MongoClient mongoClient;

    static {
        try {
            pro = new Properties();
            pro.load(new InputStreamReader(MongoJdbc.class.getResourceAsStream("/db.properties")));
            mongoClient = new MongoClient(new MongoClientURI(
                    String.format(
                            "mongodb://%s:%s@%s:%s",
                            pro.get("username"),
                            pro.get("password"),
                            pro.get("address"),
                            pro.get("port")
                    ))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MongoJdbc() {}

    public static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(String.valueOf(pro.get("db")));
    }

}
