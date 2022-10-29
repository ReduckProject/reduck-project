package net.reduck.jpa.transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.reduck.jpa.specification.transform.NameHandler;
import net.reduck.jpa.specification.transform.TupleToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigInteger;

/**
 * @author Reduck
 * @since 2022/9/23 13:36
 */
public class TupleToBeanTransformerTest {

    @Test
    public void testTransfer() {

        // Constructor 1000 duration 24 ms
        // Constructor 10000 duration 19 ms
        // Constructor 100000 duration 37 ms
        // Constructor 1000000 duration 255 ms
        // Constructor 10000000 duration 1988 ms
        // Constructor 100000000 duration 16022 ms
        // Constructor 1000000000 duration 161014 ms

        String[] alias = new String[]{"id", "password", "username", "version", "create_time", "is_deleted", "update_time", "info_id"};
        Object[] data = new Object[]{BigInteger.valueOf(1), "123456", "%<_>./\\", BigInteger.valueOf(0), BigInteger.valueOf(0), Boolean.valueOf(false), BigInteger.valueOf(0), BigInteger.valueOf(16)};
        process(data, alias, 1000);
        process(data, alias, 1_0000);
        process(data, alias, 10_0000);
        process(data, alias, 100_0000);
        process(data, alias, 1000_0000);
        process(data, alias, 1_0000_0000);
        process(data, alias, 10_0000_0000);
    }

    @Test
    public void testNameTransform(){
        System.out.println(NameHandler.withLine("ZHANJINKAI"));
        System.out.println(NameHandler.withLine("_ZHANJINKAI"));
        System.out.println(NameHandler.withLine("zhanJinKAI"));
        System.out.println(NameHandler.withLine("_zhanJinKAI"));
        System.out.println(NameHandler.withLine("_ZhanJinKAI"));
        System.out.println(NameHandler.withLine("_ZhanJinKAI_"));

        System.out.println(NameHandler.withCase("Zhanjinkai"));
        System.out.println(NameHandler.withCase("_Zhanjinkai"));
        System.out.println(NameHandler.withCase("Zhan_jin_kai"));
        System.out.println(NameHandler.withCase("_Zhan_jin_kai"));
        System.out.println(NameHandler.withCase("_zhan_jin_kai"));
        System.out.println(NameHandler.withCase("_zhan_jin_kai_"));
    }

    private void process(Object[] data, String[] alias, int maxRow){
        ResultTransformer resultTransformer = new TupleToBeanResultTransformer<>(UserVO.class);
        long time = System.currentTimeMillis();
        for (int i = 0; i < maxRow; i++) {
            resultTransformer.transformTuple(data, alias);
        }

        System.out.println("Constructor " + maxRow + " duration " + (System.currentTimeMillis() - time) + " ms");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserVO {
        private String username;

        private String password;

        /**
         * 是否删除
         */
        @Column(name = "is_deleted")
        private boolean deleted;

        /**
         * 版本
         */
        @Version
        @Column(name = "version")
        private long version;

        /**
         * 创建时间
         */
        @Column(name = "create_time")
        private long createTime;

        /**
         * 修改时间
         */
        @Column(name = "update_time")
        private long updateTime;

        @Id
        @GeneratedValue
        private Long id;

        public UserVO(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
