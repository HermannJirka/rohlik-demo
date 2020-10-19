package cz.tut.rohlik.rohlikdemo.repository;

import cz.tut.rohlik.rohlikdemo.model.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, String> {
}
