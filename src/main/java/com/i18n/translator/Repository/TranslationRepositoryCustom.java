package com.i18n.translator.Repository;

import com.i18n.translator.model.entities.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TranslationRepositoryCustom {
    Page<Translation> findTranslationsByCriteria(
            String key,
            String content,
            String localeCode,
            String tagName,
            Pageable pageable
    );
}