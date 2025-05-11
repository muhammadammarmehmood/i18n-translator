package com.i18n.translator.Service;

import com.i18n.translator.model.Dto.Request.LocaleDto;
import com.i18n.translator.model.entities.Locale;

public interface LocaleService {
    Locale addLocale(LocaleDto localeDto);
}
