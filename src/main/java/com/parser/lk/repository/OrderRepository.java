package com.parser.lk.repository;

import com.parser.lk.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Deprecated
    public Order findFirstByGuid(String guid);

    public Optional<Order> findOneByGuid(String guid);

    public Optional<Order> findOneById(Long id);

}
