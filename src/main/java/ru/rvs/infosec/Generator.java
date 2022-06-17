package ru.rvs.infosec;

import java.util.Random;

/**
 * Класс Генератор имеет методы для генерации ОГРН и ОГРНИП
 * Генерирует значение формата "С ГГ КК ХХ Ч", где:
 *  С - зависит от метода,
 *  КК - соответствует субъекту федерации в соответствии с 65 статьей конституции,
 *  ХХ - произвольный набор цифр, зависит от выбранного метода,
 *  Ч - контрольное число.
 */
public class Generator {
    /** Рандомайзер */
    private final Random random = new Random();

    /** Список субъектов РФ в соответствии с 65 статьей конституции. Ссылка:
    *   https://www.consultant.ru/document/cons_doc_LAW_215444/635c8363f3a56fc3a8c3efb0c244531b8ce38828/ */
    public final int[] theSubjectOfTheRF = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
            22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48,
            49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75,
            76, 77, 78, 79, 83, 86, 87, 89, 91, 92, 99};

    /**
     * Метод принимает на вход параметр, по которому генерирует случайное число, соответствующее ОГРН или ОГРНИП
     * @param choose true = ОГРН, false = ОГРНИП
     * @return значение типа long
     */
    public Long OGRNOrOGRNIP (boolean choose) {
        if (choose) {
            return OGRN();
        } else {
            return OGRNIP();
        }
    }

    /**
     * Метод генерирует случайное значение ОГРН
     * Генерирует значение формата "С ГГ КК ХХХХХХХ Ч"
     * @return значение, соответствующие ОГРН (13 знаков)
     */
    public Long OGRN () {
        int[] GRNRecord = {1, 5};  // Массив возможных значений в соответствии с рег. номером записи
        int GRNValue = GRNRecord[random.nextInt(GRNRecord.length)];  // Выбор случайного числа из массива (Значение ОГРН)
        int yearValue = random.nextInt(99);    // Генерация случайного года внесения в реестр
        int subjectRFValue = theSubjectOfTheRF[random.nextInt(theSubjectOfTheRF.length)];   // Выбор случайного кода субъекта РФ
        int numberValue = random.nextInt(9999999);  // Генерация случайного числа записи в гос. реестре
        long OGRNNumber = (GRNValue * 100000000000L) + (yearValue * 1000000000L) + (subjectRFValue * 10000000L)
                + numberValue;   // Получение предварительного числа, для вычисления контрольной цифры
        long controlNumber = OGRNNumber - Math.floorDiv(OGRNNumber, 11) * 11;   // Вычисление контрольной цифры
        if (controlNumber == 10L) controlNumber = 0;
        OGRNNumber = OGRNNumber * 10L + controlNumber;  // Сдвиг на 1 разряд и запись контрольного числа к конечному значению
        return OGRNNumber;
    }

    /**
     * Метод генерирует случайное значение ОГРНИП
     * Генерирует значение формата "С ГГ КК ХХХХХХХХХ Ч"
     * @return значение, соответствующие ОГРНИП (15 знаков)
     */
    public Long OGRNIP () {
        int GRNValue = 3; // Значение ОГРНИП
        int yearValue = random.nextInt(99);    // Генерация случайного года внесения в реестр
        int subjectRFValue = theSubjectOfTheRF[random.nextInt(theSubjectOfTheRF.length)];   // Выбор случайного кода субъекта РФ
        int numberValue = random.nextInt(9999999);  // Генерация случайного числа записи в гос. реестре
        long OGRNIPNumber = (GRNValue * 10000000000000L) + (yearValue * 100000000000L) + (subjectRFValue * 1000000000L)
                + numberValue;   // Получение предварительного числа, для вычисления контрольной цифры
        long controlNumber = OGRNIPNumber - Math.floorDiv(OGRNIPNumber, 11) * 11;   // Вычисление контрольной цифры
        if (controlNumber == 10L) controlNumber = 0;
        OGRNIPNumber = OGRNIPNumber * 10L + controlNumber;  // Сдвиг на 1 разряд и запись контрольного числа к конечному значению
        return OGRNIPNumber;
    }
}
