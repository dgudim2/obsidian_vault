#include <iostream>
#include <math.h>
#include <string>
#define NOMINMAX
#include <windows.h>
using namespace std;

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

string addSpaces(string input, int targetLength) {
    int spaces = targetLength - input.length();
    for (int i = 0; i < spaces; i++) {
        input.append(" ");
    }
    return input;
}

long double calculateFunction(double x) {
    return (1 - x * x / 2) * cos(x) - x / 2 * sin(x);
}

long double calculateSum(double x, int n) {
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
        double from = inputData("������� ��������� x: ");
        double to = inputData("������� �������� x: ");
        double step = inputData("������� ���: ");
        int n = (int)inputData("������� n ��� �����: ");
        if (from > to) {
            cout << "��������� ������ �� ����� ���� ������ ���������, ����� �������" << endl;
            swap(from, to);
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
            cout << "��������� ��� �������� x ������ 700 ��� n >= 5, �������� ������������" << endl;
            Sleep(2000);
        }
        if (n > 70) {
            cout << "���������� ������ � ����� ������ 70, �������� ������������" << endl;
            Sleep(2000);
        }
        long double current_sum, current_function;
        int maxFuncLen = 15;
        int maxSumLen = 15;
        for (double i = from; i <= to; i += step) {
            current_sum = calculateSum(i, n);
            current_function = calculateFunction(i);
            maxSumLen = max((int)to_string(current_sum).length(), maxSumLen);
            maxFuncLen = max((int)to_string(current_function).length(), maxFuncLen);
            cout << "x = " << addSpaces(to_string(i), max(to_string(from).length(), to_string(to).length()) + 3) << "Y(x) = " << addSpaces(to_string(current_function), maxFuncLen) << " S(x) = " << addSpaces(to_string(current_sum), maxSumLen) << " |S(x)-Y(x)| = " << to_string(abs(current_sum - current_function)) << endl;
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