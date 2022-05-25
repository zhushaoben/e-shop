<template>
    <div>
        <el-input prefix-icon="iconfont icon-sousuo" size="big" @keyup.enter.native="doSearch" v-model="searchTableInfo" placeholder="请输入搜索内容" style="width:480px;margin-left: 400px;margin-top: 30px;margin-bottom: 30px;"></el-input>
        <el-button type="primary" size="big" @click="doSearch" style="margin-left: 10px">搜索</el-button>
        <div style="background-color: white">
            <el-row style="padding: 30px;border-top: 2px solid #eee;" v-for="(item,index) in searchList" :key="index">
                <el-col :span="4">
                    <el-image  @click="seeDetail(item.id)"
                            style="width: 100px; height: 100px"
                            :src="item.imgPath"></el-image>
                </el-col>
                <el-col :span="4"><div  @click="seeDetail(item.id)">{{item.name}}</div></el-col>
                <el-col @click="seeDetail(item.id)" :span="4" style="font-size: large;font-weight: bold;color: orangered;"><div  @click="seeDetail(item.id)">￥{{item.price}}</div></el-col>
            </el-row>
        </div>
        <div class="table-content" style="margin-top: 10px">
            <div class="block" v-show="searchList.length">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[10, 20, 30, 40]"
                        :page-size="10"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="searchList.length"
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        data() {
            return {
                rawList: [],
                searchList: [],
                currentPage: 1,
                pageSize: 10,
                pageList: [],
                searchTableInfo:''
            };
        },

        mounted() {
            this.initTable();
        },

        watch:{
        },

        methods: {
            handleSizeChange: function(pageSize) {
                this.pageSize = pageSize;
                this.handleCurrentChange(this.currentPage);
            },
            handleCurrentChange: function(currentPage) {
                this.currentPage = currentPage;
                this.currentChangePage(this.searchList, currentPage);
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
            doSearch(){
                axios.get("api/product/search/"+this.searchTableInfo+"?page="+this.currentPage+"&rows="+this.pageSize).then(response =>{
                    this.searchList=response.data.value;
                })
            },
            initTable(){
                this.currentPage= 1;
                this.pageSize= 10;
                // this.doSearch();
            },
            seeDetail(id){
                this.$router.push('/detail?id='+id);
            }
        }
    };
</script>

<style>

</style>