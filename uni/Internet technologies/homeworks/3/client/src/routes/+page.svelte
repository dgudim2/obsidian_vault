<script>
    import axios from "axios";
    import { Button, Col, Row } from "@sveltestrap/sveltestrap";

    let users = [];
    let newName = "";
    let newEmail = "";

    async function fetchUsers() {
        try {
            const response = await axios.get("http://127.0.0.1:3001/getUsers");
            users = response.data;
        } catch (error) {
            console.error("Error fetching users:", error);
        }
    }

    async function addUser() {
        try {
            await axios.post("http://127.0.0.1:3001/createUser", {
                name: newName,
                email: newEmail,
            });
            newName = "";
            newEmail = "";
            fetchUsers();
        } catch (error) {
            console.error("Error adding user:", error);
        }
    }

    /**
     * @param {string} userId
     */
    async function deleteUser(userId) {
        try {
            await axios.delete(`http://127.0.0.1:3001/deleteUser/${userId}`);
            fetchUsers();
        } catch (error) {
            console.error("Error deleting user:", error);
        }
    }

    fetchUsers();
</script>

<h1>Users</h1>

<div class="container">
    <div class="row">
        <Col>
            <h2>User List</h2>
            <table class="table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone</th>
                    </tr>
                </thead>
                <tbody> </tbody>
            </table>
        </Col>
        <div class="col-4">
            <h2>Add User</h2>
            <form on:submit|preventDefault={addUser}>
                <div class="mb-3">
                    <label for="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        bind:value={newName}
                        class="form-control"
                        required
                    />
                </div>
                <div class="mb-3">
                    <label for="age">Email:</label>
                    <input
                        type="email"
                        id="age"
                        bind:value={newEmail}
                        class="form-control"
                        required
                    />
                </div>
                <button type="submit" class="btn btn-primary">Add User</button>
            </form>
        </div>
        <div class="col-4"></div>
    </div>
</div>

<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
        </tr>
    </thead>
    <tbody>
        {#each users as user}
            <tr>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>
                    <button on:click={() => deleteUser(user._id)}>Delete</button>
                </td>
            </tr>
        {/each}
    </tbody>
</table>

<style>
    body {
        font-family: "Roboto", sans-serif;
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

    body {
        background: linear-gradient(to right, #f6d365, #fda085);
    }

    form {
        display: flex;
        flex-direction: column;
        gap: 10px;
        max-width: 400px;
        margin: 0 auto;
    }

    input[type="text"],
    input[type="email"] {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    button {
        padding: 10px 20px;
        background-color: #4caf50;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    ::placeholder {
        color: blue;
    }
    input:invalid {
        background-color: #ddd;
    }
</style>
