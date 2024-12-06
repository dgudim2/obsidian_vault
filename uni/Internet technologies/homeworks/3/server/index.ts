import Fastify from 'fastify'
import mongoose from 'mongoose'
import { UserModel } from './models/users'
import { ProductModel } from './models/products'

await mongoose.connect("mongodb://127.0.0.1:27017/testdb")

const fastify = Fastify({
    logger: true
})


fastify.get('/', (request, reply) => {
    reply.send({ hello: 'world' })
})

// https://stackoverflow.com/questions/65557198/how-to-use-fastify-cors-to-enable-just-one-api-to-cross-domain
fastify.addHook('preHandler', (req, res, done) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Methods", "*");
    res.header("Access-Control-Allow-Headers", "*");

    const isPreflight = /options/i.test(req.method);
    if (isPreflight) {
        return res.send();
    }

    done();
})

fastify.get('/getUsers', async (request, reply) => {
    const users = await UserModel.find().exec()
    if (users.length > 0) {
        reply.send(users)
    } else {
        reply.send('{}')
    }
})

fastify.post('/createUser', async (request, reply) => {
    const newUser = new UserModel(request.body);
    reply.send(await newUser.save())
})

fastify.delete('/deleteUser/:id', async (req, reply) => {
    const userId = req.params.id;
    await UserModel.deleteOne({ '_id': userId })
    reply.send({ message: 'User deleted successfully' });
});

fastify.put('/updateUser', async (request, reply) => {
    const user = {...request.body}
    await UserModel.updateOne({ '_id': user._id }, user)
    reply.send({ message: `User updated successfully: ${user._id}` });
});

fastify.get('/getProducts', async (request, reply) => {
    const users = await ProductModel.find().exec()
    if (users.length > 0) {
        reply.send(users)
    } else {
        reply.send('{}')
    }
})

fastify.post('/createProduct', async (request, reply) => {
    const newProduct = new ProductModel(request.body);
    reply.send(await newProduct.save())
})

fastify.delete('/deleteProduct/:id', async (req, reply) => {
    const productId = req.params.id;
    await ProductModel.deleteOne({ '_id': productId })
    reply.send({ message: 'Product deleted successfully' });
});

fastify.put('/updateProduct', async (request, reply) => {
    const product = {...request.body}
    await ProductModel.updateOne({ '_id': product._id }, product)
    reply.send({ message: `Product updated successfully: ${product._id}` });
});

fastify.listen({ port: 3001 }, (err, address) => {
    if (err) {
        fastify.log.error(err)
        process.exit(1)
    }
})