package net.reduck.jpa.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Reduck
 * @since 2022/9/26 11:30
 */
@Transactional
@Service
@RequiredArgsConstructor
public class TransactionalService implements InitializingBean {
    private final List<MultiplyService> serviceList;

    public void test(){

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(serviceList.size());
    }
}
