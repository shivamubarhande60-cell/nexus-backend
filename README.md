# ⚙️ Nexus Backend (Core Orchestration & API Platform)
**Lead Developer:** Shivam Ubarhande (Project Leader)  
**Technologies:** Java 21, Spring Boot 3.x, MySQL, MongoDB, Spring Security (JWT)

---

## 📌 Your Roadmap from the PRD
1. **User & Auth Service**: Registration, Login, JWT tokens, Role-Based Access Control (RBAC).
2. **Child Profile & Consent Engine**: Profile creation, age-band calculation, and digital consent logging.
3. **Assessment Session API**: Managing active sessions, recording raw responses, and storing stroke streams in MongoDB.
4. **ML Service Bridge**: Orchestrating calls to the Python ML microservice (POST /predict).
5. **Clinical Review & Longitudinal Tracking**: Clinician sign-off endpoints and progress history over time.

All official project specifications are located in the [docs/](./docs) folder:
- [PRD.md](./docs/PRD.md)
- [SRS.md](./docs/SRS.md)
- [Task Breakdown (WBS)](./docs/WBS_GitHub_Issues.md)
