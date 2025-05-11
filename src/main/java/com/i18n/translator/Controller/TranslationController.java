package com.i18n.translator.Controller;

import com.i18n.translator.Service.TranslationService;
import com.i18n.translator.Util.ListUtil;
import com.i18n.translator.model.Dto.Request.TranslationRequestDTO;
import com.i18n.translator.model.Dto.Response.ErrorResponse;
import com.i18n.translator.model.Dto.Response.TranslationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/translations")
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping
    public ResponseEntity<TranslationDto> createTranslation(@RequestBody TranslationRequestDTO translationRequestDTO) {
        TranslationDto createdTranslation = translationService.createTranslation(translationRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTranslation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TranslationDto> updateTranslation(@PathVariable Long id,
                                                            @RequestBody TranslationRequestDTO translationRequestDTO) {
        TranslationDto updatedTranslation = translationService.updateTranslation(id, translationRequestDTO);

        if (updatedTranslation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(updatedTranslation);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTranslations(
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String localeCode,
            @RequestParam(required = false) String tagName) {

        List<TranslationDto> translationDTOs = translationService.getTranslations(key, content, localeCode, tagName);
        if(!ListUtil.isNullOrEmpty(translationDTOs)) {
            return ResponseEntity.ok(translationDTOs);
        }
        ErrorResponse errorResponse = new ErrorResponse("No translations found for the given search criteria.", 400);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
