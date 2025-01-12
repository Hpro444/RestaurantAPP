import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import api from "../api.jsx";
import "./EditRestaurantPage.css";

function EditRestaurantPage() {
    const {id} = useParams();
    const [restaurant, setRestaurant] = useState({
        name: "",
        description: "",
        kitchenType: "",
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
    const navigate = useNavigate();

    useEffect(() => {
        if (!id) {
            setError("Invalid restaurant ID.");
            console.error("Restaurant ID is invalid.");
            navigate("/managed-restaurants");
        } else {
            fetchRestaurant();
        }
    }, [id]);

    const fetchRestaurant = async () => {
        try {
            const token = localStorage.getItem("jwtToken");
            const response = await api.get(`/reservation-service/restaurant/${id}`, {
                headers: {Authorization: `Bearer ${token}`},
            });
            setRestaurant(response.data);
        } catch (err) {
            console.error("Failed to fetch restaurant details:", err);
            setError("Failed to load restaurant details.");
        }
    };

    const handleInputChange = (e) => {
        const {name, value} = e.target;

        if (name.startsWith("address.")) {
            const field = name.split(".")[1];
            setRestaurant((prev) => ({
                ...prev,
                address: {
                    ...prev.address,
                    [field]: value,
                },
            }));
        } else {
            setRestaurant((prev) => ({...prev, [name]: value}));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem("jwtToken");
            await api.put(`/reservation-service/restaurant/${id}`, restaurant, {
                headers: {Authorization: `Bearer ${token}`},
            });
            navigate("/managed-restaurants");
        } catch (err) {
            console.error("Failed to update restaurant:", err);
            setError("Failed to update restaurant.");
        }
    };

    return (
        <div className="edit-restaurant-page">
            <div className="card">
                <h2 className="card-title">Edit Restaurant</h2>
                {error && <p className="error-message">{error}</p>}
                <form onSubmit={handleSubmit}>
                    <div className="card-body">
                        <div className="input-group">
                            <label htmlFor="name">Name</label>
                            <input
                                type="text"
                                id="name"
                                name="name"
                                value={restaurant.name}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label htmlFor="description">Description</label>
                            <textarea
                                id="description"
                                name="description"
                                value={restaurant.description}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label htmlFor="kitchenType">Kitchen Type</label>
                            <input
                                type="text"
                                id="kitchenType"
                                name="kitchenType"
                                value={restaurant.kitchenType}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label>Address</label>
                            <input
                                type="text"
                                placeholder="Street"
                                name="address.street"
                                value={restaurant.address.street}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                type="text"
                                placeholder="City"
                                name="address.city"
                                value={restaurant.address.city}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                type="text"
                                placeholder="State"
                                name="address.state"
                                value={restaurant.address.state}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                type="text"
                                placeholder="ZIP"
                                name="address.zip"
                                value={restaurant.address.zip}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label htmlFor="openingTime">Opening Time</label>
                            <input
                                type="time"
                                id="openingTime"
                                name="openingTime"
                                value={restaurant.openingTime}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label htmlFor="closingTime">Closing Time</label>
                            <input
                                type="time"
                                id="closingTime"
                                name="closingTime"
                                value={restaurant.closingTime}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                    </div>
                    <div className="card-footer">
                        <button type="submit" className="save-button">
                            Save Changes
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default EditRestaurantPage;
