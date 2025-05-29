package com.example.library_management.repository;

import com.example.library_management.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for Member entity.
 * Provides CRUD operations and custom queries for managing members in the database.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByNameContainingIgnoreCase(String name);
}