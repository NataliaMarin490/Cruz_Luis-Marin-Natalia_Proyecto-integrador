const form = document.getElementById("addDentistForm");

form.addEventListener("submit", function (event) {

event.preventDefault();

const registrationNumber = document.getElementById("registrationNumber").value;

const lastName = document.getElementById("lastName").value;

const firstName = document.getElementById("firstName").value;

const formData = {

registrationNumber,

lastName,

firstName

};

fetch('dentists/save', {

method: 'POST',

headers: {

'Content-Type': 'application/json'

},

body: JSON.stringify(formData),

})

.then(response => response.json())

.then(data => {

alert("Dentist added successfully");

form.reset();

})

.catch(error => {

console.error("Error adding dentist:", error);

});

});
