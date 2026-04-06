import React, { useState, useEffect } from 'react';
import api from '../services/api';
import PropertyCard from '../components/PropertyCard';
import './Home.css';

const Home = () => {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filters, setFilters] = useState({
    location: '',
    propertyType: ''
  });

  // Sample properties for initial display or fallback
  const sampleProperties = [
    {
      id: 1,
      title: "Modern Luxury Villa",
      price: 850000,
      location: "Beverly Hills, CA",
      bedrooms: 4,
      bathrooms: 3,
      areaSqft: 3200,
      propertyType: "Villa",
      images: [{ imageUrl: "https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=800&q=80" }]
    },
    {
      id: 2,
      title: "Cozy Downtown Apartment",
      price: 450000,
      location: "Manhattan, NY",
      bedrooms: 2,
      bathrooms: 2,
      areaSqft: 1100,
      propertyType: "Apartment",
      images: [{ imageUrl: "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=800&q=80" }]
    },
    {
      id: 3,
      title: "Spacious Family House",
      price: 620000,
      location: "Austin, TX",
      bedrooms: 3,
      bathrooms: 2.5,
      areaSqft: 2400,
      propertyType: "House",
      images: [{ imageUrl: "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?auto=format&fit=crop&w=800&q=80" }]
    },
    {
      id: 4,
      title: "Beachfront Condo",
      price: 550000,
      location: "Miami, FL",
      bedrooms: 2,
      bathrooms: 2,
      areaSqft: 1350,
      propertyType: "Condo",
      images: [{ imageUrl: "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&w=800&q=80" }]
    },
    {
      id: 5,
      title: "Charming Suburban Cottage",
      price: 380000,
      location: "Portland, OR",
      bedrooms: 3,
      bathrooms: 2,
      areaSqft: 1800,
      propertyType: "House",
      images: [{ imageUrl: "https://images.unsplash.com/photo-1518780664697-55e3ad937233?auto=format&fit=crop&w=800&q=80" }]
    },
    {
      id: 6,
      title: "Penthouse with City View",
      price: 1200000,
      location: "Chicago, IL",
      bedrooms: 3,
      bathrooms: 3,
      areaSqft: 2800,
      propertyType: "Apartment",
      images: [{ imageUrl: "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=800&q=80" }]
    }
  ];

  useEffect(() => {
    fetchProperties();
  }, [filters]);

  const fetchProperties = async () => {
    setLoading(true);
    try {
      const { location, propertyType } = filters;
      let url = '/properties';
      const params = new URLSearchParams();
      if (location) params.append('location', location);
      if (propertyType) params.append('propertyType', propertyType);
      
      if (params.toString()) {
        url += `?${params.toString()}`;
      }

      const res = await api.get(url);
      if (res.data.content && res.data.content.length > 0) {
        setProperties(res.data.content);
      } else {
        // Fallback to filtered samples if API returns empty
        filterAndSetSamples();
      }
    } catch (err) {
      console.error('API Error, using sample data:', err);
      filterAndSetSamples();
    }
    setLoading(false);
  };

  const filterAndSetSamples = () => {
    const { location, propertyType } = filters;
    let filteredSamples = sampleProperties;
    if (location) {
      filteredSamples = filteredSamples.filter(p => 
        p.location.toLowerCase().includes(location.toLowerCase()) ||
        p.title.toLowerCase().includes(location.toLowerCase())
      );
    }
    if (propertyType) {
      filteredSamples = filteredSamples.filter(p => p.propertyType === propertyType);
    }
    setProperties(filteredSamples);
  };

  const handleFilterChange = (e) => {
    setFilters({
      ...filters,
      [e.target.name]: e.target.value
    });
  };

  return (
    <div className="home-container">
      <div className="hero-section">
        <h1>Find Your Dream Home</h1>
        <div className="search-bar">
          <input 
            type="text" 
            name="location"
            placeholder="Search by location..." 
            value={filters.location}
            onChange={handleFilterChange}
            className="search-input"
          />
          <select 
            name="propertyType" 
            value={filters.propertyType}
            onChange={handleFilterChange}
            className="search-select"
          >
            <option value="">All Types</option>
            <option value="Apartment">Apartment</option>
            <option value="House">House</option>
            <option value="Villa">Villa</option>
            <option value="Condo">Condo</option>
          </select>
        </div>
      </div>

      <div className="properties-section">
        <h2>Latest Listings</h2>
        {loading ? (
          <div className="loading-spinner">Loading properties...</div>
        ) : (
          <div className="properties-grid">
            {properties.length > 0 ? (
              properties.map(property => (
                <PropertyCard key={property.id} property={property} />
              ))
            ) : (
              <p className="no-results">No properties found matching your criteria.</p>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
