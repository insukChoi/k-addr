package com.example.kaddr.api.juso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("results")
public final class JusoApiResponseDto {
    @JsonProperty("common")
    private Common common;

    @JsonProperty("juso")
    private List<Juso> jusoList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Common {
        private int totalCount;
        private int currentPage;
        private int countPerPage;
        private String errorCode;
        private String errorMessage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Juso {
        public static final Juso EMPTY = new Juso();
        private String roadAddr; // ex) 서울특별시 마포구 성암로 301 (상암동)
        private String rn; // ex) 성암로
        private String sggNm; // ex) 마포구
        private String emdNm; // ex) 상암동

        public String getJusoByReturnType(JusoReturnType type) {
            return switch (type) {
                case RO -> rn;
                case GIL -> "%s %s".formatted(sggNm, rn); // '길' 일 경우 구 + 길 출력
            };
        }
    }

    public boolean hasJuso() {
        return this.getCommon().totalCount > 0;
    }
}