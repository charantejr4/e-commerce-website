import React, { useState } from "react";
import { TextField, Button, Container, Typography, Box, IconButton } from "@mui/material";
import { useNavigate, Link } from "react-router-dom";
import { Add, Remove } from "@mui/icons-material";

export default function SignUpPage() {
  const [userName, setUserName] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [age, setAge] = useState("");
  const [password, setPassword] = useState("");
  const [addresses, setAddresses] = useState([{ address: "" }]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // Add new address field
  const addAddressField = () => setAddresses([...addresses, { address: "" }]);

  // Remove an address field
  const removeAddressField = (index) => {
    const list = [...addresses];
    list.splice(index, 1);
    setAddresses(list);
  };

  // Handle address input change
  const handleAddressChange = (index, value) => {
    const list = [...addresses];
    list[index].address = value;
    setAddresses(list);
  };

  // Submit signup form
  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      userName,
      firstName,
      lastName,
      age: parseInt(age),
      credential: { userName, password }, // ✅ password stored here
      addresses
    };

    try {
      const response = await fetch("http://localhost:8030/users/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        alert("Signup successful!");
        navigate("/login"); // redirect to login
      } else {
        setError("Error signing up. Try again.");
      }
    } catch (err) {
      setError("Server error. Try again later.");
      console.error(err);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box display="flex" flexDirection="column" alignItems="center" minHeight="100vh">
        <Typography variant="h4" gutterBottom>Sign Up</Typography>

        <form onSubmit={handleSubmit} style={{ width: "100%" }}>
          <TextField
            label="Username"
            fullWidth
            margin="normal"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
          />
          <TextField
            label="Password"
            type="password"
            fullWidth
            margin="normal"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <TextField
            label="First Name"
            fullWidth
            margin="normal"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
          <TextField
            label="Last Name"
            fullWidth
            margin="normal"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
          />
          <TextField
            label="Age"
            type="number"
            fullWidth
            margin="normal"
            value={age}
            onChange={(e) => setAge(e.target.value)}
          />

          <Typography variant="h6" sx={{ mt: 2 }}>Addresses</Typography>
          {addresses.map((addr, index) => (
            <Box key={index} display="flex" alignItems="center" gap={1} mb={1}>
              <TextField
                label={`Address ${index + 1}`}
                fullWidth
                value={addr.address}
                onChange={(e) => handleAddressChange(index, e.target.value)}
              />
              {addresses.length > 1 && (
                <IconButton onClick={() => removeAddressField(index)} color="error">
                  <Remove />
                </IconButton>
              )}
              {index === addresses.length - 1 && (
                <IconButton onClick={addAddressField} color="primary">
                  <Add />
                </IconButton>
              )}
            </Box>
          ))}

          {error && <Typography color="error">{error}</Typography>}

          <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
            Sign Up
          </Button>
        </form>

        <Typography sx={{ mt: 2 }}>
          Already have an account? <Link to="/login">Login</Link>
        </Typography>
      </Box>
    </Container>
  );
}
