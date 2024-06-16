package edu.uoc.epcsd.productcatalog.domain.service;

import edu.uoc.epcsd.productcatalog.application.rest.response.GetUserResponse;
import edu.uoc.epcsd.productcatalog.domain.Offer;
import edu.uoc.epcsd.productcatalog.domain.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceUnitTest {

    private static final String AN_EMAIL = "email@email.com";
    private static final String A_URL = "url";
    private final List<Offer> expectedOffers = buildOffers();
    @InjectMocks
    private OfferServiceImpl testee;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(testee, "getUserByEmail", A_URL);
    }

    @Test
    void findOffersByUser_whenValidUser_returnsUserOffers() {
        ResponseEntity<GetUserResponse> successResponse = ResponseEntity.ok().body(GetUserResponse.builder().build());
        when(restTemplate.getForEntity(A_URL, GetUserResponse.class, AN_EMAIL)).thenReturn(successResponse);
        when(offerRepository.findOffersByUser(AN_EMAIL)).thenReturn(expectedOffers);

        List<Offer> result = testee.findOffersByUser(AN_EMAIL);

        assertEquals(expectedOffers, result);
    }

    @Test
    void findOffersByUser_whenInvalidUser_throwsExceptionUserNotFound() {
        ResponseEntity<GetUserResponse> errorResponse = ResponseEntity.notFound().build();
        when(restTemplate.getForEntity(A_URL, GetUserResponse.class, AN_EMAIL)).thenReturn(errorResponse);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> testee.findOffersByUser(AN_EMAIL));
        assertEquals("Could not fetch user with email email@email.com", exception.getMessage());
    }

    private List<Offer> buildOffers() {
        return Arrays.asList(
                Offer.builder().id(1L).serialNumber("number1").email(AN_EMAIL).build(),
                Offer.builder().id(2L).serialNumber("number2").email(AN_EMAIL).build()
        );
    }
}