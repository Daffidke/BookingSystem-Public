# Accommodation Booking & Management System

> **Project Status:** Archived / Prototype (University Teamwork Assignment)  
> **Disclaimer:** This project was developed as a university coursework assignment in team-work. It is currently unfinished and preserved here strictly for archival and portfolio demonstration purposes.

---

## Project Overview

A full-stack accommodation reservation and listing web application built with **Spring Boot**, **Spring Security**, **Thymeleaf**, and **PostgreSQL**. 

The system was designed to allow guests to explore accommodations, inspect detailed room specifications, submit ratings and reviews, and manage booking requests, while providing accommodation hosts and administrators with dedicated management interfaces.

---

## Features & Capabilities

### Authentication & Role-Based Access Control
* **User Registration & Login:** Custom authentication flow powered by Spring Security (`WebSecurityConfig`) and custom credential loading (`CustomUserDetailsService`).
* **Profile Management:** User overview displaying active bookings, past history, and account settings.
* **Role Separation:** Distinct views and endpoints for standard users, property hosts, and administrators.

### Accommodation Browsing & Discovery
* **Catalog View:** Filter and browse available rooms and accommodations.
* **Detailed Listing Page:** Room photos, descriptions, capacity details, and amenities.
* **Host Listing Submission:** Multi-field form for listing new accommodations with amenities and pricing.

### Booking & Reviews
* **Reservation Model:** Tracking reservation periods, guest counts, and booking statuses.
* **User Reviews & Ratings:** System for guests to submit feedback, ratings, and comments for accommodations.

### Administration
* **Admin Dashboard:** Oversight panel for reviewing listed accommodations and user bookings.

---

## Architecture & Technology Stack

The project follows a standard layered architecture with explicit **Controller – DAO – Model** separation.

* **Backend:** Java 17+, Spring Boot (MVC, Security)
* **Frontend:** Thymeleaf template engine, HTML5, CSS3
* **Database:** PostgreSQL (accessed via JDBC Data Access Objects & RowMappers)
* **Build Tool:** Apache Maven
