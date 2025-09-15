/**
 * Social Media Sharing Utilities
 * Provides functions to generate sharing URLs for various social media platforms
 * with exact content as shown in previews
 */

// Base URLs for social media sharing
const SHARE_URLS = {
  linkedin: 'https://www.linkedin.com/sharing/share-offsite/',
  linkedinNew: 'https://www.linkedin.com/feed/', // New LinkedIn sharing approach
  twitter: 'https://twitter.com/intent/tweet',
  facebook: 'https://www.facebook.com/sharer/sharer.php'
};

/**
 * Format content exactly as shown in LinkedIn preview
 * @param {Object} post - Post data
 * @returns {string} Formatted LinkedIn content
 */
const formatLinkedInContent = (post) => {
  // Replicate exact LinkedIn preview format
  let content = '';
  
  if (post.title && post.title.trim()) {
    content += `${post.title.trim()}\n\n`;
  }
  
  if (post.description && post.description.trim()) {
    content += post.description.trim();
  }
  
  return content;
};

/**
 * Format content exactly as shown in Twitter preview  
 * @param {Object} post - Post data
 * @returns {string} Formatted Twitter content
 */
const formatTwitterContent = (post) => {
  // Use the exact content from the description, preserving any truncation
  let content = post.description || '';
  
  // For Twitter, ensure we respect the character limit
  const maxLength = 280;
  if (content.length > maxLength) {
    content = content.substring(0, maxLength - 3) + '...';
  }
  
  return content;
};

/**
 * Format content exactly as shown in Facebook preview
 * @param {Object} post - Post data  
 * @returns {string} Formatted Facebook content
 */
const formatFacebookContent = (post) => {
  // Replicate exact Facebook preview format
  let content = '';
  
  if (post.title && post.title.trim()) {
    content += `${post.title.trim()}\n\n`;
  }
  
  if (post.description && post.description.trim()) {
    content += post.description.trim();
  }
  
  return content;
};

/**
 * Generate LinkedIn sharing URL with exact preview content
 * @param {Object} post - Post data
 * @param {string} url - Current page URL
 * @returns {string} LinkedIn sharing URL
 */
export const generateLinkedInShareUrl = (post, url = window.location.href) => {
  // LinkedIn's new sharing approach - use simple URL sharing
  // Content will need to be copied manually by user
  const params = new URLSearchParams({
    url: url
  });
  
  return `${SHARE_URLS.linkedin}?${params.toString()}`;
};

/**
 * Generate Twitter/X sharing URL with exact preview content
 * @param {Object} post - Post data
 * @param {string} url - Current page URL
 * @returns {string} Twitter sharing URL
 */
export const generateTwitterShareUrl = (post, url = window.location.href) => {
  const content = formatTwitterContent(post);
  
  const params = new URLSearchParams({
    text: content,
    url: url,
    via: 'mukeshswami' // Adding the username as shown in preview
  });

  // Add hashtags if present in the content
  if (post.description && post.description.includes('#')) {
    const hashtags = post.description.match(/#\w+/g);
    if (hashtags) {
      params.append('hashtags', hashtags.map(tag => tag.substring(1)).join(','));
    }
  }

  // Note: Twitter doesn't support direct image URLs in intent URLs
  // Images would need to be uploaded via Twitter API
  
  return `${SHARE_URLS.twitter}?${params.toString()}`;
};

/**
 * Generate Facebook sharing URL with exact preview content
 * @param {Object} post - Post data
 * @param {string} url - Current page URL
 * @returns {string} Facebook sharing URL
 */
export const generateFacebookShareUrl = (post, url = window.location.href) => {
  const content = formatFacebookContent(post);
  
  const params = new URLSearchParams({
    u: url,
    quote: content,
    hashtag: '#AI' // Adding relevant hashtag
  });

  // Add image if available
  if (post.imageUrls && post.imageUrls.length > 0) {
    params.append('picture', post.imageUrls[0]);
  }
  
  return `${SHARE_URLS.facebook}?${params.toString()}`;
};

/**
 * Open sharing URL in a new window
 * @param {string} url - Sharing URL
 * @param {string} platform - Platform name for window title
 */
export const openShareWindow = (url, platform) => {
  const windowFeatures = 'width=600,height=400,scrollbars=yes,resizable=yes';
  const popup = window.open(url, `share-${platform}`, windowFeatures);
  
  // Focus the popup window
  if (popup) {
    popup.focus();
  }
  
  return popup;
};

/**
 * Main sharing function that handles all platforms with exact preview content
 * @param {string} platform - Platform name ('linkedin', 'twitter', 'facebook')
 * @param {Object} post - Post data
 * @param {string} url - Current page URL (optional)
 */
export const shareOnPlatform = (platform, post, url = window.location.href) => {
  let shareUrl;
  
  switch (platform) {
    case 'linkedin':
      shareUrl = generateLinkedInShareUrl(post, url);
      break;
    case 'twitter':
    case 'x':
      shareUrl = generateTwitterShareUrl(post, url);
      break;
    case 'facebook':
      shareUrl = generateFacebookShareUrl(post, url);
      break;
    default:
      console.error('Unsupported platform:', platform);
      return;
  }
  
  console.log(`Sharing ${platform} content:`, {
    platform,
    url: shareUrl,
    content: platform === 'linkedin' ? formatLinkedInContent(post) : 
             (platform === 'twitter' || platform === 'x') ? formatTwitterContent(post) :
             formatFacebookContent(post),
    images: post.imageUrls || [],
    note: platform === 'linkedin' ? 'LinkedIn content needs to be pasted manually due to platform restrictions' : ''
  });
  
  return openShareWindow(shareUrl, platform);
};

/**
 * Copy exact preview content to clipboard
 * @param {Object} post - Post data
 * @param {string} platform - Platform name
 */
export const copyToClipboard = async (post, platform) => {
  let text = '';
  
  switch (platform) {
    case 'linkedin':
      text = formatLinkedInContent(post);
      break;
    case 'twitter':
    case 'x':
      text = formatTwitterContent(post);
      break;
    case 'facebook':
      text = formatFacebookContent(post);
      break;
    default:
      text = post.description || post.title;
  }
  
  try {
    await navigator.clipboard.writeText(text);
    console.log(`Copied ${platform} content to clipboard:`, text);
    return true;
  } catch (err) {
    console.error('Failed to copy text: ', err);
    // Fallback for older browsers
    try {
      const textArea = document.createElement('textarea');
      textArea.value = text;
      document.body.appendChild(textArea);
      textArea.focus();
      textArea.select();
      const successful = document.execCommand('copy');
      document.body.removeChild(textArea);
      return successful;
    } catch (fallbackErr) {
      console.error('Fallback copy failed: ', fallbackErr);
      return false;
    }
  }
};

/**
 * Share exact preview content using Web Share API (mobile friendly)
 * @param {string} platform - Platform name
 * @param {Object} post - Post data
 */
export const shareWithWebAPI = async (platform, post) => {
  if (!navigator.share) {
    // Fallback to traditional sharing
    return shareOnPlatform(platform, post);
  }
  
  let shareData = {
    title: post.title || `Check out this ${platform} post`,
    url: window.location.href
  };
  
  switch (platform) {
    case 'linkedin':
      shareData.text = formatLinkedInContent(post);
      break;
    case 'twitter':
    case 'x':
      shareData.text = formatTwitterContent(post);
      break;
    case 'facebook':
      shareData.text = formatFacebookContent(post);
      break;
    default:
      shareData.text = post.description || post.title;
  }
  
  try {
    await navigator.share(shareData);
    console.log(`Shared ${platform} content successfully`);
    return true;
  } catch (err) {
    console.log('Web Share API failed, falling back to traditional sharing');
    return shareOnPlatform(platform, post);
  }
};
