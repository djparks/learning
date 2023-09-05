package net.usa.djparks.sbpostgresexamples.controller;

import net.usa.djparks.sbpostgresexamples.Restaurant;
import net.usa.djparks.sbpostgresexamples.RestaurantRepository;
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
@RequestMapping("/api")
public class RestaurantController {
    final RestaurantRepository fromEmailRepository;

    public RestaurantController(RestaurantRepository fromEmailRepository) {
        this.fromEmailRepository = fromEmailRepository;
    }

    @GetMapping("/now")
    Time now() {
        return new Time(LocalDateTime.now().toString());
    }

    static class Time {
        private final String time;

        public Time(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<Restaurant>> getAllFromEmails(@RequestParam(required = false) String restaurant) {
        try {
            List<Restaurant> restaurants = new ArrayList<>();

            if (restaurant == null)
                restaurants.addAll(fromEmailRepository.findAll());
//            else
//                fromEmailRepository.find(restaurant).forEach(fromEmails::add);

            if (restaurants.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
