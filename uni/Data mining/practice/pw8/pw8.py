#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "seaborn",
#   "polars",
#   "opencv-python",
#   "tensorflow",
#   "sklearn"
# ]
# ///

from pathlib import Path
import seaborn as sb
import matplotlib.pyplot as plt
import cv2

from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Dense, Flatten
from keras.optimizers import SGD

from sklearn.model_selection import train_test_split

from sklearn.model_selection import KFold

import numpy as np

images = list(Path("images").glob('*'))
classes = ["_".join(image.stem.split('_')[:-1]).lower() for image in images]
classes_categorical_map = {
    'japanese_chin': 0,
    'wheaten_terrier': 1,
    'saint_bernard': 2,
    'english_cocker_spaniel': 3,
    'shiba_inu': 4,
    'yorkshire_terrier': 5,
    'birman': 6,
    'american_bulldog': 7,
    'american_pit_bull_terrier': 8, 
    'pug': 9,
    'english_setter': 10,
    'bombay': 11,
    'russian_blue': 12,
    'beagle': 13,
    'great_pyrenees': 14,
    'chihuahua': 15,
    'ragdoll': 16,
    'egyptian_mau': 17,
    'newfoundland': 18,
    'sphynx': 19,
    'havanese': 20,
    'maine_coon': 21,
    'keeshond': 22,
    'abyssinian': 23,
    'siamese': 24,
    'miniature_pinscher': 25,
    'staffordshire_bull_terrier': 26,
    'samoyed': 27,
    'basset_hound': 28,
    'bengal': 29,
    'boxer': 30,
    'pomeranian': 31,
    'leonberger': 32,
    'scottish_terrier': 33,
    'persian': 34,
    'german_shorthaired': 35,
    'british_shorthair': 36
}
classes_categorical = [classes_categorical_map[c] for c in classes]

def get_resolution(p: Path):
#    print(f"loading: {p}")
    return cv2.imread(p).shape[:2]

def get_pixel_count(p: Path):
    res = get_resolution(p)
    return res[0] * res[1]

resolutions = [get_pixel_count(image) for image in images]

print(set(classes))

sb.displot(classes)
plt.show()

sb.displot(resolutions, bins=500, kde=True)
plt.show()


def resize_with_pad(image: np.array, 
                    new_shape: tuple[int, int], 
                    padding_color: tuple[int] = (255, 255, 255)) -> np.array:
    """Maintains aspect ratio and resizes with padding.
    Params:
        image: Image to be resized.
        new_shape: Expected (width, height) of new image.
        padding_color: Tuple in BGR of padding color
    Returns:
        image: Resized image with padding
    """
    original_shape = (image.shape[1], image.shape[0])
    ratio = float(max(new_shape))/max(original_shape)
    new_size = tuple([int(x*ratio) for x in original_shape])
    image = cv2.resize(image, new_size)
    delta_w = new_shape[0] - new_size[0]
    delta_h = new_shape[1] - new_size[1]
    top, bottom = delta_h//2, delta_h-(delta_h//2)
    left, right = delta_w//2, delta_w-(delta_w//2)
    image = cv2.copyMakeBorder(image, top, bottom, left, right, cv2.BORDER_CONSTANT, value=padding_color)
    return image

def normalize_image(p: Path):
    img_np = resize_with_pad(cv2.imread(p), (350, 350)) / 255.0
    return img_np

def define_model():
    model = Sequential()
    model.add(Conv2D(32, (3, 3), activation='relu', kernel_initializer='he_uniform', input_shape=(28, 28, 1)))
    model.add(MaxPooling2D((2, 2)))
    model.add(Flatten())
    model.add(Dense(100, activation='relu', kernel_initializer='he_uniform'))
    model.add(Dense(10, activation='softmax'))
    # compile model
    opt = SGD(lr=0.01, momentum=0.9)
    model.compile(optimizer=opt, loss='categorical_crossentropy', metrics=['accuracy'])
    return model


def evaluate_model(dataX, dataY, n_folds=5):
    scores, histories = list(), list()
    kfold = KFold(n_folds, shuffle=True, random_state=1)

    for train_ix, test_ix in kfold.split(dataX):
        model = define_model()
        trainX, trainY, testX, testY = dataX[train_ix], dataY[train_ix], dataX[test_ix], dataY[test_ix]
        history = model.fit(trainX, trainY, epochs=10, batch_size=32, validation_data=(testX, testY), verbose=0)
        _, acc = model.evaluate(testX, testY, verbose=0)
        print('> %.3f' % (acc * 100.0))
        scores.append(acc)
        histories.append(history)
    return scores, histories

def summarize_diagnostics(histories: list):
    for i in range(len(histories)):
        # plot loss
        plt.subplot(211)
        plt.title('Cross Entropy Loss')
        plt.plot(histories[i].history['loss'], color='blue', label='train')
        plt.plot(histories[i].history['val_loss'], color='orange', label='test')
        # plot accuracy
        plt.subplot(212)
        plt.title('Classification Accuracy')
        plt.plot(histories[i].history['accuracy'], color='blue', label='train')
        plt.plot(histories[i].history['val_accuracy'], color='orange', label='test')
    plt.show()

def summarize_performance(scores: list):
    # print summary
    print('Accuracy: mean=%.3f std=%.3f, n=%d' % (np.mean(scores)*100, np.std(scores)*100, len(scores)))
    # box and whisker plots of results
    plt.boxplot(scores)
    plt.show()

prepared_images = [normalize_image(p) for p in images]

def run_test_harness():
    # X_train, X_test, y_train, y_test = train_test_split(prepared_images, classes, test_size=0.3, random_state=42)
    
    scores, histories = evaluate_model(prepared_images, classes)
    summarize_diagnostics(histories)
    summarize_performance(scores)

# entry point, run the test harness
run_test_harness()
