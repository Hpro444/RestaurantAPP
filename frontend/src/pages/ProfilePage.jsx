import {useEffect, useState} from 'react';
import './ProfilePage.css';
import api from '../api.jsx'; // Pre-configured Axios instance


function ProfilePage() {
    const [profile, setProfile] = useState(null);
    const [error, setError] = useState(null);
    const [isEditing, setIsEditing] = useState(false); // Edit mode toggle
    const [updatedProfile, setUpdatedProfile] = useState({}); // Temporary state for editing

    useEffect(() => {
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
                setUpdatedProfile(response.data); // Initialize editable state
            })
            .catch((error) => {
                console.error('Error fetching profile:', error);
                setError('Failed to fetch profile. Please try again later.');
            });
    }, []);

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setUpdatedProfile((prev) => ({...prev, [name]: value}));
    };

    const handleAddressChange = (e) => {
        const {name, value} = e.target;
        setUpdatedProfile((prev) => ({
            ...prev,
            address: {
                ...prev.address,
                [name]: value,
            },
        }));
    };

    const handleSave = () => {
        const userId = localStorage.getItem('userId');
        const token = localStorage.getItem('jwtToken');

        api.put(`/user-service/api/customers/${userId}`, updatedProfile, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(() => {
                setProfile(updatedProfile); // Update the profile with the new data
                setIsEditing(false); // Exit edit mode
                alert('Profile updated successfully.');
            })
            .catch((error) => {
                console.error('Error updating profile:', error);
                setError('Failed to update profile. Please try again later.');
            });
    };

    if (error) {
        return <div className="profile-error">{error}</div>;
    }

    if (!profile) {
        return <div className="profile-loading">Loading profile...</div>;
    }

    return (
        <div className="profile-container">
            <div className="profile-frame">
                <h2>Your Profile</h2>
                {isEditing ? (
                    <div className="profile-edit-mode">
                        <div>
                            <label>First Name:</label>
                            <input
                                type="text"
                                name="firstName"
                                value={updatedProfile.firstName || ''}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div>
                            <label>Last Name:</label>
                            <input
                                type="text"
                                name="lastName"
                                value={updatedProfile.lastName || ''}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div>
                            <label>Email:</label>
                            <input
                                type="email"
                                name="email"
                                value={updatedProfile.email || ''}
                                readOnly
                            />
                        </div>
                        <div>
                            <label>Username:</label>
                            <input
                                type="text"
                                name="username"
                                value={updatedProfile.username || ''}
                                readOnly
                            />
                        </div>
                        <div>
                            <label>Street:</label>
                            <input
                                type="text"
                                name="street"
                                value={updatedProfile.address?.street || ''}
                                onChange={handleAddressChange}
                            />
                        </div>
                        <div>
                            <label>City:</label>
                            <input
                                type="text"
                                name="city"
                                value={updatedProfile.address?.city || ''}
                                onChange={handleAddressChange}
                            />
                        </div>
                        <div>
                            <label>State:</label>
                            <input
                                type="text"
                                name="state"
                                value={updatedProfile.address?.state || ''}
                                onChange={handleAddressChange}
                            />
                        </div>
                        <div>
                            <label>Zip:</label>
                            <input
                                type="text"
                                name="zip"
                                value={updatedProfile.address?.zip || ''}
                                onChange={handleAddressChange}
                            />
                        </div>
                        <div>
                            <label>Birth Date:</label>
                            <input
                                type="date"
                                name="birthDate"
                                value={updatedProfile.birthDate || ''}
                                onChange={handleInputChange}
                            />
                        </div>
                        <button className="save-button" onClick={handleSave}>
                            Save
                        </button>
                        <button
                            className="cancel-button"
                            onClick={() => setIsEditing(false)}
                        >
                            Cancel
                        </button>
                    </div>
                ) : (
                    <div className="profile-view-mode">
                        <p>
                            <strong>First Name:</strong> {profile.firstName}
                        </p>
                        <p>
                            <strong>Last Name:</strong> {profile.lastName}
                        </p>
                        <p>
                            <strong>Email:</strong> {profile.email}
                        </p>
                        <p>
                            <strong>Username:</strong> {profile.username}
                        </p>
                        <p>
                            <strong>Address:</strong>{' '}
                            {profile.address
                                ? `${profile.address.street}, ${profile.address.city}, ${profile.address.state}, ${profile.address.zip}`
                                : 'N/A'}
                        </p>
                        <p>
                            <strong>Birth Date:</strong>{' '}
                            {profile.birthDate ? profile.birthDate : 'N/A'}
                        </p>
                        <button
                            className="edit-button"
                            onClick={() => setIsEditing(true)}
                        >
                            Edit
                        </button>
                    </div>
                )}
            </div>
        </div>
    );
}

export default ProfilePage;
