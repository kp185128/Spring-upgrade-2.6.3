package com.example.app_war.repository;

import com.example.app_war.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {

    @Query(name = "Terminal.existsByTerminalId")
    boolean existsByTerminalId(String terminalId);
}
