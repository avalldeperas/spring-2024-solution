package edu.uoc.epcsd.productcatalog;

import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.infrastructure.repository.jpa.OfferRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OfferRepositoryIntegrationTest {

    @Autowired
    private OfferRepositoryImpl offerRepository;

    private static final String USER_EMAIL = "email@email.com";

    @Test
    void whenUserAddsOffer_thenOfferIsRetrievedByUser() {
        Offer newOffer = buildOffer();
        offerRepository.addOffer(newOffer);

        List<Offer> userOffers = offerRepository.findOffersByUser(USER_EMAIL);

        assertTrue(userOffers.contains(newOffer));
    }

    private Offer buildOffer() {
        return Offer.builder()
                .id(1L)
                .categoryId(1L)
                .productId(2L)
                .email(USER_EMAIL)
                .serialNumber("12345")
                .build();
    }
}