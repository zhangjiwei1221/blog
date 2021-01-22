package cn.zjw.mongo.entity;

import cn.zjw.mongo.util.MongoObjectId;
import com.google.gson.annotations.JsonAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * User
 *
 * @author zjw
 * @createTime 2021/1/22 11:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonAdapter(MongoObjectId.class)
    private ObjectId _id;
    private String username;
    @JsonAdapter(MongoObjectId.class)
    private ObjectId address;
}
