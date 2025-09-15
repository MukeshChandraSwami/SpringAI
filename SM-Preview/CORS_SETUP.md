# CORS Setup Guide

## What is CORS?
Cross-Origin Resource Sharing (CORS) is a security feature implemented by web browsers that restricts web pages from making requests to a different domain, protocol, or port than the one serving the web page.

## Why are you getting CORS errors?
Your React app (running on `http://localhost:5173`) is trying to make requests to your API server (`http://localhost:8007`). Since these are different ports, the browser blocks the request unless the API server explicitly allows it.

## Solutions Implemented

### 1. Vite Proxy (Recommended for Development)
We've configured a Vite proxy in `vite.config.js` that forwards API requests:
- Requests to `/api/*` are forwarded to `http://localhost:8007`
- This eliminates CORS issues during development

### 2. Enhanced Error Handling
The API service now provides better error messages for CORS issues.

### 3. Mock Data Fallback
If the API is unavailable, the app automatically falls back to mock data.

## How to Fix CORS on Your API Server

### Option 1: Enable CORS in Your API Server
Add CORS headers to your API server responses:

```javascript
// Express.js example
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', 'http://localhost:5173');
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
  res.header('Access-Control-Allow-Headers', 'Content-Type, Authorization');
  next();
});
```

### Option 2: Use CORS Middleware (Express.js)
```bash
npm install cors
```

```javascript
const cors = require('cors');
app.use(cors({
  origin: ['http://localhost:5173', 'http://localhost:3000'],
  credentials: true
}));
```

### Option 3: Disable CORS in Browser (Development Only)
Start Chrome with disabled security (NOT RECOMMENDED):
```bash
# macOS/Linux
google-chrome --disable-web-security --user-data-dir="/tmp/chrome_dev"

# Windows
chrome.exe --disable-web-security --user-data-dir="c:\temp\chrome_dev"
```

## Testing Your Setup

1. **Start your API server** on `http://localhost:8007`
2. **Start this React app** with `npm run dev`
3. **Check the browser console** for any CORS errors
4. **Use the network tab** to see if requests are going through the proxy

## Production Considerations

- The Vite proxy only works in development
- For production, ensure your API server has proper CORS configuration
- Consider using environment variables for API URLs:
  ```javascript
  const API_URL = process.env.VITE_API_URL || 'http://localhost:8007';
  ```

## Alternative Solutions

If you're still having issues, check out `src/services/corsHelper.js` for additional proxy options.
