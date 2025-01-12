import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api.jsx";
import "./ShowTablesPage.css";

function ShowTablesPageForCustomer() {
    const { id } = useParams(); // Restaurant ID
    const [tables, setTables] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchTables();
    }, []);

    const fetchTables = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await api.get(`/reservation-service/tables/restaurant/${id}`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            // Ensure the response data has the `id` field
            const tableData = response.data.map((table) => ({
                id: table.id, // Add id field to the table object
                tableName: table.tableName,
                capacity: table.capacity,
                zone: table.zone,
                isMergeable: table.isMergeable,
            }));

            setTables(tableData);
        } catch (err) {
            console.error("Error fetching tables:", err);
            setError("Failed to load tables. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="show-tables-page">
            <h2 className="title">Tables for Restaurant</h2>
            {error && <p className="error-message">{error}</p>}
            {loading && <p className="loading-message">Loading tables...</p>}
            {!loading && !error && (
                <table className="table">
                    <thead>
                    <tr>
                        <th>Table Name</th>
                        <th>Capacity</th>
                        <th>Zone</th>
                        <th>Mergeable</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {tables.map((table) => (
                        <tr key={table.id}>
                            <td>{table.tableName}</td>
                            <td>{table.capacity}</td>
                            <td>{table.zone}</td>
                            <td>{table.isMergeable ? "Yes" : "No"}</td>
                            <td>
                                {/* Only show the "Make Reservation" button */}
                                <button
                                    className="action-button make-reservation-button"
                                    onClick={() => navigate(`/make-reservation/${table.id}`)}
                                >
                                    Make Reservation
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default ShowTablesPageForCustomer;
