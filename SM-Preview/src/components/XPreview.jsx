import React, { useState } from 'react';
import { User, Heart, MessageCircle, Repeat, Share, MoreHorizontal, ExternalLink, Copy } from 'lucide-react';
import { copyToClipboard } from '../utils/socialShare';
import { getImageUrls } from '../utils/imageUtils';
import XShareModal from './XShareModal';
import './SocialPreview.css';

const XPreview = ({ post }) => {
  const [showFullText, setShowFullText] = useState(false);
  const [copySuccess, setCopySuccess] = useState(false);
  const [showXShareModal, setShowXShareModal] = useState(false);
  
  // Handle case where no post is available for this platform
  if (!post) {
    return (
      <div className="social-preview twitter-preview">
        <div className="platform-header">
          <div className="platform-icon twitter-icon">𝕏</div>
          <h3>X (Twitter)</h3>
        </div>
        <div className="post-container">
          <div className="no-post-message">
            <p>No X (Twitter) post available for this index</p>
          </div>
        </div>
      </div>
    );
  }
  
  // Better handling of Twitter content
  const maxLength = 280;
  const needsTruncation = post.description.length > maxLength;
  const displayText = needsTruncation && !showFullText 
    ? post.description.substring(0, maxLength - 20) + '...' 
    : post.description;

  const handleShare = () => {
    setShowXShareModal(true);
  };

  const handleConfirmShare = () => {
    // This function is no longer needed with the new modal
    setShowXShareModal(false);
  };

  const handleCopy = async () => {
    // Copy exactly what's displayed in the preview
    const displayedPost = {
      ...post,
      description: displayText
    };
    const success = await copyToClipboard(displayedPost, 'twitter');
    if (success) {
      setCopySuccess(true);
      setTimeout(() => setCopySuccess(false), 2000);
    }
  };

  const handleModalCopy = async (content) => {
    const success = await navigator.clipboard.writeText(content);
    if (success) {
      setCopySuccess(true);
      setTimeout(() => setCopySuccess(false), 2000);
    }
  };

  return (
    <div className="social-preview twitter-preview">
      <div className="platform-header">
        <div className="platform-icon twitter-icon">𝕏</div>
        <h3>X (Twitter)</h3>
      </div>
      
      <div className="post-container">
        {/* User Header */}
        <div className="user-header">
          <div className="user-avatar">
            <User size={20} />
          </div>
          <div className="user-info">
            <div className="user-details">
              <span className="user-name">Mukesh Swami</span>
              <span className="user-handle">@mukeshswami</span>
              <span className="post-time">• now</span>
            </div>
          </div>
          <button className="more-btn">
            <MoreHorizontal size={16} />
          </button>
        </div>

        {/* Post Content */}
        <div className="post-content">
          <p className="post-description">
            {displayText}
            {needsTruncation && (
              <button 
                className="show-more-btn"
                onClick={() => setShowFullText(!showFullText)}
              >
                {showFullText ? ' Show less' : ' Show more'}
              </button>
            )}
          </p>
          
          <div className="post-images twitter-images">
            {getImageUrls(post.imageUrls).map((url, index) => (
              <img key={index} src={url} alt="Post image" className="post-image" />
            ))}
          </div>
        </div>

        {/* Action Buttons */}
        <div className="action-buttons twitter-actions">
          <button className="action-btn">
            <MessageCircle size={16} />
            <span>12</span>
          </button>
          <button className="action-btn">
            <Repeat size={16} />
            <span>5</span>
          </button>
          <button className="action-btn">
            <Heart size={16} />
            <span>24</span>
          </button>
          <button className="action-btn" onClick={handleCopy} title="Copy text">
            <Copy size={16} />
          </button>
        </div>
      </div>

      {/* Share Button */}
      <button className="share-button twitter-share" onClick={handleShare}>
        <ExternalLink size={16} />
        {copySuccess ? 'Copied!' : 'Post on X'}
      </button>

      {/* X Share Modal */}
      <XShareModal
        post={{
          ...post,
          description: displayText // Pass the currently displayed text to modal
        }}
        isOpen={showXShareModal}
        onClose={() => setShowXShareModal(false)}
      />
    </div>
  );
};

export default XPreview;
