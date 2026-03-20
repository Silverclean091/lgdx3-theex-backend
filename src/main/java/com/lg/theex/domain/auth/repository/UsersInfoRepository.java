package com.lg.theex.domain.auth.repository;

import com.lg.theex.domain.auth.entity.UsersInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersInfoRepository extends JpaRepository<UsersInfoEntity, Long> {

    @Override
    Optional<UsersInfoEntity> findById(Long userId);
}
