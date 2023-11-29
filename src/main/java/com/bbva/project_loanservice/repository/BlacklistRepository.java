package com.bbva.project_loanservice.repository;

import com.bbva.project_loanservice.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<BlackList, String> {

}
