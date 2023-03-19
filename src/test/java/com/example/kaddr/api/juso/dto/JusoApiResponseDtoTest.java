package com.example.kaddr.api.juso.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JusoApiResponseDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void init() {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    @Test
    @DisplayName("juso API response 가 DTO 객체로 정상적으로 deserialize 되는지 확인한다.")
    void apiResponseDeserializeTest() throws IOException {
        // given
        final ClassPathResource resource = new ClassPathResource("juso/response.json");

        // when
        final JusoApiResponseDto jusoResponseDto = objectMapper.readValue(resource.getInputStream(), JusoApiResponseDto.class);

        // then
        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(jusoResponseDto.getCommon()).as("common").isNotNull();
        softAssertions.assertThat(jusoResponseDto.getJusoList().size()).as("size").isEqualTo(1);

        final JusoApiResponseDto.Juso firstJuso = jusoResponseDto.getJusoList().get(0);
        softAssertions.assertThat(firstJuso.getRoadAddr()).as("roadAddr").isEqualTo("서울특별시 마포구 성암로 301 (상암동)");
        softAssertions.assertThat(firstJuso.getSggNm()).as("sggNm").isEqualTo("마포구");
        softAssertions.assertThat(firstJuso.getEmdNm()).as("emdNm").isEqualTo("상암동");
        softAssertions.assertThat(firstJuso.getRn()).as("rn").isEqualTo("성암로");
        softAssertions.assertAll();
    }
}