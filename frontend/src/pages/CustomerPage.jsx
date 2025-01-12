import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./CustomerPage.css";
import api from "../api.jsx";

function CustomerPage() {
    const [currentPage, setCurrentPage] = useState("main");
    const [restaurantCount, setRestaurantCount] = useState(0); // To store the number of restaurants
    const [reservationCount, setReservationCount] = useState(0); // To store the number of reservations
    const [loading, setLoading] = useState(false); // Loading state
    const [error, setError] = useState(null); // Error state
    const navigate = useNavigate();

    // Fetch restaurant and reservation counts on component mount
    useEffect(() => {
        fetchCounts();
    }, []);

    const fetchCounts = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem("jwtToken");

            // Fetch restaurant count
            const restaurantResponse = await api.get("/reservation-service/restaurant", {
                headers: {Authorization: `Bearer ${token}`},
            });
            setRestaurantCount(restaurantResponse.data.length);

            // Fetch reservation count for the customer
            const userId = localStorage.getItem("userId");
            const reservationResponse = await api.get(
                `/reservation-service/reservations/customer/${userId}`,
                {
                    headers: {Authorization: `Bearer ${token}`},
                }
            );
            setReservationCount(reservationResponse.data.length);
        } catch (err) {
            console.error("Error fetching counts:", err);
            setError("Failed to load data. Please try again.");
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
                        {loading ? (
                            <p>Loading your dashboard...</p>
                        ) : error ? (
                            <p className="error-message">{error}</p>
                        ) : (
                            <>
                                <p>
                                    We have <strong>{restaurantCount}</strong> restaurants available for you.
                                </p>
                                <p>
                                    You currently have <strong>{reservationCount}</strong> reservations in the system.
                                </p>
                            </>
                        )}
                    </div>
                );

            case "showAllRestaurants":
                navigate("/show-all-restaurants");
                break;

            case "showAllReservations":
                navigate("/user-reservations");
                break;

            default:
                return null;
        }
    };

    return (
        <div className="customer-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => setCurrentPage("main")}>Home</button>
                    <button onClick={() => setCurrentPage("showAllRestaurants")}>
                        Show All Restaurants
                    </button>
                    <button onClick={() => setCurrentPage("showAllReservations")}>
                        Show All Reservations
                    </button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate("/profile")}
                >
                    Profile
                </button>
            </header>
            <div className="content">{renderPage()}</div>
        </div>
    );
}

export default CustomerPage;
