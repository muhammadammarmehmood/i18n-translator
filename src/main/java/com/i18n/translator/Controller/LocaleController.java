package com.i18n.translator.Controller;

import com.i18n.translator.Service.LocaleService;
import com.i18n.translator.model.Dto.Request.LocaleDto;
import com.i18n.translator.model.entities.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/locale")
public class LocaleController {

    private final LocaleService localeService;

    @PostMapping
    public ResponseEntity<?> createLocale(@RequestBody LocaleDto localeDto) {
        Locale locale = localeService.addLocale(localeDto);
        return ResponseEntity.ok(new LocaleDto(locale.getCode()));
    }
}
