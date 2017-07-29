package com.richard.money.api.service;

import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.LaunchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class LaunchService {

    @Autowired
    private LaunchRepository launchRepository;

    @GetMapping
    public List<Launch> findAll() {
        return launchRepository.findAll();
    }

    public Launch findOne(Long code) {
        return searchLaunchByCode(code);
    }

    private Launch searchLaunchByCode(Long code) {
        Launch launchSave = launchRepository.findOne(code);

        if (null == launchSave) {
            throw new EmptyResultDataAccessException(1);
        }
        return launchSave;
    }
}
