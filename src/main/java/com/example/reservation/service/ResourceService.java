package com.example.reservation.service;

import com.example.reservation.model.Resource;
import com.example.reservation.repository.ResourceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Cacheable(value = "resources", key = "#name == null ? 'all' : #name")
    public List<Resource> searchResources(String name) {
        if (name == null || name.isBlank()) {
            return resourceRepository.findAll();
        }
        return resourceRepository.findByNameContaining(name);
    }
}
