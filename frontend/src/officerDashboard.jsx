import { useEffect, useState } from "react";
import axios from "axios";
import "./OfficerDashboard.css";

function OfficerDashboard() {
  const email = localStorage.getItem("email");
  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");

  const [requests, setRequests] = useState([]);
  const [crops, setCrops] = useState([]);
  const [officer, setOfficer] = useState(null);

  const [selectedRequest, setSelectedRequest] = useState(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  const apiHeaders = {
    Authorization: `Bearer ${token}`
  };

  useEffect(() => {
    loadOfficerData();
  }, []);

  const loadOfficerData = async () => {
    try {
      setLoading(true);
      setMessage("");

      const usersResponse = await axios.get(
        "http://localhost:8080/api/users",
        {
          headers: apiHeaders
        }
      );

      const currentOfficer = usersResponse.data.find(
        (user) =>
          user.email === email &&
          user.role === "OFFICER"
      );

      if (!currentOfficer) {
        setMessage("Officer account not found.");
        return;
      }

      setOfficer(currentOfficer);

      const cropsResponse = await axios.get(
        "http://localhost:8080/api/crops",
        {
          headers: apiHeaders
        }
      );

      setCrops(cropsResponse.data);

      await loadRequests();

    } catch (error) {
      console.error("Failed to load officer data:", error);

      if (error.response) {
        setMessage("Unable to load officer data.");
      } else {
        setMessage("Backend server is not connected.");
      }
    } finally {
      setLoading(false);
    }
  };

  const loadRequests = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/advisory-requests",
        {
          headers: apiHeaders
        }
      );

      setRequests(response.data);
    } catch (error) {
      console.error("Failed to load requests:", error);
      setMessage("Unable to load advisory requests.");
    }
  };

  const handleSelectRequest = (request) => {
    setSelectedRequest(request);
    setTitle("");
    setContent("");
    setMessage("");
  };

  const handleSubmitAdvisory = async (e) => {
    e.preventDefault();

    if (!selectedRequest) {
      setMessage("Please select a request.");
      return;
    }

    if (!title.trim()) {
      setMessage("Please enter an advisory title.");
      return;
    }

    if (!content.trim()) {
      setMessage("Please enter the advisory content.");
      return;
    }

    if (!officer) {
      setMessage("Officer account not found.");
      return;
    }

    try {
      setSubmitting(true);
      setMessage("");

      const cropId =
        selectedRequest.crop?.cropId;

      if (!cropId) {
        setMessage("Crop information is missing.");
        return;
      }

      const advisoryData = {
        advisoryId: 0,
        crop: {
          cropId: cropId
        },
        officer: {
          userId: officer.userId
        },
        title: title,
        content: content,
        createdAt: new Date().toISOString()
      };

      const advisoryResponse = await axios.post(
        "http://localhost:8080/api/advisories",
        advisoryData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        }
      );

      const createdAdvisory =
        advisoryResponse.data;

      const updateData = {
        requestId: selectedRequest.requestId,

        farmer: selectedRequest.farmer
          ? {
              userId: selectedRequest.farmer.userId
            }
          : null,

        crop: {
          cropId: cropId
        },

        advisory: {
          advisoryId: createdAdvisory.advisoryId
        },

        question: selectedRequest.question,
        status: "RESOLVED",
        createdAt: selectedRequest.createdAt
      };

      await axios.put(
        `http://localhost:8080/api/advisory-requests/${selectedRequest.requestId}`,
        updateData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        }
      );

      setMessage(
        "Advisory submitted and request resolved successfully! ✅"
      );

      setTitle("");
      setContent("");
      setSelectedRequest(null);

      await loadRequests();

    } catch (error) {
      console.error(
        "Failed to submit advisory:",
        error
      );

      if (error.response) {
        console.error(
          "Backend response:",
          error.response.data
        );

        setMessage(
          "Unable to submit advisory. Please check the request data."
        );
      } else {
        setMessage(
          "Backend server is not connected."
        );
      }
    } finally {
      setSubmitting(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("role");

    window.location.reload();
  };

  const pendingRequests = requests.filter(
    (request) =>
      request.status === "PENDING"
  );

  const resolvedRequests = requests.filter(
    (request) =>
      request.status === "RESOLVED"
  );

  return (
    <div className="officer-dashboard">

      <header className="officer-header">

        <div>
          <h1>🌱 Crop Advisory</h1>
          <p>Officer Dashboard</p>
        </div>

        <div className="officer-user">

          <span>{email}</span>

          <span className="officer-role">
            {role}
          </span>

          <button onClick={handleLogout}>
            Logout
          </button>

        </div>

      </header>

      <main className="officer-content">

        <div className="officer-welcome">

          <h2>
            Welcome, Officer 👋
          </h2>

          <p>
            Review farmer questions and provide
            crop advisory guidance.
          </p>

        </div>

        {message && (
          <div className="officer-message">
            {message}
          </div>
        )}

        <div className="officer-summary">

          <div className="summary-card">
            <span className="summary-icon">
              📋
            </span>

            <h3>
              Pending Requests
            </h3>

            <strong>
              {pendingRequests.length}
            </strong>
          </div>

          <div className="summary-card">
            <span className="summary-icon">
              ✅
            </span>

            <h3>
              Resolved Requests
            </h3>

            <strong>
              {resolvedRequests.length}
            </strong>
          </div>

          <div className="summary-card">
            <span className="summary-icon">
              🌱
            </span>

            <h3>
              Available Crops
            </h3>

            <strong>
              {crops.length}
            </strong>
          </div>

        </div>

        <section className="request-section">

          <div className="section-heading">

            <div>
              <h2>
                Farmer Advisory Requests
              </h2>

              <p>
                Select a pending request to provide
                an advisory.
              </p>
            </div>

            <button
              className="refresh-button"
              onClick={loadRequests}
            >
              🔄 Refresh
            </button>

          </div>

          {loading ? (
            <p className="empty-message">
              Loading requests...
            </p>
          ) : pendingRequests.length === 0 ? (
            <p className="empty-message">
              No pending advisory requests.
            </p>
          ) : (

            <div className="request-list">

              {pendingRequests.map((request) => (

                <div
                  className="request-card"
                  key={request.requestId}
                >

                  <div className="request-card-top">

                    <h3>
                      Request #{request.requestId}
                    </h3>

                    <span className="pending-badge">
                      PENDING
                    </span>

                  </div>

                  <p>
                    <strong>Farmer:</strong>{" "}
                    {request.farmer?.name ||
                      "Farmer"}
                  </p>

                  <p>
                    <strong>Crop:</strong>{" "}
                    {request.crop?.cropName ||
                      "Not available"}
                  </p>

                  <p>
                    <strong>Question:</strong>{" "}
                    {request.question ||
                      "No question"}
                  </p>

                  <button
                    className="answer-button"
                    onClick={() =>
                      handleSelectRequest(
                        request
                      )
                    }
                  >
                    Provide Advisory
                  </button>

                </div>

              ))}

            </div>

          )}

        </section>

        {selectedRequest && (

          <section className="advisory-form-section">

            <h2>
              Provide Advisory 💬
            </h2>

            <div className="selected-request">

              <h3>
                Request #{selectedRequest.requestId}
              </h3>

              <p>
                <strong>Crop:</strong>{" "}
                {selectedRequest.crop?.cropName ||
                  "Not available"}
              </p>

              <p>
                <strong>Farmer Question:</strong>
              </p>

              <p className="question-text">
                {selectedRequest.question}
              </p>

            </div>

            <form
              onSubmit={handleSubmitAdvisory}
            >

              <label>
                Advisory Title
              </label>

              <input
                type="text"
                value={title}
                onChange={(e) =>
                  setTitle(e.target.value)
                }
                placeholder="Example: Rice irrigation guidance"
              />

              <label>
                Advisory Content
              </label>

              <textarea
                value={content}
                onChange={(e) =>
                  setContent(e.target.value)
                }
                placeholder="Enter detailed farming guidance..."
                rows="6"
              />

              <div className="form-buttons">

                <button
                  type="submit"
                  className="submit-advisory-button"
                  disabled={submitting}
                >
                  {submitting
                    ? "Submitting..."
                    : "Submit Advisory & Resolve"}
                </button>

                <button
                  type="button"
                  className="cancel-button"
                  onClick={() => {
                    setSelectedRequest(null);
                    setTitle("");
                    setContent("");
                    setMessage("");
                  }}
                >
                  Cancel
                </button>

              </div>

            </form>

          </section>

        )}

        {resolvedRequests.length > 0 && (

          <section className="resolved-section">

            <h2>
              Recently Resolved Requests ✅
            </h2>

            <div className="request-list">

              {resolvedRequests.map(
                (request) => (

                  <div
                    className="request-card resolved-card"
                    key={request.requestId}
                  >

                    <div className="request-card-top">

                      <h3>
                        Request #
                        {request.requestId}
                      </h3>

                      <span className="resolved-badge">
                        RESOLVED
                      </span>

                    </div>

                    <p>
                      <strong>Crop:</strong>{" "}
                      {request.crop?.cropName ||
                        "Not available"}
                    </p>

                    <p>
                      <strong>Question:</strong>{" "}
                      {request.question}
                    </p>

                    {request.advisory && (
                      <>
                        <p>
                          <strong>
                            Advisory:
                          </strong>{" "}
                          {request.advisory.title}
                        </p>

                        <p>
                          <strong>
                            Answer:
                          </strong>{" "}
                          {request.advisory.content}
                        </p>
                      </>
                    )}

                  </div>

                )
              )}

            </div>

          </section>

        )}

      </main>

    </div>
  );
}

export default OfficerDashboard;