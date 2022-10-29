package net.reduck.jpa.test.hibernate;

import net.reduck.jpa.test.hbm.Event;
import net.reduck.jpa.test.hbm.HibernateUtil;
import net.reduck.jpa.test.hbm.UserDTO;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/9/21 09:50
 */
public class HibernateTest {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Test
    public void testConnect() {
        Session session = sessionFactory.openSession();

        Metamodel metamodel = sessionFactory.getMetamodel();

        session.createQuery("from Event").list();


        NativeQuery nativeQuery = session.createSQLQuery("select * from user limit 5");
        Constructor constructor = null;
        for (Constructor c : UserDTO.class.getConstructors()) {
            if (c.getParameterCount() > 0) {
                constructor = c;
            }
        }

        nativeQuery.setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class));
        List result = nativeQuery.getResultList();

        System.out.println(result.size());

    }

    @Test
    public void testSave() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new Event("Our very first event!", new Date()));
        session.save(new Event("A follow up event", new Date()));
        session.getTransaction().commit();
        session.close();
    }
}
