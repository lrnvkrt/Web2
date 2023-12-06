package com.example.Web2.repositories;

import com.example.Web2.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelRepository extends JpaRepository<Model, UUID> {
    @Query("select avg(o.price) from Offer o join o.model m where m.id = :id and m.category = :category")
    Optional<Double> getAvgPrice(UUID id, Model.Category category);

    @Query("select m from Model m where m.brand.name = :brandName")
    List<Model> findModelsByBrandName(String brandName);
}
