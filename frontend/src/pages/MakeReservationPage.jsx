import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api.jsx";
import "./MakeReservationPage.css";

function MakeReservationPage() {
    const { tableId } = useParams(); // Table ID
    const [appointments, setAppointments] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchAppointments();
    }, []);

    const fetchAppointments = async () => {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await api.get(`/reservation-service/tables/appointments/${tableId}`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setAppointments(response.data);
        } catch (err) {
            console.error("Failed to fetch appointments:", err);
            setError("Failed to load appointments. Please try again.");
        }
    };

    const handleReservation = async (appointmentId) => {
        try {
            const token = localStorage.getItem("jwtToken");
            await api.post(
                `/reservation-service/reservations`,
                { appointmentID: appointmentId, tableId, description: "Reserved by Manager" },
                { headers: { Authorization: `Bearer ${token}` } }
            );
            navigate(-1); // Go back
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
                                {new Date(appointment.date).toLocaleString()} - {appointment.isAvailable ? "Available" : "Reserved"}
                            </span>
                            {appointment.isAvailable && (
                                <button
                                    className="reserve-button"
                                    onClick={() => handleReservation(appointment.id)}
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
