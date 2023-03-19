package com.example.kaddr.api.juso;

import com.example.kaddr.api.ApiClient;
import com.example.kaddr.api.juso.dto.JusoApiResponseDto;
import com.example.kaddr.api.juso.properties.JusoProperties;
import com.example.kaddr.error.ApiCallException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * 주소 검색 오픈 API
 * @see <a href="https://business.juso.go.kr/addrlink/openApi/searchApi.do">Juso</a>
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JusoProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "api.use", value = "name", havingValue = "juso")
public class JusoApiClient implements ApiClient<JusoApiResponseDto> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JusoProperties jusoProperties;
    private final ObjectMapper objectMapper;

    @Override
    public String uri() {
        return format(
                "{0}?currentPage=1&countPerPage=10&keyword=%s&confmKey={1}&resultType={2}",
                jusoProperties.getUri(),
                key(),
                responseType()
        );
    }

    @Override
    public String key() {
        return jusoProperties.getKey();
    }

    @Override
    public JusoApiResponseDto callApi(final String keyword) {
        try {
            final URI uri = new URI(
                    String.format(uri(), encode(keyword, UTF_8))
            );

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Accept", requestType())
                    .GET()
                    .timeout(Duration.of(5, SECONDS))
                    .build();

            final HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), JusoApiResponseDto.class);
        } catch (Exception e) {
            log.error("juso Api Call Error : {}", keyword);
            throw new ApiCallException(keyword, e);
        }
    }
}