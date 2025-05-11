package com.i18n.translator.Repository;

import com.i18n.translator.model.entities.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long>, TranslationRepositoryCustom  {
    Optional<Translation> findByLocaleCodeAndTagNameAndKey(String localeCode, String tagName, String key);
}