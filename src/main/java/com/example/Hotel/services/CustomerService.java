package com.example.Hotel.services;

import com.example.Hotel.model.Customer;
import com.example.Hotel.repository.CustomerRepository;
import com.example.Hotel.repository.ReservationRepository;
import com.example.Hotel.Exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer addCustomer(Customer customer) {
        customer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    customer.setPhone(updatedCustomer.getPhone());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        reservationRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }
}
