import React, { useState, useEffect } from 'react';
import { fetchPersonalizedPosts, defaultParams } from './services/api';
import LinkedInPreview from './components/LinkedInPreview';
import XPreview from './components/XPreview';
import FacebookPreview from './components/FacebookPreview';
import LoadingSpinner from './components/LoadingSpinner';
import ErrorMessage from './components/ErrorMessage';
import './App.css';

// Mock data for when API is not available
const mockData = {
  "success": true,
  "responseMsg": "Personalized post contents fetched successfully",
  "responseCode": 200,
  "personalizedPost": {
    "posts": {
      "twitter": [
        {
          "id": "317bc218-21d1-4178-ac1f-136617e31523",
          "channel": "twitter",
          "imageUrls": ["fallback-image.png"],
          "title": "Mukesh Swami at AI Evaluation Summit 2025",
          "description": "I'm Mukesh Swami, Senior Software Engineer at Cvent India. Attending on 10 Nov 2025, 15:00 IST. Excited to dive into AI-first search evaluation—beyond keywords. See you there! #AI #AIEvaluation #TechConference #Cvent"
        },
        {
          "id": "916940d2-22c1-4d84-8a17-957932231ef5",
          "channel": "twitter",
          "imageUrls": ["fallback-image.png"],
          "title": "From Keywords to Context in AI Evaluation",
          "description": "Two sessions I'm attending: Measuring Accuracy Beyond Keywords and Trust, Transparency & Human-Centered Evaluation. Ready to discuss practical benchmarks and responsible AI. Join us: register now. #AI #Ethics #AIEvaluation"
        },
        {
          "id": "0e3df83e-0734-4567-a209-2489ae33c5cb",
          "channel": "twitter",
          "imageUrls": ["fallback-image.png"],
          "title": "Trustworthy AI Search Preview",
          "description": "Focusing on transparency, bias detection, and explainability at the AI Evaluation Summit 2025. Let's connect if you care about reliable AI search. 10 Nov 2025, 15:00 IST. #AI #AIEvaluation #ResponsibleAI"
        }
      ],
      "facebook": [
        {
          "id": "2ab3e36d-9987-4d9b-81ce-9ce6dd1ddd61",
          "channel": "facebook",
          "imageUrls": ["fallback-image.png"],
          "title": "Mukesh Swami's Journey at AI Evaluation Summit 2025",
          "description": "Hey everyone! I'm Mukesh Swami, Senior Software Engineer at Cvent India PVT LTD, excited to attend the AI Evaluation Summit 2025 on 10-Nov-2025 at 15:00 IST. I'll be joining two sessions: 'Measuring Accuracy Beyond Keywords: Evaluation in AI-First Search' and 'Trust, Transparency & Human-Centered Evaluation'. We'll explore how to benchmark AI search beyond traditional metrics, including factual correctness, contextual relevance, bias detection, and transparency. If you're curious about building trustworthy AI-powered search, let's connect here and on the ground. Are you attending? Tell me what you're hoping to learn or share. Don't forget to like, comment, and invite friends to join! #AI #AIEvaluation #TechConference #Cvent"
        },
        {
          "id": "04d90ff4-6b17-4d73-b5c3-92920ecf10a3",
          "channel": "facebook",
          "imageUrls": ["fallback-image.png"],
          "title": "Learning Together: AI Evaluation Summit 2025",
          "description": "Hi everyone, I'm Mukesh Swami from Cvent India. I'm excited to attend the AI Evaluation Summit 2025 to explore new evaluation methods for AI-driven search. I'm especially looking forward to sessions on measuring accuracy beyond keywords and human-centered evaluation. If you work in AI or product development, this is a great place to connect, ask questions, and share experiences. Will you be there? Comment below with what you hope to learn or a topic you want discussed. Let's grow our skills together. #AI #AIEvaluation #TechConference #Cvent"
        },
        {
          "id": "553b4019-bda7-4d9a-b96e-e12530f58bc3",
          "channel": "facebook",
          "imageUrls": ["fallback-image.png"],
          "title": "Two Sessions Shaping the Future of AI Search",
          "description": "Representing Cvent India at the AI Evaluation Summit 2025, I'm eager to dive into practical benchmarks for AI search and the ethics of transparency. If you're in the area, come say hi and tell me which session interests you most: Measuring Accuracy Beyond Keywords or Trust, Transparency & Human-Centered Evaluation. See you there on 10-Nov-2025 at 15:00 IST. #AI #AIEvaluation #Ethics #TechConference #Cvent"
        }
      ],
      "linkedin": [
        {
          "id": "243517cc-1948-4f2b-ae58-aaeda1535866",
          "channel": "linkedin",
          "imageUrls": ["fallback-image.png"],
          "title": "Rethinking Search at AI Evaluation Summit 2025",
          "description": "Thrilled to attend AI Evaluation Summit 2025: Rethinking Search & Responses on Nov 10 (15:00 IST). I'm Mukesh Swami, Senior Software Engineer at Cvent India PVT LTD, exploring rigorous ways to evaluate AI search.\n\nOn my agenda:\n- Measuring Accuracy Beyond Keywords: Evaluation in AI-First Search (factuality, contextual relevance, ambiguity handling, bias detection, benchmarking).\n- Trust, Transparency & Human-Centered Evaluation (explainability, responsible AI, transparency standards, user-first metrics).\n\nLet's connect to trade insights on frameworks, metrics, and real-world practices. Register now and be part of the conversation. #AI #Search #Evaluation #InformationRetrieval #ResponsibleAI #AIEthics #MLOps"
        },
        {
          "id": "948b5c98-3fbd-488b-a6a8-f86a5a23d4c4",
          "channel": "linkedin",
          "imageUrls": ["fallback-image.png"],
          "title": "Counting down to AI Evaluation Summit 2025!",
          "description": "I'm excited to attend \"AI Evaluation Summit 2025: Rethinking Search & Responses\" on Nov 10 at 15:00 IST! I'm Mukesh Swami (Senior Software Engineer, Cvent India PVT LTD), and I'm especially looking forward to:\n• Measuring Accuracy Beyond Keywords — testing factuality, context, bias, and handling ambiguity in AI-first search.\n• Trust, Transparency & Human-Centered Evaluation — building explainable, ethical, user-first AI results.\n\nWho else is going? What session are you most curious about—metrics, transparency, or real-world benchmarking? Comment below or DM to connect on-site! #AICommunity #ResponsibleAI #Search #TechCommunity"
        },
        {
          "id": "ceca4bf2-12c3-4d9b-b3e2-046d89bf6003",
          "channel": "linkedin",
          "imageUrls": ["fallback-image.png"],
          "title": "See you at AI Evaluation Summit 2025",
          "description": "Heading to AI Evaluation Summit 2025 on Nov 10, 15:00 IST. Agenda: accuracy beyond keywords + trust & transparency in AI search. Join me there! #AI #Search #Evaluation #ResponsibleAI #GenAI #TechTwitter"
        }
      ]
    }
  }
};

function App() {
  const [posts, setPosts] = useState({ linkedin: [], twitter: [], facebook: [] });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPostIndex, setCurrentPostIndex] = useState(0);
  const [useMockData, setUseMockData] = useState(false);

  const loadPosts = async () => {
    setLoading(true);
    setError(null);
    
    try {
      let data;
      if (useMockData) {
        // Simulate API delay
        await new Promise(resolve => setTimeout(resolve, 1000));
        data = mockData;
      } else {
        data = await fetchPersonalizedPosts(
          defaultParams.accountId,
          defaultParams.eventId,
          defaultParams.attendeeId
        );
      }
      
      if (data.success && data.personalizedPost?.posts) {
        const { posts: platformPosts } = data.personalizedPost;
        setPosts({
          linkedin: platformPosts.linkedin || [],
          twitter: platformPosts.twitter || [],
          facebook: platformPosts.facebook || []
        });
      } else {
        throw new Error('Invalid response format');
      }
    } catch (err) {
      console.error('Error loading posts:', err);
      setError(err.message);
      // Automatically switch to mock data if API fails
      if (!useMockData) {
        setUseMockData(true);
        return; // This will trigger useEffect again with mock data
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPosts();
  }, [useMockData]);

  const handleRetry = () => {
    setUseMockData(true);
    loadPosts();
  };

  // Get the maximum number of posts across all platforms
  const maxPosts = Math.max(posts.linkedin.length, posts.twitter.length, posts.facebook.length);

  const nextPost = () => {
    setCurrentPostIndex((prev) => (prev + 1) % maxPosts);
  };

  const prevPost = () => {
    setCurrentPostIndex((prev) => (prev - 1 + maxPosts) % maxPosts);
  };

  if (loading) {
    return (
      <div className="app">
        <LoadingSpinner />
      </div>
    );
  }

  if (error && !useMockData) {
    return (
      <div className="app">
        <ErrorMessage message={error} onRetry={handleRetry} />
      </div>
    );
  }

  if (!maxPosts) {
    return (
      <div className="app">
        <ErrorMessage 
          message="No posts found" 
          onRetry={handleRetry} 
        />
      </div>
    );
  }

  // Get current posts for each platform (with fallback to last available post)
  const currentLinkedInPost = posts.linkedin[Math.min(currentPostIndex, posts.linkedin.length - 1)] || null;
  const currentTwitterPost = posts.twitter[Math.min(currentPostIndex, posts.twitter.length - 1)] || null;
  const currentFacebookPost = posts.facebook[Math.min(currentPostIndex, posts.facebook.length - 1)] || null;

  return (
    <div className="app">
      <header className="app-header">
        <h1>Social Media Post Preview</h1>
        <p>Preview how your personalized posts will look across different platforms</p>
      </header>

      <div className="post-navigation">
        <button 
          onClick={prevPost} 
          disabled={maxPosts <= 1}
          className="nav-button"
        >
          ← Previous Post
        </button>
        <span className="post-counter">
          Post {currentPostIndex + 1} of {maxPosts}
        </span>
        <button 
          onClick={nextPost} 
          disabled={maxPosts <= 1}
          className="nav-button"
        >
          Next Post →
        </button>
      </div>

      <main className="main-content">
        <div className="preview-grid">
          <LinkedInPreview 
            post={currentLinkedInPost}
          />
          <XPreview 
            post={currentTwitterPost}
          />
          <FacebookPreview 
            post={currentFacebookPost}
          />
        </div>
      </main>
    </div>
  );
}

export default App;
