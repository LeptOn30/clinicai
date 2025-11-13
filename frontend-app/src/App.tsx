import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import DashboardPage from './components/DashboardPage';
import PrivateRoute from './components/PrivateRoute';
import PatientRegistrationPage from './components/PatientRegistrationPage';

function App() {
  return (
    <div className="app-container">
      <h1>Clinic Administration System</h1>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<PrivateRoute><DashboardPage /></PrivateRoute>} />
        <Route path="/register-patient" element={<PrivateRoute><PatientRegistrationPage /></PrivateRoute>} />

        {/* Default route */}
        <Route path="*" element={<Navigate to="/dashboard" />} />
      </Routes>
    </div>
  );
}

export default App;