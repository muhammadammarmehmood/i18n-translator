package com.i18n.translator.Repository;

import com.i18n.translator.model.entities.Translation;

import java.util.List;

public interface TranslationRepositoryCustom {
    List<Translation> findTranslationsByCriteria(String key, String content, String localeCode, String tagName);
}