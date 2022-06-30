package io.oicp.yorick61c.hospital_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.*;
import io.oicp.yorick61c.hospital_system.pojo.*;
import io.oicp.yorick61c.hospital_system.pojo.dto.AddCombinedItemDto;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;
import io.oicp.yorick61c.hospital_system.pojo.vo.ValueVo;
import io.oicp.yorick61c.hospital_system.service.BioFeatureService;
import io.oicp.yorick61c.hospital_system.utils.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BioFeatureServiceImpl implements BioFeatureService {

    @Resource
    private BioFeatureMapper bioFeatureMapper;

    @Resource
    private CombinedBioFeatureMapper combinedBioFeatureMapper;

    @Resource
    private ValueMapper valueMapper;

    @Resource
    private CBFIMappingMapper cbfiMappingMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TempListMapper tempListMapper;

    @Resource
    private CenterUserMapper centerUserMapper;

    @Resource
    private ValueCacheMapper valueCacheMapper;

    @Resource
    private CenterMapper centerMapper;
    @Override
    public List<BioFeatureItem> getBioFeatureNameList() {
        return bioFeatureMapper.selectList(new QueryWrapper<BioFeatureItem>().select("item_name"));
    }

    @Override
    public int saveValueCache(ValueCache value) {
        value.setSaveTime(TimeUtil.getPresentFormatTimeString());
        // 查询后端看之前有没有存过
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("item_name", value.getItemName());
        queryMap.put("hospital_name", value.getHospitalName());
        queryMap.put("year", value.getYear());
        ValueCache resValue = valueCacheMapper.selectOne(new QueryWrapper<ValueCache>().allEq(queryMap));

        int res;
        // 之前没存过
        if (resValue == null){
            res = valueCacheMapper.insert(value);
        // 之前已经存过
        } else {
            value.setValueId(resValue.getValueId());
            res = valueCacheMapper.myUpdate(value);
        }
        return res;
    }

    @Override
    public List<ValueCache> getValueCacheListByHospitalName(String hospitalName) {
        hospitalName = hospitalName.replace("\"", "");
        return valueCacheMapper.selectList(new QueryWrapper<ValueCache>().eq("hospital_name", hospitalName));
    }

    @Override
    public int commitValueCache(ValueCache cache) {
        // 先根据item_name和hospital_name和year查询是否已经存在
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("item_name", cache.getItemName());
        queryMap.put("hospital_name", cache.getHospitalName());
        queryMap.put("year", cache.getYear());
        Value resValue = valueMapper.selectOne(new QueryWrapper<Value>().allEq(queryMap));

        int res;
        // 要插入的值
        Value value = new Value();
        // 之前没存过
        if (resValue == null){
            // 将cache中的值拷贝到value中
            BeanUtils.copyProperties(cache, value);
            // 设置保存时间
            value.setSaveTime(TimeUtil.getPresentFormatTimeString());
            // 插入
            res = valueMapper.insert(value);
            // 删除cache 2022.6.25 修改为不删除cache
            // valueCacheMapper.deleteById(cache.getValueId());
        // 之前已经存过
        } else {
            // 将value中为空且cache中有的值拷贝到value中
            value.setValueId(resValue.getValueId());
            if (resValue.getJanuary() == null && cache.getJanuary() != null){
                value.setJanuary(cache.getJanuary());
            }
            if (resValue.getFebruary() == null && cache.getFebruary() != null){
                value.setFebruary(cache.getFebruary());
            }
            if (resValue.getMarch() == null && cache.getMarch() != null){
                value.setMarch(cache.getMarch());
            }
            if (resValue.getApril() == null && cache.getApril() != null){
                value.setApril(cache.getApril());
            }
            if (resValue.getMay() == null && cache.getMay() != null){
                value.setMay(cache.getMay());
            }
            if (resValue.getJune() == null && cache.getJune() != null){
                value.setJune(cache.getJune());
            }
            if (resValue.getJuly() == null && cache.getJuly() != null){
                value.setJuly(cache.getJuly());
            }
            if (resValue.getAugust() == null && cache.getAugust() != null){
                value.setAugust(cache.getAugust());
            }
            if (resValue.getSeptember() == null && cache.getSeptember() != null){
                value.setSeptember(cache.getSeptember());
            }
            if (resValue.getOctober() == null && cache.getOctober() != null){
                value.setOctober(cache.getOctober());
            }
            if (resValue.getNovember() == null && cache.getNovember() != null){
                value.setNovember(cache.getNovember());
            }
            if (resValue.getDecember() == null && cache.getDecember() != null){
                value.setDecember(cache.getDecember());
            }
            // 删除cache 2022.6.25 修改为不删除cache
            // valueCacheMapper.deleteById(cache.getValueId());
            // 设置更新时间
            value.setSaveTime(TimeUtil.getPresentFormatTimeString());
            res = valueMapper.myUpdate(value);
        }
        return res;
    }

    @Override
    public int updateValue(Value value) {
        return valueMapper.myUpdate(value);
    }

    @Override
    public int deleteValue(Value value) {
        return valueMapper.deleteById(value.getValueId());
    }

    @Override
    public Map<String, Object> findSingleItemValue(Value value) {
        Map<String, Object> getSingleItemInfoMap = new HashMap<>();
        getSingleItemInfoMap.put("item_name", value.getItemName());
        getSingleItemInfoMap.put("hospital_name", value.getHospitalName());
        getSingleItemInfoMap.put("year", value.getYear());
        Value res = valueMapper.selectOne(new QueryWrapper<Value>().allEq(getSingleItemInfoMap));
        ValueVo valueVo = new ValueVo();
        try {
            BeanUtils.copyProperties(res, valueVo);
        } catch (IllegalArgumentException e){
            return null;
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("item_name", value.getItemName());
        returnMap.put("values", valueVo);
        return returnMap;
    }

    @Override
    // 根据医院名称批量提交缓存数据
    public int batchCommitValueCacheByHospitalName(String hospitalName) {
        // 前端上传至后端的string中包含双引号，要先去除掉hospitalName字符串的双引号
        hospitalName = hospitalName.replace("\"", "");
        // 先根据医院名称查询出所有缓存值
        List<ValueCache> valueCaches = valueCacheMapper.selectList(new QueryWrapper<ValueCache>().eq("hospital_name", hospitalName));
        // 如果没有缓存值，直接返回
        int res = 0;
        if (valueCaches.size() == 0){
            return 0;
        } else {
            // 如果有缓存值，则遍历缓存值，插入数据库
            for (ValueCache valueCache : valueCaches){
                res += commitValueCache(valueCache);
            }
        }
        return res;
    }

    @Override
    public int updateCombinedItem(AddCombinedItemDto dto) {
        CBFIMapping cbfiMapping = new CBFIMapping();
        BeanUtils.copyProperties(dto, cbfiMapping);
        return cbfiMappingMapper.update(cbfiMapping, new QueryWrapper<CBFIMapping>().eq("cbfi_bfi_mapping_id", dto.getCbfiBfiMappingId()));
    }

    @Override
    public int updateSingleItem(BioFeatureItem item) {
        return bioFeatureMapper.update(item, new QueryWrapper<BioFeatureItem>().eq("bio_feature_item_id", item.getBioFeatureItemId()));
    }


    @Override
    public List<CombinedBioFeatureItem> getCombinedBioFeatureNameList() {
        return combinedBioFeatureMapper.selectList(new QueryWrapper<CombinedBioFeatureItem>().select("item_name"));
    }

    @Override
    public Map<String, Object> getEchartsValue(CBFIDto cbfiDto) {
        CBFIMapping CBFI = cbfiMappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", cbfiDto.getCombinedBioFeatureName()));
        assert CBFI != null;
        Map<String, Object> getNumeratorHashMap = new HashMap<>();
        Map<String, Object> getDenominatorHashMap = new HashMap<>();
        // 查询条件：item_name, hospital_name, year 返回一条value记录。
        // numerator: 分子
        getNumeratorHashMap.put("item_name", CBFI.getNumerator());
        getNumeratorHashMap.put("hospital_name", cbfiDto.getHospitalName());
        getNumeratorHashMap.put("year", cbfiDto.getYear());
        // denominator: 分母
        getDenominatorHashMap.put("item_name", CBFI.getDenominator());
        getDenominatorHashMap.put("hospital_name", cbfiDto.getHospitalName());
        getDenominatorHashMap.put("year", cbfiDto.getYear());

        // 获取两条value记录
        Value value = valueMapper.selectOne(new QueryWrapper<Value>().allEq(getNumeratorHashMap));
        Value value2 = valueMapper.selectOne(new QueryWrapper<Value>().allEq(getDenominatorHashMap));

        ValueVo nValue = new ValueVo();
        ValueVo dValue = new ValueVo();

        // 返回给前端：1.生命特征类指标名  2.分子12个月份对应的值 3.分母12个月份对应的值
        Map<String, Object> returnMap = new HashMap<>();
        if (value != null) {
            BeanUtils.copyProperties(value, nValue);
            returnMap.put("numerator", nValue);
        } else {
            returnMap.put("numerator", null);
        }
        if (value2 != null) {
            BeanUtils.copyProperties(value2, dValue);
            returnMap.put("denominator", dValue);
        } else {
            returnMap.put("denominator", null);
        }
        returnMap.put("item_name", cbfiDto.getCombinedBioFeatureName());
        returnMap.put("ratio", CBFI.getRatio());
        returnMap.put("year", cbfiDto.getYear());
        returnMap.put("numeratorName", CBFI.getNumerator());
        returnMap.put("denominatorName", CBFI.getDenominator());
        return returnMap;

    }

    @Override
    public List<BioFeatureItem> getBioFeatureList() {
        return bioFeatureMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public int saveItem(BioFeatureItem item) {
        List<BioFeatureItem> bioFeatureItems = bioFeatureMapper.selectList(new QueryWrapper<BioFeatureItem>().select("item_name"));
        for (BioFeatureItem i: bioFeatureItems) {
            if (i.getItemName().equals(item.getItemName())) {
                return 2;
            }
        }
        return bioFeatureMapper.insert(item);
    }

    @Override
    public int deleteBFI(BioFeatureItem item) {
        return bioFeatureMapper.deleteById(item.getBioFeatureItemId());
    }

    @Override
    public List<CBFIMapping> getCBFIList() {
        return cbfiMappingMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public int deleteCBFI(CBFIMapping item) {
        Map<String, Object> delMap = new HashMap<>();
        delMap.put("item_name", item.getItemName());
        combinedBioFeatureMapper.deleteByMap(delMap);
        return cbfiMappingMapper.deleteById(item.getCbfiBfiMappingId());
    }

    @Override
    @Transactional
    public int saveCombinedItem(AddCombinedItemDto dto) {
        CBFIMapping cbfiMapping = new CBFIMapping();
        CombinedBioFeatureItem combinedBioFeatureItem = new CombinedBioFeatureItem();
        BeanUtils.copyProperties(dto, cbfiMapping);
        BeanUtils.copyProperties(dto, combinedBioFeatureItem);
        int res1 = cbfiMappingMapper.insert(cbfiMapping);
        int res2 = combinedBioFeatureMapper.insert(combinedBioFeatureItem);
        return res1 + res2 == 2 ? 1 : 0;
    }

    @Override
    public List<Value> getValueListByHospitalName(String hospitalName) {
        hospitalName = hospitalName.replace("\"", "");
        return valueMapper.selectList(new QueryWrapper<Value>().eq("hospital_name", hospitalName));
    }

    @Override
    public int deleteValueCache(ValueCache value) {
        return valueCacheMapper.deleteById(value.getValueId());
    }

    @Override
    public List<User> getOtherHospitalDataById(Integer userId) {
        User user = userMapper.selectById(userId);
        Integer centerType = user.getCenterType();
        List<User> userList = new ArrayList<>();
        // 非中心医院
        if (centerType == 0) {
            return getTempUsers(userId, userList);
        }
        // 主中心医院
        if (centerType == 2) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("center_type", 0);
            return userMapper.selectList(userQueryWrapper);
        }
        // 分中心医院
        if (centerType == 1){
            User subCenterUser = userMapper.selectById(userId);
            Center subCenter = centerMapper.selectOne(new QueryWrapper<Center>().eq("center_name", subCenterUser.getHospitalName()));
            List<CenterUserMapping> centerUserMappingList = centerUserMapper.selectList(new QueryWrapper<CenterUserMapping>().eq("center_id", subCenter.getCenterId()));
            // 将中心对应医院加入返回列表中
            for (CenterUserMapping mapping: centerUserMappingList) {
                User tempUser = userMapper.selectById(mapping.getUserId());
                if (tempUser != null && tempUser.getCenterType() == 0) {
                    userList.add(tempUser);
                }
            }
            return getTempUsers(userId, userList);

        }
        return null;
    }

    // 获取临时权限
    private List<User> getTempUsers(Integer userId, List<User> userList) {
        List<TempList> tempList = tempListMapper.selectList(new QueryWrapper<TempList>().eq("user_id", userId));
        if (tempList.size() == 0)
            return userList;
        for (TempList t: tempList) {
            userList.add(userMapper.selectById(t.getOtherHospitalUserId()));
        }
        return userList;
    }



}
