package com.insidesj.weatherloverback.Location;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000/")
public class LocationController {

    @Autowired
    private GridService gridService;

    @PostMapping("/location")
    public ResponseEntity<String> receiveLocation(@RequestBody LocationDTO locationDTO) {
        String latitude = locationDTO.getLatitude();
        String longitude = locationDTO.getLongitude();
        System.out.println("Received location - Latitude: " + latitude + ", Longitude: " + longitude);

        return ResponseEntity.ok("{\"message\": \"Location received successfully\"}");
    }

    @PostMapping("/grid")
    public ResponseEntity<String> receiveGrid(@RequestBody LocationDTO locationDTO) {
        String nx = locationDTO.getNx();
        String ny = locationDTO.getNy();
        System.out.println("Received grid - nx: " + nx + ", ny: " + ny);

        gridService.setGridValues(nx, ny);

        return ResponseEntity.ok("{\"message\": \"Grid received successfully\"}");
    }

}
