package com.example.kaddr.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public final class RegexUtils {

    /**
     * 한글, 숫자 이외는 모두 제거한다.
     */
    public static String removeNotHangulAndNumber(@NonNull String input) {
        return input.replaceAll("[^0-9가-힣]", "").strip();
    }

    /**
     * 특정 문자를 포함하여 문자열을 split 하고 두글자 이상만 list 에 포함한다.
     */
    public static List<String> splitSpecialChar(@NonNull String input, @NonNull String specialChar) {
        return Arrays.stream(input.split("(?<=%s)".formatted(specialChar)))
                .filter(it -> it.contains(specialChar))
                .filter(it -> it.length() > 1)
                .toList();
    }
}