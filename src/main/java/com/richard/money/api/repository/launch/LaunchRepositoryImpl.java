package com.richard.money.api.repository.launch;

import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.filter.LaunchFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LaunchRepositoryImpl implements LaunchRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Launch> filter(LaunchFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Launch> criteria = builder.createQuery(Launch.class);
        Root<Launch> root = criteria.from(Launch.class);

        //criar as restricoes
        Predicate[] predicates = createRestrictions(filter, builder, root);
        criteria.where(predicates);

        TypedQuery<Launch> query = manager.createQuery(criteria);
        addRestrictionsOfPagination(query, pageable);


        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private Predicate[] createRestrictions(LaunchFilter filter, CriteriaBuilder builder,
                                           Root<Launch> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (null != filter) {

            if (StringUtils.isNotBlank(filter.getDescription())) {
                predicates.add(builder.like(
                        builder.lower(root.get("description")), "%" + filter.getDescription() + "%"));
            }

            if (null != filter.getDateExpirationIn()) {
                predicates.add(
                        builder.greaterThanOrEqualTo(root.get("dateExpiration"), filter.getDateExpirationIn()));
            }

            if (null != filter.getDateExpirationUpUntil()) {
                predicates.add(
                        builder.lessThanOrEqualTo(root.get("dateExpiration"), filter.getDateExpirationUpUntil())
                );
            }
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void addRestrictionsOfPagination(TypedQuery<Launch> query, Pageable pageable) {
        int currencyPage = pageable.getPageNumber();
        int totalRegisterForPage = pageable.getPageSize();
        int firstRegisterPage = currencyPage * totalRegisterForPage;

        query.setFirstResult(firstRegisterPage);
        query.setMaxResults(totalRegisterForPage);
    }

    private Long total(LaunchFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Launch> root = criteria.from(Launch.class);

        Predicate[] predicates = createRestrictions(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
