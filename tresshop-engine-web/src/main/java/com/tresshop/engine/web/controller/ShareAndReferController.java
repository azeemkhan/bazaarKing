package com.tresshop.engine.web.controller;

import com.tresshop.engine.client.rewards.ShareAndReferRequest;
import com.tresshop.engine.client.rewards.ShareAndReferResponse;
import com.tresshop.engine.services.ShareAndReferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/share_and_refer")
public class ShareAndReferController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShareAndReferService shareAndReferService;

    @PostMapping(
            value = "/share",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<ShareAndReferResponse> createShare(
            @RequestBody ShareAndReferRequest shareAndReferRequest) {
        return new ResponseEntity<>(
                shareAndReferService.share(shareAndReferRequest), HttpStatus.OK);
    }

    @PostMapping(
            value = "/refer",
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON}
    )
    public ResponseEntity<ShareAndReferResponse> createRefer(
            @RequestBody ShareAndReferRequest shareAndReferRequest) {
        return new ResponseEntity<>(
                shareAndReferService.refer(shareAndReferRequest), HttpStatus.OK);
    }
}
