//package com.example.restaurant_reservation.services;
//
//import com.example.restaurant_reservation.domain.Reservation;
//import com.example.restaurant_reservation.domain.TableEntity;
//import com.example.restaurant_reservation.dto.ReservationDTO;
//import com.example.restaurant_reservation.mapper.ReservationMapper;
//import com.example.restaurant_reservation.repository.ReservationRepository;
//import com.example.restaurant_reservation.repository.TableRepository;
//import com.example.restaurant_reservation.security.service.TokenService;
//import com.example.restaurant_reservation.service.implementations.ReservationServiceImpl;
//import io.jsonwebtoken.Claims;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ReservationServiceImplTest {
//
//    @InjectMocks
//    private ReservationServiceImpl reservationService; // Test the correct service implementation
//
//    @Mock
//    private ReservationRepository reservationRepository;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @Mock
//    private TableRepository tableRepository;
//
//    @Mock
//    private ReservationMapper reservationMapper;
//
//    @Mock
//    private TokenService tokenService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testMakeReservation_Success() {
//        Long customerId = 1L;
//        Long tableId = 1L;
//        String reservationDate = "2025-01-02T14:30:00";
//        LocalDateTime reservationTime = LocalDateTime.parse(reservationDate);
//
//        Customer customer = new Customer();
//        TableEntity tableEntity = new TableEntity();
//        Reservation reservation = new Reservation();
//        ReservationDTO reservationDTO = new ReservationDTO();
//        reservationDTO.setReservationTime(reservationTime);
//
//        // Mock repository and mapper behavior
//        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
//        when(tableRepository.findById(tableId)).thenReturn(Optional.of(tableEntity));
//        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
//        when(reservationMapper.getDTOFromDomain(any(Reservation.class))).thenReturn(reservationDTO);
//
//        ReservationDTO result = reservationService.makeReservationForCustomer(customerId, tableId, reservationDate);
//
//        assertNotNull(result, "ReservationDTO should not be null");
//        assertEquals(reservationTime, result.getReservationTime());
//        verify(reservationRepository, times(1)).save(any(Reservation.class));
//        verify(reservationMapper, times(1)).getDTOFromDomain(any(Reservation.class));
//    }
//
//    @Test
//    void testCancelReservation_Success() {
//        Long reservationId = 1L;
//        String token = "validToken";
//
//        Claims claims = mock(Claims.class);
//        when(claims.get("role", String.class)).thenReturn("ROLE_MANAGER");
//        when(tokenService.parseToken(token)).thenReturn(claims);
//
//        Reservation reservation = new Reservation();
//        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
//
//        reservationService.cancelReservationForCustomer(reservationId, token);
//
//        verify(reservationRepository, times(1)).delete(reservation);
//    }
//
//    @Test
//    void testCancelReservation_Within3Hours_ThrowsException() {
//        Long reservationId = 1L;
//        String token = "validToken";
//
//        Claims claims = mock(Claims.class);
//        when(claims.get("role", String.class)).thenReturn("ROLE_CUSTOMER");
//        when(tokenService.parseToken(token)).thenReturn(claims);
//
//        Reservation reservation = new Reservation();
//        reservation.setReservationTime(LocalDateTime.now().plusHours(2));
//        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
//
//        assertThrows(IllegalStateException.class,
//                () -> reservationService.cancelReservationForCustomer(reservationId, token));
//        verify(reservationRepository, never()).delete(any(Reservation.class));
//    }
//}
