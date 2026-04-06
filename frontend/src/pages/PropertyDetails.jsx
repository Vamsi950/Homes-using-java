import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';
import './PropertyDetails.css';

const PropertyDetails = () => {
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [loading, setLoading] = useState(true);
  const [mainImage, setMainImage] = useState('');
  const [inquiry, setInquiry] = useState({ message: '', contactEmail: '', contactPhone: '' });
  const [inquiryStatus, setInquiryStatus] = useState('');

  const getImageUrl = (url) => {
    if (!url) return 'https://via.placeholder.com/800x500?text=No+Image';
    if (url.startsWith('http')) return url;
    return `http://localhost:8080${url}`;
  };

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const res = await api.get(`/properties/${id}`);
        setProperty(res.data);
        if (res.data.images && res.data.images.length > 0) {
          setMainImage(getImageUrl(res.data.images[0].imageUrl));
        } else {
          setMainImage(getImageUrl(null));
        }
      } catch (err) {
        console.error(err);
      }
      setLoading(false);
    };
    fetchProperty();
  }, [id]);

  const handleInquirySubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post(`/properties/${id}/inquiries`, inquiry);
      setInquiryStatus('Message sent successfully!');
      setInquiry({ message: '', contactEmail: '', contactPhone: '' });
    } catch (err) {
      setInquiryStatus('Failed to send message. Try again.');
    }
  };

  if (loading) return <div className="loading-spinner">Loading...</div>;
  if (!property) return <div className="no-results">Property not found.</div>;

  return (
    <div className="property-details-container">
      <div className="details-header">
        <h1>{property.title}</h1>
        <div className="details-price">${property.price.toLocaleString()}</div>
      </div>
      <p className="details-location">📍 {property.location}</p>

      <div className="details-main">
        <div className="image-gallery">
          <img src={mainImage} alt="Main" className="main-image" />
          <div className="thumbnail-list">
            {property.images && property.images.map((img, index) => (
              <img 
                key={index} 
                src={getImageUrl(img.imageUrl)} 
                alt={`Thumb ${index}`} 
                onClick={() => setMainImage(getImageUrl(img.imageUrl))}
                className={`thumbnail ${mainImage === getImageUrl(img.imageUrl) ? 'active' : ''}`}
              />
            ))}
          </div>
        </div>

        <div className="details-sidebar">
          <div className="specs-card">
            <h3>Overview</h3>
            <div className="specs-grid">
              <div className="spec-item">
                <span className="spec-label">Bedrooms</span>
                <span className="spec-value">{property.bedrooms}</span>
              </div>
              <div className="spec-item">
                <span className="spec-label">Bathrooms</span>
                <span className="spec-value">{property.bathrooms}</span>
              </div>
              <div className="spec-item">
                <span className="spec-label">Type</span>
                <span className="spec-value">{property.propertyType}</span>
              </div>
              <div className="spec-item">
                <span className="spec-label">Area</span>
                <span className="spec-value">{property.areaSqft} sqft</span>
              </div>
            </div>
            
            <div className="description">
              <h3>Description</h3>
              <p>{property.description}</p>
            </div>
          </div>

          <div className="contact-card">
            <h3>Contact Seller</h3>
            {inquiryStatus && <div className="status-msg">{inquiryStatus}</div>}
            <form onSubmit={handleInquirySubmit} className="inquiry-form">
              <input 
                type="email" 
                placeholder="Your Email" 
                value={inquiry.contactEmail}
                onChange={(e) => setInquiry({ ...inquiry, contactEmail: e.target.value })}
                required 
              />
              <input 
                type="text" 
                placeholder="Your Phone Number" 
                value={inquiry.contactPhone}
                onChange={(e) => setInquiry({ ...inquiry, contactPhone: e.target.value })}
                required 
              />
              <textarea 
                placeholder="I am interested in this property..." 
                value={inquiry.message}
                onChange={(e) => setInquiry({ ...inquiry, message: e.target.value })}
                required 
                rows="4"
              ></textarea>
              <button type="submit" className="btn-contact">Send Message</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PropertyDetails;
