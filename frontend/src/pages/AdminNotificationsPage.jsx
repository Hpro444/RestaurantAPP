import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./AdminNotificationsPage.css";
import api from "../api.jsx";

function AdminNotificationsPage() {
    const navigate = useNavigate(); // If you want to redirect to /login, for example
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchNotifications();
        // eslint-disable-next-line
    }, []);

    const fetchNotifications = async () => {
        setLoading(true);
        setError(null);

        // 1. Grab the token from localStorage
        const token = localStorage.getItem("jwtToken");
        console.log("Token from localStorage:", token);

        if (!token) {
            // 2. If there's no token, set an error (or redirect)
            setError("No token found. Please log in as an ADMIN first.");
            setLoading(false);
            // Optionally redirect user to a login page:
            // navigate("/login");
            return;
        }

        try {
            // 3. Attempt to fetch notifications
            const response = await api.get(
                "/notification-service/notifications/all-notifications",
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            setNotifications(response.data);
        } catch (err) {
            console.error("Error fetching notifications:", err);
            // 4. If we got a 401, it means the token is invalid, expired, or missing ADMIN role
            if (err?.response?.status === 401) {
                setError("Unauthorized (401). Your token may be invalid or expired.");
                // Optionally clear token and redirect
                // localStorage.removeItem("jwtToken");
                // navigate("/login");
            } else {
                setError("Failed to load notifications. Please try again later.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="admin-notifications-page">
            <header className="header">
                <h2>All Notifications</h2>
            </header>
            <div className="content">
                {loading && <p>Loading notifications...</p>}
                {error && <p className="error-message">{error}</p>}
                {!loading && !error && notifications.length === 0 && (
                    <p>No notifications found.</p>
                )}
                {!loading && !error && notifications.length > 0 && (
                    <table className="notifications-table">
                        <thead>
                        <tr>
                            <th>Notification ID</th>
                            <th>Type</th>
                            <th>Email</th>
                            <th>Message</th>
                            <th>Sent At</th>
                        </tr>
                        </thead>
                        <tbody>
                        {notifications.map((notification) => (
                            <tr key={notification.id}>
                                <td>{notification.id}</td>
                                <td>{notification.type}</td>
                                <td>{notification.email}</td>
                                <td>{notification.message}</td>
                                <td>
                                    {new Date(notification.sentAt).toLocaleString()}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}

export default AdminNotificationsPage;
