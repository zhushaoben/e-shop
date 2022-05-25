package com.ldh.edu.maiyu.sys.web;


import com.ldh.edu.maiyu.sys.model.Product;
import com.ldh.edu.maiyu.sys.model.ProductDomain;
import com.ldh.edu.maiyu.sys.service.ProductService;
import com.ldh.edu.maiyu.sys.statu.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(
        value="product",
        tags = "商品管理"
)
@RestController
@RequestMapping("/api/sys/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation(value="获得商品实体",notes = "根据id获取信息")
    @RequestMapping(
            method= RequestMethod.GET,
            value = "single"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByPK(@RequestParam("id")String id){
        return Status.builder()
                .addDataValue(productService.getSimpleMapByPK(id))
                .map();
    }

    @ApiOperation(value="获取product集合",notes = "根据id集合查找获得")
    @RequestMapping(
            value="list",
            method = RequestMethod.GET
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getByIdList(@RequestBody List<String> idList){
        return Status.builder()
                .addDataValue(productService.getMapListByKeySet(idList))
                .map();
    }


    @ApiOperation(value="新增单个商品",notes = "传入商品实体，返回插入后带主键等信息的实体")
    @RequestMapping(
            value="",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> post(@RequestBody Product product){
        return Status.builder()
                .addDataValue(productService.post(ProductDomain.buildMap(product)))
                .map();
    }

    @ApiOperation(value="批量新增商品实体",notes="同时返回插入后带主键的信息")
    @RequestMapping(
            value="postBatch",
            method=RequestMethod.POST
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> postBatch(@RequestBody List<Product> products){
        return Status.builder()
                .addDataValue(productService.postList(products))
                .map();
    }

    @ApiOperation(value="更新商品实体",notes = "同时返回插入后可能有改动的信息")
    @RequestMapping(
            method=RequestMethod.PATCH,
            value=""
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> patch(@RequestBody Product product){
        return Status.builder()
                .addDataValue(productService.updateMapping(ProductDomain.buildMap(product)))
                .map();
    }

    @ApiOperation(value="删除实体",notes="根据主键id进行删除，返回删除数量")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value=""
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> delete(@RequestParam("id")String id){
        return Status.builder()
                .addDataValue(productService.delete(id))
                .map();
    }

    @ApiOperation(value="批量删除",notes="根据主键id进行批量删除，返回删除数量")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value="deleteBatch"
    )
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> deleteBatch(@RequestBody List<String> idList){
        return Status.builder()
                .addDataValue(productService.deleteList(idList))
                .map();
    }

    @ApiOperation(value="查找所有商品",notes = "返回所有商品项，可以传入page与limit参数来进行分页。如果想要以特定顺序返回可以联系我修改")
    @RequestMapping(method=RequestMethod.GET,value="selectAll")
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> selectAll(@RequestParam(value = "page",required = false)Long page,
                                        @RequestParam(value = "rows",required = false)Integer limit){
        Map<String,Object> map = new HashMap<>();
        if(page!=null){
            map.put("page",page);
        }
        if(limit!=null){
            map.put("rows",limit);
        }
        return Status.builder()
                .addDataValue(productService.getMapListByMap(map))
                .map();
    }

    @ApiOperation(value="查找某个商家的商品",notes = "传入商家的主键id值，可以选择分页，返回所有商品")
    @RequestMapping(method = RequestMethod.GET,value="selectAll/{busmanId}")
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> selectByBusManID(@PathVariable("busmanId")String busmanId,
                                               @RequestParam(value = "page",required = false)Long page,
                                               @RequestParam(value = "rows",required = false)Integer limit){
        Map<String,Object> map = new HashMap<>();
        map.put("busmanId",busmanId);
        if(page!=null){
            map.put("page",page);
        }
        if(limit!=null){
            map.put("rows",limit);
        }
        return Status.builder()
                .addDataValue(productService.getMapListByMap(map))
                .map();
    }

    @ApiOperation(value="根据名字查找某个商品",notes = "采用模糊查询进行查找，可以选择分页,可以返回商品信息")
    @RequestMapping(method = RequestMethod.GET,value = "search/{name}")
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> selectByName(@PathVariable("name")String name,
                                           @RequestParam(value = "page",required = false)Long page,
                                           @RequestParam(value = "rows",required = false)Integer limit){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        if(page!=null){
            map.put("page",page);
        }
        if(limit!=null){
            map.put("rows",limit);
        }
        return Status.builder()
                .addDataValue(productService.getMapListByMap(map))
                .map();
    }




}
