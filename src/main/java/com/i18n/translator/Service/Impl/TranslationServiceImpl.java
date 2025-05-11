package com.i18n.translator.Service.Impl;

import com.i18n.translator.Repository.LocaleRepository;
import com.i18n.translator.Repository.TagRepository;
import com.i18n.translator.Repository.TranslationRepository;
import com.i18n.translator.Service.TranslationService;
import com.i18n.translator.model.Dto.Request.TranslationRequestDTO;
import com.i18n.translator.model.Dto.Response.TranslationDto;
import com.i18n.translator.model.entities.Locale;
import com.i18n.translator.model.entities.Tag;
import com.i18n.translator.model.entities.Translation;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final LocaleRepository localeRepository;
    private final TagRepository tagRepository;
    private final TranslationRepository translationRepository;
    private final ModelMapper modelMapper;

    @Override
    public TranslationDto createTranslation(TranslationRequestDTO dto) {
        Locale locale = localeRepository.findByCode(dto.getLocaleCode())
                .orElseThrow(() -> new IllegalArgumentException("Locale not found"));

        Tag tag = tagRepository.findByName(dto.getTagName())
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        Optional<Translation> translationOptional = translationRepository.findByLocaleCodeAndTagNameAndKey(dto.getLocaleCode(), dto.getTagName(), dto.getKey());
        if (translationOptional.isPresent()) {
            throw new IllegalArgumentException("Translation already exists.");
        }

        Translation translation = new Translation();
        translation.setLocale(locale);
        translation.setTag(tag);
        translation.setKey(dto.getKey());
        translation.setContent(dto.getContent());

        translation = translationRepository.save(translation);
        return modelMapper.map(translation, TranslationDto.class);
    }

    public TranslationDto updateTranslation(Long translationId, TranslationRequestDTO dto) {
        Translation translation = translationRepository.findById(translationId)
                .orElseThrow(() -> new EntityNotFoundException("Translation not found with id: " + translationId));

        if (dto.getContent() != null) {
            translation.setContent(dto.getContent());
        }

        if (dto.getActiveIndicator() != null) {
            translation.setActiveIndicator(dto.getActiveIndicator());
        }

        translation = translationRepository.save(translation);
        return modelMapper.map(translation, TranslationDto.class);
    }


    public List<TranslationDto> getTranslations(String key, String content, String localeCode, String tagName) {
        List<Translation> translations = translationRepository.findTranslationsByCriteria(key, content, localeCode, tagName);
        return translations.stream().map(TranslationDto::new).toList();

    }
}
