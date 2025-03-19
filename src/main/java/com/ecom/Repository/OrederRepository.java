package com.ecom.Repository;

import com.ecom.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrederRepository extends JpaRepository<Order,Long> {

}
