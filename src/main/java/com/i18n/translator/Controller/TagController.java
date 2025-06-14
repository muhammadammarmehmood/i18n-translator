package com.i18n.translator.Controller;

import com.i18n.translator.Service.TagService;
import com.i18n.translator.model.Dto.Request.TagDto;
import com.i18n.translator.model.entities.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "ApiKeyAuth")
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;


    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagDto tagDto) {
         Tag tag = tagService.addTag(tagDto);
        return ResponseEntity.ok(new TagDto(tag.getName()));
    }
}
