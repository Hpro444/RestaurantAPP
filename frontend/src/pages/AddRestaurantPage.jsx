import {useState} from "react";
import {useNavigate} from "react-router-dom";
import api from "../api.jsx";
import "./AddRestaurantPage.css";

function AddRestaurantPage() {
    const [newRestaurant, setNewRestaurant] = useState({
        name: "",
        description: "",
        kitchenType: "ITALIAN",
        address: {
            street: "",
            city: "",
            state: "",
            zip: "",
        },
        openingTime: "",
        closingTime: "",
    });
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const navigate = useNavigate();

    const handleNewRestaurantChange = (e) => {
        const {name, value} = e.target;

        if (name.startsWith("address.")) {
            const field = name.split(".")[1];
            setNewRestaurant((prev) => ({
                ...prev,
                address: {
                    ...prev.address,
                    [field]: value,
                },
            }));
        } else {
            setNewRestaurant((prev) => ({...prev, [name]: value}));
        }
    };

    const handleAddRestaurant = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccessMessage(null);

        try {
            const token = localStorage.getItem("jwtToken");
            await api.post("/reservation-service/restaurant", newRestaurant, {
                headers: {Authorization: `Bearer ${token}`},
            });
            setSuccessMessage("Restaurant added successfully!");
            setTimeout(() => navigate("/manager"), 2000); // Redirect to the ManagerPage after 2 seconds
        } catch (err) {
            console.error("Error adding restaurant:", err);
            setError("Failed to add restaurant. Please try again.");
        }
    };

    return (
        <div className="add-restaurant-page">
            <h2>Add a New Restaurant</h2>
            <form onSubmit={handleAddRestaurant}>
                <div className="input-group">
                    <label htmlFor="name">Name</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={newRestaurant.name}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="description">Description</label>
                    <input
                        type="text"
                        id="description"
                        name="description"
                        value={newRestaurant.description}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="kitchenType">Kitchen Type</label>
                    <select
                        id="kitchenType"
                        name="kitchenType"
                        value={newRestaurant.kitchenType}
                        onChange={handleNewRestaurantChange}
                        required
                    >
                        <option value="ITALIAN">Italian</option>
                        <option value="CHINESE">Chinese</option>
                        <option value="MEXICAN">Mexican</option>
                        <option value="AMERICAN">American</option>
                    </select>
                </div>
                <div className="input-group">
                    <label htmlFor="street">Street</label>
                    <input
                        type="text"
                        id="street"
                        name="address.street"
                        value={newRestaurant.address.street}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="city">City</label>
                    <input
                        type="text"
                        id="city"
                        name="address.city"
                        value={newRestaurant.address.city}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="state">State</label>
                    <input
                        type="text"
                        id="state"
                        name="address.state"
                        value={newRestaurant.address.state}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="zip">ZIP</label>
                    <input
                        type="text"
                        id="zip"
                        name="address.zip"
                        value={newRestaurant.address.zip}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="openingTime">Opening Time</label>
                    <input
                        type="time"
                        id="openingTime"
                        name="openingTime"
                        value={newRestaurant.openingTime}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <div className="input-group">
                    <label htmlFor="closingTime">Closing Time</label>
                    <input
                        type="time"
                        id="closingTime"
                        name="closingTime"
                        value={newRestaurant.closingTime}
                        onChange={handleNewRestaurantChange}
                        required
                    />
                </div>
                <button type="submit">Add Restaurant</button>
            </form>
            {error && <p className="error-message">{error}</p>}
            {successMessage && <p className="success-message">{successMessage}</p>}
        </div>
    );
}

export default AddRestaurantPage;
