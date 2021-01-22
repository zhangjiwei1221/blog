package cn.zjw.mongo.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * MongoObjectId
 *
 * @author zjw
 * @createTime 2021/1/22 12:08
 */
public class MongoObjectId extends TypeAdapter<ObjectId> {

    @Override
    public void write(JsonWriter out, ObjectId value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.toString());
    }

    @Override
    public ObjectId read(JsonReader in) throws IOException {
        in.beginObject();
        int i = 0;
        long[] nums = new long[4];
        while (in.hasNext()) {
            in.nextName();
            nums[i++] = in.nextLong();
        }
        in.endObject();
        if (i == 0) {
            return null;
        }
        return new ObjectId(String.format("%08x%06x%04x%06x", nums[0], nums[2], nums[3], nums[1]));
    }

}
