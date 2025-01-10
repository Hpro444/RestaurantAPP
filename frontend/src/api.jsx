import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8084', // Replace with your API Gateway's URL
});

export default api;
