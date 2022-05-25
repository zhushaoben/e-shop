package com.ldh.edu.maiyu.sys.domain;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value="maiyu",path="/facade/sys/orderProduct")
public interface OrderProductFacade {
    @RequestMapping(value="single",method= RequestMethod.GET)
    @ResponseBody
    public OrderProductTo getSingleByPK(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="",method = RequestMethod.POST)
    public void post(@RequestBody OrderProductTo entityTo);

    @RequestMapping(value="",method=RequestMethod.PATCH)
    public void patch(@RequestBody OrderProductTo entityTo);

    @RequestMapping(value="",method=RequestMethod.DELETE)
    public void delete(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @ResponseBody
    public List<OrderProductTo> getList(@RequestBody ArrayList<String> idList);
}
