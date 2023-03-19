package com.example.kaddr;

import com.example.kaddr.service.KAddrService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "command.line.runner", value = "enabled", havingValue = "true", matchIfMissing = true)
public final class KAddrCommandRunner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final static String EXIT_CODE = "exit";

    private final KAddrService kAddrService;

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String address = "";

            while (!address.equals(EXIT_CODE)) {
                log.info("주소를 입력해주세요. (종료하시려면 exit 를 입력하세요.)");
                address = scanner.nextLine();

                if (Objects.equals(address, EXIT_CODE)) break;

                log.info("정확한 주소 출력 = {}",
                        kAddrService.convertCorrectAddr(address)
                );
            }

            log.info("프로그램을 종료 합니다.\n감사합니다.");
        }
    }
}