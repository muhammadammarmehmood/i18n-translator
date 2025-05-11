package com.i18n.translator.model.Dto.Response;

import com.i18n.translator.model.entities.Translation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TranslationDto {
    private String localeCode;
    private String tagName;
    private String key;
    private String content;
    private boolean activeIndicator;


    public TranslationDto(Translation translation) {
        this.localeCode = translation.getLocale().getCode();
        this.tagName = translation.getTag().getName();
        this.key = translation.getKey();
        this.content = translation.getContent();
        this.activeIndicator = Boolean.TRUE.equals(translation.getActiveIndicator());;
    }
}