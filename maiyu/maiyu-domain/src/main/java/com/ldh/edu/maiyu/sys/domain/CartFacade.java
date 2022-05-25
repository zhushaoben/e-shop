package com.ldh.edu.maiyu.sys.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value="maiyu",path="/facade/sys/cart")
public interface CartFacade {
    @RequestMapping(value="single",method= RequestMethod.GET)
    @ResponseBody
    public CartTo getSingleByPK(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="",method = RequestMethod.POST)
    @ResponseBody
    public void post(@RequestBody CartTo cartTo);

    @RequestMapping(value="",method=RequestMethod.PATCH)
    @ResponseBody
    public void patch(@RequestBody CartTo cartTo);

    @RequestMapping(value="",method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @ResponseBody
    public List<CartTo> getList(@RequestBody ArrayList<String> idList);

}
