package com.i18n.translator.Service;

import com.i18n.translator.model.Dto.Request.TranslationRequestDTO;
import com.i18n.translator.model.Dto.Response.ExportTranslationDTO;
import com.i18n.translator.model.Dto.Response.PageableDto;
import com.i18n.translator.model.Dto.Response.TranslationDto;

import java.util.List;

public interface TranslationService {

    TranslationDto createTranslation(TranslationRequestDTO dto);

    TranslationDto updateTranslation(Long translationId, TranslationRequestDTO dto);

    PageableDto<List<TranslationDto>> getTranslations(String key, String content, String localeCode, String tagName, int page, int size);

    ExportTranslationDTO exportTranslations(String localeCode);
}
