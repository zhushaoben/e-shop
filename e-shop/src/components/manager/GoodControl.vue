<template>
    <div>
        <el-input prefix-icon="iconfont icon-sousuo" size="small" v-model="searchTableInfo" placeholder="请输入搜索内容" style="width:240px"></el-input>
        <el-checkbox :indeterminate="isIndeterminate" style="margin-left: 10px;color: white" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <el-checkbox-group v-model="checkedsearches" style="display: inline" @change="handleCheckedCitiesChange">
            <el-checkbox v-for="search in searches" style="margin-left: 5px;color: white" :label="search" :key="search">{{search}}</el-checkbox>
        </el-checkbox-group>
        <el-input size="small"  v-model="lowprice" placeholder="最低价格" style="margin-top:10px;width:85px"></el-input>
        <a style="color: white;font-size:large"> - </a>
        <el-input size="small"  v-model="highprice" placeholder="最高价格" style="margin-top:10px;width:85px"></el-input>
        <el-button type="primary" size="small" @click="handleAdd" style="margin-left:80px">添加商品</el-button>
        <div class="table-content" style="margin-top: 10px">
            <el-table :data="pageList" border stripe @selection-change style="width: 100%;">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="center" inline class="demo-table-expand" style="width: 50%">
                            <el-form-item label="商品id" class="el-form-item">
                                <p>{{ props.row.id }}</p>
                            </el-form-item>
                            <el-form-item label="店铺id" class="el-form-item">
                                <p>{{ props.row.busmanId }}</p>
                            </el-form-item>
                            <el-form-item label="商品名" class="el-form-item">
                                <p>{{ props.row.name }}</p>
                            </el-form-item>
                            <el-form-item label="图片地址" class="el-form-item">
                                <p>{{ props.row.imgPath }}</p>
                            </el-form-item>
                            <el-form-item label="详情" class="el-form-item">
                                <p>{{ props.row.details }}</p>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column prop="id" show-overflow-tooltip="true" label="商品id"></el-table-column>
                <el-table-column prop="busmanId" show-overflow-tooltip="true" label="店铺id"></el-table-column>
                <el-table-column prop="name" show-overflow-tooltip="true" label="商品名"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
                <el-table-column prop="stock" label="库存"></el-table-column>
                <el-table-column prop="sell" label="销量"></el-table-column>
                <el-table-column prop="imgPath" show-overflow-tooltip="true" label="图片地址"></el-table-column>
                <el-table-column prop="details" show-overflow-tooltip="true" label="详情"></el-table-column>
                <el-table-column prop="gmtUpdate" label="修改时间"></el-table-column>
                <el-table-column prop="gmtCreate" label="创建时间"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="block">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage1"
                        :page-sizes="[10, 20, 30, 40]"
                        :page-size="10"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="searchList.length"
                ></el-pagination>
            </div>
        </div>
        <el-dialog
                title="编辑商品信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div class="updatePerInput">
                <el-tag>商品id</el-tag>
                <el-input size="small" :disabled="true" style="width: 80%" v-model="updatePer.id"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>店铺id</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.busmanId"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>商品名</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.name"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>价格</el-tag>
                <el-input size="small" style="width: 80%"  v-model="updatePer.price"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>库存</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.stock"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>销量</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.sell"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>图片地址</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.imgPath"></el-input>
            </div>
            <div>
                <el-divider>详情</el-divider>
                <el-input type="textarea" :rows="10" placeholder="请输入内容" v-model="updatePer.details"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
                title="新建商品信息"
                :visible.sync="addDialogVisible"
                width="30%">
            <div class="updatePerInput">
                <el-tag>店铺id</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.busmanId"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>商品名</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.name"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>价格</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.price"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>库存</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.stock"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>销量</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.sell"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>图片地址</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.imgPath"></el-input>
            </div>
            <div>
                <el-divider>详情</el-divider>
                <el-input type="textarea" :rows="10" placeholder="请输入内容" v-model="addPer.details"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="addDialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="doAdd">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "axios";
    const searchOptions = ['商品id', '店铺id', '价格','商品名', '库存','销量','图片地址','详情','修改时间','创建时间'];
    export default {
        data() {
            return {
                rawList: [],
                searchList: [],
                currentPage1: 1,
                pageSize: 10,
                pageList: [],
                dialogVisible: false,
                addDialogVisible: false,
                options:["男","女"],
                lowprice:'',
                highprice:'',
                updatePer:{
                    id:'',
                    busmanId:'',
                    price:0,
                    name:'',
                    stock:0,
                    sell:0,
                    imgPath:'',
                    details:''
                },
                addPer:{
                    busmanId:'',
                    price:0,
                    name:'',
                    stock:0,
                    sell:0,
                    imgPath:'',
                    details:''
                },
                searchTableInfo:'',
                checkedsearches: [],
                searches: searchOptions,
                checkAll: false,
                isIndeterminate: true
            };
        },

        mounted() {
            this.initTable();
        },

        watch:{
            'searchTableInfo'(){
                this.search();
            },
            'checkedsearches'(){
                this.search();
            },
            'lowprice'(){
                this.search();
            },
            'highprice'(){
                this.search();
            },
        },

        methods: {
            handleSizeChange: function(pageSize) {
                this.pageSize = pageSize;
                this.handleCurrentChange(this.currentPage1);
            },
            handleCurrentChange: function(currentPage) {
                this.currentPage1 = currentPage;
                this.currentChangePage(this.searchList, currentPage);
            },
            handleCheckAllChange(val) {
                this.checkedsearches = val ? searchOptions : [];
                this.isIndeterminate = false;
            },
            handleCheckedCitiesChange(value) {
                let checkedCount = value.length;
                this.checkAll = checkedCount === this.searches.length;
                this.isIndeterminate = checkedCount > 0 && checkedCount < this.searches.length;
            },
            currentChangePage(list, currentPage) {
                let from = (currentPage - 1) * this.pageSize;
                let to = currentPage * this.pageSize;
                this.pageList = [];
                for (; from < to; from++) {
                    if (list[from]) {
                        this.pageList.push(list[from]);
                    }
                }
            },
            initTable(){
                axios.get("/api/product/selectAll").then(response => {
                    this.rawList = response.data.value;
                    this.currentPage1= 1;
                    this.pageSize= 10;
                    this.search();
                });
            },
            doUpdate: function (){
                axios.patch("api/product",this.updatePer).then(resp=>{
                    if(resp){
                        this.initTable();
                        this.dialogVisible=false;
                    }
                })
            },
            doAdd: function (){
                axios.post("api/product",this.addPer).then(resp=>{
                    if(resp){
                        this.initTable();
                        this.addDialogVisible=false;
                    }
                })
            },
            search(){
                let from=0;
                // console.log("搜索");
                let to=this.rawList.length;
                console.log(this.rawList[0]);
                this.searchList=[];
                for(;from<to;from++){
                    if(this.searchTableInfo==''||(this.checkedsearches.indexOf('商品id')!=-1&&this.rawList[from].id.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('店铺id')!=-1&&this.rawList[from].busmanId.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('价格')!=-1&&this.rawList[from].price.toString().indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('商品名')!=-1&&this.rawList[from].name.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('库存')!=-1&&this.rawList[from].stock.toString().indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('销量')!=-1&&this.rawList[from].sell.toString().indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('图片地址')!=-1&&this.rawList[from].imgPath.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('详情')!=-1&&this.rawList[from].details.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('修改时间')!=-1&&this.rawList[from].gmtUpdate.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('创建时间')!=-1&&this.rawList[from].gmtCreate.indexOf(this.searchTableInfo)!=-1)){
                        if(this.rawList[from].price>=Number(this.lowprice)&&(this.highprice==''||this.rawList[from].price<=Number(this.highprice)))this.searchList.push(this.rawList[from]);
                    }
                }
                this.currentChangePage(this.searchList, 1);
            },
            handleAdd(){
                this.addPer={};
                this.addPer.money=0;
                this.addDialogVisible=true;
            },
            handleEdit(index,data){
                Object.assign(this.updatePer,data);
                this.dialogVisible=true;
            },
            handleDelete(index,data){
                this.$confirm('此操作将删除['+data.name+'],是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.delete("api/product/deleteBatch",{data: [data.id]}).then(resp=>{
                        if(resp){
                            this.initTable();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }
    };
</script>

<style>
    .updatePerInput{
        display: flex;
        justify-content: space-between;
        margin-top: 10px;
    }
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .demo-table-expand p {
        margin-left: 100px;
        width: 700px;
        text-overflow: ellipsis;
        word-wrap: break-word;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }
</style>