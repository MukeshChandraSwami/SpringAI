import React from 'react';
import { X, Copy, ExternalLink } from 'lucide-react';
import './SharePreview.css';

const SharePreview = ({ platform, post, content, isOpen, onClose, onConfirmShare, onCopy }) => {
  if (!isOpen) return null;

  const platformConfig = {
    linkedin: {
      name: 'LinkedIn',
      color: '#0077b5',
      icon: 'in',
      maxLength: 3000
    },
    twitter: {
      name: 'X (Twitter)',
      color: '#000000', 
      icon: '𝕏',
      maxLength: 280
    },
    facebook: {
      name: 'Facebook',
      color: '#1877f2',
      icon: 'f',
      maxLength: 63206
    }
  };

  const config = platformConfig[platform] || platformConfig.twitter;
  const contentLength = content.length;
  const isOverLimit = contentLength > config.maxLength;

  return (
    <div className="share-preview-overlay">
      <div className="share-preview-modal">
        <div className="share-preview-header">
          <div className="platform-info">
            <div 
              className="platform-icon-large"
              style={{ backgroundColor: config.color }}
            >
              {config.icon}
            </div>
            <h3>Share on {config.name}</h3>
          </div>
          <button className="close-btn" onClick={onClose}>
            <X size={20} />
          </button>
        </div>

        <div className="share-preview-content">
          <div className="content-preview">
            <h4>Preview of content to be shared:</h4>
            <div className="content-box">
              <pre className="content-text">{content}</pre>
            </div>
            
            <div className={`character-count ${isOverLimit ? 'over-limit' : ''}`}>
              {contentLength} / {config.maxLength} characters
              {isOverLimit && (
                <span className="warning"> - Content will be truncated</span>
              )}
            </div>
          </div>

          {post.imageUrls && post.imageUrls.length > 0 && (
            <div className="images-preview">
              <h4>Images to be shared:</h4>
              <div className="image-list">
                {post.imageUrls.map((url, index) => (
                  <img key={index} src={url} alt={`Image ${index + 1}`} className="preview-image" />
                ))}
              </div>
            </div>
          )}
        </div>

        <div className="share-preview-actions">
          <button className="copy-btn" onClick={() => onCopy(content)}>
            <Copy size={16} />
            Copy Content
          </button>
          <button 
            className="share-btn"
            style={{ backgroundColor: config.color }}
            onClick={onConfirmShare}
          >
            <ExternalLink size={16} />
            Share on {config.name}
          </button>
        </div>
      </div>
    </div>
  );
};

export default SharePreview;
