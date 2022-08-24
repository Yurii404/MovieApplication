package com.example.movie.mocks;

import org.mockito.Mockito;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.util.List;

@Component
public class MockBeanTestExecutionListener implements TestExecutionListener {
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        List<Object> mocks = testContext.getApplicationContext()
                .getBean(MockContainer.class)
                .getMocks();
        mocks.forEach(Mockito::reset);
    }

    
}