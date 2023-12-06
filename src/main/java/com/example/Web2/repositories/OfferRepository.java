package com.example.Web2.repositories;

import com.example.Web2.models.Model;
import com.example.Web2.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

    @Query("select o from Offer o join o.seller u where u.isActive = true and o.model.id = :id")
    List<Offer> findAllOffersByActiveUsersAndModel(UUID id);

}
