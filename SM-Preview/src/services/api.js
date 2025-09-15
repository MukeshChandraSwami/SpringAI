/**
 * API service for fetching personalized posts
 */

// Use proxy in development, direct URL in production
const API_BASE_URL = import.meta.env.DEV ? '/api' : 'http://localhost:8007';

export const fetchPersonalizedPosts = async (accountId, eventId, attendeeId) => {
  try {
    const response = await fetch(
      `${API_BASE_URL}/event-marketing/v1/acct/${accountId}/event/${eventId}/attendee/${attendeeId}/post`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
        mode: 'cors', // Explicitly set CORS mode
      }
    );
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching personalized posts:', error);
    // Check if it's a CORS error
    if (error.name === 'TypeError' && error.message.includes('fetch')) {
      throw new Error('CORS error: Unable to connect to API server. Please ensure the server is running and CORS is configured properly.');
    }
    throw error;
  }
};

// Default parameters for testing
export const defaultParams = {
  accountId: 'edcca975-3003-4623-9cfb-aead0ae40639',
  eventId: 'fdcca975-3003-4623-9cfb-aead0ae40639',
  attendeeId: '03aca985-3003-4623-9cfb-aead0ae40647'
};
