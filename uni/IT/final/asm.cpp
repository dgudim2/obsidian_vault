#include <iostream>
using namespace std;

extern "C" int f_asm(int x);
asm (R"(
    .global f_asm

loop:
    movl %ebx, %eax    #copy ebx - i to eax
    imul %eax, %eax    #square
    imul %ebx, %eax    #cube
    
    movl $5,   %ecx    #divisor (b)
    cdq                #Convert Double to Quad, needed for division
    idivl %ecx         #divide by ecx

    add  %eax, %esi    #accumulate to esi

    cmp  $0, %edx      #compare remainder with 0
    jne  ceil          #remainder is not 0, add 1
    jmp  continue_loop

ceil:
    add  $1, %esi      #add 1 to esi
    jmp continue_loop  #continue looping

continue_loop:
    cmp  %ebx, %edi    #compare our current iteration with x
    je   end           #our iteration is equal to end value, exit function
    inc  %ebx          #increment eax
    jmp  loop

f_asm:
    movl $0,   %ebx    #reset iteration
    movl $0,   %esi    #reset accumulator
    jmp loop          

end:
    movl %esi, %eax   #copy our accumulator to eax for returning
    add  $10,  %eax   #add f(0)
    ret

)");

int main() {

    cout << "Please input x: ";
    int x;
    cin >> x;
    cout << "\n";

    int res = f_asm(x);

    cout << " ---------------------------------- Results of the calculation -------------------------------------- \n";
    cout << " The recurrent function is f(x) = f(x-1) + ceil(x^3/b) where b = 5, and f(0) = 10 \n";
    cout << " X = " << x << "\n";
    cout << " The result of function: " << res << "\n";
    cout << " ---------------------------------------------------------------------------------------------------- \n";
    cout << " For the verification: \n";
    cout << "f(1) = 11\n";
    cout << "f(2) = 13\n";
    cout << "f(3) = 19\n";
    cout << "f(4) = 32\n";
    cout << "f(5) = 57\n";
    cout << "f(6) = 101\n";
    cout << "f(7) = 170\n";
    cout << "f(8) = 273\n";
    return 0;
} 

// maximum register value: 2147483647
// maximum value of x is: 454
// checked with
//for(int i = 1; i < 2147483647; i++) {
//    res = f_asm(i);
//    cout << res << "\n";
//    if(res < 0) {
//        break;
//    }
//}