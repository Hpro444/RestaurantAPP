import {useEffect, useState} from 'react';
// import axios from 'axios';
import './ProfilePage.css';
import api from '../api.jsx'; // Import the pre-configured Axios instance

function ProfilePage() {
    const [profile, setProfile] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Assume the user ID is stored in localStorage after login
        const userId = localStorage.getItem('userId');
        const token = localStorage.getItem('jwtToken');

        if (!userId || !token) {
            setError('User ID or token not found. Please log in again.');
            return;
        }

        api.get(`/user-service/user/${userId}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => {
                setProfile(response.data);
            })
            .catch((error) => {
                console.error('Error fetching profile:', error);
                setError('Failed to fetch profile. Please try again later.');
            });

    }, []);

    if (error) {
        return <div className="profile-error">{error}</div>;
    }

    if (!profile) {
        return <div className="profile-loading">Loading profile...</div>;
    }

    return (
        <div className="profile-container">
            <h2>Your Profile</h2>
            <p><strong>First Name:</strong> {profile.firstName}</p>
            <p><strong>Last Name:</strong> {profile.lastName}</p>
            <p><strong>Email:</strong> {profile.email}</p>
            <p><strong>Username:</strong> {profile.username}</p>
        </div>
    );
}

export default ProfilePage;
