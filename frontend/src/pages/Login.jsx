import  { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api.jsx';
import {jwtDecode} from "jwt-decode"; // Axios instance

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null); // Clear previous errors

        try {
            const response = await api.post('/user-service/user/auth/login', {
                username: email,
                password,
            });

            console.log('API Response:', response);

            // Extract token directly from response.data
            const token = response?.data;
            if (!token) {
                setError('Token not received from server. Please contact support.');
                console.error('Token Missing:', response?.data);
                return;
            }

            // Save token to localStorage
            localStorage.setItem('jwtToken', token);

            // Decode the token
            const decodedToken = jwtDecode(token);
            console.log('Decoded Token:', decodedToken);

            // Extract and save userId to localStorage
            const userId = decodedToken.userId; // Replace 'id' with the correct field from your token
            if (!userId) {
                setError('Invalid token: User ID not found.');
                console.error('User ID Missing in Token:', decodedToken);
                return;
            }
            localStorage.setItem('userId', userId); // Store the userId

            const role = decodedToken.role;
            if (!role) {
                setError('Invalid token: Role not found.');
                console.error('Role Missing in Token:', decodedToken);
                return;
            }

            // Redirect based on role
            if (role === 'CUSTOMER') {
                navigate('/customer');
            } else if (role === 'MANAGER') {
                navigate('/manager');
            } else if (role === 'ADMIN') {
                navigate('/admin');
            } else {
                setError('Unknown role detected. Contact support.');
                console.error('Unknown Role:', role);
            }
        } catch (err) {
            if (err.response?.status === 401) {
                setError('Invalid username or password.');
            } else {
                setError('Something went wrong. Please try again later.');
            }
            console.error('Login Error:', err);
        }
    };


    // return (
    //     <div className="login-page">
    //         <h2>Login</h2>
    //         <form onSubmit={handleLogin}>
    //             <input
    //                 type="text" // Changed to 'text' to match 'username'
    //                 placeholder="Username"
    //                 value={email}
    //                 onChange={(e) => setEmail(e.target.value)}
    //                 required
    //             />
    //             <input
    //                 type="password"
    //                 placeholder="Password"
    //                 value={password}
    //                 onChange={(e) => setPassword(e.target.value)}
    //                 required
    //             />
    //             <button type="submit">Login</button>
    //         </form>
    //         {error && <p className="error">{error}</p>}
    //     </div>
    // );

    return (
        <div className="login-container">
            <div className="login-card">
                <h2>Login</h2>
                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <label htmlFor="email">Username</label>
                        <input
                            id="email"
                            type="text"
                            placeholder="Enter your username"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input
                            id="password"
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="login-button">Login</button>
                </form>
                {error && <p className="error-message">{error}</p>}
            </div>
        </div>
    );
}

export default Login;