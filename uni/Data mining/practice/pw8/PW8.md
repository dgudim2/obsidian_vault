>[!note]
> Author: **Danila Gudim**
> Group: **ITvfu-22**
> Completed on: **17.04.2026**

## Data analysis and preprocessing

I chose the cat/dog dataset, it has **7k** images across **37** classes.

![[Pasted image 20260417212828.png]]

The dataset is very uniform with about *180* samples per class, which is very good.

`````col 
````col-md 
flexGrow=1
===

Looking at pixel count distribution, most images are between **180k** and **200k** which is *400x450* on average. There are some smaller images as well. I am going to be downscaling images so even this resolution is ok

```` 
````col-md 
flexGrow=1
===

![[Pasted image 20260417213000.png]]

```` 
`````

The images are not equal in size, so more preprocessing is required

```python
def resize_with_pad(image: npt.NDArray, new_shape: tuple[int, int]) -> npt.NDArray:
    original_shape = (image.shape[1], image.shape[0])
    ratio = float(max(new_shape)) / max(original_shape)
    new_size = tuple([int(x * ratio) for x in original_shape])
    image = cv2.resize(image, new_size)
    delta_w = new_shape[0] - new_size[0]
    delta_h = new_shape[1] - new_size[1]
    top, bottom = delta_h // 2, delta_h - (delta_h // 2)
    left, right = delta_w // 2, delta_w - (delta_w // 2)
    image = cv2.copyMakeBorder(image, top, bottom, left, right, cv2.BORDER_CONSTANT, value=0)
    return image


def normalize_image(p: Path) -> npt.NDArray:
    return resize_with_pad(read_img(p), (image_size, image_size)) / 255.0
```

**read_img** function reads the image and converts it to grayscale and **resize_with_pad** resizes and adds bars to keep aspect ratio

## Baseline CNN

I trained a CNN from scratch. Model structure looks like this

```python
def define_model():
    model = Sequential()
    model.add(Input(shape=(image_size, image_size, 1)))
    model.add(
        Conv2D(filters=64, kernel_size=(6, 6), activation="relu", padding="Same")
    )
    model.add(MaxPooling2D((8, 8), strides=(1, 1)))
    model.add(
        Conv2D(filters=32, kernel_size=(6, 6), activation="relu", padding="Same")
    )
    model.add(MaxPooling2D((2, 2), strides=(1, 1)))
    model.add(Conv2D(filters=16, kernel_size=(3, 3), padding="Same", activation="relu"))
    model.add(MaxPooling2D((2, 2), strides=(1, 1)))
    model.add(Conv2D(filters=8, kernel_size=(3, 3), padding="Same", activation="relu"))
    model.add(Flatten())
    model.add(Dense(200, activation="relu", kernel_initializer="he_uniform"))
    model.add(Dense(len(classes_categorical_map), activation="softmax"))
    # https://keras.io/api/optimizers/sgd/
    opt = SGD(learning_rate=0.01, momentum=0.9)
    model.compile(optimizer=opt, loss="sparse_categorical_crossentropy", metrics=["accuracy"])
    return model

```

The only issue is the performance, it never got above **8%** accuracy 😅. Either the design is bad or it needs higher resolution input images (I am downscaling to 100x100)

I did k-fold splitting into 5 folds and here are the results:
```
> Fold Accuracy: 3.000% | F1: 0.004 | Recall: 0.030
> Fold Accuracy: 8.333% | F1: 0.089 | Recall: 0.083
> Fold Accuracy: 5.667% | F1: 0.058 | Recall: 0.057
> Fold Accuracy: 4.000% | F1: 0.003 | Recall: 0.040
> Fold Accuracy: 4.667% | F1: 0.041 | Recall: 0.047
```

![[Pasted image 20260417223523.png]]

The model overfit pretty quickly and predicts worse than a rock. (orange - validation, blue - training)

```
Accuracy: mean=5.733 std=2.453, n=5
F1: mean=5.005 std=3.299, n=5
Recall: mean=5.733 std=2.453, n=5
```

> [!note] 
> Training time: **1 minute** per fold

## Resnet

Next model I trained was **ResNet50**

I took a pretrained model and fine-tuned it for my dataset

```python
model = resnet50(weights=ResNet50_Weights.IMAGENET1K_V2)

# Replace the final layer with the one matching our classes
num_features = model.fc.in_features
model.fc = nn.Linear(num_features, len(classes_categorical_map))

criterion = nn.CrossEntropyLoss()
optimizer = optim.SGD(model.parameters(), lr=0.0005, momentum=0.9)
```

I added an additional step to image processing - data augmentation:

```python
train_transform = transforms.Compose(
    [
        transforms.Resize(256),
        transforms.RandomResizedCrop(224),
        transforms.RandomHorizontalFlip(p=0.5),
        transforms.RandomRotation(degrees=15),
        transforms.ColorJitter(brightness=0.2, contrast=0.2, saturation=0.2),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
    ]
)
```

This flips, crops, rotates and changes colors slightly generating new synthetic data. Even though in my testing the impact was minimal I still left it here because it makes the model more robust.

`````col 
````col-md 
flexGrow=1
===

### Loss 

![[Pasted image 20260417224841.png]]

blue - training
orange - testing

```` 
````col-md 
flexGrow=1
===

### Accuracy

![[Pasted image 20260417225003.png]]

This hasn't yes flattened, but is very close to, maybe a couple more iterations would improve the accuracy by another 0.01 - 0.3%

```` 
`````

This is much much better than the *baseline CNN*

Final epoch result:
```
Epoch [10/10]
Loss: [Train: 0.6394, Test: 0.2413]
Metrics: [Acc: 0.9328, F1: 0.9322, Recall: 0.9328]
```

> [!note]
> Training time: **15 minutes**

## ViT

Another model I trained was **ViT**. This has a different architechure - *transformer*, the other ones were *convolutional*.

I also took a pretrained model and fine-tuned it

```python
vit_model = vit_b_16(weights=ViT_B_16_Weights.IMAGENET1K_V1)

# Replace the classifier head layer with ours
num_features = cast(nn.Linear, vit_model.heads.head).in_features
vit_model.heads.head = nn.Linear(num_features, len(classes_categorical_map))

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
vit_model = vit_model.to(device)

optimizer_vit = optim.SGD(vit_model.parameters(), lr=0.0005, momentum=0.9)
criterion = nn.CrossEntropyLoss()
```

The *augmentation* was the same as in the previous model

`````col 
````col-md 
flexGrow=1
===

### Loss

![[Screenshot_20260418_180641.png]]

blue - training
orange - testing

```` 
````col-md 
flexGrow=1
===

### Accuracy

![[Screenshot_20260418_180701.png]]

It jumps around a bit, maybe lowering the learning rate and increasing iterations would help. Ching the optimizer might help as well

```` 
`````

This model takes more resources to train and more time too (because there is no inherent correlation of close pixels like it a convolutional network). A thing to note though, the model seems to converge faster at the start then ResNet.

The final accuracy is **94.4%** Which is the best among all the models

Final epoch result:

```
Epoch [10/10]
Loss: [Train: 0.3884, Test: 0.1898]
Metrics: [Acc: 0.9448, F1: 0.9447, Recall: 0.9448]
```

> [!note]
> Training time: **20 minutes**

## MaxViT

I tried training a *hybrid model* as well, but I didn't have enought video memory, I only have 6GB. I even tried disabling some layers, that didn't help either.

But this model should in theory outperform all of the previous models.