// import {useEffect, useState} from 'react';
// import {useNavigate} from 'react-router-dom';
// import './ManagerPage.css';
// import api from '../api.jsx';
//
// function ManagerPage() {
//     const [currentPage, setCurrentPage] = useState("main");
//     const [restaurants, setRestaurants] = useState([]);
//     const [loading, setLoading] = useState(false);
//     const [error, setError] = useState(null);
//     const [newRestaurant, setNewRestaurant] = useState({
//         name: "",
//         description: "",
//         kitchenType: "ITALIAN", // Default value, adjust as needed
//         address: {
//             street: "",
//             city: "",
//             state: "",
//             zip: "",
//         },
//         openingTime: "",
//         closingTime: "",
//     });
//     const [successMessage, setSuccessMessage] = useState(null);
//     const navigate = useNavigate();
//
//     // Fetch restaurants on component mount or when `currentPage` changes
//     useEffect(() => {
//         fetchManagedRestaurants();
//     }, []);
//
//     useEffect(() => {
//         if (currentPage === "restaurants") {
//             fetchManagedRestaurants();
//         }
//     }, [currentPage]);
//
//     const fetchManagedRestaurants = async () => {
//         setLoading(true);
//         setError(null);
//         try {
//             const token = localStorage.getItem('jwtToken'); // Retrieve JWT token
//             const response = await api.get('/reservation-service/restaurant/manager-restaurants', {
//                 headers: {Authorization: `Bearer ${token}`},
//             });
//             setRestaurants(response.data); // Set fetched manager's restaurants
//         } catch (err) {
//             console.error("Error fetching managed restaurants:", err);
//             setError("Failed to load restaurants. Please try again later.");
//         } finally {
//             setLoading(false);
//         }
//     };
//
//     const handleNewRestaurantChange = (e) => {
//         const {name, value} = e.target;
//
//         if (name.startsWith("address.")) {
//             const field = name.split(".")[1];
//             setNewRestaurant((prev) => ({
//                 ...prev,
//                 address: {
//                     ...prev.address,
//                     [field]: value,
//                 },
//             }));
//         } else {
//             setNewRestaurant((prev) => ({...prev, [name]: value}));
//         }
//     };
//
//     const handleAddRestaurant = async (e) => {
//         e.preventDefault();
//         setError(null);
//         setSuccessMessage(null);
//
//         try {
//             const token = localStorage.getItem('jwtToken');
//             await api.post("/reservation-service/restaurant", newRestaurant, {
//                 headers: {Authorization: `Bearer ${token}`},
//             });
//             setSuccessMessage("Restaurant added successfully!");
//             fetchManagedRestaurants(); // Update restaurant list immediately
//             setCurrentPage("restaurants"); // Redirect to the restaurant list
//         } catch (err) {
//             console.error("Error adding restaurant:", err);
//             setError("Failed to add restaurant. Please try again.");
//         }
//     };
//
//     const renderPage = () => {
//         switch (currentPage) {
//             case "main":
//                 return (
//                     <div className="main-dashboard">
//                         <h1>Welcome, Manager!</h1>
//                         <p>Here is your management dashboard.</p>
//                         <p>You currently manage <strong>{restaurants.length}</strong> restaurant(s).</p>
//                     </div>
//                 );
//
//             case "restaurants":
//                 if (loading) {
//                     return <div>Loading your restaurants...</div>;
//                 }
//                 if (error) {
//                     return <div className="error-message">{error}</div>;
//                 }
//                 return (
//                     <div className="centered-content">
//                         <h2>Your Managed Restaurants</h2>
//                         <table>
//                             <thead>
//                             <tr>
//                                 <th>Name</th>
//                                 <th>Description</th>
//                                 <th>Kitchen Type</th>
//                                 <th>Address</th>
//                                 <th>Opening Time</th>
//                                 <th>Closing Time</th>
//                             </tr>
//                             </thead>
//                             <tbody>
//                             {restaurants.map((restaurant) => (
//                                 <tr key={restaurant.id}>
//                                     <td>{restaurant.name}</td>
//                                     <td>{restaurant.description}</td>
//                                     <td>{restaurant.kitchenType}</td>
//                                     <td>{`${restaurant.address.street}, ${restaurant.address.city}, ${restaurant.address.state} ${restaurant.address.zip}`}</td>
//                                     <td>{restaurant.openingTime}</td>
//                                     <td>{restaurant.closingTime}</td>
//                                 </tr>
//                             ))}
//                             </tbody>
//                         </table>
//                     </div>
//                 );
//
//             case "add-restaurant":
//                 return (
//                     <div className="centered-content">
//                         <h2>Add a New Restaurant</h2>
//                         <form onSubmit={handleAddRestaurant}>
//                             <div className="input-group">
//                                 <label htmlFor="name">Name</label>
//                                 <input
//                                     type="text"
//                                     id="name"
//                                     name="name"
//                                     value={newRestaurant.name}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="description">Description</label>
//                                 <input
//                                     type="text"
//                                     id="description"
//                                     name="description"
//                                     value={newRestaurant.description}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="kitchenType">Kitchen Type</label>
//                                 <select
//                                     id="kitchenType"
//                                     name="kitchenType"
//                                     value={newRestaurant.kitchenType}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 >
//                                     <option value="ITALIAN">Italian</option>
//                                     <option value="CHINESE">Chinese</option>
//                                     <option value="MEXICAN">Mexican</option>
//                                     <option value="AMERICAN">American</option>
//                                 </select>
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="street">Street</label>
//                                 <input
//                                     type="text"
//                                     id="street"
//                                     name="address.street"
//                                     value={newRestaurant.address.street}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="city">City</label>
//                                 <input
//                                     type="text"
//                                     id="city"
//                                     name="address.city"
//                                     value={newRestaurant.address.city}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="state">State</label>
//                                 <input
//                                     type="text"
//                                     id="state"
//                                     name="address.state"
//                                     value={newRestaurant.address.state}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="zip">ZIP</label>
//                                 <input
//                                     type="text"
//                                     id="zip"
//                                     name="address.zip"
//                                     value={newRestaurant.address.zip}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="openingTime">Opening Time</label>
//                                 <input
//                                     type="time"
//                                     id="openingTime"
//                                     name="openingTime"
//                                     value={newRestaurant.openingTime}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <div className="input-group">
//                                 <label htmlFor="closingTime">Closing Time</label>
//                                 <input
//                                     type="time"
//                                     id="closingTime"
//                                     name="closingTime"
//                                     value={newRestaurant.closingTime}
//                                     onChange={handleNewRestaurantChange}
//                                     required
//                                 />
//                             </div>
//                             <button type="submit">Add Restaurant</button>
//                         </form>
//                         {error && <p className="error-message">{error}</p>}
//                         {successMessage && <p className="success-message">{successMessage}</p>}
//                     </div>
//                 );
//
//             default:
//                 return null;
//         }
//     };
//
//     return (
//         <div className="manager-page">
//             <header className="header">
//                 <div className="nav-buttons">
//                     <button onClick={() => setCurrentPage("main")}>Home</button>
//                     <button onClick={() => setCurrentPage("restaurants")}>Managed Restaurants</button>
//                     <button onClick={() => setCurrentPage("add-restaurant")}>Add Restaurant</button>
//                 </div>
//                 <button
//                     className="profile-button"
//                     onClick={() => navigate('/profile')} // Navigate to ProfilePage
//                 >
//                     Profile
//                 </button>
//             </header>
//             <div className="content">{renderPage()}</div>
//         </div>
//     );
// }
//
// export default ManagerPage;

import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import './ManagerPage.css';
import api from '../api.jsx';

function ManagerPage() {
    const [restaurantCount, setRestaurantCount] = useState(0); // Store restaurant count
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    // Fetch the count of managed restaurants on component mount
    useEffect(() => {
        fetchManagedRestaurantCount();
    }, []);

    const fetchManagedRestaurantCount = async () => {
        setLoading(true);
        setError(null);
        try {
            const token = localStorage.getItem('jwtToken'); // Retrieve JWT token
            const response = await api.get('/reservation-service/restaurant/manager-restaurants', {
                headers: {Authorization: `Bearer ${token}`},
            });
            setRestaurantCount(response.data.length); // Set the count based on the response
        } catch (err) {
            console.error("Error fetching restaurant count:", err);
            setError("Failed to fetch data. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="manager-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => navigate('/manager')}>Home</button>
                    <button onClick={() => navigate('/managed-restaurants')}>Managed Restaurants</button>
                    <button onClick={() => navigate('/add-restaurant')}>Add Restaurant</button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate('/profile')} // Navigate to ProfilePage
                >
                    Profile
                </button>
            </header>
            <div className="content">
                <div className="main-dashboard">
                    <h1>Welcome, Manager!</h1>
                    <p>Here is your management dashboard.</p>
                    {loading ? (
                        <p>Loading...</p>
                    ) : error ? (
                        <p className="error-message">{error}</p>
                    ) : (
                        <p>You currently manage <strong>{restaurantCount}</strong> restaurant(s).</p>
                    )}
                    <p>
                        Use the buttons above to manage your restaurants, add new ones, or view your profile.
                    </p>
                </div>
            </div>
        </div>
    );
}

export default ManagerPage;
