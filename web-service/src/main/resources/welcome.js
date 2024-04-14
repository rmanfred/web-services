// relies on the backend controller /product/all & /product/search
// still needs to be tested
// Display products
document.addEventListener("DOMContentLoaded", function() {
    fetchProducts();
});

function fetchProducts() {
    fetch('/product/all')
        .then(response => response.json())
        .then(products => {
            // Call a function to render products
            renderProducts(products);
        })
        .catch(error => console.error('Error fetching products:', error));
}

function renderProducts(products) {
    const productListContainer = document.getElementById('product-list');
    productListContainer.innerHTML = ''; // Clear existing content

    products.forEach(product => {
        const productCard = document.createElement('div');
        productCard.classList.add('product-card');

        // Create HTML structure for product card
        productCard.innerHTML = `
            <div class="product-details">
                <h2>${product.title}</h2>
                <p>Category: ${product.category}</p>
                <p>Description: ${product.description}</p>
                <p>Price: $${product.price}</p>
                <p>Stock: ${product.stock}</p>
                <!-- Add more details as needed -->
            </div>
        `;

        productListContainer.appendChild(productCard);
    });
}
// Display products based on search
document.addEventListener("DOMContentLoaded", function() {
    fetchProducts(); // Load all products initially
    const searchButton = document.querySelector('.search-bar button');
    searchButton.addEventListener('click', handleSearch);
});

function handleSearch() {
    const searchTerm = document.querySelector('.search-bar input').value;
    if (searchTerm.trim() !== '') {
        searchProducts(searchTerm);
    }
}

function searchProducts(searchTerm) {
    fetch(`/product/search?query=${searchTerm}`)
        .then(response => response.json())
        .then(products => {
            renderProducts(products);
        })
        .catch(error => console.error('Error searching products:', error));
}

// redirect to the cart page
function goToCart() {
    window.location.href = 'cart.html';
}
