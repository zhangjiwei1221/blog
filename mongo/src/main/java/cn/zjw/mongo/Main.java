package cn.zjw.mongo;

import cn.zjw.mongo.entity.TestEntity;
import com.google.gson.Gson;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Main
 *
 * @author zjw
 * @createTime 2021/1/22 10:33
 */
public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        TestEntity id = new TestEntity(new ObjectId("600a47a0076abd67f0d588f6"));
        System.out.println(gson.fromJson(gson.toJson(id), Document.class));
    }

}
