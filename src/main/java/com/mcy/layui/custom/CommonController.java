package com.mcy.layui.custom;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class CommonController<T, ID, Form extends BaseForm<ID>> {
    @SuppressWarnings("unchecked")
    private Class<T> clazz = GenericsUtils.getSuperClassGenricType(getClass());

    @Autowired
    private CommonService<T, ID> baseService;

    @RequestMapping(value = "/manage")
    public void manage(ModelMap map) {

    }

    @RequestMapping(value = "/edit")
    public void edit(Form form, ModelMap map) throws InstantiationException, IllegalAccessException {
        T model = clazz.newInstance();
        ID id = form.getId();
        if (id != null) {
            model = baseService.findById(id);
        }
        map.put("model", model);
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(Form form) throws InstantiationException, IllegalAccessException {
        try {
            T model = clazz.newInstance();
            ID id = form.getId();
            if (id != null) {
                model = baseService.findById(id);
            }
            BeanUtils.copyProperties(form, model, "id");
            baseService.save(model);
            return new AjaxResult("数据保存成功");
        } catch (Exception e) {
            return new AjaxResult(false, "数据保存失败");
        }
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(ID id) {
        try {
            baseService.deleteById(id);
            return new AjaxResult("数据删除成功");
        } catch (Exception e) {
            return new AjaxResult(false, "数据删除失败");
        }
    }

    public Specification<T> buildSpec(Form form) {
        return null;
    }

    @RequestMapping(value = "/page")
    @ResponseBody
    public Object page(TablePageable pageParam, Form form) {
        PageRequest pageable = pageParam.bulidPageRequest();
        Specification<T> spec = buildSpec(form);
        Page<T> page = baseService.findAll(spec, pageable);
        return DataGridUtils.buildResult(page);
    }

    @RequestMapping(value = "/treedata")
    @ResponseBody
    public Object list(Form form) {
        Sort sort = bulidSort();
        Specification<T> spec = buildSpec(form);
        List<T> list = baseService.findAll(spec, sort);
        return new TreeTableModel(list);
    }

    public Sort bulidSort() {
        return Sort.by("id");
    }
}
