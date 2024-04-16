document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const formData = {
            firstName: form.firstName.value,
            lastName: form.lastName.value,
            username: form.username.value,
            email: form.email.value,
            password: form.password.value
        };

        fetch('/user/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);  // Log to see what the data structure looks like
                if (typeof data === 'number') {  // Assuming the user ID is a number
                    window.location.href = `/welcome1?userId=${data}`;
                } else {
                    console.error('Unexpected data type:', data);
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    });
});
