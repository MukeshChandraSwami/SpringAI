/**
 * Alternative API service with CORS proxy support
 * This can be used if the main API still has CORS issues
 */

// Alternative solutions for CORS
export const corsProxyOptions = {
  // Option 1: Use a public CORS proxy (for development only)
  corsAnywhere: 'https://cors-anywhere.herokuapp.com/',
  
  // Option 2: Use allorigins proxy (for development only)
  allOrigins: 'https://api.allorigins.win/raw?url=',
  
  // Option 3: Use your own proxy server
  customProxy: '/api'
};

export const fetchWithCorsProxy = async (url, proxyType = 'customProxy') => {
  const proxy = corsProxyOptions[proxyType];
  const proxiedUrl = proxyType === 'customProxy' ? url.replace('http://localhost:8007', proxy) : proxy + encodeURIComponent(url);
  
  try {
    const response = await fetch(proxiedUrl, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        // Add origin header for cors-anywhere
        ...(proxyType === 'corsAnywhere' && {
          'X-Requested-With': 'XMLHttpRequest'
        })
      }
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    return await response.json();
  } catch (error) {
    console.error(`Error with ${proxyType} proxy:`, error);
    throw error;
  }
};

// Test function to check API connectivity
export const testApiConnectivity = async () => {
  const testUrl = 'http://localhost:8007/health'; // Assuming health endpoint exists
  const methods = ['direct', 'customProxy', 'corsAnywhere'];
  
  for (const method of methods) {
    try {
      console.log(`Testing ${method} method...`);
      if (method === 'direct') {
        await fetch(testUrl);
      } else {
        await fetchWithCorsProxy(testUrl, method);
      }
      console.log(`✅ ${method} method works`);
      return method;
    } catch (error) {
      console.log(`❌ ${method} method failed:`, error.message);
    }
  }
  
  throw new Error('All CORS methods failed');
};
