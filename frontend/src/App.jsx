import {BrowserRouter as Router, Route, Routes, useNavigate} from 'react-router-dom';
import './App.css';
import Login from './pages/Login.jsx'; // Import the Login component
import CustomerPage from './pages/CustomerPage.jsx';
import ManagerPage from './pages/ManagerPage.jsx';
import AdminPage from './pages/AdminPage.jsx';
import ProfilePage from './pages/ProfilePage.jsx';
import RegistrationPage from './pages/RegistrationPage.jsx';
import ActivationPage from './pages/ActivationPage.jsx';
import AddRestaurantPage from "./pages/AddRestaurantPage.jsx";
import ManagedRestaurantsPage from './pages/ManagedRestaurantsPage.jsx';
import EditRestaurantPage from './pages/EditRestaurantPage.jsx';
import AddTablePage from './pages/AddTablePage.jsx';
import ShowTablesPage from './pages/ShowTablesPage.jsx';
import AddAppointmentPage from './pages/AddAppointmentPage.jsx';
import MakeReservationPage from './pages/MakeReservationPage.jsx';
import RestaurantReservationsPage from "./pages/RestaurantReservationsPage.jsx";


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
                <Route path="/" element={<EntryPage/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<RegistrationPage/>}/>
                <Route path="/activate" element={<ActivationPage/>}/>
                <Route path="/customer" element={<CustomerPage/>}/>
                <Route path="/manager" element={<ManagerPage/>}/>
                <Route path="/add-restaurant" element={<AddRestaurantPage/>}/>
                <Route path="/managed-restaurants" element={<ManagedRestaurantsPage/>}/>
                <Route path="/edit-restaurant/:id" element={<EditRestaurantPage/>}/>
                <Route path="/add-table/:id" element={<AddTablePage/>}/>
                <Route path="/admin" element={<AdminPage/>}/>
                <Route path="/profile" element={<ProfilePage/>}/>
                <Route path="/show-tables/:id" element={<ShowTablesPage/>}/>
                <Route path="/add-appointment/:tableId" element={<AddAppointmentPage/>}/>
                <Route path="/make-reservation/:tableId" element={<MakeReservationPage/>}/>
                <Route path="/restaurant-reservations/:restaurantId" element={<RestaurantReservationsPage/>}
                />

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