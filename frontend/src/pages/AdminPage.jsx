import {useNavigate} from "react-router-dom";
import "./AdminPage.css";

function AdminPage() {
    const navigate = useNavigate();

    return (
        <div className="admin-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => navigate("/admin")}>Home</button>
                    <button onClick={() => navigate("/show-all-restaurants-admin")}>
                        Show All Restaurants
                    </button>
                    <button onClick={() => navigate("/show-all-reservations")}>
                        Show All Reservations
                    </button>
                    <button onClick={() => navigate("/notifications")}>
                        View All Notifications
                    </button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate("/profile")}
                >
                    Profile
                </button>
            </header>
            <div className="content">
                <div className="main-dashboard">
                    <h1>Welcome, Admin!</h1>
                    <p>This is your administrative dashboard.</p>
                    <p>
                        Use the navigation buttons above to manage restaurants,
                        reservations, and notifications.
                    </p>
                </div>
            </div>
        </div>
    );
}

export default AdminPage;
