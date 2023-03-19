package com.example.kaddr.api.juso.properties;

import com.example.kaddr.api.juso.KAddrSpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static org.assertj.core.api.Assertions.assertThat;

@KAddrSpringBootTest
@EnableConfigurationProperties(JusoProperties.class)
class JusoPropertiesTest {
    @Autowired
    private JusoProperties jusoProperties;

    @Test
    @DisplayName("Juso Properties 값을 가져오는지 확인한다.")
    void jsoPropertiesTest() {
        assertThat(jusoProperties.getUri()).as("URI").isEqualTo("https://business.juso.go.kr/addrlink/addrLinkApi.do");
        assertThat(jusoProperties.getKey()).as("KEY").isEqualTo("devU01TX0FVVEgyMDIzMDMxNjIxMTcyNzExMzYwMjA=");
    }
}