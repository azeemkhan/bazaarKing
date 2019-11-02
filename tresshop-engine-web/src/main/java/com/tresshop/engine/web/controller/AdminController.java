package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.rewards.Rewards;
import com.tresshop.engine.client.rewards.SystemBanner;
import com.tresshop.engine.services.AdminService;
import com.tresshop.engine.storage.entity.SystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/createProperty")
    public ResponseEntity createSystemProperty(
            @RequestParam String propertyName, @RequestParam String propertyValue) {
        adminService.createSystemProperty(propertyName, propertyValue);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/createBanner")
    public ResponseEntity createBanner(@RequestBody SystemBanner systemBanner) {
        adminService.createBanner(systemBanner);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteBanner")
    public ResponseEntity deleteBanner(@RequestParam String bannerId) {
        adminService.deleteBanner(bannerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/banners")
    public ResponseEntity<List<SystemBanner>> getAllBanners(@RequestParam(required = false) boolean isActive) {
        if (isActive) {
            return new ResponseEntity<>(
                    adminService.getAllActiveBanner(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    adminService.getAllBanner(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/banners/{type}")
    public ResponseEntity<List<SystemBanner>> getAllBanners(@PathVariable String type) {
        return new ResponseEntity<>(
                adminService.getAllActiveBannerByType(type), HttpStatus.OK);
    }
}
