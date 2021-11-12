#include "integer.h"

int main(){
    Integer n;
   
    n.setBinary();
    n.printBinary();
    n.setTwComp();
    n.printTwoComplement();
    cout << "-A (Decimal): " << n.get2CompDecimal() << endl;

}