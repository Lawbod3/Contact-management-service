console.log("Script loaded");

function showSection(sectionId) {
  const sections = [
    "home",
    "register",
    "login",
    "dashboard",
    "addContactPage",
    "confirmDeletePage",
    "updateContactPage",
  ];
  sections.forEach((id) => {
    document.getElementById(id).style.display =
      id === sectionId ? "block" : "none";
  });
  if (sectionId !== "login") {
    const loginForm = document.getElementById("loginForm");
    if (loginForm) loginForm.reset();
    const loginMessage = document.getElementById("loginMessage");
    if (loginMessage) loginMessage.textContent = "";
  }
  if (sectionId !== "addContactPage") {
    const addContactMessage = document.getElementById("addContactMessage");
    if (addContactMessage) addContactMessage.textContent = "";
  }
  if (sectionId !== "register") {
    const registerForm = document.getElementById("registerForm");
    if (registerForm) registerForm.reset();
    const registerMessage = document.getElementById("regMessage");
    if (registerMessage) registerMessage.textContent = "";
  }
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
      <button class="delete-btn" id="delete-btn" data-id="${contact.id}">Delete</button>
      <span style="display: inline-block; width: 10px;"></span>
      <button class="edit-btn" id="edit-btn" data-id="${contact.id}">Edit</button>
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
      logout();
      localStorage.clear();
    } else if (event.target.id == "delete-btn") {
      showSection("confirmDeletePage");
    } else if (event.target.id == "edit-btn") {
      showSection("updateContactPage");
    }
  });
}

function logout() {
  localStorage.removeItem("userId");
  showSection("login");
  const loginForm = document.getElementById("loginForm");
  if (loginForm) loginForm.reset();
}

function addContact() {
  document.getElementById("addContactForm").addEventListener(
    "submit",
    function (event) {
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
            document.getElementById("addContactForm").reset();
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
    },
    { once: true }
  );
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
