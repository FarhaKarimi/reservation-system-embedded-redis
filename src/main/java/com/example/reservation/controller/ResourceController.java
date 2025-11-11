package com.example.reservation.controller;

import com.example.reservation.model.Resource;
import com.example.reservation.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAll(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(resourceService.searchResources(q));
    }
}
