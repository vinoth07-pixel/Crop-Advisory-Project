import "./Dashboard.css";

function Dashboard() {
  const email = localStorage.getItem("email");
  const role = localStorage.getItem("role");

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
          <span className="role">{role}</span>
          <button onClick={handleLogout}>Logout</button>
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
            <p>View and manage your registered farms.</p>
            <button>View Farms</button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">🌱</div>
            <h3>My Crops</h3>
            <p>View your crops and crop information.</p>
            <button>View Crops</button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">🌦️</div>
            <h3>Weather</h3>
            <p>Check weather information for your farm.</p>
            <button>View Weather</button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">💬</div>
            <h3>Ask Advisory</h3>
            <p>Send your farming questions to an officer.</p>
            <button>Ask Now</button>
          </div>

          <div className="dashboard-card">
            <div className="card-icon">📋</div>
            <h3>My Requests</h3>
            <p>Track your advisory requests and responses.</p>
            <button>View Requests</button>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Dashboard;