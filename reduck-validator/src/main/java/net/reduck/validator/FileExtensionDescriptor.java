package net.reduck.validator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gin
 * @since 2022/11/30 17:11
 */
@Data
public class FileExtensionDescriptor {
    @JsonIgnore
    private String mime;

    private List<String> signs;

//    @JsonIgnore
//    public List<String> getSigns() {
//        return signs;
//    }

    public List<Sign> getSigns() {
        return signs.stream().map(s -> {
            Sign sign = new Sign();
            String[] split = s.split(",");
            sign.setOffset(Integer.valueOf(split[0]));
            sign.setSign(split[1]);

            return sign;
        }).collect(Collectors.toList());
    }

    @Data
    public static class Sign {
        private int offset;

        private String sign;
    }

    public static void main(String[] args) throws IOException {
        Map<String, FileExtensionDescriptor> map =  JsonUtils.json2Map(FileCopyUtils.copyToString(new FileReader(
                new File("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-validator/src/main/resources/extensions.json")
        )), FileExtensionDescriptor.class);

        System.out.println(JsonUtils.object2JsonFormat(map));
    }
}
