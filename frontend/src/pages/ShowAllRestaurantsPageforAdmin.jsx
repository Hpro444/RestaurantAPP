import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./ShowAllRestaurantsPage.css";
import api from "../api.jsx";

function ShowAllRestaurantsPageForAdmin() {
    const [restaurants, setRestaurants] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchRestaurants();
    }, []);

    const fetchRestaurants = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await api.get("/reservation-service/restaurant", {
                headers: {Authorization: `Bearer ${token}`},
            });
            setRestaurants(response.data);
        } catch (err) {
            console.error("Error fetching restaurants:", err);
            setError("Failed to load restaurants. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="show-all-restaurants-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => navigate("/admin")}>Home</button>
                    <button onClick={() => navigate("/add-restaurant")}>Add Restaurant</button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate("/profile")}
                >
                    Profile
                </button>
            </header>
            <div className="content">
                {loading && <p>Loading restaurants...</p>}
                {error && <p className="error-message">{error}</p>}
                {!loading && !error && (
                    <div className="centered-content">
                        <h2>All Restaurants (Admin View)</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Kitchen Type</th>
                                <th>Address</th>
                                <th>Opening Time</th>
                                <th>Closing Time</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {restaurants.map((restaurant) => (
                                <tr key={restaurant.id}>
                                    <td>{restaurant.name}</td>
                                    <td>{restaurant.description}</td>
                                    <td>{restaurant.kitchenType}</td>
                                    <td>
                                        {`${restaurant.address.street}, ${restaurant.address.city}, ${restaurant.address.state} ${restaurant.address.zip}`}
                                    </td>
                                    <td>{restaurant.openingTime}</td>
                                    <td>{restaurant.closingTime}</td>
                                    <td>
                                        <button
                                            className="action-button edit-button"
                                            onClick={() => navigate(`/edit-restaurant/${restaurant.id}`)}
                                        >
                                            Edit
                                        </button>
                                        <button
                                            className="action-button add-table-button"
                                            onClick={() => navigate(`/add-table/${restaurant.id}`)}
                                        >
                                            Add Table
                                        </button>
                                        <button
                                            className="action-button show-tables-button"
                                            onClick={() => navigate(`/show-tables/${restaurant.id}`)}
                                        >
                                            Show Tables
                                        </button>
                                        <button
                                            className="action-button view-reservations-button"
                                            onClick={() => navigate(`/restaurant-reservations/${restaurant.id}`)}
                                        >
                                            View Reservations
                                        </button>
                                    </td>
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

export default ShowAllRestaurantsPageForAdmin;
