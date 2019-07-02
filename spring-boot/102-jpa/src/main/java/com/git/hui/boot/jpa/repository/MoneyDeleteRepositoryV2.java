package com.git.hui.boot.jpa.repository;

import com.git.hui.boot.jpa.entity.MoneyPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author yihui in 18:23 19/7/2.
 */
@Repository
@Transactional(readOnly = true)
public class MoneyDeleteRepositoryV2 extends SimpleJpaRepository<MoneyPO, Integer> {

    @Autowired
    public MoneyDeleteRepositoryV2(EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(MoneyPO.class, em), em);
    }

    public MoneyDeleteRepositoryV2(JpaEntityInformation<MoneyPO, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public MoneyDeleteRepositoryV2(Class<MoneyPO> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<MoneyPO> rec = findById(id);
        rec.ifPresent(super::delete);
    }
}
