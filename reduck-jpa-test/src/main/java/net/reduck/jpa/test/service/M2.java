package net.reduck.jpa.test.service;

import org.springframework.stereotype.Service;

/**
 * @author Reduck
 * @since 2023/1/16 15:07
 */
@Service
public class M2 implements MultiplyService {
    @Override
    public void test() {
        System.out.println("2222");
    }
}
