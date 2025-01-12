// import {useEffect, useState} from "react";
// import {useNavigate, useParams} from "react-router-dom";
// import api from "../api.jsx";
// import "./RestaurantReservationsPage.css";
//
// function RestaurantReservationsPage() {
//     const {restaurantId} = useParams();
//     const [reservations, setReservations] = useState([]);
//     const [appointments, setAppointments] = useState({});
//     const [loading, setLoading] = useState(false);
//     const [error, setError] = useState(null);
//     const navigate = useNavigate();
//
//     useEffect(() => {
//         fetchReservations();
//     }, []);
//
//     const fetchReservations = async () => {
//         setLoading(true);
//         setError(null);
//         try {
//             const token = localStorage.getItem("jwtToken");
//
//             const formatDateTime = (date) =>
//                 date.toISOString().split(".")[0]; // Format to `yyyy-MM-dd'T'HH:mm:ss`
//
//             const from = formatDateTime(new Date());
//             const to = formatDateTime(new Date(Date.now() + 3 * 24 * 60 * 60 * 1000)); // 3 days range
//
//             const response = await api.get(
//                 `/reservation-service/reservations/restaurant/${restaurantId}?from=${from}&to=${to}`,
//                 {
//                     headers: {Authorization: `Bearer ${token}`},
//                 }
//             );
//
//             const reservations = response.data;
//             setReservations(reservations);
//
//             // Fetch appointment details for each reservation
//             const fetchedAppointments = {};
//             await Promise.all(
//                 reservations.map(async (reservation) => {
//                     const appointmentResponse = await api.get(
//                         `/reservation-service/tables/appointment/${reservation.appointmentID}`,
//                         {headers: {Authorization: `Bearer ${token}`}}
//                     );
//                     fetchedAppointments[reservation.appointmentID] = appointmentResponse.data.date;
//                 })
//             );
//
//             setAppointments(fetchedAppointments);
//         } catch (err) {
//             console.error("Failed to fetch reservations:", err);
//             setError("Failed to load reservations. Please try again.");
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     return (
//         <div className="restaurant-reservations-page">
//             <h2>Reservations for Restaurant {restaurantId}</h2>
//             {error && <p className="error-message">{error}</p>}
//             {loading && <p>Loading reservations...</p>}
//             {!loading && reservations.length === 0 && <p>No reservations found.</p>}
//             {!loading && reservations.length > 0 && (
//                 <table className="reservations-table">
//                     <thead>
//                     <tr>
//                         <th>Customer ID</th>
//                         <th>Table Number</th>
//                         <th>Reservation Date</th>
//                         <th>Description</th>
//                     </tr>
//                     </thead>
//                     <tbody>
//                     {reservations.map((reservation) => (
//                         <tr key={reservation.id}>
//                             <td>{reservation.customerId}</td>
//                             <td>{reservation.tableId}</td>
//                             <td>
//                                 {appointments[reservation.appointmentID]
//                                     ? new Date(appointments[reservation.appointmentID]).toLocaleString()
//                                     : "Loading..."}
//                             </td>
//                             <td>{reservation.description}</td>
//                         </tr>
//                     ))}
//                     </tbody>
//                 </table>
//             )}
//             <button onClick={() => navigate(-1)}>Back</button>
//         </div>
//     );
// }
//
// export default RestaurantReservationsPage;


import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import api from "../api.jsx";
import "./RestaurantReservationsPage.css";

function RestaurantReservationsPage() {
    const {restaurantId} = useParams();
    const [reservations, setReservations] = useState([]);
    const [appointments, setAppointments] = useState({});
    const [restaurantName, setRestaurantName] = useState(""); // State for restaurant name
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchRestaurantName(); // Fetch restaurant name
        fetchReservations(); // Fetch reservations
    }, []);

    const fetchRestaurantName = async () => {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await api.get(`/reservation-service/restaurant/${restaurantId}`, {
                headers: {Authorization: `Bearer ${token}`},
            });
            setRestaurantName(response.data.name); // Set the restaurant name
        } catch (err) {
            console.error("Failed to fetch restaurant name:", err);
            setError("Failed to load restaurant name. Please try again.");
        }
    };

    const fetchReservations = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem("jwtToken");

            const formatDateTime = (date) =>
                date.toISOString().split(".")[0]; // Format to `yyyy-MM-dd'T'HH:mm:ss`

            const from = formatDateTime(new Date());
            const to = formatDateTime(new Date(Date.now() + 3 * 24 * 60 * 60 * 1000)); // 3 days range

            const response = await api.get(
                `/reservation-service/reservations/restaurant/${restaurantId}?from=${from}&to=${to}`,
                {
                    headers: {Authorization: `Bearer ${token}`},
                }
            );

            const reservations = response.data;
            setReservations(reservations);

            // Fetch appointment details for each reservation
            const fetchedAppointments = {};
            await Promise.all(
                reservations.map(async (reservation) => {
                    const appointmentResponse = await api.get(
                        `/reservation-service/tables/appointment/${reservation.appointmentID}`,
                        {headers: {Authorization: `Bearer ${token}`}}
                    );
                    fetchedAppointments[reservation.appointmentID] = appointmentResponse.data.date;
                })
            );

            setAppointments(fetchedAppointments);
        } catch (err) {
            console.error("Failed to fetch reservations:", err);
            setError("Failed to load reservations. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="restaurant-reservations-page">
            <h2>Reservations for Restaurant: {restaurantName || `ID ${restaurantId}`}</h2>
            {error && <p className="error-message">{error}</p>}
            {loading && <p>Loading reservations...</p>}
            {!loading && reservations.length === 0 && <p>No reservations found.</p>}
            {!loading && reservations.length > 0 && (
                <table className="reservations-table">
                    <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th>Table Number</th>
                        <th>Reservation Date</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    {reservations.map((reservation) => (
                        <tr key={reservation.id}>
                            <td>{reservation.customerId}</td>
                            <td>{reservation.tableId}</td>
                            <td>
                                {appointments[reservation.appointmentID]
                                    ? new Date(appointments[reservation.appointmentID]).toLocaleString()
                                    : "Loading..."}
                            </td>
                            <td>{reservation.description}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
            <button onClick={() => navigate(-1)}>Back</button>
        </div>
    );
}

export default RestaurantReservationsPage;
