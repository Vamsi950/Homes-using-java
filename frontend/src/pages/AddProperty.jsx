import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import { AuthContext } from '../context/AuthContext';
import './AddProperty.css';

const AddProperty = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    price: '',
    propertyType: 'House',
    location: '',
    bedrooms: '',
    bathrooms: '',
    areaSqft: ''
  });
  const [images, setImages] = useState([]);
  const [previewImages, setPreviewImages] = useState([]);

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleImageChange = (e) => {
    const files = Array.from(e.target.files);
    setImages(files);
    
    // Create previews
    const previews = files.map(file => URL.createObjectURL(file));
    setPreviewImages(previews);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      // 1. Create property
      const res = await api.post('/properties', formData);
      const propertyId = res.data.id;

      // 2. Upload images if any
      if (images.length > 0) {
        const imageFormData = new FormData();
        images.forEach(image => {
          imageFormData.append('files', image);
        });

        await api.post(`/properties/${propertyId}/images`, imageFormData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        });
      }

      navigate(`/property/${propertyId}`);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to add property. Please try again.');
      setLoading(false);
    }
  };

  return (
    <div className="add-property-container">
      <div className="add-property-card">
        <h2>List a New Property</h2>
        {error && <div className="error-message">{error}</div>}
        
        <form onSubmit={handleSubmit} className="add-property-form">
          <div className="form-row">
            <div className="form-group">
              <label>Title *</label>
              <input type="text" name="title" value={formData.title} onChange={handleInputChange} required />
            </div>
            <div className="form-group">
              <label>Price ($) *</label>
              <input type="number" name="price" value={formData.price} onChange={handleInputChange} required />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Location *</label>
              <input type="text" name="location" value={formData.location} onChange={handleInputChange} required />
            </div>
            <div className="form-group">
              <label>Property Type</label>
              <select name="propertyType" value={formData.propertyType} onChange={handleInputChange}>
                <option value="House">House</option>
                <option value="Apartment">Apartment</option>
                <option value="Condo">Condo</option>
                <option value="Villa">Villa</option>
              </select>
            </div>
          </div>

          <div className="form-row triple">
            <div className="form-group">
              <label>Bedrooms</label>
              <input type="number" name="bedrooms" value={formData.bedrooms} onChange={handleInputChange} />
            </div>
            <div className="form-group">
              <label>Bathrooms</label>
              <input type="number" name="bathrooms" value={formData.bathrooms} onChange={handleInputChange} />
            </div>
            <div className="form-group">
              <label>Area (sqft)</label>
              <input type="number" name="areaSqft" value={formData.areaSqft} onChange={handleInputChange} />
            </div>
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea name="description" rows="5" value={formData.description} onChange={handleInputChange}></textarea>
          </div>

          <div className="form-group">
            <label>Upload Images</label>
            <input type="file" multiple accept="image/*" onChange={handleImageChange} className="file-input" />
            <div className="image-previews">
              {previewImages.map((src, index) => (
                <img key={index} src={src} alt="Preview" className="preview-img" />
              ))}
            </div>
          </div>

          <button type="submit" className="btn-submit" disabled={loading}>
            {loading ? 'Submitting...' : 'List Property'}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddProperty;
