package org.example;

public class MyNoFibonacciException extends RuntimeException {
    public MyNoFibonacciException(int i, int j, String val) {
        super("Число " + val + " на " + i + " строке и " + j + " столбце не является числом Фибоначчи в пределах тысячи");
    }
}
