package cn.butterfly.flinkcdc.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.debezium.data.Envelope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Jackson 序列化配置
 *
 * @author zjw
 * @date 2023-03-16
 */
@Configuration
public class JacksonSerializerConfig {

    /**
     * 序列化配置
     *
     * @return objectMapper
     */
    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addSerializer(Envelope.Operation.class, new OpTypeSerializer());
        var opTypeModule = new SimpleModule();
        opTypeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        opTypeModule.addDeserializer(Envelope.Operation.class, new OpTypeDeserializer());
        return JsonMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true)
                .addModules(javaTimeModule, opTypeModule)
                .build();
    }

    /**
     * OpType 序列化
     */
    public static class OpTypeSerializer extends JsonSerializer<Envelope.Operation> {

        @Override
        public void serialize(Envelope.Operation opType, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (opType != null){
                gen.writeString(opType.code());
            }
        }

    }

    /**
     * OpType 反序列化
     */
    public static class OpTypeDeserializer extends JsonDeserializer<Envelope.Operation> {

        @Override
        public Envelope.Operation deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            return Envelope.Operation.forCode(p.getValueAsString());
        }

    }

    /**
     * LocalDateTime 序列化
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null){
                var timestamp = value.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
                gen.writeNumber(timestamp);
            }
        }

    }

    /**
     * LocalDateTime 反序列化
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            var timestamp = p.getValueAsLong();
            if (timestamp > 0){
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
            }
            return LocalDateTime.now(ZoneOffset.UTC);
        }
    }

}
