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
  if (sectionId !== "confirmDeletePage") {
    const deleteContactMessage = document.getElementById(
      "deleteContactMessage"
    );
    if (deleteContactMessage) deleteContactMessage.textContent = "";
  }
  if (sectionId !== "updateContactPage") {
    const updateContactMessage = document.getElementById(
      "updateContactMessage"
    );
    if (updateContactMessage) updateContactMessage.textContent = "";
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
      <button class="delete-btn"  data-id="${contact.id}" >Delete</button>
      <span style="display: inline-block; width: 10px;"></span>
      <button class="edit-btn"  data-id='${JSON.stringify(
        contact
      )}'>Edit</button>
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
    } else if (event.target.id === "logoutBtn") {
      resetNavToDefault();
      logout();
      localStorage.clear();
    } else if (event.target.classList.contains("delete-btn")) {
      const contactId = event.target.getAttribute("data-id");
      showSection("confirmDeletePage");
      confirmDeletePage(contactId);
    } else if (event.target.classList.contains("edit-btn")) {
      const contact = JSON.parse(event.target.getAttribute("data-id"));
      showSection("updateContactPage");
      updateContactPage(contact);
    }
  });
}

function logout() {
  localStorage.removeItem("userId");
  showSection("login");
  const loginForm = document.getElementById("loginForm");
  if (loginForm) loginForm.reset();
}

function confirmDeletePage(contactId) {
  const confirmDeleteButton = document.getElementById("confirmDelete");
  const cancelButton = document.getElementById("cancel");

  if (confirmDeleteButton) {
    confirmDeleteButton.addEventListener(
      "click",
      function (event) {
        deleteContact(contactId);
      },
      { once: true }
    );
  }
  if (cancelButton) {
    cancelButton.addEventListener(
      "click",
      function (event) {
        showSection("dashboard");
      },
      { once: true }
    );
  }
}

let saveChangesHandler;
function updateContactPage(contact) {
  document.getElementById("updateFirstname").value = contact.firstname;
  document.getElementById("updateLastname").value = contact.lastname;
  document.getElementById("updatePhoneNumber").value = contact.phoneNumber;
  document.getElementById("updateEmail").value = contact.email;
  const contactId = contact.id;
  const saveChangesButton = document.getElementById("saveChanges");
  if (saveChangesHandler) {
    saveChangesButton.removeEventListener("click", saveChangesHandler);
  }

  saveChangesHandler = function (event) {
    event.preventDefault();
    const firstname = document.getElementById("updateFirstname").value;
    const lastname = document.getElementById("updateLastname").value;
    const phoneNumber = document.getElementById("updatePhoneNumber").value;
    const email = document.getElementById("updateEmail").value;
    fetch("http://localhost:8080/api/BodeNetwork-contact/update", {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        firstname,
        lastname,
        phoneNumber,
        email,
        contactId: contact.id,
      }),
    })
      .then(async (response) => {
        const data = await response.json();
        console.log(data);
        const messageDiv = document.getElementById("updateContactMessage");
        if (response.ok) {
          messageDiv.textContent = "Contact updated succesful!";
          messageDiv.style.color = "green";
          const updatedContact = {
            id: contactId,
            firstname: data.data.firstname,
            lastname: data.data.lastname,
            phoneNumber: data.data.phoneNumber,
            email: data.data.email,
          };
          let contacts = JSON.parse(localStorage.getItem("userContacts")) || [];
          const updatedContacts = contacts.map((eachContact) =>
            eachContact.id === contactId ? updatedContact : eachContact
          );
          localStorage.setItem("userContacts", JSON.stringify(contacts));
          displayContactList(updatedContacts);
          showSection("dashboard");
        } else {
          messageDiv.textContent = data.data || "Registration failed.";
          messageDiv.style.color = "red";
          showSection("dashboard");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        const messageDiv = document.getElementById("regMessage");
        messageDiv.textContent = "An error occurred during registration.";
        messageDiv.style.color = "red";
      });
  };
  saveChangesButton.addEventListener("click", saveChangesHandler);
}

function deleteContact(contactId) {
  fetch("http://localhost:8080/api/BodeNetwork-contact/delete", {
    method: "Delete",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ contactId }),
  })
    .then(async (response) => {
      const data = await response.json();
      console.log(data);
      const messageDiv = document.getElementById("deleteContactMessage");
      if (response.ok) {
        messageDiv.textContent = "Registration successful!";
        messageDiv.style.color = "green";
        const storedContacts = JSON.parse(localStorage.getItem("userContacts"));
        const updatedContacts = storedContacts.filter(
          (contact) => contact.id !== contactId
        );
        localStorage.setItem("userContacts", JSON.stringify(updatedContacts));
        displayContactList(updatedContacts);
        showSection("dashboard");
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

document
  .getElementById("addContactForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();
    const firstname = document.getElementById("contactFirstname").value;
    const lastname = document.getElementById("contactLastname").value;
    const phoneNumber = document.getElementById("contactPhonenumber").value;
    const email = document.getElementById("contactEmail").value;
    const userId = localStorage.getItem("userId");

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
        const messageDiv = document.getElementById("addContactMessage");
        if (response.ok) {
          messageDiv.textContent = "Contact added successfully!";
          messageDiv.style.color = "green";
          document.getElementById("addContactForm").reset();
          localStorage.setItem(
            "userContacts",
            JSON.stringify(data.data.contacts)
          );
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
        messageDiv.textContent = "An error occurred while adding the contact.";
        messageDiv.style.color = "red";
      });
  });

dashboardButton();
