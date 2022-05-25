<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">电子商城管理系统</div>
                <div>
                    <a style="color: white;margin-right: 20px">{{name}}</a>
                    <el-button type="danger" @click="logout">退出</el-button>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
                    <el-menu @select="menuClick" >
                        <el-menu-item index="/mygood">商品管理</el-menu-item>
                        <el-menu-item index="/mygood">订单管理</el-menu-item>
                    </el-menu>
                </el-aside>
                <el-main>
                    <router-view />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        data(){
            return{
                name: window.sessionStorage.getItem("name")
            }
        },
        name:"ManagerHome",
        methods:{
            logout() {
                axios.delete("api/user/logout",{data: window.sessionStorage.getItem("token")}).then(resp=>{
                    if(resp){
                        this.$message.success("退出成功!");
                        localStorage.clear();
                        this.$router.replace("/");
                    }
                })
            },
            menuClick(index){
                this.$router.push(index);
            }
        }
    }
</script>

<style>
    .homeHeader{
        background: #409EFF;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 15px;
        box-sizing: border-box;
    }
    .homeHeader .title{
        font-size: 30px;
        font-family: 华文行楷;
        color: white;
    }
</style>