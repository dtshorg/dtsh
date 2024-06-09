package com.fdzc.springboot01.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.fdzc.springboot01.common.dto.IdDTO;
import com.fdzc.springboot01.entity.Travel;
import com.fdzc.springboot01.mapper.TravelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TravelService {

    @Resource
    TravelMapper travelMapper;

    public PageDTO<Travel> findAllTravel(int page, int pagesize) {
        PageDTO<Travel> pageDTO = new PageDTO<>();
        Page<Travel> taskPage = travelMapper.selectPage(new Page<>(page, pagesize), null);
        pageDTO.setRecords(taskPage.getRecords());
        pageDTO.setTotal(taskPage.getTotal());
        return pageDTO;
    }

    public Travel findById(Integer id) {
        return travelMapper.selectById(id);
    }

    public Integer addOneTravel(Travel travel) {
        return travelMapper.insert(travel);
    }

    public Integer updateOneTravel(Travel travel) {
        return travelMapper.updateById(travel);
    }

    public Integer deleteOneTravel(Integer id) {
        return travelMapper.deleteById(id);
    }

    public Integer deleteMultipleTravel(IdDTO idDTO) {
        List<Integer> ids = idDTO.getIds();
        Integer res = 0;
        for (Integer id : ids) {
            res += deleteOneTravel(id);
        }
        return res;
    }

}
