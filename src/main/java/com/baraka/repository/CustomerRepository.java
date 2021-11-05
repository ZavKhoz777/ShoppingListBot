package com.baraka.repository;

import com.baraka.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findCustomerByTgId(Integer tg_id);

    List<Customer> findCustomersByLastName(String lastName);


}
