
package com.turtlebone.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turtlebone.task.entity.TaskUser;
import com.turtlebone.task.model.TaskUserModel;
import com.turtlebone.task.repository.TaskUserRepository;
import com.turtlebone.task.service.TaskUserService;
import com.turtlebone.task.util.BeanCopyUtils;

@Service
public class TaskUserServiceImpl implements TaskUserService {
	@Autowired
	private TaskUserRepository taskUserRepo;
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return taskUserRepo.deleteByPrimaryKey(id);
	}
	

    /*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public TaskUserModel findByPrimaryKey(Integer id) {
		TaskUser taskUser = taskUserRepo.selectByPrimaryKey(id);
		return BeanCopyUtils.map(taskUser, TaskUserModel.class);
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKey(TaskUserModel taskUserModel) {
		return taskUserRepo.updateByPrimaryKey(BeanCopyUtils.map(taskUserModel, TaskUser.class));
	}
	
	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int updateByPrimaryKeySelective(TaskUserModel taskUserModel) {
		return taskUserRepo.updateByPrimaryKeySelective(BeanCopyUtils.map(taskUserModel, TaskUser.class));
	}
	

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int create(TaskUserModel taskUserModel) {
		return taskUserRepo.insert(BeanCopyUtils.map(taskUserModel, TaskUser.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int createSelective(TaskUserModel taskUserModel) {
		return taskUserRepo.insertSelective(BeanCopyUtils.map(taskUserModel, TaskUser.class));
	}

	/*
	 * @Transactional is not necessarry for the single atomic CRUD statement for better performance, 
	 * but you still have to take care of @Transactional for multi-statements scenario.
	 * if read only,please config as "@Transactional(readOnly = true)",otherwise "@Transactional"
	 */
	@Override
	public int selectCount(TaskUserModel taskUserModel) {
		return taskUserRepo.selectCount(BeanCopyUtils.map(taskUserModel, TaskUser.class));
	}



}
