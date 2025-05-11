package com.i18n.translator.model.Dto.Response;

import lombok.Data;

import java.util.Map;

@Data
public class ExportTranslationDTO {
    private String locale;
    private Map<String, String> translations;
}