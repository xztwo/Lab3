package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static double sum = 0; // Используем double для поддержки дробных чисел

    public static void main(String[] args) {
        // Массив с дробными числами и точками
        String[][] array1 = {
                {"98001.5", "3", "1", "1"},
                {"1", "1", "3f", "1"},  // Это значение выбросит MyArrayDataException
                {"1", "8", "3", "1"},
                {"1", "5", "3", "987"},
        };

        int[] fibbo = new int[17];
        fibbo[0] = 0;
        fibbo[1] = 1;

        for (int i = 2; i < fibbo.length; i++) {
            fibbo[i] = fibbo[i-2] + fibbo[i-1];
        }

        List<Exception> exceptions = new ArrayList<>();
        test(array1, fibbo, exceptions);

        // Выводим сумму и все собранные исключения
        System.out.println("Сумма элементов массива: " + sum);
        for (Exception e : exceptions) {
            e.printStackTrace();
        }
    }

    static void test(String[][] array, int[] fibbo, List<Exception> exceptions) {
        int arrRow = array.length;
        int arrColl = array[0].length;

        boolean flag = true;

        // Проверка корректности размера массива
        if (array.length == 4) {
            for (int i = 0; i < arrColl; i++) {
                if (array[i].length != 4) {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }

        try {
            // Если размер массива корректен
            if (flag) {
                for (int i = 0; i < arrRow; i++) {
                    for (int j = 0; j < arrColl; j++) {
                        String element = array[i][j];
                        try {
                            double value = Double.parseDouble(element);
                            sum += value; // Всегда добавляем число к сумме

                            // Проверяем только целые числа на соответствие Фибоначчи
                            if (isInteger(element)) {
                                int intValue = (int) value;
                                boolean isFibbo = false;
                                for (int k = 0; k < fibbo.length; k++) {
                                    if (fibbo[k] == intValue) {
                                        isFibbo = true;
                                        exceptions.add(new MyFibonacciException(i + 1, j + 1, intValue));
                                        break;
                                    }
                                }
                                if (!isFibbo) {
                                    exceptions.add(new MyNoFibonacciException(i + 1, j + 1, String.valueOf(intValue)));
                                }
                            }

                        } catch (NumberFormatException e) {
                            exceptions.add(new MyArrayDataException(i, j, element));
                        }
                    }
                }
            } else {
                exceptions.add(new MyArraySizeException("Некорректный размер массива!"));
            }

        } catch (MyArraySizeException e) {
            exceptions.add(e);
        }
    }

    // Метод для проверки, является ли строка целым числом
    static boolean isInteger(String s) {
        // Проверяем, является ли строка целым числом или числом с точкой в конце
        return s.matches("\\d+$");
    }
}
