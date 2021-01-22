package cn.zjw.mirai;

import cn.zjw.mirai.entity.Address;
import cn.zjw.mirai.entity.User;
import cn.zjw.mirai.util.MongoJdbc;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;

/**
 * Main
 *
 * @author zjw
 * @createTime 2021/1/22 10:33
 */
public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        MongoDatabase database = MongoJdbc.getDatabase();
        MongoCollection<Document> userCollection = database.getCollection("user");
        MongoCollection<Document> addressCollection = database.getCollection("address");
        String[] addresses = { "河南", "江苏", "浙江", "上海" };
        String[] names = { "张三", "李四", "王二", "赵六" };
        int i = 0;
        for (String addressName : addresses) {
            FindIterable<Document> address = addressCollection.find(Filters.eq("addressName", addressName));
            for (Document document : address) {
                Address tmp = gson.fromJson(gson.toJson(document), Address.class);
                User user = new User();
                user.setUsername(names[i++]);
                user.setAddress(tmp.get_id());
                userCollection.insertOne(gson.fromJson(gson.toJson(user), Document.class));
            }
        }
    }

}
