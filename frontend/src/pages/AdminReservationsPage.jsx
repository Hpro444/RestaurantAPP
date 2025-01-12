import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./AdminReservationsPage.css";
import api from "../api.jsx";

function AdminReservationsPage() {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    // Fetch all reservations when the component loads
    useEffect(() => {
        fetchAllReservations();
    }, []);

    const fetchAllReservations = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem("jwtToken");
            if (!token) {
                throw new Error("Authentication token is missing. Please log in again.");
            }

            const response = await api.get("/reservation-service/reservations", {
                headers: {Authorization: `Bearer ${token}`},
            });

            const reservationData = response.data;

            // Enrich reservation data with additional details
            const enrichedReservations = await Promise.all(
                reservationData.map(async (reservation) => {
                    try {
                        // Fetch restaurant name
                        const restaurantResponse = await api.get(
                            `/reservation-service/restaurant/restaurant-from-table/${reservation.tableId}`,
                            {
                                headers: {Authorization: `Bearer ${token}`},
                            }
                        );

                        // Fetch appointment details
                        const appointmentResponse = await api.get(
                            `/reservation-service/tables/appointment/${reservation.appointmentID}`,
                            {
                                headers: {Authorization: `Bearer ${token}`},
                            }
                        );

                        const {date} = appointmentResponse.data;

                        return {
                            ...reservation,
                            restaurantName: restaurantResponse.data || "Unknown",
                            appointmentDate: new Date(date).toLocaleDateString(),
                            appointmentTime: new Date(date).toLocaleTimeString([], {
                                hour: "2-digit",
                                minute: "2-digit",
                            }),
                        };
                    } catch (error) {
                        console.error(
                            `Error fetching details for reservation ID ${reservation.reservationId}:`,
                            error
                        );
                        return {
                            ...reservation,
                            restaurantName: "Unknown",
                            appointmentDate: "Unknown",
                            appointmentTime: "Unknown",
                        };
                    }
                })
            );

            setReservations(enrichedReservations);
        } catch (err) {
            console.error("Error fetching reservations:", err);
            setError("Failed to load reservations. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="admin-reservations-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => navigate("/admin")}>Home</button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate("/profile")}
                >
                    Profile
                </button>
            </header>
            <div className="content">
                <h2>All Reservations</h2>
                {loading && <p>Loading reservations...</p>}
                {error && <p className="error-message">{error}</p>}
                {!loading && !error && reservations.length === 0 && (
                    <p>No reservations found.</p>
                )}
                {!loading && !error && reservations.length > 0 && (
                    <table className="reservations-table">
                        <thead>
                        <tr>
                            <th>Reservation ID</th>
                            <th>Customer ID</th>
                            <th>Table ID</th>
                            <th>Appointment ID</th>
                            <th>Description</th>
                            <th>Restaurant</th>
                            <th>Date</th>
                            <th>Time</th>
                        </tr>
                        </thead>
                        <tbody>
                        {reservations.map((reservation) => (
                            <tr key={reservation.reservationId}>
                                <td>{reservation.reservationId}</td>
                                <td>{reservation.customerId}</td>
                                <td>{reservation.tableId}</td>
                                <td>{reservation.appointmentID}</td>
                                <td>{reservation.description || "N/A"}</td>
                                <td>{reservation.restaurantName}</td>
                                <td>{reservation.appointmentDate}</td>
                                <td>{reservation.appointmentTime}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}

export default AdminReservationsPage;
