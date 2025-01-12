import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import './ManagedRestaurantsPage.css';
import api from '../api.jsx';

function ManagedRestaurantsPage() {
    const [restaurants, setRestaurants] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchManagedRestaurants();
    }, []);

    const fetchManagedRestaurants = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem('jwtToken'); // Retrieve JWT token
            const response = await api.get('/reservation-service/restaurant/manager-restaurants', {
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

    return (
        <div className="managed-restaurants-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => navigate('/manager')}>Home</button>
                    <button onClick={() => navigate('/add-restaurant')}>Add Restaurant</button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate('/profile')} // Navigate to ProfilePage
                >
                    Profile
                </button>
            </header>
            <div className="content">
                {loading && <p>Loading your restaurants...</p>}
                {error && <p className="error-message">{error}</p>}
                {!loading && !error && (
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
                                    <td>{`${restaurant.address.street}, ${restaurant.address.city}, ${restaurant.address.state} ${restaurant.address.zip}`}</td>
                                    <td>{restaurant.openingTime}</td>
                                    <td>{restaurant.closingTime}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    );
}

export default ManagedRestaurantsPage;
