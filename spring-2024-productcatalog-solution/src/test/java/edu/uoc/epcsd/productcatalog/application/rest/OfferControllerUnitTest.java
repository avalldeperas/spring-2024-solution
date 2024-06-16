package edu.uoc.epcsd.productcatalog.application.rest;

import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.service.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferControllerUnitTest {

    private static final String USER_EMAIL = "user@email.com";
    @InjectMocks
    private OfferRESTController controller;
    @Mock
    private OfferService service;

    @Test
    void findOffersByUser_whenUserEmailIsAdded_thenReturnsUserOffers() {
        List<Offer> expectedOffers = buildOffers();
        when(service.findOffersByUser(USER_EMAIL)).thenReturn(expectedOffers);

        List<Offer> userOffers = controller.findOffersByUser(USER_EMAIL);

        assertEquals(expectedOffers, userOffers);
    }


    private List<Offer> buildOffers() {
        return Arrays.asList(
                Offer.builder().id(1L).serialNumber("number1").email(USER_EMAIL).build(),
                Offer.builder().id(2L).serialNumber("number2").email(USER_EMAIL).build()
        );
    }
}