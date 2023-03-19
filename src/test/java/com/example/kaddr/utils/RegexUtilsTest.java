package com.example.kaddr.utils;

import com.example.kaddr.api.juso.dto.JusoReturnType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class RegexUtilsTest {

    @ParameterizedTest
    @MethodSource("provideForRemoveSpecialChar")
    @DisplayName("숫자, 한글 이외의 문자를 모두 제거하는지 확인")
    void removeSpecialCharTest(String text, String expected) {
        // when & then
        assertThat(RegexUtils.removeNotHangulAndNumber(text)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideForSplitSpecialCharTest")
    @DisplayName("특정 문자를 포함하여 문자열을 split 하고 두글자 이상만 list 에 포함하는지 확인")
    void splitSpecialCharWithNumberTest(String text, List<String> expected) {
        // when & then
        assertThat(RegexUtils.splitSpecialChar(text, JusoReturnType.RO.getDescription()))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideForRemoveSpecialChar() {
        return Stream.of(
                of("성남, 분당 백 현 로 265, 푸른마을 아파트로 보내주세요!!", "성남분당백현로265푸른마을아파트로보내주세요"),
                of("마포구 도화-2길 코끼리분식", "마포구도화2길코끼리분식"),
                of("경기도광명시--123철산대로123", "경기도광명시123철산대로123"),
                of("서울시강남구 ABC>>테헤란로11", "서울시강남구테헤란로11"),
                of("  ", "")
        );
    }

    private static Stream<Arguments> provideForSplitSpecialCharTest() {
        return Stream.of(
                of("성남분당백현로265푸른마을아파트로보내주세요", List.of("성남분당백현로", "265푸른마을아파트로")),
                of("길주로로로로가져다주세요", List.of("길주로")),
                of("평리단길로가져다주세요", List.of("평리단길로"))
        );
    }
}