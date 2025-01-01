package com.example.hair_salon.service;

import com.example.hair_salon.entity.Service;
import com.example.hair_salon.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service getServiceById(final int id) {
        return serviceRepository.findById(id).orElse(null);
    }

    public Service saveService(final Service service) {
        return serviceRepository.save(service);
    }

    public boolean deleteServiceById(int id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
