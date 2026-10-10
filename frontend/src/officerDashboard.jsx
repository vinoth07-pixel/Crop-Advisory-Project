import React, { useEffect, useState } from "react";
import "./officerDashboard.css";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

function OfficerDashboard() {
    const [users, setUsers] = useState([]);
    const [crops, setCrops] = useState([]);
    const [requests, setRequests] = useState([]);
    const [advisories, setAdvisories] = useState([]);
    const [selectedRequest, setSelectedRequest] = useState(null);

    const [title, setTitle] = useState("");
    const [answer, setAnswer] = useState("");

    const token = localStorage.getItem("token");

    const authHeaders = {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
    };

    useEffect(() => {
        fetchUsers();
        fetchCrops();
        fetchRequests();
        fetchAdvisories();
    }, []);

    const fetchUsers = async () => {
        try {
            const response = await fetch(`${API_URL}/api/users`, {
                headers: authHeaders
            });

            if (response.ok) {
                const data = await response.json();
                setUsers(data);
            }
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    };

    const fetchCrops = async () => {
        try {
            const response = await fetch(`${API_URL}/api/crops`, {
                headers: authHeaders
            });

            if (response.ok) {
                const data = await response.json();
                setCrops(data);
            }
        } catch (error) {
            console.error("Error fetching crops:", error);
        }
    };

    const fetchRequests = async () => {
        try {
            const response = await fetch(`${API_URL}/api/advisory-requests`, {
                headers: authHeaders
            });

            if (response.ok) {
                const data = await response.json();
                setRequests(data);
            }
        } catch (error) {
            console.error("Error fetching requests:", error);
        }
    };

    const fetchAdvisories = async () => {
        try {
            const response = await fetch(`${API_URL}/api/advisories`, {
                headers: authHeaders
            });

            if (response.ok) {
                const data = await response.json();
                setAdvisories(data);
            }
        } catch (error) {
            console.error("Error fetching advisories:", error);
        }
    };

    const handleRequestClick = async (request) => {
        try {
            const response = await fetch(
                `${API_URL}/api/advisory-requests/${request.requestId}`,
                {
                    headers: authHeaders
                }
            );

            if (response.ok) {
                const data = await response.json();
                setSelectedRequest(data);
                setTitle("");
                setAnswer("");
            }
        } catch (error) {
            console.error("Error fetching request:", error);
        }
    };

    const handleResolveRequest = async () => {
        if (!selectedRequest) {
            return;
        }

        if (!title.trim() || !answer.trim()) {
            alert("Please enter advisory title and answer");
            return;
        }

        try {
            const advisoryResponse = await fetch(
                `${API_URL}/api/advisories`,
                {
                    method: "POST",
                    headers: authHeaders,
                    body: JSON.stringify({
                        title: title,
                        answer: answer,
                        requestId: selectedRequest.requestId
                    })
                }
            );

            if (!advisoryResponse.ok) {
                alert("Failed to create advisory");
                return;
            }

            const resolveResponse = await fetch(
                `${API_URL}/api/advisory-requests/${selectedRequest.requestId}`,
                {
                    method: "PUT",
                    headers: authHeaders,
                    body: JSON.stringify({
                        status: "RESOLVED"
                    })
                }
            );

            if (!resolveResponse.ok) {
                alert("Advisory created but request could not be resolved");
                return;
            }

            alert("Request resolved successfully");

            setSelectedRequest(null);
            setTitle("");
            setAnswer("");

            fetchRequests();
            fetchAdvisories();
        } catch (error) {
            console.error("Error resolving request:", error);
            alert("Something went wrong");
        }
    };

    const pendingRequests = requests.filter(
        (request) => request.status === "PENDING"
    );

    const resolvedRequests = requests.filter(
        (request) => request.status === "RESOLVED"
    );

    return (
        <div className="officer-dashboard">

            <div className="dashboard-header">
                <h1>Officer Dashboard</h1>
                <p>Manage farmer requests and provide crop advisories</p>
            </div>

            <div className="dashboard-stats">

                <div className="stat-card">
                    <h3>Pending Requests</h3>
                    <p>{pendingRequests.length}</p>
                </div>

                <div className="stat-card">
                    <h3>Resolved Requests</h3>
                    <p>{resolvedRequests.length}</p>
                </div>

                <div className="stat-card">
                    <h3>Available Crops</h3>
                    <p>{crops.length}</p>
                </div>

                <div className="stat-card">
                    <h3>Total Users</h3>
                    <p>{users.length}</p>
                </div>

            </div>

            <div className="dashboard-content">

                <div className="requests-section">

                    <h2>Farmer Advisory Requests</h2>

                    {requests.length === 0 ? (
                        <p>No advisory requests found.</p>
                    ) : (
                        <div className="request-list">

                            {requests.map((request) => (
                                <div
                                    key={request.requestId}
                                    className={`request-card ${
                                        request.status === "RESOLVED"
                                            ? "resolved"
                                            : "pending"
                                    }`}
                                    onClick={() => handleRequestClick(request)}
                                >

                                    <div className="request-header">
                                        <h3>
                                            Request #{request.requestId}
                                        </h3>

                                        <span
                                            className={`status ${
                                                request.status === "RESOLVED"
                                                    ? "status-resolved"
                                                    : "status-pending"
                                            }`}
                                        >
                                            {request.status}
                                        </span>
                                    </div>

                                    <p>
                                        <strong>Farmer:</strong>{" "}
                                        {request.farmerName ||
                                            request.farmer?.name ||
                                            request.farmer?.email ||
                                            "Unknown"}
                                    </p>

                                    <p>
                                        <strong>Crop:</strong>{" "}
                                        {request.cropName ||
                                            request.crop?.cropName ||
                                            request.crop?.name ||
                                            "Not specified"}
                                    </p>

                                    <p>
                                        <strong>Question:</strong>{" "}
                                        {request.question ||
                                            request.description ||
                                            "No question provided"}
                                    </p>

                                    {request.status === "RESOLVED" && (
                                        <p className="resolved-text">
                                            Advisory already provided
                                        </p>
                                    )}

                                </div>
                            ))}

                        </div>
                    )}

                </div>

                {selectedRequest && (
                    <div className="advisory-section">

                        <div className="advisory-form">

                            <h2>
                                Resolve Request #
                                {selectedRequest.requestId}
                            </h2>

                            <div className="request-details">

                                <p>
                                    <strong>Farmer:</strong>{" "}
                                    {selectedRequest.farmerName ||
                                        selectedRequest.farmer?.name ||
                                        selectedRequest.farmer?.email ||
                                        "Unknown"}
                                </p>

                                <p>
                                    <strong>Crop:</strong>{" "}
                                    {selectedRequest.cropName ||
                                        selectedRequest.crop?.cropName ||
                                        selectedRequest.crop?.name ||
                                        "Not specified"}
                                </p>

                                <p>
                                    <strong>Question:</strong>{" "}
                                    {selectedRequest.question ||
                                        selectedRequest.description ||
                                        "No question provided"}
                                </p>

                                <p>
                                    <strong>Status:</strong>{" "}
                                    {selectedRequest.status}
                                </p>

                            </div>

                            {selectedRequest.status === "PENDING" ? (
                                <>
                                    <div className="form-group">
                                        <label>Advisory Title</label>

                                        <input
                                            type="text"
                                            value={title}
                                            onChange={(e) =>
                                                setTitle(e.target.value)
                                            }
                                            placeholder="Enter advisory title"
                                        />
                                    </div>

                                    <div className="form-group">
                                        <label>Advisory Answer</label>

                                        <textarea
                                            value={answer}
                                            onChange={(e) =>
                                                setAnswer(e.target.value)
                                            }
                                            placeholder="Enter detailed farming guidance"
                                            rows="6"
                                        />
                                    </div>

                                    <div className="form-buttons">

                                        <button
                                            onClick={handleResolveRequest}
                                            className="resolve-button"
                                        >
                                            Resolve Request
                                        </button>

                                        <button
                                            onClick={() =>
                                                setSelectedRequest(null)
                                            }
                                            className="cancel-button"
                                        >
                                            Cancel
                                        </button>

                                    </div>
                                </>
                            ) : (
                                <div className="already-resolved">
                                    <h3>Request Already Resolved</h3>

                                    <p>
                                        This request has already been handled
                                        by the officer.
                                    </p>

                                    <button
                                        onClick={() =>
                                            setSelectedRequest(null)
                                        }
                                        className="cancel-button"
                                    >
                                        Close
                                    </button>
                                </div>
                            )}

                        </div>

                    </div>
                )}

            </div>

            <div className="advisories-section">

                <h2>Advisories</h2>

                {advisories.length === 0 ? (
                    <p>No advisories available.</p>
                ) : (
                    <div className="advisory-list">

                        {advisories.map((advisory) => (
                            <div
                                key={advisory.advisoryId}
                                className="advisory-card"
                            >

                                <h3>
                                    {advisory.title ||
                                        "Crop Advisory"}
                                </h3>

                                <p>
                                    {advisory.answer ||
                                        advisory.description ||
                                        "No advisory details available"}
                                </p>

                                {advisory.requestId && (
                                    <small>
                                        Request #{advisory.requestId}
                                    </small>
                                )}

                            </div>
                        ))}

                    </div>
                )}

            </div>

        </div>
    );
}

export default OfficerDashboard;