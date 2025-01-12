import {useNavigate} from 'react-router-dom';
import './Header.css';

function Header() {
    const navigate = useNavigate();

    return (
        <header className="header">
            <div className="nav-buttons">
                <button onClick={() => navigate('/manager')}>Home</button>
                <button onClick={() => navigate('/managed-restaurants')}>Managed Restaurants</button>
                <button onClick={() => navigate('/add-restaurant')}>Add Restaurant</button>
            </div>
            <button
                className="profile-button"
                onClick={() => navigate('/profile')}
            >
                Profile
            </button>
        </header>
    );
}

export default Header;
