package com.i18n.translator.Repository;

import com.i18n.translator.model.entities.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long>, TranslationRepositoryCustom  {
    Optional<Translation> findByLocaleCodeAndTagNameAndKey(String localeCode, String tagName, String key);

    @Query("Select t from Translation t " +
            "JOIN t.locale l " +
            "WHERE l.code =  :localeCode " +
            "AND l.activeIndicator = true " +
            "AND t.activeIndicator = true")
    List<Translation> findByLocaleCode(@Param("localeCode") String localeCode);
}