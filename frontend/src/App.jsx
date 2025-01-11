
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import './App.css';
import Login from './pages/Login.jsx'; // Import the Login component
import CustomerPage from './pages/CustomerPage.jsx';
import ManagerPage from './pages/ManagerPage.jsx';
import AdminPage from './pages/AdminPage.jsx';
import ProfilePage from './pages/ProfilePage.jsx';

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
                <Route path="/manager" element={<ManagerPage />} />
                <Route path="/admin" element={<AdminPage />} />
                <Route path="/profile" element={<ProfilePage />} />
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