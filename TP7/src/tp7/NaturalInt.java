package tp7;

public class NaturalInt {
    private int value;   // 存储自然数值（>=0）

    // 构造函数
    public NaturalInt(int value) throws ErrConst {
        if (value < 0) {
            throw new ErrConst("构造失败：自然数不能为负数！");
        }
        this.value = value;
    }

    // getter
    public int getN() {
        return value;
    }

    public static NaturalInt sum(NaturalInt a, NaturalInt b) throws ErrS, ErrConst {
        long result = (long) a.value + b.value;
        if (result > Integer.MAX_VALUE)
            throw new ErrS("加法溢出，不可表示！");
        return new NaturalInt((int) result);
    }

    public static NaturalInt difference(NaturalInt a, NaturalInt b) throws ErrD, ErrConst {
        int result = a.value - b.value;
        if (result < 0)
            throw new ErrD("减法结果为负，不是自然数！");
        return new NaturalInt(result);
    }

    public static NaturalInt product(NaturalInt a, NaturalInt b) throws ErrP, ErrConst {
        long result = (long) a.value * b.value;
        if (result > Integer.MAX_VALUE)
            throw new ErrP("乘法溢出，不可表示！");
        return new NaturalInt((int) result);
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
