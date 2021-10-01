
#define NOMINMAX
#include <windows.h>
#include <iostream>
#include <conio.h>
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <limits>
#include <string>
#include <time.h>
using namespace std;

int *dynamic_array;
int dynamic_array_size;

double inputData(string message) {
    cout << message << flush;
    double toReturn;
    while (!(cin >> toReturn)) {
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << "����������, ����������� �����" << endl;
    }
    return toReturn;
}

void setConsoleColor(int color) {
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), color);
}

void coutWithColor(int color, string message) {
    setConsoleColor(color);
    cout << message << flush;
    setConsoleColor(7);
}

void setConsoleCursorPosition(int x, int y) {
    COORD c;
    c.X = x;
    c.Y = y;
    SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), c);
}

COORD getConsoleCursorPosition()
{
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &csbi);
    return csbi.dwCursorPosition;
}

int displaySelection(string* options, int optionCount) {

    int offset = getConsoleCursorPosition().Y;
    int counter = 0;
    int key = 0;

    for (int i = 0; i < optionCount; i++) {
        setConsoleCursorPosition(0, offset + i);
        cout << options[i];
    }

    while (true) {
        if (key == 72){
            counter--;
            if (counter < 0) {
                counter = optionCount - 1;
            }
        }
        if (key == 80){
            counter++;
            if (counter > optionCount - 1) {
                counter = 0;
            }
        }
        for (int i = 0; i < optionCount; i++) {
            if (abs(counter - i) <= 1 || i == 0 || i == optionCount - 1) {
                setConsoleCursorPosition(0, offset + i);
                setConsoleColor(counter == i ? 12 : 7);
                cout << options[i];
            }
        }
        key = _getch();
        if (key == 224) {
            key = _getch();
        }
        if (key == '\r') {
            coutWithColor(8, "\n�� �������: " + options[counter] + "\n");
            return counter + 1;
        }
    }
}

void fillArray(bool manual) {
    for (int i = 0; i < dynamic_array_size; i++) {
        if (manual) {
            dynamic_array[i] = (int)inputData("");
        }
        else {
            dynamic_array[i] = rand() % 10 - 4;
            cout << dynamic_array[i] << " ";
        }
    }
    if (manual) {
        cout << "��� ������(��������� ������������): ";
        for (int i = 0; i < dynamic_array_size; i++) {
            cout << dynamic_array[i] << " ";
        }
    }
    cout << endl;
}

void initializeArray (int size_double) {
    int size = (int)size_double;
    if (size_double == 1) {
        cout << "������ ����� 1, �� ��� ������ �� ��������, ���� ����-�� 3" << endl;
        size = 3;
    }
    if (size_double <= 0) {
        cout << "������ ������������� ��� ����� ����, ��������� ������ 10" << endl;
        size = 10;
    }
    if (size_double > 1000000) {
        cout << "������ ������� ������� �������, ��������� ������ 1000000" << endl;
        size = 1000000;
    }
    dynamic_array = (int*)malloc(size * 4 + 1);
    dynamic_array_size = size;
    coutWithColor(14, "\n��� �� �� ������ ������������������� ������?\n");
    switch (displaySelection(new string[2]{ "1. ���������� �������", "2. �������" }, 2)) {
    case 1:
        cout << "�������� ��� ������ ���������� �������..." << endl;
        fillArray(false);
        break;
    case 2:
        cout << "                           ������ = ";
        fillArray(true);
        break;
    }
}

int findMinElementIndex(bool absolute) {
    int minIndex = 0;
    int minElement = dynamic_array[0];
    for (int i = 1; i < dynamic_array_size; i++) {
        int currElem = absolute ? abs(dynamic_array[i]) : dynamic_array[i];
        if (currElem < minElement) {
            minElement = currElem;
            minIndex = i;
        }
    }
    coutWithColor(3, "����� ��������� " + string(absolute ? "�� ������ " : "") + "�����: " + to_string(minElement) + "(������ " + to_string(minIndex) + ")\n");
    return minIndex;
}

int findMaxElementIndex() {
    int maxIndex = 0;
    int maxElement = dynamic_array[0];
    for (int i = 1; i < dynamic_array_size; i++) {
        if (dynamic_array[i] > maxElement) {
            maxElement = dynamic_array[i];
            maxIndex = i;
        }
    }
    coutWithColor(3, "����� ������� �����: " + to_string(maxElement) + "(������ " + to_string(maxIndex) + ")\n");
    return maxIndex;
}

int findLastZeroElementIndex() {
    for (int i = dynamic_array_size - 1; i >= 0; i--) {
        if (dynamic_array[i] == 0) {
            coutWithColor(3, "������ ���������� �������� ��������: "+to_string(i) + "\n");
            return i;
        }
    }
    return -2;
}

int findFirstZeroElementIndex() {
    for (int i = 0; i < dynamic_array_size; i++) {
        if (dynamic_array[i] == 0) {
            coutWithColor(3, "������ ������� �������� ��������: " + to_string(i) + "\n");
            return i;
        }
    }
    return -2;
}

int findSecondZeroElementIndex() {
    bool firstZeroFound = false;
    for (int i = 0; i < dynamic_array_size; i++) {
        if (dynamic_array[i] == 0) {
            if (firstZeroFound) {
                coutWithColor(3, "������ ������� �������� ��������: " + to_string(i) + "\n");
                return i;
            }
            firstZeroFound = true;
        }
    }
    return -2;
}

int findLastPositiveElementIndex() {
    for (int i = dynamic_array_size - 1; i >= 0; i--) {
        if (dynamic_array[i] > 0) {
            coutWithColor(3, "������ ���������� �������������� ��������("+ to_string(dynamic_array[i]) + "): " + to_string(i) + "\n");
            return i;
        }
    }
    return -2;
}

int findFirstPositiveElementIndex() {
    for (int i = 0; i < dynamic_array_size; i++) {
        if (dynamic_array[i] > 0) {
            coutWithColor(3, "������ ������� �������������� ��������(" + to_string(dynamic_array[i]) + "): " + to_string(i) + "\n");
            return i;
        }
    }
    return -2;
}

int findFirstNegativeElementIndex() {
    for (int i = 0; i < dynamic_array_size; i++) {
        if (dynamic_array[i] < 0) {
            coutWithColor(3, "������ ������� �������������� ��������(" + to_string(dynamic_array[i]) + "): " + to_string(i) + "\n");
            return i;
        }
    }
    return -2;
}

int findLastNegativeElementIndex() {
    for (int i = dynamic_array_size - 1; i >= 0; i--) {
        if (dynamic_array[i] < 0) {
            coutWithColor(3, "������ ���������� �������������� ��������(" + to_string(dynamic_array[i]) + "): " + to_string(i) + "\n");
            return i;
        }
    }
    return -2;
}

int findSecondNegativeElementIndex() {
    bool secondZeroFound = false;
    for (int i = 0; i < dynamic_array_size; i++) {
        if (dynamic_array[i] < 0) {
            if (secondZeroFound) {
                coutWithColor(3, "������ ������� �������������� ��������(" + to_string(dynamic_array[i]) + "): " + to_string(i) + "\n");
                return i;
            }
            secondZeroFound = true;
        }
    }
    return -2;
}

void checkIndexes(int& index1, int& index2) {
    if (index1 < -1 || index2 < -1) {
        throw "���� �� �������� �������������(��� �����, ����������� ��� �������� � �������)";
    }
    if (index1 == index2) {
        throw "��� ��������� ����� ��������� ���������, ������� ��������";
    }
    if (abs(index2 - index1) == 1) {
        throw "��� ��������� ����� ��������� ���������, ������� �����";
    }
    if (index1 > index2) {
        swap(index1, index2);
    }
}

double caclulateSumBetweenTwoIndexes(int index1, int index2, bool absolute) {
    checkIndexes(index1, index2);
    double sum = 0;
    for (int i = index1 + 1; i < index2; i++) {
        sum += absolute ? abs(dynamic_array[i]) : dynamic_array[i];
    }
    return sum;
}

double caclulateProductBetweenTwoIndexes(int index1, int index2) {
    checkIndexes(index1, index2);
    double product = 1;
    for (int i = index1 + 1; i < index2; i++) {
        product *= dynamic_array[i];
    }
    return product;
}

void printResult(double result) {
    coutWithColor(10, "���������: " + to_string(result) + "\n");
}

int main()
{
    srand((UINT)time(NULL));
    setlocale(0, "RU");

    while (true) {
        initializeArray((int)inputData("������� ������ �������: "));
        coutWithColor(14, "\n�������� ��� ���������\n");
        try
        {
            switch (displaySelection(new string[16]{
            " 1. ������������ ��������� �������, ������������� ����� ������������ � ����������� ����������",
            " 2. ����� ��������� �������, ������������� ����� ������ � ��������� �������� ����������",
            " 3. ����� ��������� �������, ������������� �� ���������� �������������� ��������",
            " 4. ����� ��������� �������, ������������� ����� ������ � ��������� �������������� ����������",
            " 5. ������������ ��������� �������, ������������� ����� ������ � ������ �������� ����������",
            " 6. ����� ��������� �������, ������������� ����� ������ � ������ �������������� ����������",
            " 7. ����� ��������� �������, ������������� �� ������������ ��������",
            " 8. ����� ������� ��������� �������, ������������� ����� ���������� �������������� ��������",
            " 9. ����� ��������� �������, ������������� ����� ���������� ��������, ������� ����",
            "10. ����� ������� ��������� �������, ������������� ����� ������������ �� ������ ��������",
            "11. ����� ��������� �������, ������������� ����� ������������ ��������",
            "12. ����� ��������� �������, ������������� ����� ������� �������������� ��������",
            "13. ����� ������� ��������� �������, ������������� ����� ������� �������������� ��������",
            "14. ����� ������� ��������� �������, ������������� ����� ������� ��������, ������� ����",
            "15. ����� ������������� ��������� �������, ������������� �� ������������� ��������",
            "16. ������������ ��������� �������, ������������� ����� ������ � ��������� �������������� ����������"}, 16)) {
            case 1:
                printResult(caclulateProductBetweenTwoIndexes(findMaxElementIndex(), findMinElementIndex(false)));
                break;
            case 2:
                printResult(caclulateSumBetweenTwoIndexes(findFirstZeroElementIndex(), findLastZeroElementIndex(), false));
                break;
            case 3:
                printResult(caclulateSumBetweenTwoIndexes(-1, findLastPositiveElementIndex(), false));
                break;
            case 4:
                printResult(caclulateSumBetweenTwoIndexes(findFirstPositiveElementIndex(), findLastPositiveElementIndex(), false));
                break;
            case 5:
                printResult(caclulateProductBetweenTwoIndexes(findFirstZeroElementIndex(), findSecondZeroElementIndex()));
                break;
            case 6:
                printResult(caclulateSumBetweenTwoIndexes(findFirstNegativeElementIndex(), findSecondNegativeElementIndex(), false));
                break;
            case 7:
                printResult(caclulateSumBetweenTwoIndexes(-1, findMinElementIndex(false), false));
                break;
            case 8:
                printResult(caclulateSumBetweenTwoIndexes(findLastNegativeElementIndex(), dynamic_array_size, true));
                break;
            case 9:
                printResult(caclulateSumBetweenTwoIndexes(findLastZeroElementIndex(), dynamic_array_size, false));
                break;
            case 10:
                printResult(caclulateSumBetweenTwoIndexes(findMinElementIndex(true), dynamic_array_size, true));
                break;
            case 11:
                printResult(caclulateSumBetweenTwoIndexes(findMinElementIndex(false), dynamic_array_size, false));
                break;
            case 12:
                printResult(caclulateSumBetweenTwoIndexes(findFirstPositiveElementIndex(), dynamic_array_size, false));
                break;
            case 13:
                printResult(caclulateSumBetweenTwoIndexes(findFirstNegativeElementIndex(), dynamic_array_size, true));
                break;
            case 14:
                printResult(caclulateSumBetweenTwoIndexes(findFirstZeroElementIndex(), dynamic_array_size, true));
                break;
                case 15:{
                    int index1 = -1;
                    int index2 = findMaxElementIndex();
                    checkIndexes(index1, index2);
                    double sum = 0;
                    for (int i = index1 + 1; i < index2; i++) {
                        if (dynamic_array[i] > 0) {
                            sum += dynamic_array[i];
                        }
                    }
                    printResult(sum);
                }
                break;
            case 16:
                printResult(caclulateProductBetweenTwoIndexes(findFirstNegativeElementIndex(), findLastNegativeElementIndex()));
                break;
            }
        }
        catch (const char* message)
        {
            setConsoleColor(4);
            cerr << message << endl;
            setConsoleColor(7);
        }

        free(dynamic_array);

        cout << "����������?" << endl;
        string input;
        cin >> input;
        if (!(input == "yes" || input == "y" || input == "1")) {
            break;
        }
    }
    return 1;
}
