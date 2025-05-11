package com.i18n.translator.Repository.impl;

import com.i18n.translator.Repository.TranslationRepositoryCustom;
import com.i18n.translator.model.entities.Locale;
import com.i18n.translator.model.entities.Tag;
import com.i18n.translator.model.entities.Translation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TranslationRepositoryCustomImpl implements TranslationRepositoryCustom {
    private final EntityManager entityManager;



    @Override
    public List<Translation> findTranslationsByCriteria(String key, String content, String localeCode, String tagName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Translation> cq = cb.createQuery(Translation.class);
        Root<Translation> translationRoot = cq.from(Translation.class);

        List<Predicate> predicates = new ArrayList<>();

        if (key != null && !key.isEmpty()) {
            predicates.add(cb.like(translationRoot.get("key"), "%" + key + "%"));
        }

        if (content != null && !content.isEmpty()) {
            predicates.add(cb.like(translationRoot.get("content"), "%" + content + "%"));
        }

        if (localeCode != null && !localeCode.isEmpty()) {
            Join<Translation, Locale> localeJoin = translationRoot.join("locale", JoinType.INNER);
            predicates.add(cb.equal(localeJoin.get("code"), localeCode));
        }

        if (tagName != null && !tagName.isEmpty()) {
            Join<Translation, Tag> tagJoin = translationRoot.join("tag", JoinType.INNER);
            predicates.add(cb.equal(tagJoin.get("name"), tagName));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(cq).getResultList();
    
    }

}
