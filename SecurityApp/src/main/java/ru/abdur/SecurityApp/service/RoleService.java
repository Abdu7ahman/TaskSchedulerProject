package ru.abdur.SecurityApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abdur.SecurityApp.models.Role;
import ru.abdur.SecurityApp.repositories.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    Optional<Role> findByName(String name){
        return roleRepository.findByName(name);
    }
}
