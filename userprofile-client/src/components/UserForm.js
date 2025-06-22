import React, { useState } from 'react';
import { decryptData } from '../helper';
import './style.css'; // Add this import for the CSS

function UserForm() {
  const [userId, setUserId] = useState('');
  const [user, setUser] = useState(null);
  const [error, setError] = useState('');

  const fetchUser = async () => {
    try {
      setError('');
      if (!userId.trim()) {
        setError('Please enter a User ID');
        return;
      }
      
      const response = await fetch(`http://localhost:8080/api/users/${userId}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || `Failed to fetch user (${response.status})`);
      }
      
      const responseData = await response.json();
      console.log("Raw server response:", responseData); // Added for debugging
      
      try {
        const userData = JSON.parse(await decryptData(responseData.data));
        setUser(userData);
      } catch (decryptError) {
        console.error('Decryption failed:', decryptError);
        setError('Could not decrypt user data. Please check the encryption configuration.');
      }
    } catch (err) {
      setError(err.message);
      setUser(null);
    }
  };

  return (
    <div className="user-form-container">
      <h1 className="form-title">User Profile</h1>
      <div className="input-container">
        <input
          type="text"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          placeholder="Enter User ID"
          className="user-input"
        />
      </div>
      <button
        onClick={fetchUser}
        className="fetch-button"
      >
        Fetch User
      </button>
      {error && <p className="error-message">{error}</p>}
      {user && (
        <div className="user-info">
          <p><strong>ID:</strong> {user.id}</p>
          <p><strong>Name:</strong> {user.name}</p>
          <p><strong>Email:</strong> {user.email}</p>
        </div>
      )}
    </div>
  );
}

export default UserForm;