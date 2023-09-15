package net.reduck.jpa.test.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.reduck.jpa.test.entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Gin
 * @since 2023/9/7 10:48
 */
@Service
@RequiredArgsConstructor
public class TestService {

    private final EntityManager entityManager;

    public Object test() {
        CriteriaQuery<Tuple> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Tuple.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.multiselect(root.get("username").alias("username")
                , root.get("password").alias("password")
                , root.get("version").alias("version")
        );
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    @Data
    public static class UserTO {
        private String username;

        private String password;
    }
}
