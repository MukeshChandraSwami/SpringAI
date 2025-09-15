import React, { useState } from 'react';
import { User, Heart, MessageCircle, Share, Send, ExternalLink } from 'lucide-react';
import LinkedInShareModal from './LinkedInShareModal';
import { getImageUrls } from '../utils/imageUtils';
import './SocialPreview.css';

const LinkedInPreview = ({ post }) => {
  const [showSharePreview, setShowSharePreview] = useState(false);
  
  // Handle case where no post is available for this platform
  if (!post) {
    return (
      <div className="social-preview linkedin-preview">
        <div className="platform-header">
          <div className="platform-icon linkedin-icon">in</div>
          <h3>LinkedIn</h3>
        </div>
        <div className="post-container">
          <div className="no-post-message">
            <p>No LinkedIn post available for this index</p>
          </div>
        </div>
      </div>
    );
  }
  
  // Format content exactly as shown in LinkedIn preview
  const formatLinkedInContent = () => {
    let content = '';
    if (post.title) {
      content += `${post.title}\n\n`;
    }
    content += post.description;
    return content;
  };

  const handleShare = () => {
    setShowSharePreview(true);
  };

  // Remove the handleConfirmShare and handleCopy functions as they're now in the modal

  return (
    <div className="social-preview linkedin-preview">
      <div className="platform-header">
        <div className="platform-icon linkedin-icon">in</div>
        <h3>LinkedIn</h3>
      </div>
      
      <div className="post-container">
        {/* User Header */}
        <div className="user-header">
          <div className="user-avatar">
            <User size={24} />
          </div>
          <div className="user-info">
            <div className="user-name">Mukesh Swami</div>
            <div className="user-title">Senior Software Engineer at Cvent India PVT LTD</div>
            <div className="post-time">Just now</div>
          </div>
        </div>

        {/* Post Content */}
        <div className="post-content">
          <h4 className="post-title">{post.title}</h4>
          <p className="post-description">{post.description}</p>
          
          <div className="post-images">
            {getImageUrls(post.imageUrls).map((url, index) => (
              <img key={index} src={url} alt="Post image" className="post-image" />
            ))}
          </div>
        </div>

        {/* Engagement Bar */}
        <div className="engagement-bar">
          <div className="engagement-stats">
            <span>42 reactions • 8 comments</span>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="action-buttons">
          <button className="action-btn">
            <Heart size={16} />
            Like
          </button>
          <button className="action-btn">
            <MessageCircle size={16} />
            Comment
          </button>
          <button className="action-btn">
            <Share size={16} />
            Repost
          </button>
          <button className="action-btn">
            <Send size={16} />
            Send
          </button>
        </div>
      </div>

      {/* Share Button */}
      <button className="share-button linkedin-share" onClick={handleShare}>
        <ExternalLink size={16} />
        Share on LinkedIn
      </button>

      {/* LinkedIn Share Modal */}
      <LinkedInShareModal
        post={post}
        content={formatLinkedInContent()}
        isOpen={showSharePreview}
        onClose={() => setShowSharePreview(false)}
      />
    </div>
  );
};

export default LinkedInPreview;
