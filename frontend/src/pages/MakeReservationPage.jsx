import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import api from "../api.jsx";
import "./MakeReservationPage.css";

function MakeReservationPage() {
    const {tableId} = useParams(); // Table ID
    const [appointments, setAppointments] = useState([]); // Appointment list state
    const [error, setError] = useState(null); // Error state
    const navigate = useNavigate();

    // Fetch appointments on component load
    useEffect(() => {
        fetchAppointments();
    }, []);

    // Fetch appointments from API
    const fetchAppointments = async () => {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await api.get(`/reservation-service/tables/appointments/${tableId}`, {
                headers: {Authorization: `Bearer ${token}`},
            });

            console.log("Fetched Appointments:", response.data); // Debug: check if appointments have `id`
            setAppointments(response.data); // Update appointments
        } catch (err) {
            console.error("Failed to fetch appointments:", err);
            setError("Failed to load appointments. Please try again.");
        }
    };

    // Decode user ID from JWT token
    const getUserIdFromToken = (token) => {
        if (!token) return null;
        try {
            const payload = JSON.parse(atob(token.split(".")[1])); // Decode JWT payload
            return payload.user_id; // Adjust based on your JWT structure
        } catch (err) {
            console.error("Failed to decode token:", err);
            return null;
        }
    };

    // Handle reservation when a button is clicked
    const handleReservation = async (appointmentId) => {
        try {
            const token = localStorage.getItem("jwtToken");
            const userId = getUserIdFromToken(token); // Extract customer ID
            if (!userId) throw new Error("Failed to extract user ID from token");

            const payload = {
                customerId: userId, // Add customer ID
                appointmentID: appointmentId, // Appointment ID from the clicked button
                tableId: parseInt(tableId), // Ensure tableId is a number
                description: "Reserved by Manager", // Custom description
            };

            console.log("Reservation Payload:", payload); // Debug the payload

            await api.post(`/reservation-service/reservations`, payload, {
                headers: {Authorization: `Bearer ${token}`},
            });

            // Navigate back or show success message
            navigate(-1);
        } catch (err) {
            console.error("Failed to make reservation:", err);
            setError("Failed to make reservation. Please try again.");
        }
    };

    return (
        <div className="make-reservation-page">
            <h2 className="title">Available Appointments</h2>
            {error && <p className="error-message">{error}</p>}
            {appointments.length === 0 && <p>No available appointments.</p>}
            {appointments.length > 0 && (
                <ul className="appointments-list">
                    {appointments.map((appointment) => (
                        <li key={appointment.id}>
                            <span>
                                {new Date(appointment.date).toLocaleString()} -{" "}
                                {appointment.available ? "Available" : "Reserved"}
                            </span>
                            {appointment.available && (
                                <button
                                    className="reserve-button"
                                    onClick={() => handleReservation(appointment.id)} // Pass the appointment ID
                                >
                                    Reserve
                                </button>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default MakeReservationPage;
