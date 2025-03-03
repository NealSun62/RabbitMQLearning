// 操作员中心获取用户角色
TSysRoleBean roleBean = new TSysRoleBean();
roleBean.setOperatorCode(userId);
// 授权标志，1：操作权限 2授权权限
List<TSysRole> userRoleList = Optional.ofNullable(iRoleService.getCurrUserRoleList(roleBean)).orElse(Lists.newArrayList())
.stream().filter(r -> "1".equals(r.getRightFlag())).collect(Collectors.toList());
if (CollectionUtils.isNotEmpty(userRoleList)) {
// 从角色维度获取数据权限配置
QueryWrapper<SysDataPermConfig> queryWrapper = new QueryWrapper<>();
queryWrapper.lambda()
.in(SysDataPermConfig::getPermObjId, userRoleList.stream().map(TSysRole::getId).collect(Collectors.toSet()))
.eq(SysDataPermConfig::getPermObjType, PermObjTypeEnum.ROLE.getCode())
.in(SysDataPermConfig::getPermDimension, permDimensionList)
.eq(SysDataPermConfig::getPermBizCode, bizCode);
rolePermConfigs.addAll(sysDataPermConfigDao.selectList(queryWrapper));
}
// 从用户维度获取数据权限配置
QueryWrapper<SysDataPermConfig> queryWrapper = new QueryWrapper<>();
queryWrapper.lambda()
.eq(SysDataPermConfig::getPermObjId, userId)
.eq(SysDataPermConfig::getPermObjType, PermObjTypeEnum.USER.getCode())
.in(SysDataPermConfig::getPermDimension, permDimensionList)
.eq(SysDataPermConfig::getPermBizCode, bizCode);
List<SysDataPermConfig> userPermConfigs = sysDataPermConfigDao.selectList(queryWrapper);