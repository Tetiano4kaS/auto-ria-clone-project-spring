package com.autoriacloneprojectspring.repository;

import com.autoriacloneprojectspring.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
            select t from Token t inner join User u 
            on t.user.id=u.id
            where u.id= :id and t.expired=false 
            """)
    public List<Token> findAllValidTokensByUser(Long id);
}
