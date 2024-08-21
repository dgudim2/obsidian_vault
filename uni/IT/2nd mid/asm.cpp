#include <iostream>
using namespace std;

extern "C" int f_asm(int from, int to);
asm (R"(
    .global f_asm

loop:
    movl %eax, %ecx   # copy eax - i to ecx
    movl %eax, %edx   # copy eax - i to edx, will be used to get the cube
    imul %ecx, %ecx   # square
    imul %edx, %ecx   # cube
    imul $3,   %ecx   # multiply by b (3)
    add  $2,   %ecx   # add a (2)

    add  %ecx, %esi   # accumulate
    cmp  $0, %esi
    jl overflow  # our accumulator is negative, exit (overflow)

    cmp  %eax, %ebx   # compare our current iteration with end
    je   end          # our iteration is equal to end value, exit function
    inc  %eax         # increment eax
    jmp loop

f_asm:
    movl %edi, %eax   # copy edi - 'from', to eax, this will be our current i value
    movl %esi, %ebx   # copy esi - 'to',   to ebx
    movl $0,   %esi   # reset accumulator
    cmp  %ebx, %eax   # compare n witn m
    jle loop          # m >= n
    movl $1, %eax     # m < n, return 1
    ret

end:
    movl %esi, %eax   #copy our accumulator to eax for returning
    ret

overflow:
    movl $2147483647, %eax # return max value
    ret

)");

int main() {

    int from = 0; // n
    int to = 10;  // m

    int res = f_asm(from, to);

    cout << res << "\n\n";

    return 0;
} 
