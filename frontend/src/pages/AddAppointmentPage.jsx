import {useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import api from "../api.jsx";
import "./AddAppointmentPage.css";

function AddAppointmentPage() {
    const {tableId} = useParams(); // Table ID from URL
    const [appointment, setAppointment] = useState({
        date: "",
        time: "",
    });
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setAppointment((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    // const handleSubmit = async (e) => {
    //     e.preventDefault();
    //     try {
    //         const token = localStorage.getItem("jwtToken");
    //         const formattedDateTime = `${appointment.date}T${appointment.time}:00`; // Ensure seconds are included
    //         await api.post(
    //             `/reservation-service/tables/appointment`,
    //             {
    //                 date: formattedDateTime,
    //                 isAvailable: true,
    //                 tableId: parseInt(tableId), // Ensure tableId is a number
    //             },
    //             {
    //                 headers: {Authorization: `Bearer ${token}`},
    //             }
    //         );
    //         navigate(-1); // Go back to the previous page
    //     } catch (err) {
    //         console.error("Failed to add appointment:", err);
    //         setError("Failed to add appointment. Please try again.");
    //     }
    // };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem("jwtToken");
            const formattedDateTime = `${appointment.date}T${appointment.time}:00`; // Ensure seconds are included
            const payload = {
                date: formattedDateTime,
                available: true,
                tableId: parseInt(tableId), // Ensure tableId is a number
            };
            console.log("Payload:", payload); // Debug log
            await api.post(`/reservation-service/tables/appointment`, payload, {
                headers: {Authorization: `Bearer ${token}`},
            });
            navigate(-1); // Go back to the previous page
        } catch (err) {
            console.error("Failed to add appointment:", err);
            setError("Failed to add appointment. Please try again.");
        }
    };


    return (
        <div className="add-appointment-page">
            <h2 className="title">Add Appointment</h2>
            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handleSubmit}>
                <div className="input-group">
                    <label htmlFor="date">Date</label>
                    <input
                        type="date"
                        id="date"
                        name="date"
                        value={appointment.date}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="time">Time</label>
                    <input
                        type="time"
                        id="time"
                        name="time"
                        value={appointment.time}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <button type="submit" className="add-button">
                    Add Appointment
                </button>
            </form>
        </div>
    );
}

export default AddAppointmentPage;
