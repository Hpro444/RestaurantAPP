import {useState} from "react";
import {useNavigate} from "react-router-dom";
import api from "../api.jsx";

function Registration() {
    const [formData, setFormData] = useState({
        username: "",
        password: "",
        email: "",
        firstName: "",
        lastName: "",
        role: "CUSTOMER", // Default role
        birthDate: "",
        address: {
            street: "",
            city: "",
            state: "",
            zip: "",
        },
    });
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const navigate = useNavigate();

    const handleChange = (e) => {
        const {name, value} = e.target;
        if (name.startsWith("address.")) {
            const addressField = name.split(".")[1];
            setFormData((prevData) => ({
                ...prevData,
                address: {
                    ...prevData.address,
                    [addressField]: value,
                },
            }));
        } else {
            setFormData((prevData) => ({
                ...prevData,
                [name]: value,
            }));
        }
    };

    // const handleSubmit = async (e) => {
    //     e.preventDefault();
    //     setError(null);
    //     setSuccess(null);
    //
    //     try {
    //         const endpoint =
    //             formData.role === "CUSTOMER"
    //                 ? "/user-service/user/auth/register/customer"
    //                 : "/user-service/user/auth/register/manager";
    //
    //         const response = await api.post(endpoint, formData);
    //
    //         setSuccess("Registration successful! Redirecting to login...");
    //         // setTimeout(() => navigate("/login"), 2000); // Redirect after 2 seconds
    //         setTimeout(() => navigate("/activate"), 3000); // Redirect to activation page after 3 seconds
    //
    //     } catch (err) {
    //         if (err.response?.data?.message) {
    //             setError(err.response.data.message);
    //         } else {
    //             setError("Something went wrong. Please try again later.");
    //         }
    //         console.error("Registration Error:", err);
    //     }
    // };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        try {
            const endpoint =
                formData.role === "CUSTOMER"
                    ? "/user-service/user/auth/register/customer"
                    : "/user-service/user/auth/register/manager";

            const response = await api.post(endpoint, formData);

            // Store the new token in localStorage
            localStorage.setItem("jwtToken", response.data);

            setSuccess("Registration successful! Redirecting to activation...");
            setTimeout(() => navigate("/activate"), 2000); // Redirect after 2 seconds
        } catch (err) {
            if (err.response?.data?.message) {
                setError(err.response.data.message);
            } else {
                setError("Something went wrong. Please try again later.");
            }
            console.error("Registration Error:", err);
        }
    };

    return (
        <div className="registration-container">
            <div className="registration-card">
                <h2>Register</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <label htmlFor="username">Username</label>
                        <input
                            id="username"
                            name="username"
                            type="text"
                            placeholder="Enter your username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="email">Email</label>
                        <input
                            id="email"
                            name="email"
                            type="email"
                            placeholder="Enter your email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input
                            id="password"
                            name="password"
                            type="password"
                            placeholder="Enter your password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="firstName">First Name</label>
                        <input
                            id="firstName"
                            name="firstName"
                            type="text"
                            placeholder="Enter your first name"
                            value={formData.firstName}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="lastName">Last Name</label>
                        <input
                            id="lastName"
                            name="lastName"
                            type="text"
                            placeholder="Enter your last name"
                            value={formData.lastName}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="birthDate">Birth Date</label>
                        <input
                            id="birthDate"
                            name="birthDate"
                            type="date"
                            value={formData.birthDate}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="role">Role</label>
                        <select
                            id="role"
                            name="role"
                            value={formData.role}
                            onChange={handleChange}
                        >
                            <option value="CUSTOMER">Customer</option>
                            <option value="MANAGER">Manager</option>
                        </select>
                    </div>
                    <div className="input-group">
                        <label htmlFor="street">Street</label>
                        <input
                            id="street"
                            name="address.street"
                            type="text"
                            placeholder="Enter your street"
                            value={formData.address.street}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="city">City</label>
                        <input
                            id="city"
                            name="address.city"
                            type="text"
                            placeholder="Enter your city"
                            value={formData.address.city}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="state">State</label>
                        <input
                            id="state"
                            name="address.state"
                            type="text"
                            placeholder="Enter your state"
                            value={formData.address.state}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="zip">ZIP Code</label>
                        <input
                            id="zip"
                            name="address.zip"
                            type="text"
                            placeholder="Enter your ZIP code"
                            value={formData.address.zip}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <button type="submit" className="register-button">Register</button>
                </form>
                {error && <p className="error-message">{error}</p>}
                {success && <p className="success-message">{success}</p>}
            </div>
        </div>
    );
}

export default Registration;
