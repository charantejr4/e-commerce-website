import React, { useState } from "react";
import {
  Container,
  Box,
  TextField,
  Button,
  Typography,
  Paper,
} from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8030/users/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ userName, password }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log(data.token);

        localStorage.setItem("token",data.token);
        console.log("JWT Token:", localStorage.getItem("token"));

        navigate("/home"); // ✅ redirect to home
      } else {
        setMessage("❌ Invalid credentials, please try again.");
      }
    } catch (error) {
      console.error("Error:", error);
      setMessage("⚠️ Something went wrong.");
    }
  };

  return (
    <Container
      maxWidth="sm"
      sx={{ display: "flex", justifyContent: "center", minHeight: "100vh" }}
    >
      <Paper elevation={6} sx={{ mt: 10, p: 4, borderRadius: 3, width: "100%" }}>
        <Typography variant="h4" align="center" gutterBottom>
          Login
        </Typography>

        <Box component="form" onSubmit={handleSubmit} sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
          <TextField
            label="Username"
            variant="outlined"
            fullWidth
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
            required
          />

          <TextField
            label="Password"
            type="password"
            variant="outlined"
            fullWidth
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
            Login
          </Button>
        </Box>

        {/* ✅ Signup Button */}
          <Button
            variant="outlined"
            color="secondary"
            fullWidth
            sx={{ mt: 1 }}
            onClick={() => navigate("/signup")}
          >
            Signup
          </Button>

        {message && (
          <Typography variant="body2" align="center" sx={{ mt: 2, color: "red" }}>
            {message}
          </Typography>
        )}
      </Paper>
    </Container>
  );
}
