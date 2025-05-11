package com.i18n.translator.model.Dto.Request;

import lombok.Data;

@Data
public class TranslationRequestDTO {
    private String localeCode;
    private String tagName;
    private String key;
    private String content;
    private Boolean activeIndicator;
}