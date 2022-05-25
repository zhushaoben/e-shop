package com.ldh.edu.maiyu.sys.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(value="maiyu",path="/facade/sys/order")
public interface OrderFacade {
    @RequestMapping(value="single",method= RequestMethod.GET)
    @ResponseBody
    public OrderTo getSingleByPK(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="",method = RequestMethod.POST)
    public void post(@RequestBody OrderTo entityTo);

    @RequestMapping(value="",method=RequestMethod.PATCH)
    public void patch(@RequestBody OrderTo entityTo);

    @RequestMapping(value="",method=RequestMethod.DELETE)
    public void delete(@RequestParam(value="id",required = true)String id);

    @RequestMapping(value="idList",method=RequestMethod.GET)
    @ResponseBody
    public List<OrderTo> getList(@RequestBody ArrayList<String> idList);
}
