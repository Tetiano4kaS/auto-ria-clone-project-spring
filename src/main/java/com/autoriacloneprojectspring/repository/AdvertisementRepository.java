package com.example.moduleproject.repository;

import com.example.moduleproject.entity.Advertisement;
import com.example.moduleproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Long countByUser(User user);
}
