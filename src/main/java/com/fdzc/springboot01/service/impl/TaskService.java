package com.fdzc.springboot01.service.impl;

import com.alibaba.excel.EasyExcel;
import com.fdzc.springboot01.entity.dto.IdDTO;
import com.fdzc.springboot01.entity.dto.PageDTO;
import com.fdzc.springboot01.entity.vo.HotTaskVo;
import com.fdzc.springboot01.entity.Task;
import com.fdzc.springboot01.entity.UserTask;
import com.fdzc.springboot01.mapper.TaskMapper;
import com.fdzc.springboot01.mapper.UserTaskMapper;
import com.fdzc.springboot01.service.ITaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {

    @Resource
    TaskMapper taskMapper;

    @Resource
    UserTaskMapper userTaskMapper;

    public PageDTO<Task> findAllTask(int page, int pagesize, Integer id, String name, String description) {
        int offset = (page - 1) * pagesize;
        PageDTO<Task> pageDTO = new PageDTO<>();
        List<Task> tasks = taskMapper.selectAllTask(id, name, description);
        pageDTO.setRecords(tasks.stream().skip(offset).limit(pagesize).collect(Collectors.toList()));
        pageDTO.setTotal(tasks.size());
        return pageDTO;
    }

    public Task findById(Integer id) {
        return taskMapper.selectById(id);
    }

    public Integer addOneTask(Task task) {
        return taskMapper.insert(task);
    }

    public Integer updateOneTask(Task task) {
        return taskMapper.updateById(task);
    }

    public Integer deleteOneTask(Integer id) {
        return taskMapper.deleteById(id);
    }

    public List<UserTask> findAllUserTask() {
        return userTaskMapper.selectList(null);
    }

    public UserTask findUserTaskById(UserTask userTask) {
        return userTaskMapper.selectUserTaskById(userTask);
    }

    public Integer addOneUserTask(UserTask userTask) {
        if(findUserTaskById(userTask) == null) {
            return userTaskMapper.insert(userTask);
        } else {
            return -1;
        }
    }

    public Integer updateOneUserTask(UserTask userTask) {
        return userTaskMapper.updateById(userTask);
    }

    public Integer deleteOneUserTask(UserTask userTask) {
        return userTaskMapper.deleteById(userTask);
    }

    public Integer deleteMultipleTask(IdDTO idDTO) {
        List<Integer> ids = idDTO.getIds();
        Integer res = 0;
        for (Integer id : ids) {
            res += deleteOneTask(id);
        }
        return res;
    }

    public void download(HttpServletResponse response) {
        List<Task> list = taskMapper.selectList(null);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=export.xlsx");
            EasyExcel.write(response.getOutputStream(),Task.class).autoCloseStream(Boolean.FALSE).sheet("任务列表").doWrite(list);
        } catch (Exception e) {
        }
    }

    public List<HotTaskVo> findHotTask(Integer num) {
        return taskMapper.selectHotTask(num);
    }

}
