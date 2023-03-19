package com.example.kaddr.service.juso;

import com.example.kaddr.api.ApiClient;
import com.example.kaddr.api.juso.dto.JusoApiResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class JusoServiceTest {
    @Mock
    private ApiClient<JusoApiResponseDto> apiClient;

    @InjectMocks
    private JusoService jusoService;

    private final static String KEYWORD = "길여사길주로부천에있는것같아요";
    private final static String EXPECTED_RESULT = "길주로";

    @Test
    @DisplayName("입력된 keyword 로 조회 서비스 로직을 잘 타는지 확인한다.")
    void convertCorrectAddrTest() {
        // given
        given(apiClient.callApi(anyString()))
                .willReturn(mockJusoApiResponseDto());

        // when
        final String jusoStr = jusoService.convertCorrectAddr(KEYWORD);

        // then
        assertThat(jusoStr).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    @DisplayName("조회 된 주소 목록 중 기존 문자열과 가장 잘 맞는 주소를 선택한다.")
    void findBestMatchJusoTest() throws Exception {
        // given
        Method findBestMatchJusoMethod = JusoService.class.getDeclaredMethod("findBestMatchJuso", String.class, List.class);
        findBestMatchJusoMethod.setAccessible(true);

        // when
        final JusoApiResponseDto.Juso bestMatchJuso = (JusoApiResponseDto.Juso) findBestMatchJusoMethod.invoke(
                jusoService,
                "길여사길주로부천에있는것같아요",
                List.of(mockJusoApiResponseDto())
        );

        // then
        assertThat(bestMatchJuso.getRoadAddr()).isEqualTo("경기도 부천시 길주로 1(상동)");
        assertThat(bestMatchJuso.getSggNm()).isEqualTo("부천시");
        assertThat(bestMatchJuso.getRn()).isEqualTo(EXPECTED_RESULT);
    }

    private static JusoApiResponseDto mockJusoApiResponseDto() {
        return new JusoApiResponseDto(
                new JusoApiResponseDto.Common(1, 1, 1, "0", "정상"),
                List.of(
                        new JusoApiResponseDto.Juso(
                                "경기도 부천시 길주로 1(상동)", "길주로", "부천시", "상동"
                        ),
                        new JusoApiResponseDto.Juso(
                                "부산광역시 사하구 승학로 278-1(괴정동)", "승학로", "사하구", "괴정동"
                        ),
                        new JusoApiResponseDto.Juso(
                                "충청남도 금산군 금산읍 여사길 3-10", "여사길", "금산군", "금산읍"
                        )
                ));
    }
}