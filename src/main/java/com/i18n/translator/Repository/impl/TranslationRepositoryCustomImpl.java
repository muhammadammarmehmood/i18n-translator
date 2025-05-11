package com.i18n.translator.Repository.impl;

import com.i18n.translator.Repository.TranslationRepositoryCustom;
import com.i18n.translator.model.entities.Locale;
import com.i18n.translator.model.entities.Tag;
import com.i18n.translator.model.entities.Translation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TranslationRepositoryCustomImpl implements TranslationRepositoryCustom {
    private final EntityManager entityManager;



    @Override
    public Page<Translation> findTranslationsByCriteria(
            String key,
            String content,
            String localeCode,
            String tagName,
            Pageable pageable
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Translation> cq = cb.createQuery(Translation.class);
        Root<Translation> translationRoot = cq.from(Translation.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isTrue(translationRoot.get("activeIndicator")));

        if (key != null && !key.isEmpty()) {
            predicates.add(cb.like(cb.lower(translationRoot.get("key")), "%" + key.toLowerCase() + "%"));
        }

        if (content != null && !content.isEmpty()) {
            predicates.add(cb.like(cb.lower(translationRoot.get("content")), "%" + content.toLowerCase() + "%"));
        }

        if (localeCode != null && !localeCode.isEmpty()) {
            Join<Translation, Locale> localeJoin = translationRoot.join("locale", JoinType.INNER);
            predicates.add(cb.and(
                    cb.equal(localeJoin.get("code"), localeCode),
                    cb.isTrue(localeJoin.get("activeIndicator"))
            ));
        }

        if (tagName != null && !tagName.isEmpty()) {
            Join<Translation, Tag> tagJoin = translationRoot.join("tag", JoinType.INNER);
            predicates.add(cb.and(
                    cb.equal(tagJoin.get("name"), tagName),
                    cb.isTrue(tagJoin.get("activeIndicator"))
            ));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        cq.orderBy(cb.asc(translationRoot.get("key")));

        List<Translation> resultList = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Translation> countRoot = countQuery.from(Translation.class);
        List<Predicate> countPredicates = new ArrayList<>();

        countPredicates.add(cb.isTrue(countRoot.get("activeIndicator")));

        if (key != null && !key.isEmpty()) {
            countPredicates.add(cb.like(cb.lower(countRoot.get("key")), "%" + key.toLowerCase() + "%"));
        }
        if (content != null && !content.isEmpty()) {
            countPredicates.add(cb.like(cb.lower(countRoot.get("content")), "%" + content.toLowerCase() + "%"));
        }

        if (localeCode != null && !localeCode.isEmpty()) {
            Join<Translation, Locale> localeJoin = countRoot.join("locale", JoinType.INNER);
            countPredicates.add(cb.and(
                    cb.equal(localeJoin.get("code"), localeCode),
                    cb.isTrue(localeJoin.get("activeIndicator"))
            ));
        }

        if (tagName != null && !tagName.isEmpty()) {
            Join<Translation, Tag> tagJoin = countRoot.join("tag", JoinType.INNER);
            countPredicates.add(cb.and(
                    cb.equal(tagJoin.get("name"), tagName),
                    cb.isTrue(tagJoin.get("activeIndicator"))
            ));
        }

        countQuery.select(cb.count(countRoot)).where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, count);
    }

}
