package com.xiuzhiwu.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/7 9:53
 * @Description
 */
@Configuration
public class LocalDateTimeSerializerConfig {


    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    // localDateTime 序列化器
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    // localDateTime 反序列化器
    @Bean
    public JsonDeserializer localDateTimeDeserializer() {
        return CustomLocalDateTimeDeserializer.getInstance();
    }

    public static class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        private static final CustomLocalDateTimeDeserializer INSTANCE = new CustomLocalDateTimeDeserializer();

        public static CustomLocalDateTimeDeserializer getInstance() {
            return INSTANCE;
        }

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String valueAsString = jsonParser.getValueAsString();
            if (valueAsString.length() == 10) {
                valueAsString += " 00:00:00";
                ;
            } else if (valueAsString.length() == 0) {
                return null;
            }
            return LocalDateTime.parse(valueAsString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        //这种方式同上
        return builder -> {
            builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
            builder.simpleDateFormat(pattern);
        };
    }
}