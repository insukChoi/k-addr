package com.example.kaddr.api.juso;

import com.example.kaddr.api.juso.dto.JusoApiResponseDto;
import com.example.kaddr.api.juso.properties.JusoProperties;
import com.example.kaddr.error.ApiCallException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@KAddrSpringBootTest
@EnableConfigurationProperties(JusoProperties.class)
class JusoApiClientTest {
    @Autowired
    private JusoApiClient jusoApiClient;

    @Test
    @DisplayName("juso API 가 정상 동작한다.")
    void jusoApiSuccess() {
        final String keyword = "구일로";

        // when
        final JusoApiResponseDto jusoResponseDto = jusoApiClient.callApi(keyword);

        // then
        successApiCall(jusoResponseDto);
        assertThat(jusoResponseDto.getCommon().getErrorMessage()).as("에러코드 없이 정상").isEqualTo("정상");
    }

    @ParameterizedTest
    @ValueSource(strings = {"마포구 도화2길"})
    @DisplayName("요구사항에 맞게 juso API 가 동작한다.")
    void jusoApiSuccessOfSpecialParam(String keyword) {
        // when
        final JusoApiResponseDto jusoResponseDto = jusoApiClient.callApi(keyword);

        // then
        successApiCall(jusoResponseDto);
        assertThat(jusoResponseDto.getJusoList().get(0).getSggNm()).as("구 이름").isEqualTo("마포구");
        assertThat(jusoResponseDto.getJusoList().get(0).getRn()).as("길 이름").isEqualTo("도화2길");
    }

    @Test
    @DisplayName("keyword 가 null 이면 ApiCallException 가 발생한다.")
    void failedTest() {
        // when & then
        assertThatThrownBy(
                () -> jusoApiClient.callApi(null)
        ).isInstanceOf(ApiCallException.class);
    }

    private static void successApiCall(final JusoApiResponseDto jusoResponseDto) {
        assertThat(jusoResponseDto.getCommon()).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"성남분당백현로265"})
    @DisplayName("요구사항에 맞게 juso API 가 동작한다.")
    void test111(String keyword) {
        // when
        final JusoApiResponseDto jusoResponseDto = jusoApiClient.callApi(keyword);

        // then
        successApiCall(jusoResponseDto);
    }
}