// import {useEffect, useState} from "react";
// import "./ReservationPage.css";
// import api from "../api.jsx";
//
// function ReservationPage() {
//     const [reservations, setReservations] = useState([]);
//     const [loading, setLoading] = useState(false);
//     const [error, setError] = useState(null);
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
//             const userId = localStorage.getItem("userId");
//
//             if (!token || !userId) {
//                 throw new Error("User ID or token is missing. Please log in.");
//             }
//
//             const reservationResponse = await api.get(
//                 `reservation-service/reservations/customer/${userId}`,
//                 {
//                     headers: {Authorization: `Bearer ${token}`},
//                 }
//             );
//
//             const reservationData = reservationResponse.data;
//
//             // Fetch restaurant names for each reservation's table
//             const enrichedReservations = await Promise.all(
//                 reservationData.map(async (reservation) => {
//                     try {
//                         const restaurantResponse = await api.get(
//                             `/reservation-service/restaurant/restaurant-from-table/${reservation.tableId}`,
//                             {
//                                 headers: {Authorization: `Bearer ${token}`},
//                             }
//                         );
//                         return {
//                             ...reservation,
//                             restaurantName: restaurantResponse.data, // Restaurant name is now a plain string
//                         };
//                     } catch (error) {
//                         console.error(
//                             `Error fetching restaurant name for table ID ${reservation.tableId}:`,
//                             error
//                         );
//                         return {...reservation, restaurantName: "Unknown"};
//                     }
//                 })
//             );
//
//             setReservations(enrichedReservations);
//         } catch (err) {
//             console.error("Error fetching reservations:", err);
//             setError("Failed to load reservations. Please try again later.");
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     if (loading) {
//         return <div className="reservation-loading">Loading reservations...</div>;
//     }
//
//     if (error) {
//         return <div className="reservation-error">{error}</div>;
//     }
//
//     if (reservations.length === 0) {
//         return <div className="reservation-empty">You have no reservations yet.</div>;
//     }
//
//     return (
//         <div className="reservation-container">
//             <h2>Your Reservations</h2>
//             <table className="reservation-table">
//                 <thead>
//                 <tr>
//                     <th>Restaurant Name</th>
//                     <th>Date</th>
//                     <th>Time</th>
//                     <th>Description</th>
//                 </tr>
//                 </thead>
//                 <tbody>
//                 {reservations.map((reservation) => (
//                     <tr key={reservation.reservationId}>
//                         <td>{reservation.restaurantName || "N/A"}</td>
//                         <td>{reservation.date || "N/A"}</td>
//                         <td>{reservation.time || "N/A"}</td>
//                         <td>{reservation.description || "No description provided"}</td>
//                     </tr>
//                 ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// }
//
// export default ReservationPage;

import {useEffect, useState} from "react";
import "./ReservationPage.css";
import api from "../api.jsx";

function ReservationPage() {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchReservations();
    }, []);

    const fetchReservations = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem("jwtToken");
            const userId = localStorage.getItem("userId");

            if (!token || !userId) {
                throw new Error("User ID or token is missing. Please log in.");
            }

            const reservationResponse = await api.get(
                `reservation-service/reservations/customer/${userId}`,
                {
                    headers: {Authorization: `Bearer ${token}`},
                }
            );

            const reservationData = reservationResponse.data;

            // Fetch additional details for each reservation
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
                            restaurantName: restaurantResponse.data,
                            appointmentDate: new Date(date).toLocaleDateString(),
                            appointmentTime: new Date(date).toLocaleTimeString([], {
                                hour: '2-digit',
                                minute: '2-digit'
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

    if (loading) {
        return <div className="reservation-loading">Loading reservations...</div>;
    }

    if (error) {
        return <div className="reservation-error">{error}</div>;
    }

    if (reservations.length === 0) {
        return <div className="reservation-empty">You have no reservations yet.</div>;
    }

    return (
        <div className="reservation-container">
            <h2>Your Reservations</h2>
            <table className="reservation-table">
                <thead>
                <tr>
                    <th>Restaurant Name</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                {reservations.map((reservation) => (
                    <tr key={reservation.reservationId}>
                        <td>{reservation.restaurantName || "N/A"}</td>
                        <td>{reservation.appointmentDate || "N/A"}</td>
                        <td>{reservation.appointmentTime || "N/A"}</td>
                        <td>{reservation.description || "No description provided"}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default ReservationPage;
