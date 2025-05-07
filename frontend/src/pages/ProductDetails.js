import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ProductService from '../services/product.service';

function ProductDetails() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  useEffect(() => {
    const fetchProduct = async () => {
      try {
        setLoading(true);
        const response = await ProductService.getProductById(id);
        setProduct(response.data);
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch product details. Please try again later.');
        setLoading(false);
        console.error('Error fetching product:', err);
      }
    };
    
    fetchProduct();
  }, [id]);

  if (loading) return <div>Loading product details...</div>;
  if (error) return <div className="error-message">{error}</div>;
  if (!product) return <div>Product not found.</div>;

  return (
    <div className="product-details">
      <h1>{product.name}</h1>
      <div className="product-image">
        <img src={product.imageUrls && product.imageUrls.length > 0 ? product.imageUrls[0] : 'https://via.placeholder.com/300'} alt={product.name} />
      </div>
      <div className="product-info">
        <p className="price">${product.price}</p>
        <p className="description">{product.description}</p>
        <p className="stock">In Stock: {product.stockQuantity}</p>
        {product.stockQuantity > 0 ? (
        <button 
          className="add-to-cart"
          onClick={handleAddToCart}
        >
          Add to Cart
        </button>
      ) : (
        <button className="out-of-stock" disabled>Out of Stock</button>
      )}
    </div>
    </div>
  );
}

export default ProductDetails;