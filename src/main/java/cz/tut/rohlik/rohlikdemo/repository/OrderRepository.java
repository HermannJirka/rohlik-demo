package cz.tut.rohlik.rohlikdemo.repository;

import cz.tut.rohlik.rohlikdemo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    Optional<Order> findByIdAndDeletedIsFalse(String orderId);
    List<Order> findAllByDeletedIsFalse();
}
