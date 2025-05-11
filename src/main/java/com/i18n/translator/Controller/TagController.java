package com.i18n.translator.Controller;

import com.i18n.translator.Service.TagService;
import com.i18n.translator.model.Dto.Request.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;


    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagDto tagDto) {
        String tagName = tagService.addTag(tagDto);
        return ResponseEntity.ok(new TagDto(tagName));
    }
}
