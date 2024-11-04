package com.morelli.carparts.controller;

import com.morelli.carparts.model.dto.response.MovementResponse;
import com.morelli.carparts.service.MovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/storage/movements")
@AllArgsConstructor
public class MovementController {

    private MovementService movementService;

    @GetMapping("/")
    public ResponseEntity<MovementResponse> getEntitiesWithLastWeekTimestamp() {
        MovementResponse response = movementService.getEntitiesFromLastWeek();
        return ResponseEntity.ok(response);
    }
}
