package com.ldh.edu.maiyu.sys.domain;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value="maiyu",path="/facade/sys/cart")
public interface ProductFacade {
    @RequestMapping(value="single",method= RequestMethod.GET)
    @ResponseBody
    public ProductTo getSingleByPK(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="",method = RequestMethod.POST)
    public void post(@RequestBody ProductTo entityTo);

    @RequestMapping(value="",method=RequestMethod.PATCH)
    public void patch(@RequestBody ProductTo entityTo);

    @RequestMapping(value="",method=RequestMethod.DELETE)
    public void delete(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @ResponseBody
    public List<ProductTo> getList(@RequestBody ArrayList<String> idList);
}
