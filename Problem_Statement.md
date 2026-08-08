# Problem Statement

## 1. Title

Crop Advisory and Farm Decision Support System

## 2. Domain

Agriculture Technology

## 3. Who is the user?

1. Farmer - Uses the application to get crop recommendations and advisory information.
2. Agricultural Officer - Reviews farmer requests and provides agricultural guidance.
3. Admin - Manages users, crop information, advisory data, and system activities.

## 4. What problem are we solving?

Farmers often need to make crop-related decisions based on soil conditions, weather conditions, crop requirements, and available agricultural information. Getting suitable recommendations at the right time can be difficult when information is scattered across different sources.

The proposed system provides a centralized platform where farmers can enter relevant field information and receive suitable crop advisory information. The system also allows agricultural officers to manage advisory information and assist farmers.

## 5. Proposed Solution

The Crop Advisory and Farm Decision Support System will provide:

- User registration and login.
- Role-based access for Farmer, Agricultural Officer, and Admin.
- Farmer profile and farm management.
- Crop information management.
- Crop advisory based on available farm information.
- Advisory request and response management.
- Weather-related information integration.
- Farmer advisory history.
- Admin management of users and crop/advisory data.
- Future scope for AI-based crop recommendation and prediction.

## 6. Core Entities / Database Tables

1. User
2. FarmerProfile
3. Farm
4. Crop
5. Advisory
6. AdvisoryRequest
7. WeatherData

## 7. User Roles & Permissions

### Farmer

- Register and login.
- Manage profile.
- Add and manage farm details.
- View crop information.
- Submit advisory requests.
- View advisory recommendations and history.

### Agricultural Officer

- Login securely.
- View farmer advisory requests.
- Provide agricultural recommendations.
- Manage advisory responses.

### Admin

- Manage users.
- Manage crop information.
- Manage advisory information.
- Monitor system activities.

## 8. Success Criteria

- A farmer should be able to register and login successfully.
- A farmer should be able to add farm information.
- A farmer should be able to submit an advisory request.
- An agricultural officer should be able to view and respond to advisory requests.
- The system should store and retrieve advisory information correctly.
- Different user roles should have different permissions.
- The application should provide a clear and usable interface.

## 9. Out of Scope

- Direct agricultural product purchasing.
- Online payment processing.
- Automated drone-based crop monitoring.
- Direct control of irrigation or farm equipment.
- Guaranteed prediction of crop yield.
- Real-time disease diagnosis in the initial version.

## 10. Chosen Track

Java (Spring Boot)