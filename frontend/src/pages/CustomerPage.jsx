import { useState } from 'react';
import './CustomerPage.css';

const fakeRestaurants = [
    {
        managerId: 1,
        manager_email: "manager1@example.com",
        name: "The Gourmet Spot",
        description: "A fine dining experience.",
        openingTime: "09:00",
        closingTime: "22:00",
        kitchenType: "Italian",
        address: "123 Gourmet Lane, Food City"
    },
    {
        managerId: 2,
        manager_email: "manager2@example.com",
        name: "Sushi Palace",
        description: "Fresh sushi and sashimi.",
        openingTime: "11:00",
        closingTime: "23:00",
        kitchenType: "Japanese",
        address: "456 Sushi Street, Ocean City"
    },
    {
        managerId: 3,
        manager_email: "manager3@example.com",
        name: "Taco Fiesta",
        description: "Authentic Mexican tacos.",
        openingTime: "10:00",
        closingTime: "21:00",
        kitchenType: "Mexican",
        address: "789 Taco Ave, Fiesta Town"
    }
];

const fakeReservations = [
    {
        reservationId: 101,
        restaurantName: "The Gourmet Spot",
        customerName: "John Doe",
        date: "2023-12-15",
        time: "19:00",
        guests: 2
    },
    {
        reservationId: 102,
        restaurantName: "Sushi Palace",
        customerName: "Jane Smith",
        date: "2023-12-16",
        time: "20:30",
        guests: 4
    },
    {
        reservationId: 103,
        restaurantName: "Taco Fiesta",
        customerName: "Alice Brown",
        date: "2023-12-17",
        time: "18:00",
        guests: 3
    }
];

const fakeProfile = {
    firstName: "John",
    lastName: "Doe",
    email: "johndoe@example.com",
    username: "johndoe",
    role: "CUSTOMER",
    status: "ACTIVE"
};

function CustomerPage() {
    const [currentPage, setCurrentPage] = useState("restaurants");

    const renderPage = () => {
        switch (currentPage) {
            case "restaurants":
                return (
                    <div>
                        <h2>Available Restaurants</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Kitchen Type</th>
                                <th>Address</th>
                                <th>Opening Time</th>
                                <th>Closing Time</th>
                            </tr>
                            </thead>
                            <tbody>
                            {fakeRestaurants.map((restaurant, index) => (
                                <tr key={index}>
                                    <td>{restaurant.name}</td>
                                    <td>{restaurant.description}</td>
                                    <td>{restaurant.kitchenType}</td>
                                    <td>{restaurant.address}</td>
                                    <td>{restaurant.openingTime}</td>
                                    <td>{restaurant.closingTime}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                );
            case "reservations":
                return (
                    <div>
                        <h2>Your Reservations</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>Restaurant</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Guests</th>
                            </tr>
                            </thead>
                            <tbody>
                            {fakeReservations.map((reservation, index) => (
                                <tr key={index}>
                                    <td>{reservation.restaurantName}</td>
                                    <td>{reservation.date}</td>
                                    <td>{reservation.time}</td>
                                    <td>{reservation.guests}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                );
            case "profile":
                return (
                    <div>
                        <h2>Your Profile</h2>
                        <p><strong>First Name:</strong> {fakeProfile.firstName}</p>
                        <p><strong>Last Name:</strong> {fakeProfile.lastName}</p>
                        <p><strong>Email:</strong> {fakeProfile.email}</p>
                        <p><strong>Username:</strong> {fakeProfile.username}</p>
                        <p><strong>Role:</strong> {fakeProfile.role}</p>
                        <p><strong>Status:</strong> {fakeProfile.status}</p>
                    </div>
                );
            default:
                return <p>Select a page to view.</p>;
        }
    };

    return (
        <div>
            <header className="header">
                <div className="nav-buttons">
                    <button onClick={() => setCurrentPage("restaurants")}>Restaurants</button>
                    <button onClick={() => setCurrentPage("reservations")}>Reservations</button>
                </div>
                <button className="profile-button" onClick={() => setCurrentPage("profile")}>
                    Profile
                </button>
            </header>
            <div className="content">{renderPage()}</div>
        </div>
    );
}

export default CustomerPage;
