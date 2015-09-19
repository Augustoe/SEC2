//运用Radix-4 Booth计算32位整数的乘法
package radix4;

public class radix4 {
	public static String radix4(int a, int b) {
		String op1 = integerRepresentation(a, 32);
		String op2 = integerRepresentation(b, 32);

		String re = integerMultiplication(op1, op2, 64);
		return re;
	}

	public static String integerRepresentation(int number, int length) {
		char[] words = new char[length];

		for (int i = 1; i <= length; i++) {
			if ((number & (1 << (i - 1))) != 0)
				words[length - i] = '1';
			else
				words[length - i] = '0';
		}
		String numberin2 = String.valueOf(words);

		return numberin2;
	}

	public static String integerMultiplication(String operand1,
			String operand2, int length) {

		char[] temp = new char[2 * length];
		int length1 = operand1.length();
		int length2 = operand2.length();
		char[] x = new char[length];
		char[] y = new char[length + 1]; // y0=0;
		// String[] S = new String[count];
		int i = 0;
		if (operand1.charAt(0) == '1') {
			for (; i < (length); i++) {
				x[i] = '1';
			}
		} else {
			for (i = 0; i < (length); i++) {
				x[i] = '0';
			}
		}
		for (i = 0; i < length1; i++) {
			x[i + length - length1] = operand1.charAt(i);
		}

		if (operand2.charAt(0) == '1') {
			for (i = 0; i < (length); i++)
				y[i] = '1';
		} else {
			for (i = 0; i < (length); i++) {
				y[i] = '0';
			}
		}
		// System.out.println(String.valueOf(y));

		for (i = 0; i < length2; i++) {
			y[i + length - length2] = operand2.charAt(i);
		}
		y[length] = '0';

		// System.out.println(String.valueOf(y));

		for (i = 0; i < 2 * length; i++) {
			temp[i] = '0';
		}

		String S = null;
		for (i = 0; i < (length / 2); i++) {
			String assis1 = "";
			String assis2 = "";
			int j;
			for (j = 0; j < length; j++) {
				assis1 = assis1 + temp[j];
				assis2 = assis2 + x[j];
			}

			// System.out.println(assis1+"\n"+assis2);
			int dt = (-2 * y[length - 2 * i - 2]) + y[length - 2 * i - 1]
					+ y[length - 2 * i];
			if (dt > 0) {
				if (dt == 2) {
					assis2 = leftShift(assis2, 1);
				}
				S = integerAddition(assis1, assis2, '0', length);
				for (int v = 0; v < length; v++) {
					temp[v] = S.charAt(v);
				}

				//
				// System.out.println("ADD!!!!!!!!!!!!!!");
				// System.out.println(S);
				// System.out.println(String.valueOf(temp));

				// System.out.println(String.valueOf(temp));
			} else if (dt < 0) {
				if (dt == (-2)) {
					assis2 = leftShift(assis2, 1);
				}
				S = integerSubtraction(assis1, assis2, length);
				for (int v = 0; v < length; v++) {
					temp[v] = S.charAt(v);

				}

				// System.out.println("SUB!!!!!!!!!!!!!!!!!!!!!!!!!!");
				// System.out.println(S);
				// System.out.println(String.valueOf(temp));
			} else {
				// System.out.println("The temp I want:"+String.valueOf(temp));
			}

			int k;
			for (k = 2 * length - 3; k >= 0; k--) {
				temp[k + 2] = temp[k];
			}
			temp[1] = temp[0];
			// System.out.println("aAAAAAA");
			// System.out.println(String.valueOf(temp));

		}

		String result = String.valueOf(temp);
		return result;
	}

	public static String integerAddition(String operand1, String operand2,
			char c, int length) {
		int count = length / 8;
		if ((length % 8) != 0) {
			count++;
		}
		int length1 = operand1.length();
		int length2 = operand2.length();
		String[] o1 = new String[count];
		String[] o2 = new String[count];
		char[] x = new char[8 * count];
		char[] y = new char[8 * count];
		String[] S = new String[count];
		int i = 0;
		if (operand1.charAt(0) == '1') {
			for (; i < (8 * count); i++) {
				x[i] = '1';
			}
		} else {
			for (i = 0; i < (8 * count); i++) {
				x[i] = '0';
			}
		}
		for (i = 0; i < length1; i++) {
			x[i + 8 * count - length1] = operand1.charAt(i);
		}

		if (operand2.charAt(0) == '1') {
			for (i = 0; i < (8 * count); i++)
				y[i] = '1';
		} else {
			for (i = 0; i < (8 * count); i++) {
				y[i] = '0';
			}
		}
		for (i = 0; i < length2; i++) {
			y[i + 8 * count - length2] = operand2.charAt(i);
		}

		String t;
		for (i = 0; i < count; i++) {
			o1[i] = "";
			o2[i] = "";
			int j = 0;
			for (; j < 8; j++) {
				t = "" + x[8 * i + j];
				o1[i] = o1[i] + t;
				t = "" + y[8 * i + j];
				o2[i] = o2[i] + t;
			}
		}

		for (i = count - 1; i >= 0; i--) {
			S[i] = claAdder(o1[i], o2[i], c);
			c = S[i].charAt(8);
		}
		String result = "";
		int j;
		String k = "";
		for (i = 0; i < count; i++) {
			for (j = 0; j < 8; j++) {
				result = result + S[i].charAt(j);
			}
		}
		if ((x[0] == y[0]) && (x[0] != result.charAt(0))) {
			result = result + '1';
		} else
			result = result + '0';
		result = result.substring(8 * count - length, 8 * count + 1);
		return result;
	}

	// 14
	public static String integerSubtraction(String operand1, String operand2,
			int length) {
		operand2 = negation(operand2);
		String result = integerAddition(operand1, operand2, '1', length);
		return result;
	}

	public static String claAdder(String operand1, String operand2, char c) {
		String result = null;
		char[] x = new char[8];
		char[] y = new char[8];
		int length1 = operand1.length();
		int length2 = operand2.length();
		int i = 0;
		if (operand1.charAt(0) == '1') {
			for (; i < 8; i++) {
				x[i] = '1';
			}
		} else {
			for (i = 0; i < 8; i++) {
				x[i] = '0';
			}
		}
		for (i = 0; i < length1; i++) {
			x[i + 8 - length1] = operand1.charAt(i);
		}
		if (operand2.charAt(0) == '1') {
			for (i = 0; i < 8; i++)
				y[i] = '1';
		} else {
			for (i = 0; i < 8; i++) {
				y[i] = '0';
			}
		}
		for (i = 0; i < length2; i++) {
			y[i + 8 - length2] = operand2.charAt(i);
		}

		i = 7;
		String[] temp = new String[8];
		char[] s = new char[8];
		for (; i > -1; i--) {
			temp[i] = fullAdder(x[i], y[i], c);
			c = temp[i].charAt(1);
			s[i] = temp[i].charAt(0);
		}
		result = String.valueOf(s) + String.valueOf(c);
		return result;
	}

	public static String fullAdder(char x, char y, char c) {
		String result = null;
		String S;
		String C;
		if (((x == '1') && (y == '1')) || ((c == '1') && (y == '1'))
				|| ((x == '1') && (c == '1')))
			C = "1";
		else
			C = "0";
		if (((x == '1') && (y == '1') && (c == '1'))
				|| ((x == '1') && (y == '0') && (c == '0'))
				|| ((x == '0') && (y == '1') && (c == '0'))
				|| ((x == '0') && (y == '0') && (c == '1')))
			S = "1";
		else
			S = "0";
		result = S + C;
		return result;
	}

	public static String negation(String operand) {
		char[] number = operand.toCharArray();
		int length = operand.length();
		int i = 0;
		for (; i < length; i++) {
			if (number[i] == '1')
				number[i] = '0';
			else
				number[i] = '1';
		}
		String result = String.valueOf(number);
		return result;
	}

	public static String leftShift(String operand, int n) {
		char[] number = operand.toCharArray();
		int length = operand.length();
		char[] newnumber = new char[length];
		int i = 0;

		for (; i < length; i++) {
			newnumber[i] = '0';
		}
		for (i = 0; i < length - n; i++) {
			newnumber[i] = number[i + n];
		}

		String result = String.valueOf(newnumber);
		return result;
	}
}
