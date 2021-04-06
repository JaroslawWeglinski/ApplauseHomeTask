package com.weglinskij.applausehometask.repository;

import com.weglinskij.applausehometask.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
}
