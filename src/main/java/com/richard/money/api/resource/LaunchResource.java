package com.richard.money.api.resource;


import com.richard.money.api.model.Launch;
import com.richard.money.api.service.LaunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/releases")
public class LaunchResource {

    @Autowired
    private LaunchService launchService;

    @GetMapping
    public List<Launch> listAll() {
        return this.launchService.findAll();
    }


    @GetMapping("/{code}")
    public ResponseEntity<Launch> searchLaunchByCode(@PathVariable Long code) {
        Launch launch = launchService.findOne(code);
        return launch != null ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build();
    }
}
