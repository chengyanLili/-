package com.example.village_back.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.ExcelSaxReader;
import com.alibaba.excel.EasyExcelFactory;
import com.example.village_back.mapper.PopulationMapper;
import com.example.village_back.service.PopulationService;
import com.example.village_back.entity.Population;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/population") //统一给接口加前缀
public class PopulationController {
    @Autowired
    private PopulationMapper populationMapper;

    @Autowired
    private PopulationService populationService;

////    查询所有数据
//    @GetMapping
//    public List<Population> findAll(){
//        return populationMapper.findAll();
//    }

    // 分页查询
    @GetMapping("/findPage")
    // 分页查询mybatis-plus的实现方式
//    public IPage<Population> getPage(@RequestParam Integer pageNum,
//                                      @RequestParam Integer pageSize,
//                                      @RequestParam String name,
//                                      @RequestParam String phone,
//                                      @RequestParam String idCard){
//        IPage<Population> page = new Page<>(pageNum,pageSize);
//        QueryWrapper<Population> queryWrapper = new QueryWrapper<>();
//        return populationService.page(page,queryWrapper);
//
//    }
    public Map<String,Object> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String name,
                                       @RequestParam String phone,
                                       @RequestParam String idCard){
        return populationService.findPage(pageNum,pageSize,name,phone,idCard);
    }

    @PostMapping("/add") //插入数据
    public int save(@RequestBody Population population){
//        新增或者更新
       return populationService.save(population);
    }

//    删除数据
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable Integer id){ //PathVariable的路径参数要有花括号{id}
            return populationMapper.delete(id);
    }

    //    批量删除
    @PostMapping("/del/batch")
    public int deleteBatch(@RequestBody List<Long> ids){ //PathVariable的路径参数要有花括号{id}
     Integer res =  populationMapper.deleteBatch(ids);
     if(res == null) return 1;
     return res;
    }

    /**
     * Excel导出
     */
    @GetMapping("/export")
    //export方法名
    public void export(HttpServletResponse response) throws IOException {
//        查出数据库中所有的数据
        List<Population> list = populationMapper.findAll();
//        在内存操作，写出浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
//        自定义标题别名
//        writer.addHeaderAlias("name","姓名");
//        writer.addHeaderAlias("gender","性别");
//        writer.addHeaderAlias("phone","电话号码");
//        writer.addHeaderAlias("age","年龄");
//        writer.addHeaderAlias("idCard","身份证号");
//        writer.addHeaderAlias("householder","是否是户主");
//        writer.addHeaderAlias("post","职位");
//        writer.addHeaderAlias("policy","政治面貌");
//        writer.addHeaderAlias("education","学历");
//        writer.addHeaderAlias("inGroup","所在小组");
        writer.setOnlyAlias(true);

//        一次性写出list的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);
//      设置浏览器响应的格式
        //设置响应头的类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //让浏览器下载文件,name是上述默认文件下载名
        String fileName = URLEncoder.encode("村民信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        //通过response.getOutputStream读临时文件，ServletOutputStream将临时文件写给浏览器
        ServletOutputStream out = response.getOutputStream();
        //刷新
        writer.flush(out, true);
        out.close();
        //关闭输出流
        writer.close();
    }
    /**
     * excel导入
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Population> list = reader.readAll((Population.class));
        if(populationService != null) {
            populationService.saveBatch(list);
        } else {
            throw new Exception("populationService对象为空");
        }
        return true;

//
//        InputStream inputStream = file.getInputStream();
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        // 获取表头并删除'id'列
//        List<String> header = reader.readRow(0).stream().map(Object::toString).collect(Collectors.toList());
//        header.remove("id");
//        // 将数据转换为Population对象并保存到数据库
//        List<Population> populations = new ArrayList<>();
//        reader.read().forEach(row -> {
//            List<String> data = row.stream().map(Object::toString).collect(Collectors.toList());
//            if (data.size() > 0 && !"id".equals(data.get(0))) {
//                Population population = new Population();
//                for (int i = 1; i < data.size(); i++) {
//                    String value = data.get(i);
//                    ReflectUtil.setFieldValue(population, header.get(i - 1), value);
//                }
//                populations.add(population);
//            }
//        });
//        for (Population population : populations) {
//            population.setId(null); // 将id设置为null，避免插入数据库时发生冲突
//        }
//        if (populationService != null) {
//            populationService.saveBatch(populations);
//        } else {
//            throw new Exception("populationService对象为空");
//        }
//        return true;
    }
}
