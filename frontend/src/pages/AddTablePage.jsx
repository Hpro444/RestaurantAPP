import {useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import api from "../api.jsx";
import "./AddTablePage.css";

function AddTablePage() {
    const {id} = useParams();
    const [table, setTable] = useState({capacity: 0});
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setTable((prev) => ({...prev, [name]: value}));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem("jwtToken");
            await api.post(`/reservation-service/restaurant/${id}/tables`, table, {
                headers: {Authorization: `Bearer ${token}`},
            });
            navigate("/managed-restaurants");
        } catch (err) {
            setError("Failed to add table.");
        }
    };

    return (
        <div className="add-table-page">
            <h2>Add Table</h2>
            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handleSubmit}>
                <div className="input-group">
                    <label htmlFor="capacity">Capacity</label>
                    <input
                        type="number"
                        id="capacity"
                        name="capacity"
                        value={table.capacity}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <button type="submit">Add Table</button>
            </form>
        </div>
    );
}

export default AddTablePage;
