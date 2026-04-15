import React from 'react';
import { Link } from 'react-router-dom';
import './PropertyCard.css';

const PropertyCard = ({ property }) => {
  const getImageUrl = (url) => {
    if (!url) return 'https://via.placeholder.com/400x300?text=No+Image';
    if (url.startsWith('http')) return url;
    const baseUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';
    return `${baseUrl}${url}`;
  };

  const imageUrl = property.images && property.images.length > 0 
    ? getImageUrl(property.images[0].imageUrl) 
    : getImageUrl(null);

  return (
    <div className="property-card">
      <img src={imageUrl} alt={property.title} className="property-image" />
      <div className="property-content">
        <div className="property-price">${property.price.toLocaleString()}</div>
        <h3 className="property-title">{property.title}</h3>
        <p className="property-location">📍 {property.location}</p>
        <div className="property-specs">
          <span>🛏️ {property.bedrooms} Beds</span>
          <span>🛁 {property.bathrooms} Baths</span>
          <span>📐 {property.areaSqft} sqft</span>
        </div>
        <Link to={`/property/${property.id}`} className="btn-details">
          View Details
        </Link>
      </div>
    </div>
  );
};

export default PropertyCard;
