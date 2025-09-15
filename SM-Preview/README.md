# Social Media Post Preview App

A modern React application that fetches personalized social media posts from an API and displays beautiful previews of how they would appear on LinkedIn, X (Twitter), and Facebook.

## Features

- 🎨 **Beautiful UI**: Modern, responsive design with professional styling
- 📱 **3-Column Layout**: Side-by-side previews for LinkedIn, X, and Facebook
- 🔄 **Real-time API Integration**: Fetches data from your marketing API
- 📤 **Share Simulation**: Interactive share buttons for each platform
- 🎯 **Mock Data Fallback**: Automatic fallback to demo data if API is unavailable
- 📱 **Responsive Design**: Works perfectly on desktop, tablet, and mobile

## API Integration

The app connects to your event marketing API:
```
GET http://localhost:8007/event-marketing/v1/acct/{accountId}/event/{eventId}/attendee/{attendeeId}/post
```

Default test parameters are included in the code for easy development.

## Quick Start

1. **Install dependencies**:
   ```bash
   npm install
   ```

2. **Start the development server**:
   ```bash
   npm run dev
   ```

3. **Open your browser**: Navigate to `http://localhost:5173`

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## Technologies Used

- **React 19** - Latest React with modern hooks
- **Vite** - Fast build tool and dev server
- **Lucide React** - Beautiful, customizable icons
- **Styled Components** - Component-level styling
- **CSS Grid & Flexbox** - Modern responsive layouts

## Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── LinkedInPreview.jsx    # LinkedIn post preview
│   ├── XPreview.jsx           # X (Twitter) post preview
│   ├── FacebookPreview.jsx    # Facebook post preview
│   ├── LoadingSpinner.jsx     # Loading state component
│   ├── ErrorMessage.jsx       # Error handling component
│   └── SocialPreview.css      # Shared styling
├── services/            # API integration
│   └── api.js          # API service functions
├── App.jsx             # Main application component
├── App.css             # Application styles
├── index.css           # Global styles
└── main.jsx            # Application entry point
```

## API Response Format

The app expects the following JSON response format:

```json
{
  "success": true,
  "responseMsg": "Personalized post contents fetched successfully",
  "responseCode": 200,
  "personalizedPost": {
    "posts": {
      "linkedin": [
        {
          "id": "unique-post-id",
          "channel": "linkedin",
          "imageUrls": ["fallback-image.png"],
          "title": "Post Title",
          "description": "Post content with hashtags..."
        }
      ]
    }
  }
}
```

## Development Features

- **Hot Module Replacement (HMR)**: Instant updates during development
- **ESLint Integration**: Code quality and consistency
- **Error Boundaries**: Graceful error handling
- **Accessibility**: WCAG-compliant design patterns
- **Performance Optimized**: Lazy loading and efficient rendering

## Browser Support

- Modern browsers (Chrome, Firefox, Safari, Edge)
- Mobile browsers (iOS Safari, Chrome Mobile)
- Responsive design for all screen sizes

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.
