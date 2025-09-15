import React, { useState } from 'react';
import { User, ThumbsUp, MessageCircle, Share, MoreHorizontal, ExternalLink } from 'lucide-react';
import { getImageUrls } from '../utils/imageUtils';
import FacebookShareModal from './FacebookShareModal';
import './SocialPreview.css';

const FacebookPreview = ({ post }) => {
  const [showFacebookShareModal, setShowFacebookShareModal] = useState(false);
  
  // Handle case where no post is available for this platform
  if (!post) {
    return (
      <div className="social-preview facebook-preview">
        <div className="platform-header">
          <div className="platform-icon facebook-icon">f</div>
          <h3>Facebook</h3>
        </div>
        <div className="post-container">
          <div className="no-post-message">
            <p>No Facebook post available for this index</p>
          </div>
        </div>
      </div>
    );
  }
  
  // Format content exactly as shown in Facebook preview
  const formatFacebookContent = () => {
    let content = '';
    if (post.title) {
      content += `${post.title}\n\n`;
    }
    content += post.description;
    return content;
  };

  const handleShare = () => {
    setShowFacebookShareModal(true);
  };

  const handleConfirmShare = () => {
    // This function is no longer needed with the new modal
    setShowFacebookShareModal(false);
  };

  const handleCopy = async (content) => {
    const success = await navigator.clipboard.writeText(content);
    if (success) {
      console.log('Content copied to clipboard');
    }
  };

  return (
    <div className="social-preview facebook-preview">
      <div className="platform-header">
        <div className="platform-icon facebook-icon">f</div>
        <h3>Facebook</h3>
      </div>
      
      <div className="post-container">
        {/* User Header */}
        <div className="user-header">
          <div className="user-avatar">
            <User size={24} />
          </div>
          <div className="user-info">
            <div className="user-name">Mukesh Swami</div>
            <div className="post-meta">
              <span className="post-time">Just now</span>
              <span className="visibility">• 🌐</span>
            </div>
          </div>
          <button className="more-btn">
            <MoreHorizontal size={16} />
          </button>
        </div>

        {/* Post Content */}
        <div className="post-content">
          <h4 className="post-title">{post.title}</h4>
          <p className="post-description">{post.description}</p>
          
          <div className="post-images facebook-images">
            {getImageUrls(post.imageUrls).map((url, index) => (
              <img key={index} src={url} alt="Post image" className="post-image" />
            ))}
          </div>
        </div>

        {/* Engagement Bar */}
        <div className="engagement-bar">
          <div className="engagement-stats">
            <span className="reactions">👍 ❤️ 👏 18</span>
            <span className="comments-shares">3 comments • 2 shares</span>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="action-buttons facebook-actions">
          <button className="action-btn">
            <ThumbsUp size={16} />
            Like
          </button>
          <button className="action-btn">
            <MessageCircle size={16} />
            Comment
          </button>
          <button className="action-btn">
            <Share size={16} />
            Share
          </button>
        </div>
      </div>

      {/* Share Button */}
      <button className="share-button facebook-share" onClick={handleShare}>
        <ExternalLink size={16} />
        Share on Facebook
      </button>

      {/* Facebook Share Modal */}
      <FacebookShareModal
        post={post}
        isOpen={showFacebookShareModal}
        onClose={() => setShowFacebookShareModal(false)}
      />
    </div>
  );
};

export default FacebookPreview;
