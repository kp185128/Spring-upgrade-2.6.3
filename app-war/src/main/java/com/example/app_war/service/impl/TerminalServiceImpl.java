package com.example.app_war.service.impl;

import com.example.app_war.repository.TerminalRepository;
import com.example.app_war.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TerminalServiceImpl implements TerminalService {

    @Autowired
    private TerminalRepository terminalRepository;

    @PostConstruct
    public void init() {
        System.out.println("Init");
        try {
            System.out.println(terminalRepository.existsByTerminalId("10000000"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
