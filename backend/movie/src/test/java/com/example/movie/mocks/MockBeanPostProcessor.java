package com.example.movie.mocks;


import org.mockito.Mockito;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

@Component
public class MockBeanPostProcessor implements BeanPostProcessor {
    private final List<String> packages = List.of("com.example.movie.services");
    @Autowired
    private MockContainer mockContainer;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String beanPackage = bean.getClass().getPackageName();

        if (packages.contains(beanPackage)) {
            return createMock(bean);
        }
        return bean;
    }

    private Object createMock(Object bean) {
        Object mock = Mockito.mock(
                AopUtils.getTargetClass(bean),
                invocationOnMock -> ReflectionTestUtils.invokeMethod(bean,
                        invocationOnMock.getMethod().getName(),
                        invocationOnMock.getArguments())
        );
        mockContainer.addMock(mock);
        return mock;
    }
}
