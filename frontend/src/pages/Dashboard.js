import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/AuthContext';
import ProductService from '../../services/product.service';
import OrderService from '../../services/order.service';

function Dashboard() {
  const { currentUser, isAuthenticated } = useContext(AuthContext);
  const navigate = useNavigate();
  
  const [products, setProducts] = useState([]);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeTab, setActiveTab] = useState('products');
  
  useEffect(() => {
    // Check if user is authenticated and has admin role
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }
    
    if (!currentUser || !currentUser.roles || !currentUser.roles.includes('ROLE_ADMIN')) {
      navigate('/');
      return;
    }
    
    const fetchData = async () => {
      try {
        setLoading(true);
        
        // Fetch products
        const productsResponse = await ProductService.getAllProducts();
        setProducts(productsResponse.data);
        
        // In a real app, you'd have an API to get all orders
        // For now, we'll just fetch orders from a dummy endpoint
        try {
          // This is just a placeholder - in a real app you'd use OrderService.getAllOrders()
          const ordersResponse = await OrderService.getUserOrders("all");
          setOrders(ordersResponse.data);
        } catch (orderErr) {
          console.error("Error fetching orders:", orderErr);
          setOrders([]);
        }
        
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch data. Please try again later.');
        setLoading(false);
        console.error('Error fetching dashboard data:', err);
      }
    };
    
    fetchData();
  }, [isAuthenticated, currentUser, navigate]);
  
  const ProductsTable = () => (
    <div className="admin-products">
      <h2>Products Management</h2>
      <button className="admin-btn add-btn">Add New Product</button>
      
      <table className="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map(product => (
            <tr key={product.id}>
              <td>{product.id}</td>
              <td>
                <img 
                  src={product.imageUrls && product.imageUrls.length > 0 ? product.imageUrls[0] : 'https://via.placeholder.com/50'} 
                  alt={product.name} 
                  width="50" 
                />
              </td>
              <td>{product.name}</td>
              <td>${product.price}</td>
              <td>{product.stockQuantity}</td>
              <td>{product.category}</td>
              <td>
                <button className="admin-btn edit-btn">Edit</button>
                <button className="admin-btn delete-btn">Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
  
  const OrdersTable = () => (
    <div className="admin-orders">
      <h2>Orders Management</h2>
      
      <table className="admin-table">
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Date</th>
            <th>Status</th>
            <th>Total</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {orders.length === 0 ? (
            <tr>
              <td colSpan="6" className="no-data">No orders available</td>
            </tr>
          ) : (
            orders.map(order => (
              <tr key={order.id}>
                <td>{order.id}</td>
                <td>{order.userId}</td>
                <td>{order.createdAt}</td>
                <td>
                  <span className={`status-badge ${order.status.toLowerCase()}`}>
                    {order.status}
                  </span>
                </td>
                <td>${order.totalAmount}</td>
                <td>
                  <button className="admin-btn view-btn">View Details</button>
                  <select 
                    className="status-select"
                    defaultValue={order.status}
                    onChange={(e) => handleStatusChange(order.id, e.target.value)}
                  >
                    <option value="PENDING">Pending</option>
                    <option value="PROCESSING">Processing</option>
                    <option value="SHIPPED">Shipped</option>
                    <option value="DELIVERED">Delivered</option>
                    <option value="CANCELLED">Cancelled</option>
                  </select>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
  
  const handleStatusChange = async (orderId, status) => {
    try {
      await OrderService.updateOrderStatus(orderId, status);
      
      // Update the orders state to reflect the change
      setOrders(prevOrders => 
        prevOrders.map(order => 
          order.id === orderId ? { ...order, status } : order
        )
      );
    } catch (err) {
      console.error('Error updating order status:', err);
      alert('Failed to update order status. Please try again.');
    }
  };
  
  if (loading) return <div>Loading dashboard data...</div>;
  if (error) return <div className="error-message">{error}</div>;
  
  return (
    <div className="admin-dashboard">
      <h1>Admin Dashboard</h1>
      
      <div className="admin-tabs">
        <button 
          className={`tab-btn ${activeTab === 'products' ? 'active' : ''}`}
          onClick={() => setActiveTab('products')}
        >
          Products
        </button>
        <button 
          className={`tab-btn ${activeTab === 'orders' ? 'active' : ''}`}
          onClick={() => setActiveTab('orders')}
        >
          Orders
        </button>
      </div>
      
      <div className="tab-content">
        {activeTab === 'products' ? <ProductsTable /> : <OrdersTable />}
      </div>
    </div>
  );
}

export default Dashboard;