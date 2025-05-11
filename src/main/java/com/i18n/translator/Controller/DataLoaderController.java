package com.i18n.translator.Controller;

import com.i18n.translator.Repository.LocaleRepository;
import com.i18n.translator.Repository.TagRepository;
import com.i18n.translator.Repository.TranslationRepository;
import com.i18n.translator.Service.LocaleService;
import com.i18n.translator.Service.TagService;
import com.i18n.translator.Service.TranslationService;
import com.i18n.translator.model.Dto.Request.LocaleDto;
import com.i18n.translator.model.Dto.Request.TagDto;
import com.i18n.translator.model.entities.Locale;
import com.i18n.translator.model.entities.Tag;
import com.i18n.translator.model.entities.Translation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/data-loader")
@AllArgsConstructor
@SecurityRequirement(name = "ApiKeyAuth")
public class DataLoaderController {

    private final TranslationRepository translationRepository;

    private final LocaleService localeService;
    private final TagService tagService;


    @Transactional
    @PostMapping
    public void loadData() throws Exception {
        Locale locale = localeService.addLocale(new LocaleDto("fr"));
        Tag tag = tagService.addTag(new TagDto("web"));
        for (int i = 0; i < 100000; i++) {
            Translation translation = new Translation();
            translation.setLocale(locale);
            translation.setTag(tag);
            translation.setKey("key_" + i);
            translation.setContent("content_" + i);
            translationRepository.save(translation);
        }
    }
}