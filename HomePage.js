import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import {
  Box,
  Button,
  Grid,
  Card,
  CardContent,
  CardMedia,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";

function HomePage() {
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");

  // Fetch products with JWT
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/"); // redirect to login if token is missing
      return;
    }

    fetch("http://localhost:8030/products/all", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch products");
        return res.json();
      })
      .then((data) => {
        setProducts(data);
        setFilteredProducts(data);

        // Extract unique categories
        const uniqueCategories = [...new Set(data.map((p) => p.categoryName))];
        setCategories(uniqueCategories);
      })
      .catch((err) => console.error(err));
  }, [navigate]);

  // Navigate to profile
  const goToProfile = () => {
    const token = localStorage.getItem("token");
    if (!token) return;

    const decoded = jwtDecode(token);
    const userName = decoded.sub;
    navigate(`/userdetails/${userName}`);
  };

  // Logout function
  const handleLogout = () => {
    localStorage.removeItem("token"); // clear JWT token
    navigate("/"); // redirect to login page
  };

  const handleAddToCart = async (productId) => {
    const token = localStorage.getItem("token");
    if (!token) return;

    const res = await fetch("http://localhost:8030/orders/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ productId }), // ONLY productId
    });

    if (!res.ok) {
      const msg = await res.text();
      alert(msg || "Failed to add product to cart");
      return;
    }
    alert("Added to cart!");
  };

  const goToOrders = () => {
    const token = localStorage.getItem("token");
    if (!token) return;

    const decoded = jwtDecode(token);
    const userName = decoded.sub;
    navigate(`/orders/${userName}`);
  };

  // Handle category filter
  const handleCategoryChange = (event) => {
    const category = event.target.value;
    setSelectedCategory(category);

    if (category === "") {
      setFilteredProducts(products);
    } else {
      setFilteredProducts(products.filter((p) => p.categoryName === category));
    }
  };

  return (
    <Box p={2}>
      {/* Header with title, profile & logout buttons */}
      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={3}
      >
        <Typography variant="h4">Products</Typography>
        <Box>
          <Button variant="contained" onClick={goToOrders} sx={{ mr: 2 }}>
            OrderItems
          </Button>
          <Button variant="contained" onClick={goToProfile} sx={{ mr: 2 }}>
            Profile
          </Button>
          <Button variant="outlined" color="error" onClick={handleLogout}>
            Logout
          </Button>
        </Box>
      </Box>

      {/* Category Filter */}
      <Box mb={3} width="250px">
        <FormControl fullWidth>
          <InputLabel>Filter by Category</InputLabel>
          <Select value={selectedCategory} onChange={handleCategoryChange}>
            <MenuItem value="">All Categories</MenuItem>
            {categories.map((cat) => (
              <MenuItem key={cat} value={cat}>
                {cat}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>

      {/* Products Grid */}
      <Grid container spacing={2}>
        {filteredProducts.map((product) => (
          <Grid item xs={12} sm={6} md={4} key={product.productId}>
            <Card>
              <CardMedia
                component="img"
                height="140"
                image={product.photoLink}
                alt={product.productName}
              />
              <CardContent>
                <Typography variant="h6">{product.productName}</Typography>
                <Typography color="text.secondary">
                  Category: {product.categoryName}
                </Typography>
                <Button
                  variant="outlined"
                  color="error"
                  onClick={() => handleAddToCart(product.productId)}
                >
                  Add to Cart
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}

export default HomePage;
