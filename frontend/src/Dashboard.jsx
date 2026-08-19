import { useState } from "react";
import axios from "axios";
import "./Dashboard.css";

function Dashboard() {
  const email = localStorage.getItem("email");
  const role = localStorage.getItem("role");

  const [farms, setFarms] = useState([]);
  const [showFarms, setShowFarms] = useState(false);
  const [farmMessage, setFarmMessage] = useState("");

  const [crops, setCrops] = useState([]);
  const [showCrops, setShowCrops] = useState(false);
  const [cropMessage, setCropMessage] = useState("");

  const [weather, setWeather] = useState([]);
  const [showWeather, setShowWeather] = useState(false);
  const [weatherMessage, setWeatherMessage] = useState("");

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

  const handleViewCrops = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get(
        "http://localhost:8080/api/crops",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setCrops(response.data);
      setCropMessage("");
      setShowCrops(true);
    } catch (error) {
      console.error("Failed to fetch crops:", error);
      setCropMessage("Unable to load crops.");
      setShowCrops(true);
    }
  };

  const handleViewWeather = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get(
        "http://localhost:8080/api/weather",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setWeather(response.data);
      setWeatherMessage("");
      setShowWeather(true);
    } catch (error) {
      console.error("Failed to fetch weather:", error);
      setWeatherMessage("Unable to load weather information.");
      setShowWeather(true);
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

            <button onClick={handleViewCrops}>
              View Crops
            </button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">🌦️</div>

            <h3>Weather</h3>

            <p>
              Check weather information for your farm.
            </p>

            <button onClick={handleViewWeather}>
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
              <p className="farm-message">{farmMessage}</p>
            ) : farms.length === 0 ? (
              <p className="farm-message">No farms found.</p>
            ) : (
              <div className="farm-list">
                {farms.map((farm) => (
                  <div
                    className="farm-card"
                    key={farm.farmId}
                  >
                    <h3>Farm #{farm.farmId}</h3>

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

        {showCrops && (
          <div className="farm-section">
            <h2>My Crops 🌱</h2>

            {cropMessage ? (
              <p className="farm-message">{cropMessage}</p>
            ) : crops.length === 0 ? (
              <p className="farm-message">No crops found.</p>
            ) : (
              <div className="farm-list">
                {crops.map((crop) => (
                  <div
                    className="farm-card"
                    key={crop.cropId}
                  >
                    <h3>{crop.cropName}</h3>

                    <p>
                      <strong>Season:</strong>{" "}
                      {crop.season || "Not available"}
                    </p>

                    <p>
                      <strong>Soil Requirement:</strong>{" "}
                      {crop.soilRequirement || "Not available"}
                    </p>

                    <p>
                      <strong>Description:</strong>{" "}
                      {crop.description || "Not available"}
                    </p>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

        {showWeather && (
          <div className="farm-section">
            <h2>Weather Information 🌦️</h2>

            {weatherMessage ? (
              <p className="farm-message">
                {weatherMessage}
              </p>
            ) : weather.length === 0 ? (
              <p className="farm-message">
                No weather data found.
              </p>
            ) : (
              <div className="farm-list">
                {weather.map((data) => (
                  <div
                    className="farm-card"
                    key={data.weatherId}
                  >
                    <h3>
                      Weather #{data.weatherId}
                    </h3>

                    <p>
                      <strong>Temperature:</strong>{" "}
                      {data.temperature} °C
                    </p>

                    <p>
                      <strong>Humidity:</strong>{" "}
                      {data.humidity} %
                    </p>

                    <p>
                      <strong>Rainfall:</strong>{" "}
                      {data.rainfall} mm
                    </p>

                    <p>
                      <strong>Recorded At:</strong>{" "}
                      {data.recordedAt}
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