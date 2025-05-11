package com.i18n.translator.Service;

import com.i18n.translator.model.Dto.Request.TagDto;
import com.i18n.translator.model.entities.Tag;

public interface TagService {
    Tag addTag(TagDto tagDto);
}
