#include "integer.h"

void Integer::invertBits(char arr[], int start, int end) {
	for (int i = start; i < end; i++) {
		
		if (arr[i] == '0') {
			arr[i] = '1';
		}
		else
			arr[i] = '0';
	}
}

void Integer::setBinary()
{
    int n;
    cout << "Enter number: ";
    cin >> n;

	int absn = abs(n);
	for (int i = SIZE - 1; i > 0; i--) 
        {
		int token = absn % 2;
		Arr[i] = (token == 1 ? '1' : '0');
        absn /= 2;
        if (absn == 0)
        {
            break;
        }
    }

	// If n is a negative number
	if (n < 0) {
		Arr[0] = '1';
		invertBits(Arr, 1, SIZE);

		int carry = 1;
		for (int i = SIZE - 1; i >= 0; i--) {

			if (Arr[i] == '1' && carry == 1) {
				Arr[i] = '0';
			}
			else if (Arr[i] == '0' && carry == 1) {
				Arr[i] = '1';
				carry = 0;
			}
			else return;
		}
	}
}

void Integer::setOnComp() {
	for (int i = 0; i < SIZE; i++) {
		onComp[i] = Arr[i];
	}

	invertBits(onComp, 0, SIZE);
}

void Integer::setTwComp() {

	setOnComp();

	// +1 to last bit
	int carry = 1;
	for (int i = SIZE - 1; i >= 0; i--) {

		if (onComp[i] == '1' && carry == 1) {
			twComp[i] = '0';
		}
		else if (onComp[i] == '0' && carry == 1) {
			twComp[i] = '1';
			carry = 0;
		}
		else {
			twComp[i] = onComp[i];
		}
	}

}

void Integer::printBinary()
{
	cout << " A (Binary): ";

	for (int i = 0; i < SIZE; i++) {
		cout << Arr[i];
		if (i == 7) cout << " ";
	}
	cout << endl;
}

void Integer::printTwoComplement() {
	cout << "-A (Binary): ";

	for (int i = 0; i < SIZE; i++) {
		cout << twComp[i];
		if (i == SIZE / 2 - 1) {
			cout << " ";
		}
	}
	cout << endl;
}


int Integer::get2CompDecimal() {
	int dec = 0;

	char tmp[SIZE];
	for (int i = 0; i < SIZE; i++) {
		tmp[i] = twComp[i];
	}

	bool neg = false;
	if (tmp[0] == '1') {
		invertBits(tmp, 0, SIZE);
		neg = true;
	
	
		int carry = 1;
		for (int i = SIZE - 1; i >= 0; i--) {
			if (tmp[i] == '1' && carry == 1) {
				tmp[i] = '0';
			}
			else if (tmp[i] == '0' && carry == 1) {
				tmp[i] = '1';
				carry = 0;
			}
			else break;

		}
	}

	int token = 0;
	for (int i = SIZE - 1; i > 0; i--) {
		token = (tmp[i] == '1' ? 1 : 0);
		dec += token * pow(2, SIZE -1 - i);
	}

	if (neg)
		return -dec;
	return dec;
}




