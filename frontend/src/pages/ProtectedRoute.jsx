// import { Navigate } from 'react-router-dom';
// import jwtDecode from 'jwt-decode';
//
// // eslint-disable-next-line react/prop-types
// function ProtectedRoute({ role, children }) {
//     const token = localStorage.getItem('jwtToken');
//     if (!token) {
//         return <Navigate to="/login" />;
//     }
//
//     const decodedToken = jwtDecode(token);
//     if (decodedToken.role !== role) {
//         return <Navigate to="/" />;
//     }
//
//     return children;
// }
//
// export default ProtectedRoute;
