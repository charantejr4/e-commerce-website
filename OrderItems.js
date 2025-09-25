import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {jwtDecode} from "jwt-decode"; // Correct import

import {
  Box,
  Grid,
  Card,
  CardContent,
  CardMedia,
  Typography,
  Button,
} from "@mui/material";

function OrderItems() {
  const navigate = useNavigate();
  const [orders, setOrders] = useState([]);
  const [orderCount, setOrderCount] = useState(0);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/");
      return;
    }

    // Decode JWT to get username or userId
    const decoded = jwtDecode(token);
    const userNameFromToken = decoded.sub; // Make sure your JWT uses 'sub' as username

    fetch(`http://localhost:8030/orders/${userNameFromToken}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch orders");
        return res.json();
      })
      .then((data) => {
        console.log("Fetched orders:", data);
        setOrders(data);

        countItems();
      })
      .catch((err) => console.error(err));
  }, [navigate]);



  const deleteOrder = async (orderId) => {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
      const decoded = jwtDecode(token);
      const userName = decoded.sub;

      const res = await fetch(
        `http://localhost:8030/orders/delete/${userName}/${orderId}`,
        {
          method: "DELETE",
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      if (!res.ok) throw new Error("Failed to delete order");

      // Remove deleted order from state
      setOrders((prev) => prev.filter((order) => order.orderId !== orderId));
    } catch (err) {
      console.error(err);
    }
  };

  const countItems = async () => {
    const token = localStorage.getItem("token");
    if (!token) return;

    try {
      const decoded = jwtDecode(token);
      const userName = decoded.sub;

      const res = await fetch(
        `http://localhost:8030/orders/items/${userName}`,
        {
          method: "GET",
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      if (!res.ok) throw new Error("Failed to get the count");

      const data = await res.json(); // backend returns just a number
      setOrderCount(data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Box p={2}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4">My Orders</Typography>
        <Typography variant="h6">Total Orders: {orderCount}</Typography>
        <Button variant="contained" onClick={() => navigate("/home")}>
          Back to Products
        </Button>
      </Box>

      <Grid container spacing={2}>
        {orders.length === 0 && <Typography>No orders found.</Typography>}
        {orders.map((order) => (
          <Grid item xs={12} sm={6} md={4} key={order.orderId}>
            <Card>
              <CardMedia
                component="img"
                height="140"
                image={order.product?.photoLink || "https://via.placeholder.com/140"}
                alt={order.product?.productName || "Product"}
              />
              <CardContent>
                <Typography variant="h6">{order.product?.productName}</Typography>
                <Typography color="text.secondary">
                  Category: {order.product?.categoryName}
                </Typography>
                <Typography color="text.secondary">
                  Quantity: {order.quantity}
                </Typography>
                <Button
                  variant="contained"
                  color="error"
                  onClick={() => deleteOrder(order.orderId)}
                  sx={{ mt: 1 }}
                >
                  Delete
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}

export default OrderItems;

