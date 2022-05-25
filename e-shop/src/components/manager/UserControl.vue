<template>
    <div>
        <el-input prefix-icon="iconfont icon-sousuo" size="small" v-model="searchTableInfo" placeholder="请输入搜索内容" style="width:240px"></el-input>
        <el-checkbox :indeterminate="isIndeterminate" style="margin-left: 10px;color: white" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <el-checkbox-group v-model="checkedsearches" style="display: inline" @change="handleCheckedCitiesChange">
            <el-checkbox v-for="search in searches" style="margin-left: 5px;color: white" :label="search" :key="search">{{search}}</el-checkbox>
        </el-checkbox-group>
        <el-button type="primary" size="small" @click="handleAdd" style="margin-left: 10px">添加用户</el-button>
        <div class="table-content" style="margin-top: 10px">
            <el-table :data="pageList" border stripe @selection-change style="width: 100%;">
                <el-table-column prop="id" label="id"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="name" label="店铺名/昵称"></el-table-column>
                <el-table-column prop="password" label="密码"></el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
                <el-table-column prop="phone" label="电话号码"></el-table-column>
                <el-table-column prop="email" label="电子邮箱"></el-table-column>
                <el-table-column prop="type" label="类型"></el-table-column>
                <el-table-column prop="money" label="余额"></el-table-column>
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
                title="编辑用户信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div class="updatePerInput">
                <el-tag>id</el-tag>
                <el-input size="small" :disabled="true" style="width: 80%" v-model="updatePer.id"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>用户名</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.username"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>密码</el-tag>
                <el-input type="password" size="small" style="width: 80%" show-password v-model="updatePer.password"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>昵称</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.name"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>地址</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.address"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>电话号码</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.phone"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>电子邮箱</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.email"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>余额</el-tag>
                <el-input size="small" style="width: 80%" v-model="updatePer.money"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
                title="新建用户信息"
                :visible.sync="addDialogVisible"
                width="30%">
            <div class="updatePerInput">
                <el-tag>用户名</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.username"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>密码</el-tag>
                <el-input type="password" size="small" style="width: 80%" show-password v-model="addPer.password"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>昵称</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.name"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>地址</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.address"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>电话号码</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.phone"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>电子邮箱</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.email"></el-input>
            </div>
            <div class="updatePerInput">
                <el-tag>类型</el-tag>
                <el-input size="small" style="width: 80%" v-model="addPer.type"></el-input>
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
    const searchOptions = ['id', '用户名', '昵称','密码', '地址','电话号码','电子邮箱','余额','类型','创建时间'];
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
                updatePer:{
                    username:'',
                    name:'',
                    password:'',
                    email:'',
                    address:'',
                    phone:'',
                    type:0,
                    money:0},
                addPer:{
                    username:'',
                    name:'',
                    password:'',
                    email:'',
                    address:'',
                    phone:'',
                    type:0,
                    money:0
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
            }
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
                axios.get("/api/user/selectAll").then(response => {
                    this.rawList = response.data.value;
                    this.currentPage1= 1;
                    this.pageSize= 10;
                    this.search();
                });
            },
            doUpdate: function (){
                axios.patch("api/user",this.updatePer).then(resp=>{
                    if(resp){
                        this.initTable();
                        this.dialogVisible=false;
                    }
                })
            },
            doAdd: function (){
                axios.post("api/user/register",this.addPer).then(resp=>{
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
                console.log(to);
                this.searchList=[];
                for(;from<to;from++){
                    if(this.searchTableInfo==''||(this.checkedsearches.indexOf('id')!=-1&&this.rawList[from].id.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('用户名')!=-1&&this.rawList[from].username.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('昵称')!=-1&&this.rawList[from].name.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('密码')!=-1&&this.rawList[from].password.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('地址')!=-1&&this.rawList[from].address.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('电话号码')!=-1&&this.rawList[from].phone.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('电子邮箱')!=-1&&this.rawList[from].email.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('余额')!=-1&&this.rawList[from].money.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('类型')!=-1&&this.rawList[from].type.indexOf(this.searchTableInfo)!=-1)
                        ||(this.checkedsearches.indexOf('创建时间')!=-1&&this.rawList[from].gmtCreate.indexOf(this.searchTableInfo)!=-1)){
                        this.searchList.push(this.rawList[from]);
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
                this.$confirm('此操作将删除['+data.username+'],是否继续', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.delete("api/user/deleteBatch",{data: [data.id]}).then(resp=>{
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

</style>