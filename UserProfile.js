import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Box, Typography, CircularProgress, Button } from "@mui/material";

function UserProfile() {
  const { userName } = useParams(); // URL param from /userdetails/:userName
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setError("No token found. Please login.");
      setLoading(false);
      return;
    }

    fetch(`http://localhost:8030/userdetails/${userName}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        return res.json();
      })
      .then((data) => setUser(data))
      .catch((err) => {
        console.error(err);
        setError("Failed to fetch user details.");
      })
      .finally(() => setLoading(false));
  }, [userName]);

  if (loading) {
    return (
      <Box p={4} display="flex" justifyContent="center">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box p={4}>
        <Typography color="error">{error}</Typography>
        <Button variant="contained" onClick={() => navigate("/home")}>
          Go Back
        </Button>
      </Box>
    );
  }

  return (
    <Box p={4}>
      <Typography variant="h4" gutterBottom>
        User Details
      </Typography>
      <Typography><strong>Username:</strong> {user.userName}</Typography>
      <Typography><strong>Firstname:</strong> {user.firstName}</Typography>
      {/* Add other UserDTO fields here if needed */}
      <Box mt={2}>
        <Button variant="contained" onClick={() => navigate("/home")}>
          Back to Home
        </Button>
      </Box>
    </Box>
  );
}

export default UserProfile;
