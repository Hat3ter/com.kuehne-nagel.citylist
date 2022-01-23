package com.solbegsoft.sitylist;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Abstract test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SitylistApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class AbstractTestCaseTest {

    //empty
}