
package com.example.movie;

import com.example.movie.dtos.UserDto;
import com.example.movie.dtos.weather.WeatherDto;
import com.example.movie.mocks.MockBeanTestExecutionListener;
import com.example.movie.services.UserService;
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
public class UserServiceServiceIntegrationTest {
    @MockBean
    private WeatherService weatherService;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnWeather() {
        Mockito.when(weatherService.getWeather()).thenReturn(new WeatherDto());

        WeatherDto weatherDto = weatherService.getWeather();
        System.out.println(weatherDto);
        assertNotNull(weatherDto);
    }

    @Test
    public void shouldReturnUser() {
        UserDto userDtoFromMock = UserDto.builder()
                .id(1)
                .email("12@gmail.com")
                .firstName("Yu")
                .secondName("Ku")
                .password("123")
                .phone("344141")
                .roleId(1)
                .build();

        Mockito.when(userService.findById(1)).thenReturn(userDtoFromMock);

        UserDto userDto = userService.findById(1);
        System.out.println(userDto);
        assertNotNull(userDto);
    }

}