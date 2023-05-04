package net.reduck.jpa.test.jpa;

import net.reduck.jpa.test.ReduckJpaTestApplication;
import net.reduck.jpa.test.entity.PersonalInfo;
import net.reduck.jpa.test.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

/**
 * @author Gin
 * @since 2023/2/9 16:47
 */
@SpringBootTest(classes = ReduckJpaTestApplication.class)
public class QueryTest {

    @Autowired
    EntityManager em;

    @Test
    public void test() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = builder.createQuery(Tuple.class);


        Root<User> root = cq.from(User.class);
        cq.multiselect(root.get("id"), root.get("username"));

        Path x = root.get("id");

        {
            Subquery<PersonalInfo> subquery = cq.subquery(PersonalInfo.class);
            Root<PersonalInfo> subRoot = subquery.from(PersonalInfo.class);
            subquery.select(subRoot.get("id"));
        }

        Set<EntityType<?>> types = em.getMetamodel().getEntities();
        for(EntityType<?> type : types) {
            for(Attribute attribute : type.getAttributes()) {
                System.out.println(attribute.getName());
                System.out.println(attribute.getJavaType());
            }
        }
        Predicate predicate = builder.and(builder.equal(root.get("id"), 10));
        Predicate predicate2 =builder.or(builder.like(root.get("username"), "%test%"), builder.greaterThanOrEqualTo(root.get("id"), 4));

        predicate = builder.and(
                predicate
                , predicate2
        );
        cq.where(predicate);

        List<Tuple> users = em.createQuery(cq).getResultList();

        cq.orderBy(builder.desc(root.get("id")));

        System.out.println(users.size());
        System.out.println();


    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec("1234567812345678".getBytes(), "AES"));
        byte[] data = cipher.doFinal(FileCopyUtils.copyToByteArray(new File("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-jpa-test/src/test/java/debug.test")));

        FileCopyUtils.copy(data, new File("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-jpa-test/src/test/java/debug.zip"));
    }
}
