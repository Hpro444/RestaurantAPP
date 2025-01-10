// import  { useState } from 'react';
// import './App.css';
//
// // Fake data for restaurants
// const fakeRestaurants = [
//     {
//         managerId: 1,
//         manager_email: "manager1@example.com",
//         name: "The Gourmet Spot",
//         description: "A fine dining experience.",
//         openingTime: "09:00",
//         closingTime: "22:00",
//         kitchenType: "Italian",
//         address: "123 Gourmet Lane, Food City"
//     },
//     {
//         managerId: 2,
//         manager_email: "manager2@example.com",
//         name: "Sushi Palace",
//         description: "Fresh sushi and sashimi.",
//         openingTime: "11:00",
//         closingTime: "23:00",
//         kitchenType: "Japanese",
//         address: "456 Sushi Street, Ocean City"
//     },
//     {
//         managerId: 3,
//         manager_email: "manager3@example.com",
//         name: "Taco Fiesta",
//         description: "Authentic Mexican tacos.",
//         openingTime: "10:00",
//         closingTime: "21:00",
//         kitchenType: "Mexican",
//         address: "789 Taco Ave, Fiesta Town"
//     }
// ];
//
// // Fake data for reservations
// const fakeReservations = [
//     {
//         reservationId: 101,
//         restaurantName: "The Gourmet Spot",
//         customerName: "John Doe",
//         date: "2023-12-15",
//         time: "19:00",
//         guests: 2
//     },
//     {
//         reservationId: 102,
//         restaurantName: "Sushi Palace",
//         customerName: "Jane Smith",
//         date: "2023-12-16",
//         time: "20:30",
//         guests: 4
//     },
//     {
//         reservationId: 103,
//         restaurantName: "Taco Fiesta",
//         customerName: "Alice Brown",
//         date: "2023-12-17",
//         time: "18:00",
//         guests: 3
//     }
// ];
//
// // Component for the restaurant table
// // eslint-disable-next-line react/prop-types
// function RestaurantTable({ restaurants }) {
//     return (
//         <table className="restaurant-table">
//             <thead>
//             <tr>
//                 <th>Manager ID</th>
//                 <th>Manager Email</th>
//                 <th>Name</th>
//                 <th>Description</th>
//                 <th>Opening Time</th>
//                 <th>Closing Time</th>
//                 <th>Kitchen Type</th>
//                 <th>Address</th>
//             </tr>
//             </thead>
//             <tbody>
//             {/* eslint-disable-next-line react/prop-types */}
//             {restaurants.map((restaurant, index) => (
//                 <tr key={index}>
//                     <td>{restaurant.managerId}</td>
//                     <td>{restaurant.manager_email}</td>
//                     <td>{restaurant.name}</td>
//                     <td>{restaurant.description}</td>
//                     <td>{restaurant.openingTime}</td>
//                     <td>{restaurant.closingTime}</td>
//                     <td>{restaurant.kitchenType}</td>
//                     <td>{restaurant.address}</td>
//                 </tr>
//             ))}
//             </tbody>
//         </table>
//     );
// }
//
// // Component for the reservations table
// // eslint-disable-next-line react/prop-types
// function ReservationsTable({ reservations }) {
//     return (
//         <table className="restaurant-table">
//             <thead>
//             <tr>
//                 <th>Reservation ID</th>
//                 <th>Restaurant Name</th>
//                 <th>Customer Name</th>
//                 <th>Date</th>
//                 <th>Time</th>
//                 <th>Guests</th>
//             </tr>
//             </thead>
//             <tbody>
//             {/* eslint-disable-next-line react/prop-types */}
//             {reservations.map((reservation, index) => (
//                 <tr key={index}>
//                     <td>{reservation.reservationId}</td>
//                     <td>{reservation.restaurantName}</td>
//                     <td>{reservation.customerName}</td>
//                     <td>{reservation.date}</td>
//                     <td>{reservation.time}</td>
//                     <td>{reservation.guests}</td>
//                 </tr>
//             ))}
//             </tbody>
//         </table>
//     );
// }
//
// function App() {
//     const [currentPage, setCurrentPage] = useState("main"); // Track the current page
//
//     return (
//         <div className="App">
//             {/* Navigation Bar */}
//             <nav className="navbar">
//                 <button onClick={() => setCurrentPage("main")}>Main Page</button>
//                 <button onClick={() => setCurrentPage("restaurants")}>Restaurants</button>
//                 <button onClick={() => setCurrentPage("reservations")}>Reservations</button>
//             </nav>
//
//             {/* Main Content */}
//             <div className="content">
//                 {currentPage === "main" && (
//                     <div className="welcome">
//                         <h1>Welcome to the Restaurant App</h1>
//                         <p>Click on the buttons above to view restaurants or reservations.</p>
//                     </div>
//                 )}
//                 {currentPage === "restaurants" && <RestaurantTable restaurants={fakeRestaurants} />}
//                 {currentPage === "reservations" && <ReservationsTable reservations={fakeReservations} />}
//             </div>
//         </div>
//     );
// }
//
// export default App;




import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import './App.css';
import Login from './Login.jsx'; // Import the Login component
import CustomerPage from './pages/CustomerPage.jsx';
// import ManagerPage from './ManagerPage.jsx';
// import AdminPage from './AdminPage.jsx';

function EntryPage() {
    const navigate = useNavigate();

    return (
        <div className="entry-page">
            <h1>Welcome to the Restaurant Reservation System</h1>
            <button onClick={() => navigate('/login')}>Login</button>
            <button onClick={() => navigate('/register')}>Register</button>
        </div>
    );
}

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<EntryPage />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<div>Registration Page Placeholder</div>} />
                <Route path="/customer" element={<CustomerPage />} />
                {/* Add other routes as needed */}
            </Routes>
        </Router>
    );
}

// how should be:
// function App() {
//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<EntryPage />} />
//                 <Route path="/login" element={<Login />} />
//                 <Route path="/customer" element={<CustomerPage />} />
//                 <Route path="/manager" element={<ManagerPage />} />
//                 <Route path="/admin" element={<AdminPage />} />
//                 <Route path="/register" element={<div>Registration Page Placeholder</div>} />
//             </Routes>
//         </Router>
//     );
// }

export default App;