
// Make sure to replace the fetch('/cart?id=1') URL with the actual endpoint to fetch cart information from the backend,
// and adjust the rendering logic according to the structure of your cart data.
document.addEventListener("DOMContentLoaded", function() {
    // Fetch items in the cart when the cart page loads
    fetchCartItems();
});

// Function to fetch items in the cart from the backend
function fetchCartItems() {
    fetch('/cart?id=1') // Replace '1' with the actual cart ID
        .then(response => response.json())
        .then(cart => {
            renderCartItems(cart.products);
        })
        .catch(error => console.error('Error fetching cart items:', error));
}

// Function to render items in the cart
function renderCartItems(products) {
    const cartContainer = document.getElementById('cart-container');
    cartContainer.innerHTML = ''; // Clear existing content

    if (products.length === 0) {
        const emptyMessage = document.createElement('p');
        emptyMessage.textContent = 'Your cart is empty.';
        cartContainer.appendChild(emptyMessage);
    } else {
        products.forEach(product => {
            const item = document.createElement('div');
            item.textContent = `${product.title} - Quantity: ${product.quantity}`;
            cartContainer.appendChild(item);
        });
    }
}
