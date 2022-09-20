package net.reduck.clickhouse.test.repository.converter;

import lombok.SneakyThrows;
import ru.yandex.clickhouse.ClickHouseArray;
import ru.yandex.clickhouse.domain.ClickHouseDataType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Reduck
 * @since 2022/9/17 17:14
 */
public class ClickhouseTypeConverters {
    @Converter(autoApply = true)
    public static class ArrayString implements AttributeConverter<String[], ClickHouseArray> {
        @Override
        public ClickHouseArray convertToDatabaseColumn(String[] strings) {
            if(strings == null){
                return new ClickHouseArray(ClickHouseDataType.String, new String[0]);
            }

            return new ClickHouseArray(ClickHouseDataType.String, strings);
        }

        @SneakyThrows
        @Override
        public String[] convertToEntityAttribute(ClickHouseArray clickHouseArray) {
            String[] data =  (String[]) clickHouseArray.getArray();
            if(data == null){
                return null;
            }

            // clickhouse 字符串数组 `NULL`会处理成 null类型，如果不允许为NULL可以统一全局处理
//            for(int i = 0; i< data.length; i++){
//                if(data[i] == null){
//                    data[i] = "NULL";
//                }
//            }

            return data;
        }
    }
}
