package com.dvivasva.account.webclient;

import com.dvivasva.account.utils.UriAccess;
import com.dvivasva.account.utils.UriBase;
import com.dvivasva.account.webclient.dto.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class CustomerWebClient {

    WebClient client = WebClient.builder()
            .baseUrl(UriBase.URL_CUSTOMER_SERVICE_8092)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", UriBase.URL_CUSTOMER_SERVICE_8092))
            .build();

    public Mono<CustomerDto> details(String id) {
        return client.get()
                .uri( UriBase.URL_CUSTOMER_SERVICE_8092 + UriAccess.CUSTOMER+ "/" +id)
                .accept(MediaType.APPLICATION_NDJSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(CustomerDto.class);
                    }
                else {
                    // Turn to error
                    return response.createException().flatMap(Mono::error);
                }
        });
    }


}
