package com.vinod.springboot.keycloak.repository;

import com.vinod.springboot.keycloak.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findCustomerByEmailId(String emailId);
}
