import React from 'react';
import { AlertCircle, RefreshCw } from 'lucide-react';
import './ErrorMessage.css';

const ErrorMessage = ({ message, onRetry }) => {
  return (
    <div className="error-container">
      <AlertCircle size={48} className="error-icon" />
      <h3>Oops! Something went wrong</h3>
      <p className="error-message">{message}</p>
      <button className="retry-button" onClick={onRetry}>
        <RefreshCw size={16} />
        Try Again
      </button>
      <div className="error-note">
        <p><strong>Note:</strong> Make sure the API server is running on localhost:8007</p>
        <p>You can use mock data by clicking "Try Again" to see the preview functionality.</p>
      </div>
    </div>
  );
};

export default ErrorMessage;
