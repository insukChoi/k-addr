package com.example.kaddr.utils;

import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 자카드 지수 (두 집합 사이의 유사도를 측정하는 방법)
 * @see <a href="https://ko.wikipedia.org/wiki/%EC%9E%90%EC%B9%B4%EB%93%9C_%EC%A7%80%EC%88%98">자카드 지수</a>
 */
@UtilityClass
public class JaccardIndexUtils {
    /**
     * str1 과 str2 의 유사도를 return 한다.
     */
    public static double calculateJaccardIndex(String str1, String str2) {
        Set<Character> set1 = str1.chars()
                .distinct()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
        Set<Character> set2 = str2.chars()
                .distinct()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());

        int intersection = 0;
        for (char c : set1) {
            if (set2.contains(c)) {
                intersection++;
            }
        }

        int union = set1.size() + set2.size() - intersection;

        return (double) intersection / union;
    }
}