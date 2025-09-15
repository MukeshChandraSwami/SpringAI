import React, { useState } from 'react';
import { X, Copy, ExternalLink, Check } from 'lucide-react';
import './FacebookShareModal.css';

const FacebookShareModal = ({ post, isOpen, onClose }) => {
  const [copied, setCopied] = useState(false);

  if (!isOpen) return null;

  // Format content exactly as shown in Facebook preview
  const formatFacebookContent = (post) => {
    let content = '';
    
    if (post.title && post.title.trim()) {
      content += `${post.title.trim()}\n\n`;
    }
    
    if (post.description && post.description.trim()) {
      content += post.description.trim();
    }
    
    return content;
  };

  const content = formatFacebookContent(post);

  const handleCopyAndShare = async () => {
    try {
      await navigator.clipboard.writeText(content);
      setCopied(true);
      
      // Open Facebook in new tab
      window.open('https://www.facebook.com/', '_blank');
      
      // Reset copied state after 3 seconds
      setTimeout(() => setCopied(false), 3000);
    } catch (err) {
      console.error('Failed to copy text: ', err);
      // Fallback for older browsers
      try {
        const textArea = document.createElement('textarea');
        textArea.value = content;
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();
        const successful = document.execCommand('copy');
        document.body.removeChild(textArea);
        if (successful) {
          setCopied(true);
          window.open('https://www.facebook.com/', '_blank');
          setTimeout(() => setCopied(false), 3000);
        }
      } catch (fallbackErr) {
        console.error('Fallback copy failed: ', fallbackErr);
      }
    }
  };

  const handleManualCopy = async () => {
    try {
      await navigator.clipboard.writeText(content);
      setCopied(true);
      setTimeout(() => setCopied(false), 3000);
    } catch (err) {
      console.error('Failed to copy text: ', err);
    }
  };

  return (
    <div className="facebook-share-modal-overlay">
      <div className="facebook-share-modal">
        <div className="facebook-share-header">
          <div className="platform-info">
            <div className="platform-icon-large facebook-icon">
              f
            </div>
            <h3>Share on Facebook</h3>
          </div>
          <button className="close-btn" onClick={onClose}>
            <X size={20} />
          </button>
        </div>

        <div className="facebook-share-content">
          <div className="content-preview">
            <h4>Content to share:</h4>
            <div className="content-box">
              <div className="content-text">{content}</div>
            </div>
            
            <div className="character-count">
              {content.length} characters
            </div>
          </div>

          {post.imageUrls && post.imageUrls.length > 0 && (
            <div className="images-note">
              <h4>📷 Images:</h4>
              <div className="image-preview-list">
                {post.imageUrls.map((url, index) => (
                  <img key={index} src={url} alt={`Image ${index + 1}`} className="preview-image" />
                ))}
              </div>
              <p className="image-note">
                Note: You'll need to manually upload images in Facebook after pasting the text.
              </p>
            </div>
          )}

          <div className="instructions">
            <h4>How to share:</h4>
            <ol>
              <li>Click "Copy & Open Facebook" below</li>
              <li>Click on "What's on your mind?" in Facebook</li>
              <li>Paste the content in your Facebook post</li>
              <li>Upload images if any (shown above)</li>
              <li>Publish your post!</li>
            </ol>
          </div>
        </div>

        <div className="facebook-share-actions">
          <button 
            className="copy-only-btn" 
            onClick={handleManualCopy}
            disabled={copied}
          >
            {copied ? <Check size={16} /> : <Copy size={16} />}
            {copied ? 'Copied!' : 'Copy Only'}
          </button>
          
          <button 
            className="copy-and-share-btn"
            onClick={handleCopyAndShare}
          >
            <ExternalLink size={16} />
            Copy & Open Facebook
          </button>
        </div>

        {copied && (
          <div className="success-message">
            ✅ Content copied to clipboard! Paste it in Facebook.
          </div>
        )}
      </div>
    </div>
  );
};

export default FacebookShareModal;
