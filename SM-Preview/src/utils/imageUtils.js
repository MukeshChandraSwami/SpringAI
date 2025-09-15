/**
 * Utility function to get image URLs with fallback
 * If imageUrls is empty or undefined, returns fallback image
 * @param {Array} imageUrls - Array of image URLs from post
 * @returns {Array} Array of image URLs with fallback if needed
 */
export const getImageUrls = (imageUrls) => {
  if (!imageUrls || imageUrls.length === 0) {
    return ['fallback-image.png'];
  }
  return imageUrls;
};
