package wang.laic.chaos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wang.laic.chaos.mapper.RoleMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
}
