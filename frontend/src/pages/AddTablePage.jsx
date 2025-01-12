import {useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import api from "../api.jsx";
import "./AddTablePage.css";

function AddTablePage() {
    const {id} = useParams(); // Restaurant ID
    const [table, setTable] = useState({
        capacity: 0,
        isMergeable: false,
        zone: "Indoor", // Default to "Indoor"
        tableName: "",
    });
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const {name, value, type, checked} = e.target;
        setTable((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem("jwtToken");
            await api.post(`/reservation-service/tables/restaurant/${id}`, table, {
                headers: {Authorization: `Bearer ${token}`},
            });
            navigate(`/managed-restaurants`); // Navigate back to the restaurant management page
        } catch (err) {
            console.error("Failed to add table:", err);
            setError("Failed to add table. Please try again.");
        }
    };

    return (
        <div className="add-table-page">
            <div className="card">
                <h2 className="card-title">Add Table</h2>
                {error && <p className="error-message">{error}</p>}
                <form onSubmit={handleSubmit}>
                    <div className="card-body">
                        <div className="input-group">
                            <label htmlFor="tableName">Table Name</label>
                            <input
                                type="text"
                                id="tableName"
                                name="tableName"
                                value={table.tableName}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label htmlFor="capacity">Capacity</label>
                            <input
                                type="number"
                                id="capacity"
                                name="capacity"
                                value={table.capacity}
                                onChange={handleInputChange}
                                required
                                min="1"
                            />
                        </div>
                        <div className="input-group">
                            <label htmlFor="zone">Zone</label>
                            <select
                                id="zone"
                                name="zone"
                                value={table.zone}
                                onChange={handleInputChange}
                                required
                            >
                                <option value="Indoor">Indoor</option>
                                <option value="Outdoor">Outdoor</option>
                            </select>
                        </div>
                        <div className="input-group checkbox-group">
                            <label htmlFor="isMergeable">Is Mergeable</label>
                            <input
                                type="checkbox"
                                id="isMergeable"
                                name="isMergeable"
                                checked={table.isMergeable}
                                onChange={handleInputChange}
                            />
                        </div>
                    </div>
                    <div className="card-footer">
                        <button type="submit" className="add-button">
                            Add Table
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default AddTablePage;
