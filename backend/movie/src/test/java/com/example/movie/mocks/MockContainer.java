package com.example.movie.mocks;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class MockContainer {
    private final List<Object> mocks = new ArrayList<>();

    public void addMock(Object bean) {
        mocks.add(bean);
    }
}