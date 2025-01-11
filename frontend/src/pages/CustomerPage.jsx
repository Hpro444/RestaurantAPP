import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './CustomerPage.css';
import api from '../api.jsx';

// const fakeRestaurants = [
//     {
//         managerId: 1,
//         manager_email: "manager1@example.com",
//         name: "The Gourmet Spot",
//         description: "A fine dining experience.",
//         openingTime: "09:00",
//         closingTime: "22:00",
//         kitchenType: "Italian",
//         address: "123 Gourmet Lane, Food City"
//     },
//     {
//         managerId: 2,
//         manager_email: "manager2@example.com",
//         name: "Sushi Palace",
//         description: "Fresh sushi and sashimi.",
//         openingTime: "11:00",
//         closingTime: "23:00",
//         kitchenType: "Japanese",
//         address: "456 Sushi Street, Ocean City"
//     },
//     {
//         managerId: 3,
//         manager_email: "manager3@example.com",
//         name: "Taco Fiesta",
//         description: "Authentic Mexican tacos.",
//         openingTime: "10:00",
//         closingTime: "21:00",
//         kitchenType: "Mexican",
//         address: "789 Taco Ave, Fiesta Town"
//     }
// ];

const fakeReservations = [
    {
        reservationId: 101,
        restaurantName: "The Gourmet Spot",
        customerName: "John Doe",
        date: "2023-12-15",
        time: "19:00",
        guests: 2
    },
    {
        reservationId: 102,
        restaurantName: "Sushi Palace",
        customerName: "Jane Smith",
        date: "2023-12-16",
        time: "20:30",
        guests: 4
    },
    {
        reservationId: 103,
        restaurantName: "Taco Fiesta",
        customerName: "Alice Brown",
        date: "2023-12-17",
        time: "18:00",
        guests: 3
    }
];

function CustomerPage() {
    const [currentPage, setCurrentPage] = useState("main");
    const [restaurants, setRestaurants] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate(); // Use navigate for routing to ProfilePage

    useEffect(() => {
        if (currentPage === "restaurants") {
            fetchRestaurants();
        }
    }, [currentPage]);

    const fetchRestaurants = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem('jwtToken'); // Retrieve JWT token
            const response = await api.get('/reservation-service/restaurant', {
                headers: { Authorization: `Bearer ${token}` },
            });
            setRestaurants(response.data); // Set fetched restaurants
        } catch (err) {
            console.error("Error fetching restaurants:", err);
            setError("Failed to load restaurants. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    const renderPage = () => {
        switch (currentPage) {
            case "main":
                return (
                    <div className="main-dashboard">
                        <h1>Welcome!</h1>
                        <p>We have <strong>a lot of</strong> restaurants available for you.</p>
                        <p>You currently have <strong>a lot of</strong> reservations in the system.</p>
                    </div>
                );

            // case "restaurants":
            //     return (
            //         <div className="centered-content">
            //             <h2>Available Restaurants</h2>
            //             <table>
            //                 <thead>
            //                 <tr>
            //                     <th>Name</th>
            //                     <th>Description</th>
            //                     <th>Kitchen Type</th>
            //                     <th>Address</th>
            //                     <th>Opening Time</th>
            //                     <th>Closing Time</th>
            //                 </tr>
            //                 </thead>
            //                 <tbody>
            //                 {fakeRestaurants.map((restaurant, index) => (
            //                     <tr key={index}>
            //                         <td>{restaurant.name}</td>
            //                         <td>{restaurant.description}</td>
            //                         <td>{restaurant.kitchenType}</td>
            //                         <td>{restaurant.address}</td>
            //                         <td>{restaurant.openingTime}</td>
            //                         <td>{restaurant.closingTime}</td>
            //                     </tr>
            //                 ))}
            //                 </tbody>
            //             </table>
            //         </div>
            //     );

            case "restaurants":
                if (loading) {
                    return <div>Loading restaurants...</div>;
                }
                if (error) {
                    return <div className="error-message">{error}</div>;
                }
                return (
                    <div className="centered-content">
                        <h2>Available Restaurants</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Kitchen Type</th>
                                <th>Address</th>
                                <th>Opening Time</th>
                                <th>Closing Time</th>
                            </tr>
                            </thead>
                            <tbody>
                            {restaurants.map((restaurant) => (
                                <tr key={restaurant.managerId || restaurant.id}>
                                    <td>{restaurant.name}</td>
                                    <td>{restaurant.description}</td>
                                    <td>{restaurant.kitchenType}</td>
                                    <td>
                                        {typeof restaurant.address === "object"
                                            ? `${restaurant.address.street}, ${restaurant.address.city}, ${restaurant.address.state} ${restaurant.address.zip}`
                                            : restaurant.address}
                                    </td>
                                    <td>{restaurant.openingTime}</td>
                                    <td>{restaurant.closingTime}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                );

            case "reservations":
                return (
                    <div className="centered-content">
                        <h2>Your Reservations</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>Restaurant</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Guests</th>
                            </tr>
                            </thead>
                            <tbody>
                            {fakeReservations.map((reservation, index) => (
                                <tr key={index}>
                                    <td>{reservation.restaurantName}</td>
                                    <td>{reservation.date}</td>
                                    <td>{reservation.time}</td>
                                    <td>{reservation.guests}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                );
            default:
                return null;
        }
    };

    return (
        <div className="customer-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => setCurrentPage("main")}>Home</button>
                    <button onClick={() => setCurrentPage("restaurants")}>Restaurants</button>
                    <button onClick={() => setCurrentPage("reservations")}>Reservations</button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate('/profile')} // Navigate to ProfilePage
                >
                    Profile
                </button>
            </header>
            <div className="content">{renderPage()}</div>
        </div>
    );
}

export default CustomerPage;
