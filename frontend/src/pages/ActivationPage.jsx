import {useState} from "react";
import {useNavigate} from "react-router-dom";
import api from "../api.jsx";

function ActivationPage() {
    const [activationCode, setActivationCode] = useState("");
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const navigate = useNavigate();

    // const handleActivation = async (e) => {
    //     e.preventDefault();
    //     setError(null);
    //     setSuccess(null);
    //
    //     try {
    //         const token = localStorage.getItem("jwtToken"); // Get the token from localStorage
    //
    //         if (!token) {
    //             throw new Error("Authorization token is missing. Please log in.");
    //         }
    //
    //         const response = await api.post(
    //             "/user-service/user/register/activation",
    //             activationCode,
    //             {
    //                 headers: {
    //                     Authorization: `Bearer ${token}`,
    //                     "Content-Type": "application/json",
    //                 },
    //             }
    //         );
    //
    //         setSuccess(response.data); // Backend success message
    //         setTimeout(() => navigate("/login"), 2000); // Redirect to login after 2 seconds
    //     } catch (err) {
    //         if (err.response?.data) {
    //             setError(err.response.data); // Show backend error message
    //         } else {
    //             setError("Something went wrong. Please try again later.");
    //         }
    //         console.error("ActivationPage Error:", err);
    //     }
    // };

    // const handleActivation = async (e) => {
    //     e.preventDefault();
    //     setError(null);
    //     setSuccess(null);
    //
    //     try {
    //         const token = localStorage.getItem("jwtToken"); // Get the token from localStorage
    //
    //         if (!token) {
    //             throw new Error("Authorization token is missing. Please log in.");
    //         }
    //
    //         // Send the activation code as a JSON object
    //         const response = await api.post(
    //             "/user-service/user/register/activation",
    //             JSON.stringify({code: activationCode}), // Wrap in an object
    //             {
    //                 headers: {
    //                     Authorization: `Bearer ${token}`,
    //                     "Content-Type": "application/json",
    //                 },
    //             }
    //         );
    //
    //         setSuccess(response.data); // Backend success message
    //         setTimeout(() => navigate("/login"), 2000); // Redirect to login after 2 seconds
    //     } catch (err) {
    //         if (err.response?.data) {
    //             setError(err.response.data); // Show backend error message
    //         } else {
    //             setError("Something went wrong. Please try again later.");
    //         }
    //         console.error("ActivationPage Error:", err);
    //     }
    // };

    const handleActivation = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        try {
            const token = localStorage.getItem("jwtToken");

            if (!token) {
                throw new Error("Authorization token is missing. Please log in.");
            }

            // Send the activation code as a raw string
            const response = await api.post(
                "/user-service/user/register/activation",
                activationCode, // Send raw string
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                }
            );

            setSuccess(response.data);
            setTimeout(() => navigate("/login"), 2000);
        } catch (err) {
            if (err.response?.data) {
                setError(err.response.data);
            } else {
                setError("Something went wrong. Please try again later.");
            }
            console.error("ActivationPage Error:", err);
        }
    };


    return (
        <div className="activation-container">
            <div className="activation-card">
                <h2>Activate Your Account</h2>
                <form onSubmit={handleActivation}>
                    <div className="input-group">
                        <label htmlFor="activationCode">Activation Code</label>
                        <input
                            id="activationCode"
                            type="text"
                            placeholder="Enter your activation code"
                            value={activationCode}
                            onChange={(e) => setActivationCode(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="activation-button">
                        Activate
                    </button>
                </form>
                {error && <p className="error-message">{error}</p>}
                {success && <p className="success-message">{success}</p>}
            </div>
        </div>
    );
}

export default ActivationPage;
