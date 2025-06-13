console.log("Script loaded");

function showSection(sectionId) {
  const sections = ["home", "register", "login"];
  sections.forEach((id) => {
    document.getElementById(id).style.display =
      id === sectionId ? "block" : "none";
  });
}

document
  .getElementById("registerForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    const phoneNumber = document.getElementById("regPhonenumber").value;
    const email = document.getElementById("regEmail").value;
    const password = document.getElementById("regPassword").value;

    fetch("http://localhost:8080/api/BodeNetwork-user/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ phoneNumber, email, password }),
    })
      .then(async (response) => {
        const data = await response.json();
        console.log(data);
        const messageDiv = document.getElementById("regMessage");
        if (response.ok) {
          messageDiv.textContent = "Registration successful!";
          messageDiv.style.color = "green";
          showSection("login");
        } else {
          messageDiv.textContent = data.data || "Registration failed.";
          messageDiv.style.color = "red";
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        const messageDiv = document.getElementById("regMessage");
        messageDiv.textContent = "An error occurred during registration.";
        messageDiv.style.color = "red";
      });
  });

document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    const phoneNumber = document.getElementById("loginPhonenumber").value;
    const password = document.getElementById("loginPassword").value;

    fetch("http://localhost:8080/api/BodeNetwork-user/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ phoneNumber, password }),
    })
      .then(async (response) => {
        const data = await response.json();
        console.log(data);
        const messageDiv = document.getElementById("loginMessage");
        if (response.ok) {
          messageDiv.textContent = "Login successful!";
          messageDiv.style.color = "green";
          // Redirect or perform further actions
        } else {
          messageDiv.textContent = data.data || "Login failed.";
          messageDiv.style.color = "red";
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        const messageDiv = document.getElementById("loginMessage");
        messageDiv.textContent = "An error occurred during login.";
        messageDiv.style.color = "red";
      });
  });
