import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import './ManagerPage.css';
import api from '../api.jsx';

function ManagerPage() {
    const [currentPage, setCurrentPage] = useState("main");
    const [restaurants, setRestaurants] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (currentPage === "restaurants") {
            fetchManagedRestaurants();
        }
    }, [currentPage]);

    const fetchManagedRestaurants = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem('jwtToken'); // Retrieve JWT token
            const response = await api.get('/reservation-service/manager/restaurants', {
                headers: {Authorization: `Bearer ${token}`},
            });
            setRestaurants(response.data); // Set fetched manager's restaurants
        } catch (err) {
            console.error("Error fetching managed restaurants:", err);
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
                        <h1>Welcome, Manager!</h1>
                        <p>Here is your management dashboard.</p>
                        <p>You currently manage <strong>{restaurants.length}</strong> restaurant(s).</p>
                    </div>
                );

            case "restaurants":
                if (loading) {
                    return <div>Loading your restaurants...</div>;
                }
                if (error) {
                    return <div className="error-message">{error}</div>;
                }
                return (
                    <div className="centered-content">
                        <h2>Your Managed Restaurants</h2>
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
                                <tr key={restaurant.id}>
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

            default:
                return null;
        }
    };

    return (
        <div className="manager-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => setCurrentPage("main")}>Home</button>
                    <button onClick={() => setCurrentPage("restaurants")}>Managed Restaurants</button>
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

export default ManagerPage;
