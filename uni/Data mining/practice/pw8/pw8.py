#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "seaborn",
#   "polars",
#   "opencv-python",
#   "tensorflow==2.21.0",
#   "sklearn",
#   "keras",
#   "numpy",
#   "scikit-learn",
#   "torchvision",
#   "torch"
# ]
# ///

# https://www.tensorflow.org/install/source#tested_build_configurations

import os
# os.environ["CUDA_VISIBLE_DEVICES"] = "-1"

from pathlib import Path
import random
from typing import cast
import seaborn as sb
import matplotlib.pyplot as plt
import cv2

from keras.models import Sequential
from keras.layers import Conv2D, Input, MaxPooling2D, Dense, Flatten
from keras.optimizers import SGD

from sklearn.metrics import f1_score, recall_score

from sklearn.model_selection import KFold

import polars as pl
import numpy as np
import numpy.typing as npt

import torch
import torch.nn as nn
import torch.optim as optim
from torchvision.models import MaxVit, ResNet, ResNet50_Weights, VisionTransformer, resnet50
from torchvision.models import vit_b_16, ViT_B_16_Weights
from torchvision.models import maxvit_t, MaxVit_T_Weights
import torchvision
from torchvision import transforms
import torchvision.ops as ops

image_size = 100

images = list(Path("/tmp/images").glob("*"))

random.seed(42)
random.shuffle(images)

images = images[:7000]

print("loaded images")

classes = ["_".join(image.stem.split("_")[:-1]).lower() for image in images]
classes_categorical_map = {
    "japanese_chin": 0,
    "wheaten_terrier": 1,
    "saint_bernard": 2,
    "english_cocker_spaniel": 3,
    "shiba_inu": 4,
    "yorkshire_terrier": 5,
    "birman": 6,
    "american_bulldog": 7,
    "american_pit_bull_terrier": 8,
    "pug": 9,
    "english_setter": 10,
    "bombay": 11,
    "russian_blue": 12,
    "beagle": 13,
    "great_pyrenees": 14,
    "chihuahua": 15,
    "ragdoll": 16,
    "egyptian_mau": 17,
    "newfoundland": 18,
    "sphynx": 19,
    "havanese": 20,
    "maine_coon": 21,
    "keeshond": 22,
    "abyssinian": 23,
    "siamese": 24,
    "miniature_pinscher": 25,
    "staffordshire_bull_terrier": 26,
    "samoyed": 27,
    "basset_hound": 28,
    "bengal": 29,
    "boxer": 30,
    "pomeranian": 31,
    "leonberger": 32,
    "scottish_terrier": 33,
    "persian": 34,
    "german_shorthaired": 35,
    "british_shorthair": 36,
}
classes_categorical = [classes_categorical_map[c] for c in classes]


def read_img(p: Path) -> npt.NDArray:
    return cast(npt.NDArray, cv2.imread(p, cv2.IMREAD_GRAYSCALE))


def get_pixel_count(p: Path):
    res = read_img(p).shape
    return res[0] * res[1]


# resolutions = [get_pixel_count(image) for image in images]
# print(set(classes))
#
# sb.displot(classes)
# plt.xticks(rotation=45)
# plt.show()
#
# sb.displot(resolutions, bins=500, kde=True)
# plt.show()
#
# exit(1)


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


def evaluate_model(dataXlist: list[npt.NDArray], dataYlist: list[int], n_folds=5):
    accuracies = []
    histories = []
    f1_scores = []
    recalls = []

    kfold = KFold(n_folds, shuffle=True, random_state=1)

    dataX = np.array(dataXlist)
    dataY = np.array(dataYlist)

    for train_index, test_index in kfold.split(dataX, dataY):
        model = define_model()

        trainX, testX = dataX[train_index], dataX[test_index]
        trainY, testY = dataY[train_index], dataY[test_index]

        # https://stackoverflow.com/questions/47665391/keras-valueerror-input-0-is-incompatible-with-layer-conv2d-1-expected-ndim-4
        trainX = trainX.reshape(-1, image_size, image_size, 1)
        testX = testX.reshape(-1, image_size, image_size, 1)

        # https://github.com/tensorflow/tensorflow/issues/65237
        history = model.fit(trainX, trainY, epochs=25, batch_size=100, validation_data=(testX, testY), verbose="1")

        _, acc = model.evaluate(testX, testY, verbose="1")
        y_pred = np.argmax(model.predict(testX), axis=1)

        f1 = f1_score(testY, y_pred, average="weighted")
        recall = recall_score(testY, y_pred, average="weighted")

        print(f"> Fold Accuracy: {acc * 100.0:.3f}% | F1: {f1:.3f} | Recall: {recall:.3f}")

        recalls.append(recall)
        f1_scores.append(f1)
        accuracies.append(acc)
        histories.append(history)
    return accuracies, histories, f1_scores, recalls


def summarize_diagnostics(histories: list):
    for i in range(len(histories)):
        # plot loss
        plt.subplot(211)
        plt.title("Loss")
        plt.plot(histories[i].history["loss"], color="blue", label="train")
        plt.plot(histories[i].history["val_loss"], color="orange", label="test")
        # plot accuracy
        plt.subplot(212)
        plt.title("Classification Accuracy")
        plt.plot(histories[i].history["accuracy"], color="blue", label="train")
        plt.plot(histories[i].history["val_accuracy"], color="orange", label="test")
    plt.show()


def summarize_performance(scores: list, f1_scores: list, recalls: list):
    # print summary
    print("Accuracy: mean=%.3f std=%.3f, n=%d" % (np.mean(scores) * 100, np.std(scores) * 100, len(scores)))
    print("F1: mean=%.3f std=%.3f, n=%d" % (np.mean(f1_scores) * 100, np.std(f1_scores) * 100, len(f1_scores)))
    print("Recall: mean=%.3f std=%.3f, n=%d" % (np.mean(recalls) * 100, np.std(recalls) * 100, len(recalls)))
    # plt.boxplot(scores)
    # plt.show()
    # plt.boxplot(f1_scores)
    # plt.show()
    # plt.boxplot(recalls)
    # plt.show()

def run_baseline_cnn():
    prepared_images = [normalize_image(p) for p in images]
    
    scores, histories, f1_scores, recalls = evaluate_model(prepared_images, classes_categorical)
    summarize_diagnostics(histories)
    summarize_performance(scores, f1_scores, recalls)



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

test_transform = transforms.Compose(
    [
        transforms.Resize(256),
        transforms.CenterCrop(224),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
    ]
)

split_point = 4500
train_image_tuples = list(zip([str(p) for p in images[:split_point]], classes_categorical[:split_point]))
test_image_tuples = list(zip([str(p) for p in images[split_point:]], classes_categorical[split_point:]))

train_data = torchvision.datasets.ImageFolder(root=".", transform=train_transform)
train_data.samples = train_image_tuples
train_data.imgs = train_image_tuples
train_data.classes = classes
train_data.class_to_idx = classes_categorical_map
train_data.targets = [s[1] for s in train_data.imgs]

test_data = torchvision.datasets.ImageFolder(root=".", transform=test_transform)
test_data.samples = test_image_tuples
test_data.imgs = test_image_tuples
test_data.classes = classes
test_data.class_to_idx = classes_categorical_map
test_data.targets = [s[1] for s in test_data.imgs]

print(len(train_data))
print(len(test_data))

train_loader = torch.utils.data.DataLoader(train_data, batch_size=32, shuffle=True, num_workers=12)
test_loader = torch.utils.data.DataLoader(test_data, batch_size=32, shuffle=False, num_workers=12)

# Sanity checks
all_targets = [s[1] for s in train_data.samples]
if min(all_targets) < 0 or max(all_targets) >= len(classes_categorical_map):
    print(f"Error: Label out of range! Range: {min(all_targets)}-{max(all_targets)}")
    exit(1)

for inputs, labels in train_loader:
    if torch.isnan(inputs).any():
        print("Detected NaN in input batch!")
        exit(1)

def run_torch_generic(lr: float, momentum: float, num_epochs: int, model: MaxVit | VisionTransformer | ResNet):
    
    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    model = model.to(device)
    
    criterion = nn.CrossEntropyLoss()
    optimizer = optim.SGD(model.parameters(), lr=lr, momentum=momentum)

    train_losses = []
    test_losses = []
    f1_scores = []
    recall_scores = []
    accuracies = []
    
    # Train the model
    for epoch in range(num_epochs):
        model.train()
        train_loss = 0.0
        for inputs, labels in train_loader:
            # Move the data to the device
            inputs = inputs.to(device)
            labels = labels.to(device)

            # Zero the parameter gradients
            optimizer.zero_grad()

            # Forward + backward + optimize
            outputs = model(inputs)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            # Update the training loss
            train_loss += loss.item() * inputs.size(0)

        # Evaluate the model on the test set
        model.eval()
        test_loss = 0.0
        test_acc = 0.0
        
        all_preds = []
        all_labels = []
        
        with torch.no_grad():
            for inputs, labels in test_loader:
                # Move the data to the device
                inputs = inputs.to(device)
                labels = labels.to(device)

                # Forward
                outputs = model(inputs)
                loss = criterion(outputs, labels)

                # Update the test loss and accuracy
                test_loss += loss.item() * inputs.size(0)
                _, preds = torch.max(outputs, 1)
                test_acc += (preds == labels).sum().item()
                
                # Store for F1/Recall
                all_preds.extend(preds.cpu().numpy())
                all_labels.extend(labels.cpu().numpy())

        # Print the training and test loss and accuracy
        train_loss /= len(train_data)
        test_loss /= len(test_data)
        test_acc /= len(test_data)
        
        epoch_f1 = f1_score(all_labels, all_preds, average='weighted')
        epoch_recall = recall_score(all_labels, all_preds, average='weighted')

        print(f"Epoch [{epoch + 1}/{num_epochs}]")
        print(f"Loss: [Train: {train_loss:.4f}, Test: {test_loss:.4f}]")
        print(f"Metrics: [Acc: {test_acc:.4f}, F1: {epoch_f1:.4f}, Recall: {epoch_recall:.4f}]")
        print("-" * 30)

        train_losses.append(train_loss)
        test_losses.append(test_loss)
        accuracies.append(float(test_acc))
        recall_scores.append(float(epoch_recall))
        f1_scores.append(float(epoch_f1))

    sb.lineplot(train_losses)
    sb.lineplot(test_losses)

    plt.show()

    sb.lineplot(accuracies)
    plt.show()

    sb.lineplot(f1_scores)
    plt.show()

    sb.lineplot(recall_scores)
    plt.show()

def run_resnet():
    print("Runnnig Resnet")
    
    model = resnet50(weights=ResNet50_Weights.IMAGENET1K_V2)

    num_features = model.fc.in_features
    model.fc = nn.Linear(num_features, len(classes_categorical_map))

    run_torch_generic(0.0005, 0.9, 10, model)

def run_vit():
    print("Running VIT")

    vit_model = vit_b_16(weights=ViT_B_16_Weights.IMAGENET1K_V1)

    num_features = cast(nn.Linear, vit_model.heads.head).in_features
    vit_model.heads.head = nn.Linear(num_features, len(classes_categorical_map))

    run_torch_generic(0.0005, 0.9, 10, vit_model)
    
def run_maxvit():
    print("Running MaxViT (Hybrid)...")

    model = maxvit_t(weights=MaxVit_T_Weights.IMAGENET1K_V1)

    def remove_stochastic_depth(model):
        for name, child in model.named_children():
            if isinstance(child, ops.StochasticDepth):
                setattr(model, name, nn.Identity())
            else:
                remove_stochastic_depth(child)

    # These layers cause crashes, remove them
    remove_stochastic_depth(model)

    num_ftrs = cast(nn.Linear, model.classifier[-1]).in_features
    model.classifier[-1] = nn.Linear(num_ftrs, len(classes_categorical_map))

    run_torch_generic(0.0005, 0.9, 10, model)

run_maxvit()
# run_vit()
# run_baseline_cnn()
# run_resnet()
