package net.reduck.validator;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Reduck
 * @since 2022/8/25 11:26
 */

@PrepareForTest(Integer.class)
@RunWith(PowerMockRunner.class)
public class MojoTest {

    @Before
    public void before() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
    }
}
