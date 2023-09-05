package net.usa.djparks.sbpostgresexamples.controller;

import net.usa.djparks.sbpostgresexamples.Restaurant;
import net.usa.djparks.sbpostgresexamples.RestaurantRepository;
import net.usa.djparks.sbpostgresexamples.dto.RootObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/json")
public class JsonTestController {

    @GetMapping("/test")
    public ResponseEntity<RootObject> getAllFromEmails(@RequestParam RootObject root) {
        try {

            return new ResponseEntity<>(root, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
