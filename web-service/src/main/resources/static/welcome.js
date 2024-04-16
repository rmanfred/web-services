// relies on the backend controller /product/all & /product/search
// still needs to be tested

// redirect to the cart page
document.getElementById('cartLink').addEventListener('click', function(event) {
    event.preventDefault();
    const cartId = getCartId(); //

    if (cartId) {
        window.location.href = `/cart?cartId=${cartId}`;
    } else {
        alert("No cart available. Please ensure you have items in your cart.");
    }
});


function getCartId() {
    // Retrieve the cart ID from session storage, local storage, or cookies
    return sessionStorage.getItem('cartId');
}
document.getElementById('loginLink').addEventListener('click', function(event) {
    event.preventDefault();
    window.location.href = "/user/log-in";
});



// Displays an error message
function showError(message) {
    const productListContainer = document.getElementById('product-list');
    productListContainer.innerHTML = `<p>${message}</p>`;
}
