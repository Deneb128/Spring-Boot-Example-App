package com.naos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
    }

    public void updateCustomer(Integer id, @RequestBody Customer.NewCustomerRequest request) {
        assert(customerRepository.findById(id).isPresent());

        Customer customer = customerRepository.findById(id).get();
        String name = request.name();
        String email = request.email();
        Integer age = request.age();

        if (name != null && name.length() > 0 && !Objects.equals(customer.getName(), name)) {
            customer.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)) {
            customer.setEmail(email);
        }

        if (age != null && !Objects.equals(customer.getAge(), age)) {
            customer.setAge(age);
        }
        customerRepository.save(customer);
    }
}
