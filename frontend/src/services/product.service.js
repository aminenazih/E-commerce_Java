import api from './api';

const ProductService = {
  getAllProducts: () => {
    return api.get('/products');
  },

  getProductById: (id) => {
    return api.get(`/products/${id}`);
  },

  getFeaturedProducts: () => {
    return api.get('/products/featured');
  },

  getProductsByCategory: (category) => {
    return api.get(`/products/category/${category}`);
  },

  searchProducts: (query) => {
    return api.get(`/products/search?query=${query}`);
  }
};

export default ProductService;