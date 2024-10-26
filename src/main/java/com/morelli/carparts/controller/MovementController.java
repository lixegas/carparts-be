//package com.morelli.carparts.controller;
//
//import com.morelli.carparts.model.dto.response.MovementResponse;
//import com.morelli.carparts.service.MovementService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/storage/movements")
//@AllArgsConstructor
//public class MovementController {
//
//    private MovementService movementService;
//
//    @GetMapping("/today")
//    public ResponseEntity<MovementResponse> getEntitiesWithTodayTimestamp() {
//        MovementResponse response = movementService.getEntitiesWithTodayTimestamp();
//        return ResponseEntity.ok(response);
//    }
//}
