package com.unipay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Getter
@Setter
public class WebhookDto {
    private String url;
    private String secret;
    private List<String> events;
}
