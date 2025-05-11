package com.i18n.translator.Service.Impl;

import com.i18n.translator.Repository.TagRepository;
import com.i18n.translator.Service.TagService;
import com.i18n.translator.model.Dto.Request.TagDto;
import com.i18n.translator.model.entities.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;


    @Override
    public Tag addTag(TagDto tagDto) {
        if (tagRepository.findByName(tagDto.getName()).isPresent()) {
            throw new IllegalArgumentException("Tag already exists.");
        }
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag = tagRepository.save(tag);
        return tag;
    }
}