#include <iostream>
#include <math.h>
#include <string>
#include <conio.h>
#include <sstream>
#include <iomanip>
#define NOMINMAX
#include <windows.h>
using namespace std;

typedef long double (*CalcFuncPointer)(long double);

long double defaultFunction(long double x) {
    return (1 - x * x / 2) * cos(x) - x / 2 * sin(x);
}

long double expFunction(long double x) {
    return 2 * exp(x);
}

long double sin2Function(long double x) {
    return sin(x) * sin(x);
}

long double inputData(string message) {
    cout << message << flush;
    long double toReturn;
    while (!(cin >> toReturn)) {
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        cout << "����������, ����������� �����" << endl;
    }
    return toReturn;
}

string doubleToString(long double value){
    ostringstream out;
    out.precision(9);
    out << std::fixed << value;
    string strOut = out.str();
    char currChar = strOut[strOut.length() - 1];
    while ((currChar == '0' || currChar == '.') && strOut.length() > 1) {
        strOut.erase(strOut.length() - 1, 1);
        if (currChar == '.') {
            break;
        }
        currChar = strOut[strOut.length() - 1];
    }
    return strOut;
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
        if (key == 72) {
            counter--;
            if (counter < 0) {
                counter = optionCount - 1;
            }
        }
        if (key == 80) {
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

string addSpaces(string input, int targetLength) {
    int spaces = targetLength - input.length();
    for (int i = 0; i < spaces; i++) {
        input.append(" ");
    }
    return input;
}

long double calculateSum(long double x, int n) {
    if (n == 0) {
        return 1;
    }
    long double sum = 1 - x * x * 1.5;
    if (n == 1) {
        return sum;
    }
    long double intermediate_top = 0;
    char curr_sign = -1;
    long double curr_bottom = 2;
    long curr_bottom_fact_index = 2;
    long double curr_x = x;
    for (int i = 2; i <= n; i++) {
        intermediate_top = 2 * i * i + 1;
        for (int f = curr_bottom_fact_index + 1; f <= i * 2; f++) {
            curr_bottom *= f;
        }
        curr_sign *= -1;
        curr_x *= x;
        sum += curr_sign * intermediate_top * curr_x * curr_x / curr_bottom;
    }
    return sum;
}

int main() {

    setlocale(0, "RU");

    while (true) {
        long double from = inputData("������� ��������� x: ");
        long double to = inputData("������� �������� x: ");
        long double step = inputData("������� ���: ");
        int n = (int)inputData("������� n ��� �����: ");
        if (from > to && step > 0) {
            cout << "��������� ������ �� ����� ���� ������ ���������, ����� �������" << endl;
            swap(from, to);
        }
        if (step == 0) {
            cout << "��� ����� 0, ���������� ������� �� ����������, ������ ��� = 0.1" << endl;
            step = 0.1;
        }
        if (from == to) {
            cout << "��������� ������ ����� ���������, ������������ �������� ������ �� 1 ������ ����������" << endl;
            to++;
        }
        if (n < 0) {
            cout << "���������� ������ � ����� �� ����� ���� ������ 0, ��������� ��������� ����������: 3 �����" << endl;
            n = 3;
        }
        if ((abs(to) > 700 || abs(from) > 700) && n >= 5) {
            coutWithColor(4, "��������� ��� �������� x ������ 700 �� ������ ��� n >= 5, �������� ������������\n");
        }
        if (n > 70) {
            coutWithColor(4, "���������� ������ � ����� ������ 70, �������� ������������ ��� ����� ������ ����������\n");
        }

        coutWithColor(14, "\n�������� ������� Y(x)\n");
        CalcFuncPointer func;
        switch (displaySelection(new string[6]{ "1. (1 - x^2 / 2) * cos(x) - x / 2*sin(x)", "2. 2*exp(x)", "3. sin(x)^2", "4. sin(x)" , "5. cos(x)" , "6. ���������� ������ �� x" }, 6)) {
            case(1):
            default:
                func = defaultFunction;
                break;
            case(2):
                func = expFunction;
                break;
            case(3):
                func = sin2Function;
                break;
            case(4):
                func = sin;
                break;
            case(5):
                func = cos;
                break;
            case(6):
                func = cbrt;
                break;
        }

        long double current_sum, current_function;
        int maxFuncLen = 15;
        int maxSumLen = 15;
        for (long double i = from; i *(step < 0 ? -1 : 1) <= to * (step < 0 ? -1 : 1); i += step) {
            current_sum = calculateSum(i, n);
            current_function = func(i);
            maxSumLen = max((int)doubleToString(current_sum).length(), maxSumLen);
            maxFuncLen = max((int)doubleToString(current_function).length(), maxFuncLen);
            cout << "x = " << addSpaces(doubleToString(i), max(doubleToString(from).length(), doubleToString(to).length()) + 3) << "Y(x) = " << addSpaces(doubleToString(current_function), maxFuncLen) << " S(x) = " << addSpaces(doubleToString(current_sum), maxSumLen) << " |S(x)-Y(x)| = " << doubleToString(abs(current_sum - current_function)) << endl;
        }
        cout << "����������?" << endl;
        string input;
        cin >> input;
        if (!(input == "yes" || input == "y" || input == "1")) {
            break;
        }
    }
    return 1;
}