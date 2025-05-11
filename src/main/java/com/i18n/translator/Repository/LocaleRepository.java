package com.i18n.translator.Repository;

import com.i18n.translator.model.entities.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocaleRepository extends JpaRepository<Locale, Long> {
    Optional<Locale> findByCode(String code);
}
