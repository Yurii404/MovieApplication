package com.example.movie;

import com.example.movie.dtos.weather.WeatherDto;
import com.example.movie.mocks.MockBeanTestExecutionListener;
import com.example.movie.services.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
        value = MockBeanTestExecutionListener.class)
public class WeatherServiceIntegrationTest {
    @MockBean
    private WeatherService weatherService;

    @Test
    public void shouldReturnWeather() {
        Mockito.when(weatherService.getWeather()).thenReturn(new WeatherDto());

        WeatherDto weatherDto = weatherService.getWeather();
        System.out.println(weatherDto);
        assertNotNull(weatherDto);
    }

}
