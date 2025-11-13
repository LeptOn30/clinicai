import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1', // Your API Gateway URL
});

// Set the initial token if it exists in local storage
const token = localStorage.getItem('token');
if (token) {
  api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}

// Add a response interceptor to handle global errors
api.interceptors.response.use(
  (response) => response, // Pass through successful responses
  (error) => {
    // Check if the error is a 401 Unauthorized
    if (error.response && error.response.status === 401) {
      // Don't intercept 401s from the login endpoint, as that's expected on bad credentials
      if (!error.config.url.includes('/auth/login')) {
        // Clear the token from storage
        localStorage.removeItem('token');
        // Redirect to the login page
        // This will cause the AuthContext to re-initialize without a token
        window.location.href = '/login';
      }
    }
    // Reject the promise to allow individual components to handle other errors
    return Promise.reject(error);
  }
);

export default api;