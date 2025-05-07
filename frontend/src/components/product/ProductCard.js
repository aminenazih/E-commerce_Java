import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { CartContext } from '../../contexts/CartContext';

function ProductCard({ product }) {
  const { addToCart } = useContext(CartContext);

  const handleAddToCart = (e) => {
    e.preventDefault();
    addToCart(product);
  };

  return (
    <div className="product-card">
      <Link to={`/product/${product.id}`}>
        <img src={product.imageUrls && product.imageUrls.length > 0 ? product.imageUrls[0] : 'https://via.placeholder.com/150'} alt={product.name} />
        <h3>{product.name}</h3>
        <p className="price">${product.price}</p>
      </Link>
      <button 
        onClick={handleAddToCart}
        className="add-to-cart"
      >
        Add to Cart
      </button>
    </div>
  );
}

export default ProductCard;