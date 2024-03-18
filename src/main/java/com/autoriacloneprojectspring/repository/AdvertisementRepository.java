package com.autoriacloneprojectspring.repository;

import com.autoriacloneprojectspring.entity.Advertisement;
import com.autoriacloneprojectspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Long countByUser(User user);
}
