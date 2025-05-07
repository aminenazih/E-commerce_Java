import api from './api';

const OrderService = {
  getUserOrders: (userId) => {
    return api.get(`/orders/user/${userId}`);
  },

  getOrderById: (id) => {
    return api.get(`/orders/${id}`);
  },

  createOrder: (orderData) => {
    return api.post('/orders', orderData);
  },

  updateOrderStatus: (id, status) => {
    return api.put(`/orders/${id}/status`, status);
  }
};

export default OrderService;