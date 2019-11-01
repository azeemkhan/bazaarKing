package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.rewards.Rewards;
import com.tresshop.engine.services.AdminService;
import com.tresshop.engine.storage.entity.SystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/createProperty")
    public ResponseEntity redeemReward(
            @RequestParam String propertyName, @RequestParam String propertyValue) {
        adminService.createSystemProperty(propertyName, propertyValue);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
