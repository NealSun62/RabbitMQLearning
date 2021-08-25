package com.sun.overweight.controller;

import com.sun.overweight.common.utils.BeanUtil;
import com.sun.overweight.ramp.common.model.*;
import com.sun.overweight.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sunwx33102
 * @description
 * @date 2021-05-14 14:48
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource(name = "userService")
    UserService userService;

    @GetMapping("getUser1")
    @ResponseBody
    @ApiOperation(value = "用户", notes = "用户")
    public Users getUser1(@RequestParam("name") String name) {
        Users users = userService.findDs1User(name);
        return users;
    }

    @GetMapping("getUser2")
    @ResponseBody
    @ApiOperation(value = "用户", notes = "用户")
    public Users getUser2(@RequestParam("name") String name) {
        Users users = userService.findDs2User(name);
        return users;

    }

//    @GetMapping("getInfo")
//    @ResponseBody
//    @ApiOperation(value = "info", notes = "getINfo")
//    public List<CrdmBaseComptCfgDetailResponseVo> getInfo() {
//        List<CrdmBaseComptCfgDetailTypeVo> list = userService.getInfo();
//        List<CrdmBaseComptCfgDetailResponseVo> crdmBaseComptCfgDetailResponseVos = new ArrayList<>();
//        Map<String, List<CrdmBaseComptCfgDetailTypeVo>> map = list.parallelStream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getAreaId));
//        Set<Map.Entry<String, List<CrdmBaseComptCfgDetailTypeVo>>> entries = map.entrySet();
//        for (Map.Entry entry : entries) {
//            List<CrdmBaseComptCfgDetailTypeVo> list1 = (List<CrdmBaseComptCfgDetailTypeVo>) entry.getValue();
//            Map<String, List<CrdmBaseComptCfgDetailTypeVo>> map2 = list1.parallelStream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getLabelName));
//            Set<Map.Entry<String, List<CrdmBaseComptCfgDetailTypeVo>>> entries2 = map2.entrySet();
//            for (Map.Entry entry2 : entries2) {
//                List<CrdmBaseCompTypeVo> list3 = new ArrayList<>();
//                CrdmBaseComptCfgDetailResponseVo crdmBaseComptCfgDetailResponseVo = new CrdmBaseComptCfgDetailResponseVo();
//                List<CrdmBaseComptCfgDetailTypeVo> list2 = (List<CrdmBaseComptCfgDetailTypeVo>) entry2.getValue();
//                CrdmBaseComptCfgDetailTypeVo tempVo = list2.get(0);
//                BeanUtil.copyProperties(tempVo, crdmBaseComptCfgDetailResponseVo);
//                list2.forEach(p -> {
//                    CrdmBaseCompTypeVo vo = new CrdmBaseCompTypeVo();
//                    if (p.getTypeId() != null) {
//                        vo.setTypeId(p.getTypeId());
//                        vo.setValue(p.getValue());
//                        list3.add(vo);
//                    }
//                });
//                crdmBaseComptCfgDetailResponseVo.setLabelTypeList(list3);
//                crdmBaseComptCfgDetailResponseVos.add(crdmBaseComptCfgDetailResponseVo);
//            }
//        }
//        return crdmBaseComptCfgDetailResponseVos;
//
//    }
//
//    @GetMapping("getInfo2")
//    @ResponseBody
//    @ApiOperation(value = "info", notes = "getINfo")
//    public List<CrdmBaseComptCfgDetailResponseVo2> getInfo2() {
//        List<CrdmBaseComptCfgDetailTypeVo> list = userService.getInfo();
//        List<CrdmBaseComptCfgDetailResponseVo2> crdmBaseComptCfgDetailResponseVo2s = new ArrayList<>();
//        Map<String, List<CrdmBaseComptCfgDetailTypeVo>> map = list.parallelStream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getAreaId));
//        Set<Map.Entry<String, List<CrdmBaseComptCfgDetailTypeVo>>> entries = map.entrySet();
//        for (Map.Entry entry : entries) {
//            CrdmBaseComptCfgDetailResponseVo2 crdmBaseComptCfgDetailResponseVo2 = new CrdmBaseComptCfgDetailResponseVo2();
//            List<CrdmBaseComptTypeDetailPreVo> preVos = new ArrayList<>();
//            List<CrdmBaseComptCfgDetailTypeVo> list1 = (List<CrdmBaseComptCfgDetailTypeVo>) entry.getValue();
//            CrdmBaseComptCfgDetailTypeVo crdmBaseComptCfgDetailTypeVo = list1.get(0);
//            BeanUtil.copyProperties(crdmBaseComptCfgDetailTypeVo, crdmBaseComptCfgDetailResponseVo2);
//            list1.forEach(p -> {
//                CrdmBaseComptTypeDetailPreVo crdmBaseComptTypeDetailPreVo = new CrdmBaseComptTypeDetailPreVo();
//                BeanUtil.copyProperties(p, crdmBaseComptTypeDetailPreVo);
//                preVos.add(crdmBaseComptTypeDetailPreVo);
//            });
//            Map<String, Map<String, List<CrdmBaseComptCfgDetailTypeVo>>> a = list.stream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getAreaName, Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getLabelName, Collectors.toList())));
//            crdmBaseComptCfgDetailResponseVo2.setPreVos(preVos);
//            crdmBaseComptCfgDetailResponseVo2s.add(crdmBaseComptCfgDetailResponseVo2);
//            Map<String, List<CrdmBaseComptCfgDetailTypeVo>> map2 = list1.parallelStream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getLabelName));
//            Set<Map.Entry<String, List<CrdmBaseComptCfgDetailTypeVo>>> entries2 = map2.entrySet();
//            for (Map.Entry entry2 : entries2) {
//                List<CrdmBaseCompTypeVo> list3 = new ArrayList<>();
//                CrdmBaseComptCfgDetailResponseVo crdmBaseComptCfgDetailResponseVo = new CrdmBaseComptCfgDetailResponseVo();
//                List<CrdmBaseComptCfgDetailTypeVo> list2 = (List<CrdmBaseComptCfgDetailTypeVo>) entry2.getValue();
//                CrdmBaseComptCfgDetailTypeVo tempVo = list2.get(0);
//                BeanUtil.copyProperties(tempVo, crdmBaseComptCfgDetailResponseVo);
//                list2.forEach(p -> {
//                    CrdmBaseCompTypeVo vo = new CrdmBaseCompTypeVo();
//                    if (p.getTypeId() != null) {
//                        vo.setTypeId(p.getTypeId());
//                        vo.setValue(p.getValue());
//                        list3.add(vo);
//                    }
//                });
//                crdmBaseComptCfgDetailResponseVo.setLabelTypeList(list3);
//            }
//        }
//        return crdmBaseComptCfgDetailResponseVo2s;
//
//    }

    @GetMapping("getInfo3")
    @ResponseBody
    @ApiOperation(value = "info", notes = "getINfo")
    public List<CrdmBaseComptCfgDetailResponseVo2> getInfo3() {
        List<CrdmBaseComptCfgDetailTypeVo> list = userService.getInfo();
        List<CrdmBaseComptCfgDetailResponseVo2> crdmBaseComptCfgDetailResponseVo2s = new ArrayList<>();
        Map<String, Map<String, List<CrdmBaseComptCfgDetailTypeVo>>> a = list.stream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getAreaName, Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getLabelName, Collectors.toList())));
        Map<String, List<CrdmBaseComptCfgDetailTypeVo>> a2 = list.stream().collect(Collectors.groupingBy(CrdmBaseComptCfgDetailTypeVo::getAreaName));
        Set<Map.Entry<String, Map<String, List<CrdmBaseComptCfgDetailTypeVo>>>> entries = a.entrySet();
        for (Map.Entry entry : entries) {
            CrdmBaseComptCfgDetailResponseVo2 crdmBaseComptCfgDetailResponseVo2 = new CrdmBaseComptCfgDetailResponseVo2();
            List<CrdmBaseComptCfgDetailTypeVo> tme = a2.get(entry.getKey());
            CrdmBaseComptCfgDetailTypeVo crdmBaseComptCfgDetailTypeVo2 = tme.get(0);
            BeanUtil.copyProperties(crdmBaseComptCfgDetailTypeVo2, crdmBaseComptCfgDetailResponseVo2);
            Map<String, List<CrdmBaseComptCfgDetailTypeVo>> map2 = (Map<String, List<CrdmBaseComptCfgDetailTypeVo>>) entry.getValue();
            Set<Map.Entry<String, List<CrdmBaseComptCfgDetailTypeVo>>> entries2 = map2.entrySet();
            List<CrdmBaseComptTypeDetailPreVo> preVos = new ArrayList<>();
            for (Map.Entry entry2 : entries2) {
                List<CrdmBaseCompTypeVo> list3 = new ArrayList<>();
                CrdmBaseComptTypeDetailPreVo crdmBaseComptTypeDetailPreVo = new CrdmBaseComptTypeDetailPreVo();
                List<CrdmBaseComptCfgDetailTypeVo> temp = (List<CrdmBaseComptCfgDetailTypeVo>)entry2.getValue();
                CrdmBaseComptCfgDetailTypeVo crdmBaseComptCfgDetailTypeVo = temp.get(0);
                BeanUtil.copyProperties(crdmBaseComptCfgDetailTypeVo, crdmBaseComptTypeDetailPreVo);
                temp.forEach(p -> {
                    CrdmBaseCompTypeVo vo = new CrdmBaseCompTypeVo();
                    if (p.getTypeId() != null){
                        vo.setLablTypeCode(p.getTypeId());
                        vo.setLablTypeVal(p.getValue());
                        list3.add(vo);
                    }
                });
                crdmBaseComptTypeDetailPreVo.setLabelTypeList(list3);
                preVos.add(crdmBaseComptTypeDetailPreVo);
            }
            crdmBaseComptCfgDetailResponseVo2.setPreVos(preVos);
            crdmBaseComptCfgDetailResponseVo2s.add(crdmBaseComptCfgDetailResponseVo2);
        }
        return crdmBaseComptCfgDetailResponseVo2s;

    }

}