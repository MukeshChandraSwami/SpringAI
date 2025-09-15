import React, { useState } from 'react';
import { X, Copy, ExternalLink, CheckCircle, AlertCircle } from 'lucide-react';
import './LinkedInShareModal.css';

const LinkedInShareModal = ({ post, content, isOpen, onClose }) => {
  const [copied, setCopied] = useState(false);
  const [error, setError] = useState('');

  if (!isOpen) return null;

  const handleCopyAndShare = async () => {
    try {
      await navigator.clipboard.writeText(content);
      setCopied(true);
      setError('');
      
      // Auto-open LinkedIn after a short delay
      setTimeout(() => {
        window.open('https://www.linkedin.com/feed/', '_blank', 'width=600,height=400,scrollbars=yes,resizable=yes');
      }, 1000);
      
      // Reset copied state after 3 seconds
      setTimeout(() => {
        setCopied(false);
        onClose();
      }, 3000);
      
    } catch (err) {
      setError('Failed to copy content. Please copy manually.');
      console.error('Copy failed:', err);
    }
  };

  const handleManualCopy = async () => {
    try {
      await navigator.clipboard.writeText(content);
      setCopied(true);
      setError('');
      setTimeout(() => setCopied(false), 2000);
    } catch (err) {
      setError('Failed to copy. Please select and copy the text manually.');
    }
  };

  return (
    <div className="linkedin-share-overlay">
      <div className="linkedin-share-modal">
        <div className="linkedin-share-header">
          <div className="linkedin-info">
            <div className="linkedin-icon-large">in</div>
            <h3>Share on LinkedIn</h3>
          </div>
          <button className="close-btn" onClick={onClose}>
            <X size={20} />
          </button>
        </div>

        <div className="linkedin-share-content">
          <div className="content-preview">
            <h4>Content to share:</h4>
            <div className="content-box">
              <pre className="content-text" onDoubleClick={handleManualCopy}>{content}</pre>
            </div>
            
            {copied && (
              <div className="success-message">
                <CheckCircle size={16} />
                Content copied to clipboard!
              </div>
            )}
            
            {error && (
              <div className="error-message">
                <AlertCircle size={16} />
                {error}
              </div>
            )}
          </div>

          <div className="instructions">
            <h4>How to share:</h4>
            <ol>
              <li>Click "Copy & Open LinkedIn" below</li>
              <li>Paste the content in your LinkedIn post</li>
              <li>Add any additional comments if desired</li>
              <li>Publish your post!</li>
            </ol>
          </div>
        </div>

        <div className="linkedin-share-actions">
          <button className="manual-copy-btn" onClick={handleManualCopy}>
            <Copy size={16} />
            Copy Only
          </button>
          <button className="copy-and-share-btn" onClick={handleCopyAndShare}>
            <ExternalLink size={16} />
            {copied ? 'Opening LinkedIn...' : 'Copy & Open LinkedIn'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default LinkedInShareModal;
