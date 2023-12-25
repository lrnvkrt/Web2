package com.example.Web2.repositories;

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

    @Query("select o from Offer o where o.price <= (SELECT AVG(o2.price) FROM Offer o2) " +
            "order by o.mileage desc")
    Offer findBestOffer();

    @Query("select o from Offer o where o.seller.isActive = true")
    List<Offer> findAllActiveOffers();

    List<Offer> findTop5ByOrderByCreatedDesc();

    @Query("select o from Offer o join o.model m join m.brand b where b.name = :brandName and o.seller.isActive = true")
    List<Offer> findByBrandName(String brandName);


    List<Offer> findAllByModelNameAndSellerIsActive(String modelName, boolean active);

    List<Offer> findAllBySellerUsername(String username);
    List<Offer> findAll();
}
