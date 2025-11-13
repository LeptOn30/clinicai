import React, { createContext, useState, useContext, ReactNode, useEffect } from 'react';
import api from '../api/api';
import { jwtDecode } from 'jwt-decode';

// Define the structure of the decoded JWT payload
interface DecodedToken {
  sub: string; // Subject (username)
  roles: string;
  iat: number;
  exp: number;
}

// Define the structure for the user object we'll use in our app
interface User {
  username: string;
  roles: string[];
}

interface AuthContextType {
  token: string | null;
  user: User | null;
  login: (token: string) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(localStorage.getItem('token'));
  const [user, setUser] = useState<User | null>(null);

  // This effect handles token decoding, validation, and automatic logout scheduling.
  useEffect(() => {
    let logoutTimer: NodeJS.Timeout;

    if (token) {
      try {
        const decodedToken: DecodedToken = jwtDecode(token);

        // The 'exp' claim is in seconds, so we multiply by 1000 for milliseconds
        if (decodedToken.exp * 1000 < Date.now()) {
          // Token is already expired
          logout();
        } else {
          // Token is valid, set user state
          setUser({
            username: decodedToken.sub,
            roles: decodedToken.roles.split(','),
          });

          // Calculate remaining time and set a timer to log out
          const expiresIn = decodedToken.exp * 1000 - Date.now();
          logoutTimer = setTimeout(() => {
            logout();
          }, expiresIn);
        }
      } catch (error) {
        console.error("Failed to decode token on initial load", error);
        logout(); // Clear invalid token
      }
    }
    // Cleanup function to clear the timer if the component unmounts or token changes
    return () => clearTimeout(logoutTimer);
  }, [token]);

  const login = (newToken: string) => {
    setToken(newToken);
    localStorage.setItem('token', newToken);
    api.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
    // The useEffect will automatically handle decoding the new token
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem('token');
    delete api.defaults.headers.common['Authorization'];
  };

  const isAuthenticated = !!token;

  return (
    <AuthContext.Provider value={{ token, user, login, logout, isAuthenticated }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};