

// document.getElementById("productForm").addEventListener("submit", function(event) {
//     event.preventDefault();
//     if (validateForm()) {
//         let formData = new FormData(this);
//         fetch('ProductController', { method: 'POST', body: formData })
//             .then(response => response.text())
//             .then(data => {
//                 console.log(data);
//                 document.getElementById("productModal").style.display = "none";
//                 location.reload();
//             })
//             .catch(error => {
//                 console.error('Error submitting product:', error);
//                 alert("Failed to submit product. Please check the data and try again.");
//             });
//     }
// });

// function validateForm() {
//     let name = document.getElementById("name").value;
//     let price = parseFloat(document.getElementById("price").value);
//     let quantity = parseInt(document.getElementById("quantity").value);
//     if (!name.trim()) {
//         alert("Please enter a valid product name.");
//         return false;
//     }
//     if (isNaN(price) || price <= 0) {
//         alert("Please enter a valid price.");
//         return false;
//     }
//     if (isNaN(quantity) || quantity < 0) {
//         alert("Please enter a valid quantity.");
//         return false;
//     }
//     return true;
// }

// function clearFormData() {
//     document.getElementById("productId").value = '';
//     document.getElementById("name").value = '';
//     document.getElementById("price").value = '';
//     document.getElementById("quantity").value = '';
//     document.getElementById("category").value = '';
// }


// document.getElementById("addProductBtn").addEventListener("click", function () {
//     clearFormData();
//     document.getElementById("productModal").style.display = "block";
// });

// document.querySelector(".close").addEventListener("click", function () {
//     document.getElementById("productModal").style.display = "none";
// });

// function editProduct(productId) {
//     fetch(`ProductController?action=fetch&id=${productId}`)
//         .then(response => response.json())
//         .then(data => {
//             document.getElementById("productId").value = data.id;
//             document.getElementById("name").value = data.name;
//             document.getElementById("price").value = data.price;
//             document.getElementById("quantity").value = data.stock; 
//             document.getElementById("category").value = data.categoryId;
//             document.getElementById("productModal").style.display = "block";
//         })
//         .catch(error => {
//             console.error('Error fetching product details:', error);
//             alert("Failed to fetch product details. Please try again.");
//         });
// }

// function deleteProduct(productId) {
//     if (confirm("Are you sure you want to delete this product?")) {
//         fetch(`ProductController?action=delete&id=${productId}`, { method: 'POST' })
//             .then(response => response.text())
//             .then(data => {
//                 console.log(data);
//                 location.reload();
//             })
//             .catch(error => {
//                 console.error('Error deleting product:', error);
//                 alert("Failed to delete product. Please try again.");
//             });
//     }
// }

// document.getElementById("productForm").addEventListener("submit", function(event) {
//     event.preventDefault();
//     if (validateForm()) {
//         let formData = new FormData(this);
//         fetch('ProductController', { method: 'POST', body: formData })
//             .then(response => response.text())
//             .then(data => {
//                 console.log(data);
//                 document.getElementById("productModal").style.display = "none";
//                 location.reload();
//             })
//             .catch(error => {
//                 console.error('Error submitting product:', error);
//                 alert("Failed to submit product. Please check the data and try again.");
//             });
//     }
// });

// function validateForm() {
//     let name = document.getElementById("name").value;
//     let price = parseFloat(document.getElementById("price").value);
//     let stock = parseInt(document.getElementById("stock").value); 
//     if (!name.trim()) {
//         alert("Please enter a valid product name.");
//         return false;
//     }
//     if (isNaN(price) || price <= 0) {
//         alert("Please enter a valid price.");
//         return false;
//     }
//     if (isNaN(stock) || stock < 0) {
//         alert("Please enter a valid stock.");
//         return false;
//     }
//     return true;
// }



// function clearFormData() {
//     document.getElementById("productId").value = '';
//     document.getElementById("name").value = '';
//     document.getElementById("price").value = '';
//     document.getElementById("quantity").value = ''; // Changed from quantity to stock
//     document.getElementById("category").value = '';
// }





document.getElementById("productForm").addEventListener("submit", function(event) {
    event.preventDefault();
    if (validateForm()) {
        let formData = new FormData(this);
        fetch('ProductController', { method: 'POST', body: formData })
            .then(response => response.text())
            .then(data => {
                document.getElementById("productModal").style.display = "none";
                location.reload();
            })
            .catch(error => {
                alert("Failed to submit product. Please try again.");
            });
    }
});

function validateForm() {
    let name = document.getElementById("name").value;
    let price = parseFloat(document.getElementById("price").value);
    let quantity = parseInt(document.getElementById("quantity").value);
    if (!name.trim() || isNaN(price) || price <= 0 || isNaN(quantity) || quantity < 0) {
        alert("Please provide valid data.");
        return false;
    }
    return true;
}

function editProduct(productId) {
    fetch(`ProductController?action=fetch&id=${productId}`)
        .then(response => response.json())
        .then(product => {
            document.getElementById("productId").value = product.id;
            document.getElementById("name").value = product.name;
            document.getElementById("price").value = product.price;
            document.getElementById("quantity").value = product.stock;
            document.getElementById("category").value = product.category.id;
            document.getElementById("productModal").style.display = "block";
        })
        .catch(error => alert("Failed to fetch product details."));
}

function deleteProduct(productId) {
    if (confirm("Are you sure you want to delete this product?")) {
        fetch(`ProductController?action=delete&id=${productId}`, { method: 'POST' })
            .then(() => location.reload())
            .catch(error => alert("Failed to delete product."));
    }
}