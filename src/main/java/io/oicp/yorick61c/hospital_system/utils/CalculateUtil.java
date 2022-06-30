package io.oicp.yorick61c.hospital_system.utils;

import io.oicp.yorick61c.hospital_system.pojo.DecimalValue;
import io.oicp.yorick61c.hospital_system.pojo.Value;

import java.text.DecimalFormat;

public class CalculateUtil {

    // 计算组合数据
    public static DecimalValue CalculateCombinedValue(Value numerator, Value denominator, int ratio) {
        if (numerator == null || denominator == null) {
            return null;
        }
        DecimalValue value = new DecimalValue();
        // 小数格式保留两位小数
        DecimalFormat df = new DecimalFormat("0.00");
        if (numerator.getJanuary() != null && denominator.getJanuary() != null && denominator.getJanuary() != 0)
            value.setJanuary(Double.valueOf(df.format(numerator.getJanuary().doubleValue() / denominator.getJanuary().doubleValue() * ratio)));
            // 若有任意项目为空则设置成0
        else value.setJanuary(0.0);
        if (numerator.getFebruary() != null && denominator.getFebruary() != null && denominator.getFebruary() != 0)
            value.setFebruary(Double.valueOf(df.format(numerator.getFebruary().doubleValue() / denominator.getFebruary().doubleValue() * ratio)));
        else value.setFebruary(0.0);
        if (numerator.getMarch() != null && denominator.getMarch() != null && denominator.getMarch() != 0)
            value.setMarch(Double.valueOf(df.format(numerator.getMarch().doubleValue() / denominator.getMarch().doubleValue() * ratio)));
        else value.setMarch(0.0);
        if (numerator.getApril() != null && denominator.getApril() != null && denominator.getApril() != 0)
            value.setApril(Double.valueOf(df.format(numerator.getApril().doubleValue() / denominator.getApril().doubleValue() * ratio)));
        else value.setApril(0.0);
        if (numerator.getMay() != null && denominator.getMay() != null && denominator.getMay() != 0)
            value.setMay(Double.valueOf(df.format(numerator.getMay().doubleValue() / denominator.getMay().doubleValue() * ratio)));
        else value.setMay(0.0);
        if (numerator.getJune() != null && denominator.getJune() != null && denominator.getJune() != 0)
            value.setJune(Double.valueOf(df.format(numerator.getJune().doubleValue() / denominator.getJune().doubleValue() * ratio)));
        else value.setJune(0.0);
        if (numerator.getJuly() != null && denominator.getJuly() != null && denominator.getJuly() != 0)
            value.setJuly(Double.valueOf(df.format(numerator.getJuly().doubleValue() / denominator.getJuly().doubleValue() * ratio)));
        else value.setJuly(0.0);
        if (numerator.getAugust() != null && denominator.getAugust() != null && denominator.getAugust() != 0)
            value.setAugust(Double.valueOf(df.format(numerator.getAugust().doubleValue() / denominator.getAugust().doubleValue() * ratio)));
        else value.setAugust(0.0);
        if (numerator.getSeptember() != null && denominator.getSeptember() != null && denominator.getSeptember() != 0)
            value.setSeptember(Double.valueOf(df.format(numerator.getSeptember().doubleValue() / denominator.getSeptember().doubleValue() * ratio)));
        else value.setSeptember(0.0);
        if (numerator.getOctober() != null && denominator.getOctober() != null && denominator.getOctober() != 0)
            value.setOctober(Double.valueOf(df.format(numerator.getOctober().doubleValue() / denominator.getOctober().doubleValue() * ratio)));
        else value.setOctober(0.0);
        if (numerator.getNovember() != null && denominator.getNovember() != null && denominator.getNovember() != 0)
            value.setNovember(Double.valueOf(df.format(numerator.getNovember().doubleValue() / denominator.getNovember().doubleValue() * ratio)));
        else value.setNovember(0.0);
        if (numerator.getDecember() != null && denominator.getDecember() != null && denominator.getDecember() != 0)
            value.setDecember(Double.valueOf(df.format(numerator.getDecember().doubleValue() / denominator.getDecember().doubleValue() * ratio)));
        else value.setDecember(0.0);
        return value;

    }

    // Value相加数据
    public static Value addValue(Value valueA, Value valueB) {
        if (valueA == null || valueB == null) {
            return valueA;
        }
        if (valueB.getJanuary() != null)
            valueA.setJanuary(valueA.getJanuary() + valueB.getJanuary());
        if (valueB.getFebruary() != null)
            valueA.setFebruary(valueA.getFebruary() + valueB.getFebruary());
        if (valueB.getMarch() != null)
            valueA.setMarch(valueA.getMarch() + valueB.getMarch());
        if (valueB.getApril() != null)
            valueA.setApril(valueA.getApril() + valueB.getApril());
        if (valueB.getMay() != null)
            valueA.setMay(valueA.getMay() + valueB.getMay());
        if (valueB.getJune() != null)
            valueA.setJune(valueA.getJune() + valueB.getJune());
        if (valueB.getJuly() != null)
            valueA.setJuly(valueA.getJuly() + valueB.getJuly());
        if (valueB.getAugust() != null)
            valueA.setAugust(valueA.getAugust() + valueB.getAugust());
        if (valueB.getSeptember() != null)
            valueA.setSeptember(valueA.getSeptember() + valueB.getSeptember());
        if (valueB.getOctober() != null)
            valueA.setOctober(valueA.getOctober() + valueB.getOctober());
        if (valueB.getNovember() != null)
            valueA.setNovember(valueA.getNovember() + valueB.getNovember());
        if (valueB.getDecember() != null)
            valueA.setDecember(valueA.getDecember() + valueB.getDecember());

        return valueA;

    }

    // 计算平均值,保留整数
    public static Value getAverage(Value value, int size) {
        if (value == null) {
            return null;
        }
        Value result = new Value();
        if (value.getJanuary() != null)
            result.setJanuary(size == 0 ? 0 : value.getJanuary() / size);
        if (value.getFebruary() != null)
            result.setFebruary(size == 0 ? 0 : value.getFebruary() / size);
        if (value.getMarch() != null)
            result.setMarch(size == 0 ? 0 : value.getMarch() / size);
        if (value.getApril() != null)
            result.setApril(size == 0 ? 0 : value.getApril() / size);
        if (value.getMay() != null)
            result.setMay(size == 0 ? 0 : value.getMay() / size);
        if (value.getJune() != null)
            result.setJune(size == 0 ? 0 : value.getJune() / size);
        if (value.getJuly() != null)
            result.setJuly(size == 0 ? 0 : value.getJuly() / size);
        if (value.getAugust() != null)
            result.setAugust(size == 0 ? 0 : value.getAugust() / size);
        if (value.getSeptember() != null)
            result.setSeptember(size == 0 ? 0 : value.getSeptember() / size);
        if (value.getOctober() != null)
            result.setOctober(size == 0 ? 0 : value.getOctober() / size);
        if (value.getNovember() != null)
            result.setNovember(size == 0 ? 0 : value.getNovember() / size);
        if (value.getDecember() != null)
            result.setDecember(size == 0 ? 0 : value.getDecember() / size);
        return result;
    }
}
