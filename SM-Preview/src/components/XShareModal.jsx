import React, { useState } from 'react';
import { X, Copy, ExternalLink, Check } from 'lucide-react';
import './XShareModal.css';

const XShareModal = ({ post, isOpen, onClose }) => {
  const [copied, setCopied] = useState(false);

  if (!isOpen) return null;

  // Format content exactly as shown in X preview
  const formatXContent = (post) => {
    const maxLength = 280;
    let content = post.description || '';
    
    // Handle truncation consistently with preview
    if (content.length > maxLength) {
      content = content.substring(0, maxLength - 20) + '...';
    }
    
    return content;
  };

  const content = formatXContent(post);

  const handleCopyAndShare = async () => {
    try {
      await navigator.clipboard.writeText(content);
      setCopied(true);
      
      // Open X in new tab
      window.open('https://twitter.com/compose/post', '_blank');
      
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
          window.open('https://twitter.com/compose/post', '_blank');
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
    <div className="x-share-modal-overlay">
      <div className="x-share-modal">
        <div className="x-share-header">
          <div className="platform-info">
            <div className="platform-icon-large x-icon">
              𝕏
            </div>
            <h3>Share on X (Twitter)</h3>
          </div>
          <button className="close-btn" onClick={onClose}>
            <X size={20} />
          </button>
        </div>

        <div className="x-share-content">
          <div className="content-preview">
            <h4>Content to share:</h4>
            <div className="content-box">
              <div className="content-text">{content}</div>
            </div>
            
            <div className="character-count">
              {content.length} / 280 characters
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
                Note: You'll need to manually upload images in X after pasting the text.
              </p>
            </div>
          )}

          <div className="instructions">
            <h4>How to share:</h4>
            <ol>
              <li>Click "Copy & Open X" below</li>
              <li>Paste the content in your X post</li>
              <li>Upload images if any (shown above)</li>
              <li>Publish your post!</li>
            </ol>
          </div>
        </div>

        <div className="x-share-actions">
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
            Copy & Open X
          </button>
        </div>

        {copied && (
          <div className="success-message">
            ✅ Content copied to clipboard! Paste it in X.
          </div>
        )}
      </div>
    </div>
  );
};

export default XShareModal;
