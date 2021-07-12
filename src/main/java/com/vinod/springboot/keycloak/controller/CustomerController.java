package com.vinod.springboot.keycloak.controller;

import com.vinod.springboot.keycloak.dto.CustomerDto;
import com.vinod.springboot.keycloak.dto.CustomerRegisterDto;
import com.vinod.springboot.keycloak.dto.CustomerUpdateDto;
import com.vinod.springboot.keycloak.exception.UserNotFoundException;
import com.vinod.springboot.keycloak.service.ICustomerService;
import com.vinod.springboot.keycloak.util.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static com.vinod.springboot.keycloak.util.GlobalUtility.buildResponseForSuccess;

@RestController
@Log4j2
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    /**
     * User Page.
     *
     * @return
     */
    @GetMapping("/v1/welcome")
    public String userPage() {
        log.trace("Request came for user page");
        return "springboot-with-keycloak - Welcome Page";
    }

    /**
     * Admin Page.
     *
     * @return
     */
    @RolesAllowed("admin")
    @GetMapping("/v1/admin")
    public String adminPage() {
        log.trace("Request came for admin page");
        return "springboot-with-keycloak - Admin Page";
    }

    /**
     * Get customer details base on the customer Email id.
     *
     * @param emailId   - Customer Email Id.
     * @return          - CustomerDto object.
     * @throws UserNotFoundException
     */
    @RolesAllowed({ "admin", "user" })
    @GetMapping("/v1/customers/{emailId}")
    public ResponseEntity<Response> getCustomer(@PathVariable("emailId") String emailId) throws UserNotFoundException {
        log.info("Request came to get the customer details having the email id: {}", emailId);
        CustomerDto customerDto= customerService.getCustomerByEmailId(emailId);
        return buildResponseForSuccess(HttpStatus.SC_OK,"Successfully fetched customer.",customerDto);
    }

    /**
     * This endpoint is for adding new customer information into the system.
     *
     * @param customerRegisterDto   - CustomerRegisterDto object.
     * @return                      - Response.
     */
    @RolesAllowed({ "admin", "user" })
    @PostMapping("/v1/customers")
    public ResponseEntity<Response> addNewCustomer(@RequestBody CustomerRegisterDto customerRegisterDto) {
        log.trace("Request came to add new customer with following details: {}", customerRegisterDto);
        customerService.addCustomer(customerRegisterDto);
        return buildResponseForSuccess(HttpStatus.SC_OK,"Successfully added the customer details.",null);
    }

    /**
     * This endpoint is for updating the existing customer details into the system.
     *
     * @param emailId               - Customer Email Id.
     * @param customerUpdateDto     - CustomerUpdateDto object.
     * @return                      - Response.
     */
    @RolesAllowed({ "admin", "user" })
    @PutMapping("/v1/customers/{emailId}")
    public ResponseEntity<Response> updateCustomer(@PathVariable("emailId") String emailId, @RequestBody CustomerUpdateDto customerUpdateDto) throws UserNotFoundException {
        log.trace("Request came to update customer details: {}", customerUpdateDto);
        customerService.updateCustomer(customerUpdateDto, emailId);
        return buildResponseForSuccess(HttpStatus.SC_OK,"Successfully updated the customer details.",null);
    }

    /**
     * This endpoint is for deleting the existing customer details from the system.
     *
     * @param emailId   -   Customer Email Id.
     * @return          -   String msg.
     */
    @RolesAllowed("admin")
    @DeleteMapping("/v1/customers/{emailId}")
    public ResponseEntity<Response> deleteCustomer(@PathVariable("emailId") String emailId) throws UserNotFoundException {
        log.trace("Request came to get the customer details having the email id: {}", emailId);
        customerService.deleteCustomer(emailId);
        return buildResponseForSuccess(HttpStatus.SC_OK,"Successfully deleted the customer.",null);
    }
}
