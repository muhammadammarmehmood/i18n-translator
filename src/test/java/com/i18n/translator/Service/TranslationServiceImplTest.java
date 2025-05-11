package com.i18n.translator.Service;

import com.i18n.translator.Repository.LocaleRepository;
import com.i18n.translator.Repository.TagRepository;
import com.i18n.translator.Repository.TranslationRepository;
import com.i18n.translator.Service.Impl.TranslationServiceImpl;
import com.i18n.translator.model.Dto.Request.TranslationRequestDTO;
import com.i18n.translator.model.Dto.Response.ExportTranslationDTO;
import com.i18n.translator.model.Dto.Response.PageableDto;
import com.i18n.translator.model.Dto.Response.TranslationDto;
import com.i18n.translator.model.entities.Locale;
import com.i18n.translator.model.entities.Tag;
import com.i18n.translator.model.entities.Translation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TranslationServiceImplTest {

    @Mock private LocaleRepository localeRepository;
    @Mock private TagRepository tagRepository;
    @Mock private TranslationRepository translationRepository;
    @Mock private ModelMapper modelMapper;

    @InjectMocks
    private TranslationServiceImpl translationService;

    private TranslationRequestDTO requestDTO;
    private Translation translation;
    private TranslationDto translationDto;
    private Locale locale;
    private Tag tag;

    @BeforeEach
    void setup() {
        requestDTO = new TranslationRequestDTO();
        requestDTO.setLocaleCode("en");
        requestDTO.setTagName("web");
        requestDTO.setKey("greeting");
        requestDTO.setContent("Hello");

        locale = new Locale();
        locale.setCode("en");

        tag = new Tag();
        tag.setName("web");

        translation = new Translation();
        translation.setId(1L);
        translation.setKey("greeting");
        translation.setContent("Hello");
        translation.setLocale(locale);
        translation.setTag(tag);

        translationDto = new TranslationDto();
        translationDto.setKey("greeting");
        translationDto.setContent("Hello");
    }

    @Test
    void testCreateTranslation_Success() {
        Mockito.when(localeRepository.findByCode("en")).thenReturn(Optional.of(locale));
        Mockito.when(tagRepository.findByName("web")).thenReturn(Optional.of(tag));
        Mockito.when(translationRepository.findByLocaleCodeAndTagNameAndKey("en", "web", "greeting")).thenReturn(Optional.empty());
        Mockito.when(translationRepository.save(Mockito.any())).thenReturn(translation);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TranslationDto.class))).thenReturn(translationDto);

        TranslationDto result = translationService.createTranslation(requestDTO);

        assertEquals("greeting", result.getKey());
        assertEquals("Hello", result.getContent());
    }

    @Test
    void testCreateTranslation_LocaleNotFound() {
        Mockito.when(localeRepository.findByCode("en")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            translationService.createTranslation(requestDTO);
        });
    }

    @Test
    void testUpdateTranslation_Success() {
        Translation updatedTranslation = new Translation();
        updatedTranslation.setId(1L);
        updatedTranslation.setContent("Hi");
        updatedTranslation.setActiveIndicator(true);

        requestDTO.setContent("Hi");
        requestDTO.setActiveIndicator(true);

        Mockito.when(translationRepository.findById(1L)).thenReturn(Optional.of(translation));
        Mockito.when(translationRepository.save(Mockito.any())).thenReturn(updatedTranslation);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TranslationDto.class))).thenReturn(translationDto);

        TranslationDto result = translationService.updateTranslation(1L, requestDTO);

        assertNotNull(result);
        assertEquals("greeting", result.getKey());
    }

    @Test
    void testUpdateTranslation_NotFound() {
        Mockito.when(translationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            translationService.updateTranslation(1L, requestDTO);
        });
    }

    @Test
    void testGetTranslations_Success() {
        Page<Translation> page = new PageImpl<>(List.of(translation));
        Mockito.when(translationRepository.findTranslationsByCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(page);

        PageableDto<List<TranslationDto>> result = translationService.getTranslations("key", "Hello", "en", "web", 0, 10);
        assertNotNull(result);
        assertEquals(1, result.getData().size());
    }

    @Test
    void testExportTranslations_Success() {
        Mockito.when(localeRepository.findByCode("en")).thenReturn(Optional.of(locale));
        Mockito.when(translationRepository.findByLocaleCode("en")).thenReturn(List.of(translation));

        ExportTranslationDTO result = translationService.exportTranslations("en");

        assertNotNull(result);
        assertEquals("en", result.getLocale());
        assertEquals("Hello", result.getTranslations().get("greeting"));
    }

    @Test
    void testExportTranslations_LocaleNotFound() {
        Mockito.when(localeRepository.findByCode("en")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            translationService.exportTranslations("en");
        });
    }

    @Test
    void testExportTranslations_NoTranslations() {
        Mockito.when(localeRepository.findByCode("en")).thenReturn(Optional.of(locale));
        Mockito.when(translationRepository.findByLocaleCode("en")).thenReturn(Collections.emptyList());

        ExportTranslationDTO result = translationService.exportTranslations("en");

        assertNull(result);
    }
}
