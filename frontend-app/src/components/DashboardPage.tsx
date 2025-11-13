import React from 'react';
import { useAuth } from '../context/AuthContext';
import { Link } from 'react-router-dom';

const DashboardPage = () => {
  const { user, logout } = useAuth();

  return (
    <div>
      <h2>Dashboard</h2>
      {user && (
        <div>
          <p>Welcome, <strong>{user.username}</strong>!</p>
          <p>Your roles: {user.roles.join(', ')}</p>
        </div>
      )}
      <nav>
        <Link to="/register-patient">Register a New Patient</Link>
      </nav>
      <br />
      <button onClick={logout}>Logout</button>
    </div>
  );
};

export default DashboardPage;