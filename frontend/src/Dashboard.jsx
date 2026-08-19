import { useState } from "react";
import axios from "axios";
import "./Dashboard.css";

function Dashboard() {
  const email = localStorage.getItem("email");
  const role = localStorage.getItem("role");

  const [farms, setFarms] = useState([]);
  const [showFarms, setShowFarms] = useState(false);
  const [farmMessage, setFarmMessage] = useState("");

  const handleViewFarms = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get(
        "http://localhost:8080/api/farms",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setFarms(response.data);
      setFarmMessage("");
      setShowFarms(true);
    } catch (error) {
      console.error("Failed to fetch farms:", error);
      setFarmMessage("Unable to load farms.");
      setShowFarms(true);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("role");

    window.location.reload();
  };

  return (
    <div className="dashboard">

      <header className="dashboard-header">
        <div>
          <h1>🌱 Crop Advisory</h1>
          <p>Farmer Dashboard</p>
        </div>

        <div className="user-section">
          <span>{email}</span>

          <span className="role">
            {role}
          </span>

          <button onClick={handleLogout}>
            Logout
          </button>
        </div>
      </header>

      <main className="dashboard-content">

        <div className="welcome">
          <h2>Welcome, Farmer 👋</h2>

          <p>
            Manage your farms, crops, weather information and advisory
            requests from one place.
          </p>
        </div>

        <div className="dashboard-grid">

          <div className="dashboard-card">
            <div className="card-icon">🚜</div>

            <h3>My Farms</h3>

            <p>
              View and manage your registered farms.
            </p>

            <button onClick={handleViewFarms}>
              View Farms
            </button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">🌱</div>

            <h3>My Crops</h3>

            <p>
              View your crops and crop information.
            </p>

            <button>
              View Crops
            </button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">🌦️</div>

            <h3>Weather</h3>

            <p>
              Check weather information for your farm.
            </p>

            <button>
              View Weather
            </button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">💬</div>

            <h3>Ask Advisory</h3>

            <p>
              Send your farming questions to an officer.
            </p>

            <button>
              Ask Now
            </button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">📋</div>

            <h3>My Requests</h3>

            <p>
              Track your advisory requests and responses.
            </p>

            <button>
              View Requests
            </button>
          </div>

        </div>

        {showFarms && (
          <div className="farm-section">

            <h2>My Farms 🚜</h2>

            {farmMessage ? (
              <p className="farm-message">
                {farmMessage}
              </p>
            ) : farms.length === 0 ? (
              <p className="farm-message">
                No farms found.
              </p>
            ) : (
              <div className="farm-list">

                {farms.map((farm) => (
                  <div
                    className="farm-card"
                    key={farm.farmId}
                  >

                    <h3>
                      Farm #{farm.farmId}
                    </h3>

                    <p>
                      <strong>Location:</strong>{" "}
                      {farm.location || "Not available"}
                    </p>

                    <p>
                      <strong>Area:</strong>{" "}
                      {farm.area || "Not available"}
                    </p>

                    <p>
                      <strong>Soil Type:</strong>{" "}
                      {farm.soilType || "Not available"}
                    </p>

                  </div>
                ))}

              </div>
            )}

          </div>
        )}

      </main>

    </div>
  );
}

export default Dashboard;