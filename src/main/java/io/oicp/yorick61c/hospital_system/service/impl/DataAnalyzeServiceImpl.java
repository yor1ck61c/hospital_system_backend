package io.oicp.yorick61c.hospital_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.*;
import io.oicp.yorick61c.hospital_system.pojo.*;
import io.oicp.yorick61c.hospital_system.pojo.dto.TotalValueDTO;
import io.oicp.yorick61c.hospital_system.pojo.vo.CompareVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.CountVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.RankVO;
import io.oicp.yorick61c.hospital_system.service.DataAnalyzeService;
import io.oicp.yorick61c.hospital_system.utils.CalculateUtil;
import io.oicp.yorick61c.hospital_system.utils.ReturnUtil;
import io.oicp.yorick61c.hospital_system.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataAnalyzeServiceImpl implements DataAnalyzeService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ValueMapper valueMapper;

    @Resource
    private CenterUserMappingMapper centerUserMappingMapper;

    @Resource
    private CBFIMappingMapper cbfimappingMapper;

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private TypeCombinedItemMappingMapper typeCombinedItemMappingMapper;

    @Resource
    private CenterMapper centerMapper;
    @Override
    // 统计单个数据项的数据
    public Map<String, Object> countSingle(CountVO countVO) {
        // 若分中心id为0，是特殊需求：统计所有分中心信息并返回。
        if (countVO.getCenterId() != null && countVO.getCenterId() == 0) {
            return countAllSubCenterInfoForSingle(countVO);
        }
        // 根据医院等级或医院类型或分中心名称获取用户列表
        List<User> users = getAvailableUserList(countVO);
        // 返回的总和数据
        List<Value> values = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        for (User user : users) {
            QueryWrapper<Value> valueQueryWrapper = new QueryWrapper<>();
            /*
            * 根据hospital_name, item_name, year获取一条value数据
            * */
            valueQueryWrapper.eq("hospital_name", user.getHospitalName());
            valueQueryWrapper.eq("item_name", countVO.getItemName());
            valueQueryWrapper.eq("year", countVO.getYear());
            Value value = valueMapper.selectOne(valueQueryWrapper);
            /*
            * 若value不为空，则将每个月的值添加到totalValue中
            * */
            if (value != null) {
                // 加之前先判断是否为空,非空才加
                values.add(value);
            } else {
                // 如果value为空，则将该月的值设置为0
                values.add(createDefaultValue(user.getHospitalName(), countVO.getItemName(), countVO.getYear()));
            }
        }
        result.put("values", values);
        result.put("hospitalNum", users.size());
        result.put("isSubCenterData", 0);
        return result;
    }

    private Map<String, Object> countAllSubCenterInfoForSingle(CountVO countVO) {

        Map<String, Object> returnMap = new HashMap<>();

        // 获取所有分中心信息
        List<Center> centers = centerMapper.selectList(new QueryWrapper<>());
        // 获取所有分中心下的用户信息
        for (Center center : centers) {
            // 获取分中心下的用户信息
            Integer centerId = center.getCenterId();
            List<CenterUserMapping> CUMappings = centerUserMappingMapper.selectList(new QueryWrapper<CenterUserMapping>().eq("center_id", centerId));
            Value totalValue = createDefaultValue(countVO.getItemName(), countVO.getYear());
            for (CenterUserMapping CUMapping : CUMappings) {
                User user = userMapper.selectById(CUMapping.getUserId());
                // 根据用户信息获取值
                QueryWrapper<Value> valueQueryWrapper = new QueryWrapper<>();
                valueQueryWrapper.eq("hospital_name", user.getHospitalName());
                valueQueryWrapper.eq("item_name", countVO.getItemName());
                valueQueryWrapper.eq("year", countVO.getYear());
                Value value = valueMapper.selectOne(valueQueryWrapper);
                // 若value不为空，且value中每个属性也不为空，将value中每项数据加至总和中
                if (value != null ) {
                    if (value.getJanuary() != null) {
                        totalValue.setJanuary(totalValue.getJanuary() + value.getJanuary());
                    }
                    if (value.getFebruary() != null) {
                        totalValue.setFebruary(totalValue.getFebruary() + value.getFebruary());
                    }
                    if (value.getMarch() != null) {
                        totalValue.setMarch(totalValue.getMarch() + value.getMarch());
                    }
                    if (value.getApril() != null) {
                        totalValue.setApril(totalValue.getApril() + value.getApril());
                    }
                    if (value.getMay() != null) {
                        totalValue.setMay(totalValue.getMay() + value.getMay());
                    }
                    if (value.getJune() != null) {
                        totalValue.setJune(totalValue.getJune() + value.getJune());
                    }
                    if (value.getJuly() != null) {
                        totalValue.setJuly(totalValue.getJuly() + value.getJuly());
                    }
                    if (value.getAugust() != null) {
                        totalValue.setAugust(totalValue.getAugust() + value.getAugust());
                    }
                    if (value.getSeptember() != null) {
                        totalValue.setSeptember(totalValue.getSeptember() + value.getSeptember());
                    }
                    if (value.getOctober() != null) {
                        totalValue.setOctober(totalValue.getOctober() + value.getOctober());
                    }
                    if (value.getNovember() != null) {
                        totalValue.setNovember(totalValue.getNovember() + value.getNovember());
                    }
                    if (value.getDecember() != null) {
                        totalValue.setDecember(totalValue.getDecember() + value.getDecember());
                    }
                }
            }
            // totalValue中Integer类型的属性值除以userSize得到平均值
//            Integer userSize = CUMappings.size();
//            if (userSize != 0) {
//                if (totalValue.getJanuary() != null) {
//                    totalValue.setJanuary(totalValue.getJanuary() / userSize);
//                }
//                if (totalValue.getFebruary() != null) {
//                    totalValue.setFebruary(totalValue.getFebruary() / userSize);
//                }
//                if (totalValue.getMarch() != null) {
//                    totalValue.setMarch(totalValue.getMarch() / userSize);
//                }
//                if (totalValue.getApril() != null) {
//                    totalValue.setApril(totalValue.getApril() / userSize);
//                }
//                if (totalValue.getMay() != null) {
//                    totalValue.setMay(totalValue.getMay() / userSize);
//                }
//                if (totalValue.getJune() != null) {
//                    totalValue.setJune(totalValue.getJune() / userSize);
//                }
//                if (totalValue.getJuly() != null) {
//                    totalValue.setJuly(totalValue.getJuly() / userSize);
//                }
//                if (totalValue.getAugust() != null) {
//                    totalValue.setAugust(totalValue.getAugust() / userSize);
//                }
//                if (totalValue.getSeptember() != null) {
//                    totalValue.setSeptember(totalValue.getSeptember() / userSize);
//                }
//                if (totalValue.getOctober() != null) {
//                    totalValue.setOctober(totalValue.getOctober() / userSize);
//                }
//                if (totalValue.getNovember() != null) {
//                    totalValue.setNovember(totalValue.getNovember() / userSize);
//                }
//                if (totalValue.getDecember() != null) {
//                    totalValue.setDecember(totalValue.getDecember() / userSize);
//                }
//            }
            returnMap.put(center.getCenterName(), totalValue);
        }
        returnMap.put("isSubCenterData", 1);
        return returnMap;
    }

    @Override
    // 统计组合数据项的数据
    public Map<String, Object> countCombined(CountVO countVO) {
        // 若分中心id为0，是特殊需求：统计所有分中心信息并返回。
        if (countVO.getCenterId() != null && countVO.getCenterId() == 0) {
            return countAllSubCenterInfoForCombined(countVO);
        }
        // 若分中心id不为0，根据医院等级或医院类型或分中心名称获取医院名称列表
        List<User> users = getAvailableUserList(countVO);
        // 根据组合数据项名称获取分子项和分母项数据
        CBFIMapping combinedItem = cbfimappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", countVO.getItemName()));
        // 分子项数据名称
        String numerator = combinedItem.getNumerator();
        // 分母项数据名称
        String denominator = combinedItem.getDenominator();
        // 公式比率
        int ratio = combinedItem.getRatio();

        int noDataHospitalCount = 0;
        // 创建一个默认的总和数据
        List<List<Value>> valueList = new ArrayList<>();

        // 循环每个医院
        for (User user : users) {
            QueryWrapper<Value> numeratorValueQueryWrapper = new QueryWrapper<>();
            QueryWrapper<Value> denominatorValueQueryWrapper = new QueryWrapper<>();
            /*
             * 先获取分子数据，再获取分母数据，得到数据后相除，加至total中。
             * */
            numeratorValueQueryWrapper.eq("hospital_name", user.getHospitalName());
            numeratorValueQueryWrapper.eq("item_name", numerator);
            numeratorValueQueryWrapper.eq("year", countVO.getYear());

            denominatorValueQueryWrapper.eq("hospital_name", user.getHospitalName());
            denominatorValueQueryWrapper.eq("item_name", denominator);
            denominatorValueQueryWrapper.eq("year", countVO.getYear());

            Value numeratorValue = valueMapper.selectOne(numeratorValueQueryWrapper);
            Value denominatorValue = valueMapper.selectOne(denominatorValueQueryWrapper);
            /*
             * 若分子项或分母项均不为空，则将每个月的值添加到valueList中
             * */
            List<Value> tempValue = new ArrayList<>();
            if (numeratorValue != null && denominatorValue != null) {
                tempValue.add(numeratorValue);
                tempValue.add(denominatorValue);
                valueList.add(tempValue);
            } else {
                noDataHospitalCount++;
            }

        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("valueList", valueList);
        returnMap.put("hospitalNum", users.size());
        returnMap.put("ratio", ratio);
        returnMap.put("noDataHospitalNum", noDataHospitalCount);
        returnMap.put("isSubCenterData", 0);
        return returnMap;
    }

    // 统计所有分中心的平均值并返回
    private Map<String, Object> countAllSubCenterInfoForCombined(CountVO countVO) {
        // 首先获取分子项，分母项以及比率
        CBFIMapping combinedItem = cbfimappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", countVO.getItemName()));
        // 分子项数据名称
        String numerator = combinedItem.getNumerator();
        // 分母项数据名称
        String denominator = combinedItem.getDenominator();
        // 公式比率
        int ratio = combinedItem.getRatio();
        // 返回值
        Map<String, Object> returnMap = new HashMap<>();
        // 查询分中心表，获取所有分中心信息
        List<Center> centerList = centerMapper.selectList(new QueryWrapper<>());
        // 遍历分中心列表，对分中心下所属医院信息进行统计
        for (Center center : centerList) {
            Integer centerId = center.getCenterId();
            // 根据分中心id获取分中心下的所有医院信息
            List<CenterUserMapping> CUMappings = centerUserMappingMapper.selectList(new QueryWrapper<CenterUserMapping>().eq("center_id", centerId));
            Value totalNumeratorValue = createDefaultValue(numerator, countVO.getYear());
            Value totalDenominatorValue = createDefaultValue(denominator, countVO.getYear());
            // 遍历映射表，获取每个医院的信息
            for (CenterUserMapping CUMapping : CUMappings) {
                // 先创建默认的总和数据
                Integer userId = CUMapping.getUserId();
                // 根据医院id获取医院信息
                User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
                // 查询Value表，根据item_name, year, hospital_name分别获取分子项和分母项的数据
                QueryWrapper<Value> numeratorValueQueryWrapper = new QueryWrapper<>();
                numeratorValueQueryWrapper.eq("hospital_name", user.getHospitalName());
                numeratorValueQueryWrapper.eq("item_name", numerator);
                numeratorValueQueryWrapper.eq("year", countVO.getYear());
                Value numeratorValue = valueMapper.selectOne(numeratorValueQueryWrapper);
                // 若分子项不为空，则将分子项的值加至总和中
                if (numeratorValue != null) {
                    // 加之前先判断是否为空,非空才加
                    if (numeratorValue.getJanuary() != null) {
                        totalNumeratorValue.setJanuary(totalNumeratorValue.getJanuary() + numeratorValue.getJanuary());
                    }
                    if (numeratorValue.getFebruary() != null) {
                        totalNumeratorValue.setFebruary(totalNumeratorValue.getFebruary() + numeratorValue.getFebruary());
                    }
                    if (numeratorValue.getMarch() != null) {
                        totalNumeratorValue.setMarch(totalNumeratorValue.getMarch() + numeratorValue.getMarch());
                    }
                    if (numeratorValue.getApril() != null) {
                        totalNumeratorValue.setApril(totalNumeratorValue.getApril() + numeratorValue.getApril());
                    }
                    if (numeratorValue.getMay() != null) {
                        totalNumeratorValue.setMay(totalNumeratorValue.getMay() + numeratorValue.getMay());
                    }
                    if (numeratorValue.getJune() != null) {
                        totalNumeratorValue.setJune(totalNumeratorValue.getJune() + numeratorValue.getJune());
                    }
                    if (numeratorValue.getJuly() != null) {
                        totalNumeratorValue.setJuly(totalNumeratorValue.getJuly() + numeratorValue.getJuly());
                    }
                    if (numeratorValue.getAugust() != null) {
                        totalNumeratorValue.setAugust(totalNumeratorValue.getAugust() + numeratorValue.getAugust());
                    }
                    if (numeratorValue.getSeptember() != null) {
                        totalNumeratorValue.setSeptember(totalNumeratorValue.getSeptember() + numeratorValue.getSeptember());
                    }
                    if (numeratorValue.getOctober() != null) {
                        totalNumeratorValue.setOctober(totalNumeratorValue.getOctober() + numeratorValue.getOctober());
                    }
                    if (numeratorValue.getNovember() != null) {
                        totalNumeratorValue.setNovember(totalNumeratorValue.getNovember() + numeratorValue.getNovember());
                    }
                    if (numeratorValue.getDecember() != null) {
                        totalNumeratorValue.setDecember(totalNumeratorValue.getDecember() + numeratorValue.getDecember());
                    }
                }
                QueryWrapper<Value> denominatorValueQueryWrapper = new QueryWrapper<>();
                denominatorValueQueryWrapper.eq("hospital_name", user.getHospitalName());
                denominatorValueQueryWrapper.eq("item_name", denominator);
                denominatorValueQueryWrapper.eq("year", countVO.getYear());
                Value denominatorValue = valueMapper.selectOne(denominatorValueQueryWrapper);
                // 若分母项不为空，则将分母项的值加至总和中
                if (denominatorValue != null) {
                    // 加之前先判断是否为空,非空才加
                    if (denominatorValue.getJanuary() != null) {
                        totalDenominatorValue.setJanuary(totalDenominatorValue.getJanuary() + denominatorValue.getJanuary());
                    }
                    if (denominatorValue.getFebruary() != null) {
                        totalDenominatorValue.setFebruary(totalDenominatorValue.getFebruary() + denominatorValue.getFebruary());
                    }
                    if (denominatorValue.getMarch() != null) {
                        totalDenominatorValue.setMarch(totalDenominatorValue.getMarch() + denominatorValue.getMarch());
                    }
                    if (denominatorValue.getApril() != null) {
                        totalDenominatorValue.setApril(totalDenominatorValue.getApril() + denominatorValue.getApril());
                    }
                    if (denominatorValue.getMay() != null) {
                        totalDenominatorValue.setMay(totalDenominatorValue.getMay() + denominatorValue.getMay());
                    }
                    if (denominatorValue.getJune() != null) {
                        totalDenominatorValue.setJune(totalDenominatorValue.getJune() + denominatorValue.getJune());
                    }
                    if (denominatorValue.getJuly() != null) {
                        totalDenominatorValue.setJuly(totalDenominatorValue.getJuly() + denominatorValue.getJuly());
                    }
                    if (denominatorValue.getAugust() != null) {
                        totalDenominatorValue.setAugust(totalDenominatorValue.getAugust() + denominatorValue.getAugust());
                    }
                    if (denominatorValue.getSeptember() != null) {
                        totalDenominatorValue.setSeptember(totalDenominatorValue.getSeptember() + denominatorValue.getSeptember());
                    }
                    if (denominatorValue.getOctober() != null) {
                        totalDenominatorValue.setOctober(totalDenominatorValue.getOctober() + denominatorValue.getOctober());
                    }
                    if (denominatorValue.getNovember() != null) {
                        totalDenominatorValue.setNovember(totalDenominatorValue.getNovember() + denominatorValue.getNovember());
                    }
                    if (denominatorValue.getDecember() != null) {
                        totalDenominatorValue.setDecember(totalDenominatorValue.getDecember() + denominatorValue.getDecember());
                    }


                }
            }
            // 将计算好的总和数据加至返回数据中
            List<Value> centerValue = new ArrayList<>();
            centerValue.add(totalNumeratorValue);
            centerValue.add(totalDenominatorValue);
            returnMap.put(center.getCenterName(), centerValue);
        }
        returnMap.put("ratio", ratio);
        returnMap.put("isSubCenterData", 1);
        returnMap.put("numeratorName", numerator);
        returnMap.put("denominatorName", denominator);
        return returnMap;
    }

    // 比较单个数据
    @Override
    public Map<String, Object> compareSingle(CompareVO compareVO) {
        Map<String, Object> returnMap = new HashMap<>();
        String itemName = compareVO.getItemName();
        if (compareVO.getCompareObjTypeA().equals("hospital")) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", compareVO.getCompareObjA()));
            Value objAValue = getSingleItemValue(itemName, user.getHospitalName(), compareVO.getYearA());
            Map<String, Value> dataMap = new HashMap<>();
            dataMap.put(user.getHospitalName(), objAValue);
            returnMap.put("objA", dataMap);
        }
        // 获取分中心或全省数据
        if (compareVO.getCompareObjTypeA().equals("center")) {
            Center center = centerMapper.selectOne(new QueryWrapper<Center>().eq("center_id", compareVO.getCompareObjA()));
            Value objAValue = getSingleItemCenterValue(itemName, compareVO.getYearA(), compareVO.getHospitalLevelA(), compareVO.getHospitalTypeA(), compareVO.getCompareObjA());
            Map<String, Value> dataMap = new HashMap<>();
            dataMap.put(center.getCenterName(), objAValue);
            returnMap.put("objA", dataMap);
        }
        if (compareVO.getCompareObjTypeA().equals("province")) {
            // 若为全省，则获取全省数据
            Value objAValue = getSingleItemCenterValue(compareVO.getItemName(), compareVO.getYearA(), compareVO.getHospitalLevelA(), compareVO.getHospitalTypeA(), null);
            Map<String, Value> dataMap = new HashMap<>();
            dataMap.put("全省数据", objAValue);
            returnMap.put("objA", dataMap);
        }
        if (compareVO.getCompareObjTypeB().equals("hospital")) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", compareVO.getCompareObjB()));
            Value objBValue = getSingleItemValue(itemName, user.getHospitalName(), compareVO.getYearB());
            Map<String, Value> dataMap = new HashMap<>();
            dataMap.put(user.getHospitalName(), objBValue);
            returnMap.put("objB", dataMap);
        }
        // 获取分中心或全省数据
        if (compareVO.getCompareObjTypeB().equals("center")) {
            Center center = centerMapper.selectOne(new QueryWrapper<Center>().eq("center_id", compareVO.getCompareObjB()));
            Value objBValue = getSingleItemCenterValue(itemName, compareVO.getYearB(), compareVO.getHospitalLevelB(), compareVO.getHospitalTypeB(), compareVO.getCompareObjB());
            Map<String, Value> dataMap = new HashMap<>();
            dataMap.put(center.getCenterName(), objBValue);
            returnMap.put("objB", dataMap);
        }
        if (compareVO.getCompareObjTypeB().equals("province")) {
            // 若为全省，则获取全省数据
            Value objBValue = getSingleItemCenterValue(compareVO.getItemName(), compareVO.getYearB(), compareVO.getHospitalLevelB(), compareVO.getHospitalTypeB(), null);

            Map<String, Value> dataMap = new HashMap<>();
            dataMap.put("全省数据", objBValue);
            returnMap.put("objB", dataMap);
        }

        return returnMap;
    }

    private Value getSingleItemCenterValue(String itemName, Integer year, String hospitalLevel, String hospitalType, Integer centerId) {
        List<User> availableUserList = getAvailableUserList(hospitalLevel, hospitalType, centerId);
        Value totalValue = createDefaultValue(itemName, year);
        for (User user: availableUserList) {
            Value singleItemValue = getSingleItemValue(itemName, user.getHospitalName(), year);
            totalValue = CalculateUtil.addValue(totalValue, singleItemValue);
        }
        totalValue = CalculateUtil.getAverage(totalValue, availableUserList.size());
        return totalValue;
    }

    private Value getSingleItemValue(String itemName, String hospitalName, Integer year) {
        // 获取单个指标信息
        QueryWrapper<Value> ValueQueryWrapper = new QueryWrapper<>();
        ValueQueryWrapper.eq("hospital_name", hospitalName);
        ValueQueryWrapper.eq("item_name", itemName);
        ValueQueryWrapper.eq("year", year);
        return valueMapper.selectOne(ValueQueryWrapper);
    }

    // 比较组合数据
    @Override
    public Map<String, Object> compareCombined(CompareVO compareVO) {
        Map<String, Object> returnMap = new HashMap<>();
        // 根据组合数据项名称获取组合数据项
        CBFIMapping combinedItem = cbfimappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", compareVO.getItemName()));
        // 先判断比较对象A的类型，分别处理
        // 若为医院，则获取医院A的数据
        if (compareVO.getCompareObjTypeA().equals("hospital")) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", compareVO.getCompareObjA()));
            returnMap.put(user.getHospitalName(), getCombinedItemValue(compareVO.getItemName(), user.getHospitalName(), compareVO.getYearA()));
        }
        // 获取分中心或全省数据
        if (compareVO.getCompareObjTypeA().equals("center")) {
            Center center = centerMapper.selectOne(new QueryWrapper<Center>().eq("center_id", compareVO.getCompareObjA()));
            returnMap.put(center.getCenterName(), getCombinedItemCenterValue(compareVO.getItemName(), compareVO.getYearA(), compareVO.getHospitalLevelA(), compareVO.getHospitalTypeA(), compareVO.getCompareObjA()));
        }
        if (compareVO.getCompareObjTypeA().equals("province")) {
            // 若为全省，则获取全省数据
            returnMap.put("全省数据", getCombinedItemCenterValue(compareVO.getItemName(), compareVO.getYearA(), compareVO.getHospitalLevelA(), compareVO.getHospitalTypeA(), null));
        }
        // 再对比较对象B进行处理
        if (compareVO.getCompareObjTypeB().equals("hospital")) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", compareVO.getCompareObjB()));
            returnMap.put(user.getHospitalName(), getCombinedItemValue(compareVO.getItemName(), user.getHospitalName(), compareVO.getYearB()));
        }
        // 获取分中心或全省数据
        if (compareVO.getCompareObjTypeB().equals("center")) {
            Center center = centerMapper.selectOne(new QueryWrapper<Center>().eq("center_id", compareVO.getCompareObjB()));
            returnMap.put(center.getCenterName(), getCombinedItemCenterValue(compareVO.getItemName(), compareVO.getYearB(), compareVO.getHospitalLevelB(), compareVO.getHospitalTypeB(), compareVO.getCompareObjB()));
        }
        if (compareVO.getCompareObjTypeB().equals("province")) {

            // 若为全省，则获取全省数据
            returnMap.put("全省数据", getCombinedItemCenterValue(compareVO.getItemName(), compareVO.getYearB(), compareVO.getHospitalLevelB(), compareVO.getHospitalTypeB(), null));
        }
        returnMap.put("ratio", combinedItem.getRatio());
        returnMap.put("itemName", combinedItem.getItemName());
        return returnMap;
    }

    // 获取中心下所有符合条件的医院的组合数据的值
    private List<Value> getCombinedItemCenterValue(String itemName, Integer year, String hospitalLevel, String hospitalType, Integer centerId) {
        List<User> availableUserList = getAvailableUserList(hospitalLevel, hospitalType, centerId);
        CBFIMapping combinedItem = cbfimappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", itemName));
        // 分子项数据名称
        String numerator = combinedItem.getNumerator();
        // 分母项数据名称
        String denominator = combinedItem.getDenominator();

        Value totalNumeratorValue = createDefaultValue(numerator, year);
        Value totalDenominatorValue = createDefaultValue(denominator, year);
        for (User user : availableUserList) {
            List<Value> combinedItemValue = getCombinedItemValue(itemName, user.getHospitalName(), year);
            Value numeratorValue = combinedItemValue.get(0);
            Value denominatorValue = combinedItemValue.get(1);
            totalNumeratorValue = CalculateUtil.addValue(totalNumeratorValue, numeratorValue);
            totalDenominatorValue = CalculateUtil.addValue(totalDenominatorValue, denominatorValue);
        }
        List<Value> totalValueList = new ArrayList<>();
        // 获取平均值
        // totalNumeratorValue = CalculateUtil.getAverage(totalNumeratorValue, availableUserList.size());
        // totalDenominatorValue = CalculateUtil.getAverage(totalDenominatorValue, availableUserList.size());
        totalNumeratorValue.setItemName(numerator);
        totalNumeratorValue.setYear(year);
        totalDenominatorValue.setItemName(denominator);
        totalDenominatorValue.setYear(year);
        totalValueList.add(totalNumeratorValue);
        totalValueList.add(totalDenominatorValue);
        return totalValueList;
    }

    // 对单个数据排行
    @Override
    public List<Value> rankSingle(RankVO rankVO) {
        List<User> availableUserList = getAvailableUserList(rankVO);
        // 如果没有可用用户，则直接返回
        if (availableUserList.size() == 0) {
            return null;
        }
        List<Value> valueList = new ArrayList<>();
        // 获取排行榜数据
        for (User user : availableUserList) {
            // 根据医院名称，单项指标名，年份选取值。
            QueryWrapper<Value> valueQueryWrapper = new QueryWrapper<>();
            valueQueryWrapper.eq("hospital_name", user.getHospitalName());
            valueQueryWrapper.eq("item_name", rankVO.getItemName());
            valueQueryWrapper.eq("year", rankVO.getYear());
            Value value = valueMapper.selectOne(valueQueryWrapper);
            valueList.add(value);
        }
        // 去除valueList中的null值
        valueList.removeIf(Objects::isNull);
        // 根据月份对valueList进行排序
        // 根据month拼接方法名,保证月份首字母大写
        String methodName = "get" + rankVO.getMonth().substring(0, 1).toUpperCase() + rankVO.getMonth().substring(1);
        try {
            // 通过反射获取方法
            Method method = Value.class.getMethod(methodName);
            valueList.sort((o1, o2) -> {
                try {
                    // 根据o1的methodName方法获取值，再比较
                    Integer m1 = (Integer) method.invoke(o1);
                    Integer m2 = (Integer) method.invoke(o2);
                    // 根据rankVO的order属性选择降序还是升序排序
                    if (rankVO.getOrder().equals("desc") && m1 != null && m2 != null) {
                        return m1 - m2;
                    } else if (rankVO.getOrder().equals("asc") && m1 != null && m2 != null) {
                        return m2 - m1;
                    } else {
                        return 0;
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    return 0;
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return valueList;
    }

    // 对组合数据排行
    @Override
    public List<DecimalValue> rankCombined(RankVO rankVO) {
        List<User> availableUserList = getAvailableUserList(rankVO);
        // 如果没有可用用户，则直接返回
        if (availableUserList.size() == 0) {
            return null;
        }
        List<DecimalValue> valueList = new ArrayList<>();
        // 根据itemName分子项和分母项和比例
        CBFIMapping mapping = cbfimappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", rankVO.getItemName()));
        String numerator = mapping.getNumerator();
        String denominator = mapping.getDenominator();
        Integer ratio = mapping.getRatio();
        // 获取排行榜数据
        for (User user : availableUserList) {
            // 根据医院名称，分子项和分母项，年份获取值。
            QueryWrapper<Value> numeratorQueryWrapper = new QueryWrapper<>();
            numeratorQueryWrapper.eq("hospital_name", user.getHospitalName());
            numeratorQueryWrapper.eq("item_name", numerator);
            numeratorQueryWrapper.eq("year", rankVO.getYear());
            Value numeratorValue = valueMapper.selectOne(numeratorQueryWrapper);

            QueryWrapper<Value> denominatorQueryWrapper = new QueryWrapper<>();
            denominatorQueryWrapper.eq("hospital_name", user.getHospitalName());
            denominatorQueryWrapper.eq("item_name", denominator);
            denominatorQueryWrapper.eq("year", rankVO.getYear());
            Value denominatorValue = valueMapper.selectOne(denominatorQueryWrapper);
            DecimalValue tempDecimalValue = CalculateUtil.CalculateCombinedValue(numeratorValue, denominatorValue, ratio);
            if (tempDecimalValue != null) {
                tempDecimalValue.setItemName(rankVO.getItemName());
                tempDecimalValue.setHospitalName(user.getHospitalName());
                tempDecimalValue.setYear(rankVO.getYear());
                valueList.add(tempDecimalValue);
            }
        }
        // 去除valueList中的空对象
        valueList.removeIf(Objects::isNull);
        // 根据月份对valueList进行排序
        // 根据month拼接方法名
        // 根据month拼接方法名,保证月份首字母大写
        String methodName = "get" + rankVO.getMonth().substring(0, 1).toUpperCase() + rankVO.getMonth().substring(1);
        try {
            // 通过反射获取方法
            Method method = DecimalValue.class.getMethod(methodName);
            valueList.sort((o1, o2) -> {
                try {
                    // 根据o1的methodName方法获取值，再比较
                    Double v1 = (Double) method.invoke(o1);
                    Double v2 = (Double) method.invoke(o2);
                    // 根据rankVO的order属性选择降序还是升序排序
                    if (rankVO.getOrder().equals("desc") && v1 != null && v2 != null) {
                        return v2 < v1 ? 1 : -1;
                    } else if (rankVO.getOrder().equals("asc") && v1 != null && v2 != null) {
                        return v1 < v2 ? 1 : -1;
                    } else {
                        return 0;
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                    return 0;
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return valueList;
    }

    // 返回所有组合数据类型
    @Override
    public List<Type> getCombinedType() {
        return typeMapper.selectList(new QueryWrapper<>());
    }

    // 根据组合数据类型Id获取该类型下的组合数据项
    @Override
    public List<CBFIMapping> getCombinedItemByTypeId(Integer typeId) {
        // 首先根据typeId查询映射表，获取组合指标id列表
        List<Integer> itemIdList = typeCombinedItemMappingMapper.selectList(new QueryWrapper<TypeCombinedItemMapping>().eq("type_id", typeId)).stream().map(TypeCombinedItemMapping::getCombinedItemId).collect(Collectors.toList());
        // 根据组合指标id列表查询组合指标表，获取组合指标列表
        return cbfimappingMapper.selectList(new QueryWrapper<CBFIMapping>().in("cbfi_bfi_mapping_id", itemIdList));
    }

    @Override
    public Map<Integer, String> getCombinedItemCompareObjByType(String type) {
        // 如果type为空，则返回null
        if (type == null) {
            return null;
        }
        Map<Integer, String> map = new HashMap<>();
        // 如果type是hospital, 查询user表,选取center_type为0的用户，获取医院名称列表
        if (type.equals("hospital")) {
            List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("center_type", 0));
            for (User user : userList) {
                map.put(user.getId(), user.getHospitalName());
            }
        }
        // 如果type是center, 查询center表,获取中心名称列表
        if (type.equals("center")) {
            List<Center> centerList = centerMapper.selectList(new QueryWrapper<>());
            for (Center center : centerList) {
                map.put(center.getCenterId(), center.getCenterName());
            }
        }
        return map;
    }


    // 获取用户列表
    public List<User> getAvailableUserList(CountVO countVO) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (countVO.getHospitalLevel() != null && !"".equals(countVO.getHospitalLevel())) {
            userQueryWrapper.eq("hospital_level", countVO.getHospitalLevel());
        }

        if (countVO.getHospitalType() != null && !"".equals(countVO.getHospitalType())) {
            userQueryWrapper.eq("hospital_type", countVO.getHospitalType());
        }

        List<User> users = userMapper.selectList(userQueryWrapper);

        // 去除不属于给定中心下的医院
        if (countVO.getCenterId() != null) {
            List<CenterUserMapping> mappings = centerUserMappingMapper.selectList(new QueryWrapper<CenterUserMapping>().eq("center_id", countVO.getCenterId()));
            // 将users中userId与mappings中userId不同的对象删除
            users.removeIf(user -> mappings.stream().noneMatch(mapping -> mapping.getUserId().equals(user.getId())));
        }
        // 去除中心类型为1或2的医院
        users.removeIf(user -> user.getCenterType() == 1 || user.getCenterType() == 2);
        return users;
    }
    public List<User> getAvailableUserList(String hospitalLevel, String hospitalType, Integer centerId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (hospitalLevel != null && !"".equals(hospitalLevel)) {
            userQueryWrapper.eq("hospital_level", hospitalLevel);
        }

        if (hospitalType != null && !"".equals(hospitalType)) {
            userQueryWrapper.eq("hospital_type", hospitalType);
        }

        List<User> users = userMapper.selectList(userQueryWrapper);

        // 去除不属于给定中心下的医院
        if (centerId != null) {
            List<CenterUserMapping> mappings = centerUserMappingMapper.selectList(new QueryWrapper<CenterUserMapping>().eq("center_id", centerId));
            // 将users中userId与mappings中userId不同的对象删除
            users.removeIf(user -> mappings.stream().noneMatch(mapping -> mapping.getUserId().equals(user.getId())));
        }
        // 去除中心类型为1或2的医院
        users.removeIf(user -> user.getCenterType() == 1 || user.getCenterType() == 2);
        return users;
    }
    public List<User> getAvailableUserList(RankVO rankVO) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (rankVO.getHospitalLevel() != null && !"".equals(rankVO.getHospitalLevel())) {
            userQueryWrapper.eq("hospital_level", rankVO.getHospitalLevel());
        }

        if (rankVO.getHospitalType() != null && !"".equals(rankVO.getHospitalType())) {
            userQueryWrapper.eq("hospital_type", rankVO.getHospitalType());
        }

        List<User> users = userMapper.selectList(userQueryWrapper);

        // 去除不属于给定中心下的医院
        if (rankVO.getCenterId() != null) {
            List<CenterUserMapping> mappings = centerUserMappingMapper.selectList(new QueryWrapper<CenterUserMapping>().eq("center_id", rankVO.getCenterId()));
            // 将users中userId与mappings中userId不同的对象删除
            users.removeIf(user -> mappings.stream().noneMatch(mapping -> mapping.getUserId().equals(user.getId())));
        }
        // 去除中心类型为1或2的医院
        users.removeIf(user -> user.getCenterType() == 1 || user.getCenterType() == 2);
        return users;
    }
    // 初始化返回总和数据
    public TotalValueDTO createDefaultTotalValue(CountVO countVO) {
        // 返回的总和数据
        TotalValueDTO totalValue = new TotalValueDTO();
        // 将总和初始化为0
        totalValue.setJanuary(0);
        totalValue.setFebruary(0);
        totalValue.setMarch(0);
        totalValue.setApril(0);
        totalValue.setMay(0);
        totalValue.setJune(0);
        totalValue.setJuly(0);
        totalValue.setAugust(0);
        totalValue.setSeptember(0);
        totalValue.setOctober(0);
        totalValue.setNovember(0);
        totalValue.setDecember(0);
        totalValue.setItemName(countVO.getItemName());
        totalValue.setYear(countVO.getYear());
        return totalValue;
    }
    // 初始化Value
    public Value createDefaultValue() {
        Value value = new Value();
        value.setJanuary(0);
        value.setFebruary(0);
        value.setMarch(0);
        value.setApril(0);
        value.setMay(0);
        value.setJune(0);
        value.setJuly(0);
        value.setAugust(0);
        value.setSeptember(0);
        value.setOctober(0);
        value.setNovember(0);
        value.setDecember(0);
        return value;
    }
    public Value createDefaultValue(String itemName, int year) {
        Value value = new Value();
        value.setJanuary(0);
        value.setFebruary(0);
        value.setMarch(0);
        value.setApril(0);
        value.setMay(0);
        value.setJune(0);
        value.setJuly(0);
        value.setAugust(0);
        value.setSeptember(0);
        value.setOctober(0);
        value.setNovember(0);
        value.setDecember(0);
        value.setItemName(itemName);
        value.setYear(year);
        return value;
    }
    public Value createDefaultValue(String hospitalName, String itemName, int year) {
        Value value = new Value();
        value.setJanuary(0);
        value.setFebruary(0);
        value.setMarch(0);
        value.setApril(0);
        value.setMay(0);
        value.setJune(0);
        value.setJuly(0);
        value.setAugust(0);
        value.setSeptember(0);
        value.setOctober(0);
        value.setNovember(0);
        value.setDecember(0);
        value.setHospitalName(hospitalName);
        value.setItemName(itemName);
        value.setYear(year);
        return value;
    }
    // 获取组合指标的值
    public List<Value> getCombinedItemValue(String itemName, String hospitalName, Integer year) {
        CBFIMapping combinedItem = cbfimappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", itemName));
        // 分子项数据名称
        String numerator = combinedItem.getNumerator();
        // 分母项数据名称
        String denominator = combinedItem.getDenominator();

        // 获取医院分子指标信息
        QueryWrapper<Value> numeratorValueQueryWrapper = new QueryWrapper<>();
        numeratorValueQueryWrapper.eq("hospital_name", hospitalName);
        numeratorValueQueryWrapper.eq("item_name", numerator);
        numeratorValueQueryWrapper.eq("year", year);
        Value numeratorValue = valueMapper.selectOne(numeratorValueQueryWrapper);
        // 获取医院分母指标信息
        QueryWrapper<Value> denominatorValueQueryWrapper = new QueryWrapper<>();
        denominatorValueQueryWrapper.eq("hospital_name", hospitalName);
        denominatorValueQueryWrapper.eq("item_name", denominator);
        denominatorValueQueryWrapper.eq("year", year);
        Value denominatorValue = valueMapper.selectOne(denominatorValueQueryWrapper);

        List<Value> valueList = new ArrayList<>();
        valueList.add(numeratorValue);
        valueList.add(denominatorValue);
        return valueList;
    }
}
