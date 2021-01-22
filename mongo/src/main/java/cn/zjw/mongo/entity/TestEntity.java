package cn.zjw.mongo.entity;

import cn.zjw.mongo.util.MongoObjectId;
import com.google.gson.annotations.JsonAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * TestEntity
 *
 * @author zjw
 * @createTime 2021/1/22 15:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {

    @JsonAdapter(MongoObjectId.class)
    private ObjectId _id;
}
