<script>
    import axios from "axios";

    /**
     * @type {any[]}
     */
    let products = [];
    let newName = "";
    let newDescription = "";
    /**
     * @type {Number | undefined}
     */
    let newQuantity = undefined;

    async function fetchProducts() {
        try {
            const response = await axios.get(
                "http://127.0.0.1:3001/getProducts",
            );
            products = response.data;
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    }

    async function addProducts() {
        try {
            await axios.post("http://127.0.0.1:3001/createProduct", {
                name: newName,
                description: newDescription,
                quantity: newQuantity,
            });
            newName = "";
            newDescription = "";
            newQuantity = undefined;
            fetchProducts();
        } catch (error) {
            console.error("Error adding product:", error);
        }
    }

    /**
     * @param {string} productId
     */
    async function deleteProduct(productId) {
        try {
            await axios.delete(
                `http://127.0.0.1:3001/deleteProduct/${productId}`,
            );
            fetchProducts();
        } catch (error) {
            console.error("Error deleting product:", error);
        }
    }

    /**
     * @param {any} product
     */
    async function updateProduct(product) {
        try {
            product.quantity += 1;
            await axios.put("http://127.0.0.1:3001/updateProduct", product);
            fetchProducts();
        } catch (error) {
            console.error("Error updating product:", error);
        }
    }

    fetchProducts();
</script>

<h1>Products</h1>

<div class="container">
    <form on:submit|preventDefault={addProducts}>
        <h2>Add Product</h2>

        <div class="form-group">
            <label for="name">Name:</label>
            <input
                type="text"
                id="name"
                bind:value={newName}
                class="form-control"
                required
                placeholder="Nice product"
            />
        </div>
        <div class="form-group">
            <label for="desc">Description:</label>
            <input
                type="test"
                id="desc"
                bind:value={newDescription}
                class="form-control"
                required
                placeholder="..."
            />
        </div>

        <div class="form-group">
            <label for="q">Quantity:</label>
            <input
                type="number"
                id="q"
                bind:value={newQuantity}
                class="form-control"
                required
                placeholder="20"
            />
        </div>

        <button id="add-product" type="submit" class="btn btn-primary"
            >Add Product</button
        >
    </form>
</div>

<div class="card">
    <div class="card-body">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Quantity</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                {#each products as product}
                    <tr>
                        <th scope="row">{product.name}</th>
                        <td>{product.description}</td>
                        <td>{product.quantity}</td>
                        <td>
                            <button
                                class="btn btn-danger"
                                on:click={() => deleteProduct(product._id)}
                                >Delete</button
                            >
                        </td>
                        <td>
                            <button
                                class="btn btn-info"
                                on:click={() => updateProduct(product)}
                                >Update</button
                            >
                        </td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </div>
</div>

<style>
    h1 {
        width: 100%;
        text-align: center;
        border-bottom: 1px solid black
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th,
    td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    button {
        width: 100%;
    }

    #add-product {
        margin-top: 1em;
        margin-bottom: 1em;
    }

    input {
        background-color: #b5d8b3 !important;
    }

    ::placeholder {
        color: blue;
    }
    input:invalid {
        background-color: #ddd !important;
    }
</style>
