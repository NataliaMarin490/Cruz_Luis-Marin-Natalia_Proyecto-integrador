const tableBody = document.querySelector("#dentistTable tbody");

const editModal = new bootstrap.Modal(document.getElementById("editModal"));


const editForm = document.getElementById("editForm");

let currentDentistId;

function fetchDentists() {

fetch('dentists/all')

.then(response => response.json())

.then(data => {

tableBody.innerHTML = "";

data.forEach(dentist => {

const row = document.createElement("tr");

row.innerHTML = `

<td>${dentist.id}</td>

<td>${dentist.registrationNumber}</td>

<td>${dentist.lastName}</td>

<td>${dentist.firstName}</td>

<td>

<button class="btn btn-primary btn-sm" onclick="editDentist(${dentist.id}, '${dentist.registrationNumber}', '${dentist.lastName}', '${dentist.firstName}')">Edit</button>

<button class="btn btn-danger btn-sm" onclick="deleteDentist(${dentist.id})">Delete</button>

</td>

`;

tableBody.appendChild(row);

});

})

.catch(error => {

console.error("Error fetching dentists:", error);

});

}

function editDentist(id, registrationNumber, lastName, firstName) {

currentDentistId = id;

document.getElementById("editRegistrationNumber").value = registrationNumber;

document.getElementById("editLastName").value = lastName;

document.getElementById("editFirstName").value = firstName;

editModal.show();

}

editForm.addEventListener("submit", function (event) {

event.preventDefault();

const registrationNumber = document.getElementById("editRegistrationNumber").value;

const lastName = document.getElementById("editLastName").value;

const firstName = document.getElementById("editFirstName").value;

const updatedDentist = {

id: currentDentistId,

registrationNumber,

lastName,

firstName

};

fetch('dentists/update', {

method: "PUT",

headers: {

"Content-Type": "application/json"

},

body: JSON.stringify(updatedDentist)

})

.then(response => response.json())

.then(data => {

alert("Dentist updated successfully");

fetchDentists();

editModal.hide();

})

.catch(error => {

console.error("Error updating dentist:", error);

});

});

function deleteDentist(id) {

if (confirm("Are you sure you want to delete this dentist?")) {

fetch(`dentists/delete/${id}`, {

method: 'DELETE',

})

.then(response => response.json())

.then(data => {

alert("Dentist deleted successfully");

fetchDentists();

})

.catch(error => {

console.error("Error deleting dentist:", error);

});

}

}


fetchDentists();
