<script>
    import axios from "axios";

    /**
     * @type {any[]}
     */
    let users = [];
    let newUsername = "";
    /**
     * @type {Number | undefined}
     */
    let newAge = undefined;
    let newPhoneNum = "";
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
                username: newUsername,
                // @ts-ignore
                age: newAge,
                email: newEmail,
                phone: newPhoneNum,
            });
            newName = "";
            newUsername = "";
            newEmail = "";
            newAge = undefined;
            newPhoneNum = "";
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

    /**
     * @param {any} user
     */
    async function updateUser(user) {
        try {
            user.age += 1;
            await axios.put("http://127.0.0.1:3001/updateUser", user);
            fetchUsers();
        } catch (error) {
            console.error("Error updating user:", error);
        }
    }

    fetchUsers();
</script>

<h1>Users</h1>

<div class="container">
    <form on:submit|preventDefault={addUser}>
        <h2>Add User</h2>
        <div class="row">
            <div class="col">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        bind:value={newName}
                        class="form-control"
                        required
                        placeholder="John"
                    />
                </div>
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input
                        type="test"
                        id="username"
                        bind:value={newUsername}
                        class="form-control"
                        required
                        placeholder="john123488"
                    />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="age">Age:</label>
                    <input
                        type="number"
                        id="age"
                        bind:value={newAge}
                        class="form-control"
                        required
                        placeholder="20"
                    />
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        bind:value={newEmail}
                        class="form-control"
                        required
                    />
                </div>
            </div>
            <div class="col">
                <div class="mb-3">
                    <label for="phone">Phone:</label>
                    <input
                        type="tel"
                        id="phone"
                        bind:value={newPhoneNum}
                        class="form-control"
                        required
                    />
                </div>
            </div>
        </div>
        <button id="add-user" type="submit" class="btn btn-primary"
            >Add User</button
        >
    </form>
</div>

<div class="card">
    <div class="card-body">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Username</th>
                    <th scope="col">Name</th>
                    <th scope="col">Age</th>
                    <th scope="col">Email</th>
                    <th scope="col">Phone</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                {#each users as user}
                    <tr>
                        <th scope="row">{user.username}</th>
                        <td>{user.name}</td>
                        <td>{user.age}</td>
                        <td>{user.email}</td>
                        <td>{user.phone}</td>
                        <td>
                            <button
                                class="btn btn-danger"
                                on:click={() => deleteUser(user._id)}
                                >Delete</button
                            >
                        </td>
                        <td>
                            <button
                                class="btn btn-info"
                                on:click={() => updateUser(user)}>Update</button
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
        border-bottom: 1px solid black;
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

    #add-user {
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
