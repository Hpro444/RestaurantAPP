//package com.example.restaurant_reservation.services;
//
//import com.example.restaurant_reservation.domain.Restaurant;
//import com.example.restaurant_reservation.dto.RestaurantDTO;
//import com.example.restaurant_reservation.mapper.RestaurantMapper;
//import com.example.restaurant_reservation.repository.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CustomerServiceImplTest {
//
//    @InjectMocks
//    private CustomerServiceImpl customerService;
//
//    @Mock
//    private RestaurantRepository restaurantRepository;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//
//    @Mock
//    private RestaurantMapper restaurantMapper;
//
//    @Mock
//    private CustomerMapper customerMapper;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    @Test
//    void testUpdateCustomerProfile_Success() {
//        Long customerId = 1L;
//
//        // Mock existing customer
//        Customer customer = new Customer();
//        customer.setId(customerId);
//        customer.setFirstName("John");
//        customer.setLastName("Doe");
//
//        // Mock new data for update
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setFirstName("Jane");
//        customerDTO.setLastName("Smith");
//
//        // Mock repository and mapper behavior
//        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
//        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        when(customerMapper.getDTOFromDomain(any(Customer.class))).thenAnswer(invocation -> {
//            Customer input = invocation.getArgument(0, Customer.class);
//            CustomerDTO dto = new CustomerDTO();
//            dto.setFirstName(input.getFirstName());
//            dto.setLastName(input.getLastName());
//            return dto;
//        });
//
//        CustomerDTO updatedCustomer = customerService.updateCustomerProfile(customerDTO, customerId);
//
//        assertNotNull(updatedCustomer, "Updated customer should not be null");
//        assertEquals("Jane", updatedCustomer.getFirstName());
//        assertEquals("Smith", updatedCustomer.getLastName());
//        verify(customerRepository, times(1)).save(any(Customer.class));
//    }
//
//
//    @Test
//    void testFindAllRestaurants() {
//        Restaurant restaurant = new Restaurant();
//        RestaurantDTO restaurantDTO = new RestaurantDTO();
//
//        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
//        when(restaurantMapper.getDTOFromDomain(restaurant)).thenReturn(restaurantDTO);
//
//        List<RestaurantDTO> restaurants = customerService.findAllRestaurants();
//
//        assertEquals(1, restaurants.size());
//        verify(restaurantRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testFindRestaurantById_Success() {
//        Long restaurantId = 1L;
//        Restaurant restaurant = new Restaurant();
//        RestaurantDTO restaurantDTO = new RestaurantDTO();
//
//        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
//        when(restaurantMapper.getDTOFromDomain(restaurant)).thenReturn(restaurantDTO);
//
//        RestaurantDTO result = customerService.findRestaurantById(restaurantId);
//
//        assertNotNull(result);
//        verify(restaurantRepository, times(1)).findById(restaurantId);
//    }
//
//}
