// relies on the backend controller /product/all & /product/search
// still needs to be tested

// Display products on page load
document.addEventListener("DOMContentLoaded", function () {
    fetchProducts(); // Load all products initially
    const searchButton = document.querySelector('.search-bar button');
    searchButton.addEventListener('click', handleSearch);
});

function fetchProducts() {
    showLoading(true);
    fetch('/product/all')
        .then(response => response.json())
        .then(products => {
            if (products.length > 0) {
                renderProducts(products);
            } else {
                showNoProductsMessage();
            }
        })
        .catch(error => {
            console.error('Error fetching products:', error);
            showError('Failed to load products. Please try again.');
        })
        .finally(() => {
            showLoading(false);
        });
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
document.getElementById('cartLink').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default link behavior
    const cartId = getCartId(); // Function to retrieve the cart ID

    if (cartId) {
        window.location.href = `/cart?cartId=${cartId}`;
    } else {
        alert("No cart available. Please ensure you have items in your cart.");
    }
});

// redirection when the login icon is clicked
function getCartId() {
    // Retrieve the cart ID from session storage, local storage, or cookies
    return sessionStorage.getItem('cartId'); // Example: using session storage
}
document.getElementById('loginLink').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent the default link behavior
    window.location.href = "/user/log-in"; // The URL to your login page
});


//might be removed
// Shows a loading indicator
function showLoading(isLoading) {
    const loader = document.getElementById('loading-indicator');
    loader.style.display = isLoading ? 'block' : 'none';
}

// Displays a message when no products are found
function showNoProductsMessage() {
    const productListContainer = document.getElementById('product-list');
    productListContainer.innerHTML = '<p>No products found.</p>';
}

// Displays an error message
function showError(message) {
    const productListContainer = document.getElementById('product-list');
    productListContainer.innerHTML = `<p>${message}</p>`;
}

//--------Filtering products based on category & price ------
document.addEventListener("DOMContentLoaded", function () {
    fetchCategories();
    fetchProducts();
});

function fetchCategories() {
    fetch('/categories')
        .then(response => response.json())
        .then(categories => {
            const categoryFilter = document.getElementById('category-filter');
            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category;
                option.textContent = category;
                categoryFilter.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching categories:', error));
}

function applyFilters() {
    const category = document.getElementById('category-filter').value;
    const priceUpLimit = document.getElementById('price-up-limit').value;
    const priceDownLimit = document.getElementById('price-down-limit').value;

    const queryParams = new URLSearchParams({
        category: category || undefined, // Avoid sending empty string
        priceUpLimit: priceUpLimit || undefined,
        priceDownLimit: priceDownLimit || undefined
    }).toString();

    fetch(`/product/filter?${queryParams}`)
        .then(response => response.json())
        .then(products => renderProducts(products))
        .catch(error => console.error('Error fetching filtered products:', error));
}

