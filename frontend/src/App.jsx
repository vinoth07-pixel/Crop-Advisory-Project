import { useState } from "react";
import axios from "axios";
import Dashboard from "./Dashboard";
import "./App.css";

function App() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const [loggedIn, setLoggedIn] = useState(
    !!localStorage.getItem("token")
  );

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {
          email: email,
          password: password,
        }
      );

      const token = response.data.token;

      localStorage.setItem("token", token);
      localStorage.setItem("email", response.data.email);
      localStorage.setItem("role", response.data.role);

      setMessage("Login successful!");
      setLoggedIn(true);

      console.log("Token:", token);
      console.log("Role:", response.data.role);
    } catch (error) {
      if (error.response) {
        setMessage("Invalid email or password");
      } else {
        setMessage("Backend server is not connected");
      }
    }

    setLoading(false);
  };

  if (loggedIn) {
    return <Dashboard />;
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h1>Crop Advisory</h1>
        <p className="subtitle">Login to your account</p>

        <form onSubmit={handleLogin}>
          <label>Email</label>

          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <label>Password</label>

          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <button type="submit" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>

        {message && <p className="message">{message}</p>}
      </div>
    </div>
  );
}

export default App;