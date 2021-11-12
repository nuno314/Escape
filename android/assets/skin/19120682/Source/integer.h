#define SIZE 16
#include <iostream>
#include <math.h>
using namespace std;


class Integer
{
public:
    Integer() {
        for (int i = 0; i < SIZE; i++) {
            Arr[i] = '0';
            onComp[i] = '0';
            twComp[i] = '0';
        }
    };
    char Arr[16];
    char onComp[16];
    char twComp[16];

    void invertBits(char arr[], int start, int end);
    
    void setBinary();
    void setTwComp();
    void setOnComp();

    void printBinary(); 
    void printTwoComplement();
    int get2CompDecimal();
};