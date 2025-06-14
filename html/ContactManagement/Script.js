console.log("Script loaded");

function showSection(sectionId) {
  const sections = ["home", "register", "login", "dashboard", "addContactPage"];
  sections.forEach((id) => {
    document.getElementById(id).style.display =
      id === sectionId ? "block" : "none";
  });
}

function updateNavForLogin() {
  document.getElementById("navRegister").style.display = "none";
  document.getElementById("navLogin").style.display = "none";
  document.getElementById("navLogout").style.display = "inline";
}

function resetNavToDefault() {
  document.getElementById("navRegister").style.display = "inline";
  document.getElementById("navLogin").style.display = "inline";
  document.getElementById("navLogout").style.display = "none";
}

function displayContactList(contacts) {
  const contactList = document.getElementById("contactsList");
  contactList.innerHTML = "";
  contacts.sort((a, b) => a.firstname.localeCompare(b.firstname));
  if (contacts.length === 0) {
    contactList.innerHTML = "<p>No contacts found.</p>";
    return;
  }
  contacts.forEach((contact) => {
    const contactItem = document.createElement("DIV");
    contactItem.className = "contact-item";
    contactItem.innerHTML = `
      <h3>${contact.firstname}  ${contact.lastname}</h3>
      <p>Phone: ${contact.phoneNumber}</p>
      <p>Email: ${contact.email}</p>
      <button onclick="deleteContact('${contact.id}')">Delete</button>
    `;
    contactList.appendChild(contactItem);
  });
}

function dashboardButton() {
  document.addEventListener("click", function (event) {
    if (event.target.closest("#navHome")) {
      resetNavToDefault();
      showSection("home");
      localStorage.clear();
    } else if (event.target.id === "addContactBtn") {
      showSection("addContactPage");
      addContact();
    } else if (event.target.id === "logoutBtn") {
      resetNavToDefault();
      showSection("home");
      localStorage.clear();
    }
  });
}

function addContact() {
  document
    .getElementById("addContactForm")
    .addEventListener("submit", function (event) {
      event.preventDefault();
      const firstname = document.getElementById("contactFirstname").value;
      const lastname = document.getElementById("contactLastname").value;
      const phoneNumber = document.getElementById("contactPhonenumber").value;
      const email = document.getElementById("contactEmail").value;
      const userId = localStorage.getItem("userId");
      console.log(userId);
      fetch("http://localhost:8080/api/BodeNetwork-contact/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          firstname,
          lastname,
          email,
          phoneNumber,
          userId,
        }),
      })
        .then(async (response) => {
          const data = await response.json();
          console.log(data);
          const messageDiv = document.getElementById("addContactMessage");
          if (response.ok) {
            messageDiv.textContent = "Contact added successfully!";
            messageDiv.style.color = "green";
            displayContactList(data.data.contacts);
            showSection("dashboard");
          } else {
            messageDiv.textContent = data.data || "Failed to add contact.";
            messageDiv.style.color = "red";
          }
        })
        .catch((error) => {
          console.error("Error:", error);
          const messageDiv = document.getElementById("addContactMessage");
          messageDiv.textContent =
            "An error occurred while adding the contact.";
          messageDiv.style.color = "red";
        });
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
        localStorage.setItem("userId", data.data.id);
        localStorage.setItem("userPhoneNumber", phoneNumber);
        localStorage.setItem("userEmail", data.data.email);
        localStorage.setItem(
          "userContacts",
          JSON.stringify(data.data.contacts)
        );
        console.log(data);
        const messageDiv = document.getElementById("loginMessage");
        if (response.ok) {
          messageDiv.textContent = "Login successful!";
          messageDiv.style.color = "green";
          showSection("dashboard");
          updateNavForLogin();
          displayContactList(data.data.contacts);
          dashboardButton();
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
