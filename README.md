# Laptop Recommender System

Welcome to the **Laptop Recommender System**! This is a full-stack Spring Boot application that recommends laptops based on user-defined criteria (such as budget, RAM, and purpose) and compares them to equivalent custom PC builds.

## Features
- **Live Data Scraping:** Recommends the best laptops based on real-time data dynamically scraped from Amazon and Flipkart using Headless Selenium.
- **Smart Data Normalization:** Unifies specifications (RAM, CPU, GPU, Storage) from disparate e-commerce sites into a normalized schema stored in MongoDB Atlas.
- **Custom PC Comparison:** Compares recommended laptops to cost-effective PC builds and visualizes price differences.
- **Premium UI:** Responsive and dynamic interface built with modern web design principles and Chart.js for visualization.

## Getting Started
Please refer to the `USER_GUIDE.md` file for detailed instructions on how to install, set up, and run this application on your local machine.

## Technical Details
- **Backend:** Java 17, Spring Boot (Web, Thymeleaf, Data MongoDB)
- **Scraping Engine:** Selenium WebDriver (Headless Chrome), WebDriverManager
- **Database:** MongoDB Atlas
- **Frontend:** HTML, CSS, JavaScript, Chart.js
- **Build Tool:** Maven

Enjoy exploring the best laptop recommendations!
