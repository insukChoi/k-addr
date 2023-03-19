package com.example.kaddr.service.juso;

import com.example.kaddr.api.ApiClient;
import com.example.kaddr.api.juso.dto.JusoApiResponseDto;
import com.example.kaddr.api.juso.dto.JusoReturnType;
import com.example.kaddr.service.KAddrService;
import com.example.kaddr.utils.JaccardIndexUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.kaddr.api.juso.dto.JusoApiResponseDto.Juso.EMPTY;
import static com.example.kaddr.utils.MessageUtils.NO_SEARCH_JUSO_MESSAGE;
import static com.example.kaddr.utils.RegexUtils.removeNotHangulAndNumber;
import static com.example.kaddr.utils.RegexUtils.splitSpecialChar;

@Service
@RequiredArgsConstructor
public final class JusoService implements KAddrService {

    private final ApiClient<JusoApiResponseDto> apiClient;

    @Override
    public String convertCorrectAddr(final String address) {
        final JusoApiResponseDto.Juso juso = callJusoApi(address);
        if (juso == EMPTY) return NO_SEARCH_JUSO_MESSAGE;

        return juso.getJusoByReturnType(JusoReturnType.defineType(juso.getRn()));
    }

    public JusoApiResponseDto.Juso callJusoApi(final String address) {
        final String refinedAddress = removeNotHangulAndNumber(address);

        List<JusoApiResponseDto> dtoList = new ArrayList<>();
        for (final JusoReturnType value : JusoReturnType.values()) {
            final List<String> splitByReturnType = splitSpecialChar(refinedAddress, value.getDescription());

            for (String splitAddress : splitByReturnType) {
                final JusoApiResponseDto dto = apiClientCall(splitAddress);
                if (dto.hasJuso()) {
                    dtoList.add(dto);
                } else {
                    retryShortenString(splitAddress, dtoList);
                }
            }
        }

        return findBestMatchJuso(refinedAddress, dtoList);
    }

    // 조회 결과가 없을시, 주소 문자열을 한글자씩 줄이면서 조회해본다.
    private void retryShortenString(final String addressStr, final List<JusoApiResponseDto> dtoList) {
        for (int i = 1; i < addressStr.length(); i++) {
            String shortenAddress = addressStr.substring(i);
            if (shortenAddress.length() < 2) break;

            final JusoApiResponseDto dto = apiClientCall(shortenAddress);
            if (dto.hasJuso()) dtoList.add(dto);
        }
    }

    // 조회 된 주소 목록 중 기존 문자열과 가장 잘 맞는 주소를 선택한다.
    private JusoApiResponseDto.Juso findBestMatchJuso(final String refinedAddress, final List<JusoApiResponseDto> dtoList) {
        return dtoList.stream()
                .flatMap(dto -> dto.getJusoList().stream())
                .max(Comparator.comparing(juso ->
                        JaccardIndexUtils.calculateJaccardIndex(refinedAddress, juso.getRoadAddr()))
                )
                .orElse(EMPTY);
    }

    private JusoApiResponseDto apiClientCall(final String shortenAddress) {
        return apiClient.callApi(shortenAddress);
    }
}