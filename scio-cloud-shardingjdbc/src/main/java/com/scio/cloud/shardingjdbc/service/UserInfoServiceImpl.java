package com.scio.cloud.shardingjdbc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scio.cloud.shardingjdbc.domain.UserInfo;
import com.scio.cloud.shardingjdbc.repository.UserInfoJpaRepository;
import com.scio.cloud.shardingjdbc.utils.BeanCopyUtils;
import com.scio.cloud.shardingjdbc.web.vo.UserInfoVo;
/**
 * user info service
 *
 * @author Wang.ch
 * @date 2019-04-15 10:29:47
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
  @Autowired private UserInfoJpaRepository userinfo;

  @Override
  @Transactional
  public UserInfoVo save(UserInfoVo vo) {
    UserInfo save = BeanCopyUtils.copy(vo, UserInfo::new);
    userinfo.save(save);
    return BeanCopyUtils.copy(save, UserInfoVo::new);
  }

  @Override
  @Transactional(readOnly = true)
  public UserInfoVo findById(String id) {
    return userinfo
        .findById(id)
        .map(info -> BeanCopyUtils.copy(info, UserInfoVo::new))
        .orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public UserInfoVo findByName(String name) {
    return userinfo
        .findByName(name)
        .map(info -> BeanCopyUtils.copy(info, UserInfoVo::new))
        .orElse(null);
  }

  @Override
  @Transactional
  public List<UserInfoVo> save(List<UserInfoVo> userinfos) {
    List<UserInfo> results =
        userinfo.batchSave(
            userinfos
                .stream()
                .map(info -> BeanCopyUtils.copy(info, UserInfo::new))
                .collect(Collectors.toList()));
    return results
        .stream()
        .map(info -> BeanCopyUtils.copy(info, UserInfoVo::new))
        .collect(Collectors.toList());
  }

  @Override
  public List<UserInfoVo> findAll() {
    return userinfo
        .findAll()
        .stream()
        .map(info -> BeanCopyUtils.copy(info, UserInfoVo::new))
        .collect(Collectors.toList());
  }
}
