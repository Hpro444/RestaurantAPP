import {useState} from "react";
import {useNavigate} from "react-router-dom";
import "./CustomerPage.css";

function CustomerPage() {
    const [currentPage, setCurrentPage] = useState("main");
    const navigate = useNavigate();

    const renderPage = () => {
        switch (currentPage) {
            case "main":
                return (
                    <div className="main-dashboard">
                        <h1>Welcome!</h1>
                        <p>We have <strong>a lot of</strong> restaurants available for you.</p>
                        <p>You currently have <strong>a lot of</strong> reservations in the system.</p>
                    </div>
                );

            case "showAllRestaurants":
                navigate("/show-all-restaurants");
                break;

            case "showAllReservations":
                navigate("/user-reservations");
                break;

            default:
                return null;
        }
    };

    return (
        <div className="customer-page">
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => setCurrentPage("main")}>Home</button>
                    <button onClick={() => setCurrentPage("showAllRestaurants")}>
                        Show All Restaurants
                    </button>
                    <button onClick={() => setCurrentPage("showAllReservations")}>
                        Show All Reservations
                    </button>
                </div>
                <button
                    className="profile-button"
                    onClick={() => navigate("/profile")}
                >
                    Profile
                </button>
            </header>
            <div className="content">{renderPage()}</div>
        </div>
    );
}

export default CustomerPage;
