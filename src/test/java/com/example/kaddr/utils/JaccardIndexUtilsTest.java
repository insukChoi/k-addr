package com.example.kaddr.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JaccardIndexUtilsTest {

    @Test
    @DisplayName("유사도가 100% 일 경우 1.0 을 return 한다.")
    void similarity100() {
        // when & then
        assertThat(JaccardIndexUtils.calculateJaccardIndex("구일로8길", "구일로8길"))
                .isEqualTo(1.0);
    }

    @Test
    @DisplayName("유사도가 0% 일 경우 0.0 을 return 한다.")
    void similarity0() {
        // when & then
        assertThat(JaccardIndexUtils.calculateJaccardIndex("테헤란길", "구일로"))
                .isEqualTo(0.0);
    }

    @Test
    @DisplayName("유사도가 가장 높은것을 선택할 수 있다.")
    void findBestSimilarity() {
        // given
        String target = "서울인가? 경기도인가? 영등포구 영신로 220인지 221인지...";
        final List<String> strings = List.of(
                "영등포구 영신로 220",
                "영신로 221",
                "서울시 영등포구 영신로 220",
                "강원도 영등포구 영신로 220"
        );

        // when
        final String bestString = strings.stream()
                .max(Comparator.comparing(s ->
                        JaccardIndexUtils.calculateJaccardIndex(s, target)))
                .get();

        // then
        assertThat(bestString)
                .isEqualTo("서울시 영등포구 영신로 220");
    }
}