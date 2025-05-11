package com.i18n.translator.Service.Impl;

import com.i18n.translator.Repository.LocaleRepository;
import com.i18n.translator.Service.LocaleService;
import com.i18n.translator.model.Dto.Request.LocaleDto;
import com.i18n.translator.model.entities.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocaleServiceImpl implements LocaleService {
    private final LocaleRepository localeRepository;


    @Override
    public String addLocale(LocaleDto localeDto) {
        if (localeRepository.findByCode(localeDto.getCode()).isPresent()) {
            throw new IllegalArgumentException("Locale already exists.");
        }
        Locale locale = new Locale();
        locale.setCode(localeDto.getCode());
        locale = localeRepository.save(locale);
        return locale.getCode();
    }
}
