package edu.uoc.epcsd.productcatalog.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OfferUnitTest {

    @Test
    void whenNewOfferCreated_thenStatusIsPending() {
        Offer offer = Offer.builder().build();
        assertEquals(OfferStatus.PENDING, offer.getStatus());
    }
}